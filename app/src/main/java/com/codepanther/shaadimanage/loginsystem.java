package com.codepanther.shaadimanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.regex.Pattern;

import javax.microedition.khronos.egl.EGLDisplay;

public class loginsystem extends AppCompatActivity {

    CountryCodePicker cpp;
    EditText phoneNumber;
    Button login;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsystem);

         cpp =(CountryCodePicker)findViewById(R.id.ccp);
         phoneNumber=(EditText)findViewById(R.id.phone_number);
        login=(Button)findViewById(R.id.getotp);
        cpp.registerPhoneNumberTextView(phoneNumber);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phoneNumber.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(loginsystem.this, "enter mobile", Toast.LENGTH_SHORT).show();

                    return;
                }
                else {

                    Intent intent = new Intent(getApplicationContext(),getOtp.class);
                    intent.putExtra("mobile",cpp.getFullNumberWithPlus().replace("", ""));

                    startActivity(intent);
                }


            }
        });

    }





    }
