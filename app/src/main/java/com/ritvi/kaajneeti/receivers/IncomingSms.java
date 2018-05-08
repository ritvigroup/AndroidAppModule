package com.ritvi.kaajneeti.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.ritvi.kaajneeti.Util.StringUtils;

public class IncomingSms extends BroadcastReceiver {
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                    // Show Alert
//                    int duration = Toast.LENGTH_LONG;
//                    Toast toast = Toast.makeText(context,
//                            "senderNum: "+ senderNum + ", message: " + message, duration);
//                    toast.show();
                    passForgotMpinMessage(context, message);
                    parseLoginOTPMessage(context, message);


                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }

    public void parseLoginOTPMessage(Context context,String message){
        Intent intent = new Intent(StringUtils.LOGIN_OTP_CLASS);
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }

    public void passForgotMpinMessage(Context context, String message) {
        Intent intent = new Intent(StringUtils.FORGOT_SMS_CLASS);
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }
}
