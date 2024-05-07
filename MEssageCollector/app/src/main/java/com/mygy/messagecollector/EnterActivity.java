package com.mygy.messagecollector;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mygy.messagecollector.models.User;
import com.mygy.messagecollector.utility.PhoneDataHelper;

public class EnterActivity extends AppCompatActivity {
    private PhoneDataHelper phoneDataHelper;
    private User user;
    EditText phoneNumberET;
    Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        getPermissions();

        initData();

    }

    private void getPhone(){
        String phone = phoneDataHelper.getPhoneNumber();
        if(phone != null){
            user.setPhoneNumber(phone);
            phoneNumberET.setText(phone);
        }
        else{
            Toast.makeText(this, "Ошибка определения! Введите номер вручную",Toast.LENGTH_LONG).show();
        }
    }

    private void initData(){
        user = new User();
        user.readPhone(this);
        phoneDataHelper = new PhoneDataHelper(this);
        phoneNumberET = findViewById(R.id.enter_phoneNumber);
        nextBtn = findViewById(R.id.enter_nextBtn);

        if(user.getPhoneNumber() == null) {
            getPhone();
        }
        else{
            phoneNumberET.setText(user.getPhoneNumber());
            Intent intent= new Intent(this, MainActivity.class);
            MainActivity.user = user;
            startActivity(intent);
        }

        nextBtn.setOnClickListener(v -> {
            String enteredPhone = phoneNumberET.getText().toString();

            if(enteredPhone.length()==0){
                Toast.makeText(this, "Не введен номер телефона!",Toast.LENGTH_SHORT).show();
            }
            else if(user.getPhoneNumber() == null){
                if(!enteredPhone.matches("\\+7\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d")){
                    Toast.makeText(this, "Введенный телефон должен соответствовать формату +70000000000 !!!",Toast.LENGTH_SHORT).show();
                }
                else{
                    user.setPhoneNumber(enteredPhone);
                }
            }
            else{
                if(!user.getPhoneNumber().equals(enteredPhone)){
                    Toast.makeText(this, "Введен не ваш номер телефона!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent= new Intent(this, NumberConfirmActivity.class);
                    NumberConfirmActivity.user = user;
                    startActivity(intent);
                }
            }
        });
    }

    private void getPermissions() {
        //if(ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.RECEIVE_SMS")
        //  == PackageManager.PERMISSION_DENIED) {
        //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        requestPermissions(new String[]{READ_SMS, READ_PHONE_NUMBERS, READ_PHONE_STATE, SEND_SMS}, 100);
        //}
    }
}