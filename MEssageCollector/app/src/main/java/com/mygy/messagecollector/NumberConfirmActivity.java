package com.mygy.messagecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mygy.messagecollector.models.User;
import com.mygy.messagecollector.utility.CodeGenerator;
import com.mygy.messagecollector.utility.ServerAPIHelper;
import com.mygy.messagecollector.utility.SimpleCodeGenerator;

public class NumberConfirmActivity extends AppCompatActivity {

    private final int TIME_TO_RESEND_CODE = 60;
    public static User user;
    private String correctCode;
    private TextView emailTV;
    private CountDownTimer timer;
    private CodeGenerator codeGenerator;
    private ServerAPIHelper apiHelper;

    private ImageButton homeBtn;
    private Button nextBtn;
    private TextView phoneTV;
    private TextView counterTV;
    private EditText codeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_confirm);

        if(user == null || user.getPhoneNumber()==null) finish();

        initData();
    }

    private void initData(){
        homeBtn = findViewById(R.id.smsCode_homeBtn);
        nextBtn = findViewById(R.id.smsCode_nextBtn);
        phoneTV = findViewById(R.id.smsCode_phoneTV);
        counterTV = findViewById(R.id.smsCode_timerTV);
        codeET = findViewById(R.id.smsCode_SMSCodeET);
        apiHelper = new ServerAPIHelper();

        codeGenerator = new SimpleCodeGenerator();

        homeBtn.setOnClickListener(v-> {
            finish();
        });

        nextBtn.setOnClickListener(v -> {
            if(codeET.getText().toString().equals(correctCode)){
                timer.cancel();
                Intent intent= new Intent(this, MainActivity.class);
                MainActivity.user = user;
                user.savePhone(this);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Неверный код!",Toast.LENGTH_LONG).show();
            }
        });

        phoneTV.setText(user.getPhoneNumber());
        createConfirmationCode();

        startTimer();
    }

    private void createConfirmationCode(){
        correctCode = codeGenerator.generate();
        System.out.println("\n\nGENERATED: "+correctCode+"\n\n");

        apiHelper.sendMessageFromServer(user.getPhoneNumber(),"Код для СМС_Коллектор: "+correctCode, success -> {
            System.out.println("\n-=-=-\nResult sending code:"+success+"\n-=-=-\n");
        });
    }

    private void startTimer(){
        timer = new CountDownTimer(TIME_TO_RESEND_CODE *1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counterTV.setText(Long.toString(millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                createConfirmationCode();
                startTimer();
            }
        };
        timer.start();
    }
}