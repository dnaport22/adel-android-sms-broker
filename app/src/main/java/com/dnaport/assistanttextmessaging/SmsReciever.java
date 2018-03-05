package com.dnaport.assistanttextmessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import org.json.JSONObject;

public class SmsReciever extends BroadcastReceiver {
    private final SmsManager sms = SmsManager.getDefault();

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

                    String[] msgSplit = message.split(" ");

                    JSONObject jsonObject = new MakeRequest().newRequest(message);
                    replyBack(phoneNumber, jsonObject.getString("response"));
                }
            }

        } catch (Exception e) {
            Log.e("SMSReceiver Exception:", e.getMessage());
        }
    }

    private void replyBack(String number, String message) {
        if (message == null) {
            message = "How can I help?";
        }
        sms.sendTextMessage(number, null, message, null, null);
    }
}
