package com.dnaport.assistanttextmessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        CallListener callListener = new CallListener();
        telephonyManager.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
