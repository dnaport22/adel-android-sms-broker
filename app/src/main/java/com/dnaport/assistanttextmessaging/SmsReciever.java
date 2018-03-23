package com.dnaport.assistanttextmessaging;

import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SmsReciever extends BroadcastReceiver {
    private final SmsManager sms = SmsManager.getDefault();
    private final String trigger = AssistantConfig.getTrigger();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    if (hasNumber(phoneNumber)) {
                        if (hasTrigger(message.toLowerCase())) {
                            String query = getQuery(message).trim();
                            JSONObject jsonObject = new MakeRequest().newRequest(
                                    query, AssistantConfig.doResponseWithAudio());
                            replyBack(phoneNumber, jsonObject.getJSONObject("response"));
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.e("SMSReceiver Exception:", e.getMessage());
        }
    }

    private boolean hasNumber(String number) {
        return AssistantConfig.getUserNumbers().contains(number);
    }

    private String getQuery(String msg) {
        return msg.toLowerCase().replaceAll("\\b" + trigger + "\\b", "");
    }

    private void replyBack(String number, JSONObject response) throws JSONException {
        String message = response.getString("text");
//        if (AssistantConfig.doResponseWithAudio()) {
//            String audio = response.getString("audio");
//            byte[] audioByte = audio.getBytes();
//            convertBytesToFile(audioByte);
//        }

        if (message == "null") {
            sms.sendTextMessage(number, null, "How can I help?", null, null);
        } else {
            ArrayList<String> msgParts = SmsManager.getDefault().divideMessage(message);
            sms.sendMultipartTextMessage(number, null, msgParts, null, null);
        }

    }

    private void convertBytesToFile(byte[] bytearray) {
        try {

            File outputFile = File.createTempFile("file", ".mp3", AssistantConfig.getTempFileSaveDir());
            outputFile.deleteOnExit();
            FileOutputStream fileoutputstream = new FileOutputStream(outputFile);
            fileoutputstream.write(bytearray);
            fileoutputstream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean hasTrigger(String message) {
        Pattern p = Pattern.compile("(.*\\b" + trigger + "\\b)");
        if (p.matcher(message).find()) {
            return true;
        } else {
            return false;
        }
    }
}
