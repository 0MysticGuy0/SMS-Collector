package com.mygy.messagecollector.utility;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;

import java.util.ArrayList;

public class PhoneDataHelper {
    private Context context;

    public PhoneDataHelper(Context context) {
        this.context = context;
    }

    public String readSMS(){
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null,null,null);
        ArrayList<String> res = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {

//                System.out.println("=========================");
//                for(int idx=0;idx<cursor.getColumnCount();idx++)
//                {
//                    System.out.println(cursor.getColumnName(idx)+": "+cursor.getString(idx));
//                }
//                System.out.println("=========================");
                sb.append(cursor.getString(2)).append(":\n").append("\t").append(cursor.getString(12))
                        .append(">-=-=-=-=-<");
                //res.add(sb.toString());
                // use msgData
            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
            //res.add("null");
            sb.append("null");
        }
        cursor.close();
        //String[] arr = new String[res.size()];
        //for(int i = 0; i<arr.length;i++ ){
           // arr[i] = res.get(i);
        //}
        return sb.toString();
    }
    public String getPhoneNumber() {

        if (context.checkSelfPermission(READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                context.checkSelfPermission(READ_PHONE_NUMBERS) ==
                        PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(
                READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            // Permission check
            // Create obj of TelephonyManager and ask for current telephone service
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = telephonyManager.getLine1Number();
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-- "+phoneNumber);
            return phoneNumber;
        } else {
            // Ask for permission
           return "noPermissions!";
        }
    }
    public void sendMessage(String phoneNumber, String message){
        SmsManager.getDefault()
                .sendTextMessage(phoneNumber, null, message, null, null);
    }


}
