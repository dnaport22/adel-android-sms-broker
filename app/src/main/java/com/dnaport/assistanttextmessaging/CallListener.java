package com.dnaport.assistanttextmessaging;

import android.telephony.PhoneStateListener;
import android.util.Log;
import android.widget.Toast;

public class CallListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        Log.d("CallListener::", state + "incoming num:" + incomingNumber);

        if (state == 1) {
            String msg = "New Phone Call Event." +
                    "Incoming Number:" + incomingNumber;
            System.out.println(msg);
        }
        
    }
}
