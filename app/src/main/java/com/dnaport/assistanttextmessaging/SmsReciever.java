package com.dnaport.assistanttextmessaging;

import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONObject;

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
                            JSONObject jsonObject = new MakeRequest().newRequest(query);
                            replyBack(phoneNumber, jsonObject.getString("response"));
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

    private void replyBack(String number, String message) {
        if (message == "null") {
            sms.sendTextMessage(number, null, "How can I help?", null, null);
            AssistantConfig.updateAssistantLogs("How can I help", number);
        } else {
            sms.sendTextMessage(number, null, message, null, null);
            AssistantConfig.updateAssistantLogs(message, number);
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
