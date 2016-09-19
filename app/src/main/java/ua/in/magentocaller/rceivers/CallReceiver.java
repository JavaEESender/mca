package ua.in.magentocaller.rceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import ua.in.magentocaller.main.AcceptCall;

/**
 * Created by Alexander on 18.09.2016.
 */
public class CallReceiver extends BroadcastReceiver {

    public static boolean isRinging = false;

    public CallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber;
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            String phone_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING) && !isRinging) {
                isRinging = true;
                Log.d("Magento_caller","Calling1");
                phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Thread tr = new Thread(new AcceptCall(phoneNumber, context));
                tr.start();
                Toast toast = Toast.makeText(context, "New Calling" + phoneNumber, Toast.LENGTH_LONG);
                toast.show();
            }else{
                isRinging = false;
            }
        }
    }
}
