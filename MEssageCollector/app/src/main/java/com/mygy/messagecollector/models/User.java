package com.mygy.messagecollector.models;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class User {
    private String phoneNumber;
    private final String phoneKeyName = "phone-number";
    private final String fileName = "user-data.property";


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void savePhone(Context context){
        Properties properties = new Properties();
        properties.setProperty(phoneKeyName,phoneNumber);
        try(FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE)) {
            properties.store(fos,"user-data");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void readPhone(Context context){
        Properties properties = new Properties();
        try(FileInputStream fis = context.openFileInput(fileName)) {
            properties.load(fis);
            phoneNumber = properties.getProperty(phoneKeyName);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
