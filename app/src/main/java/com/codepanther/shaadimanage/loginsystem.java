package com.codepanther.shaadimanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.microedition.khronos.egl.EGLDisplay;

public class loginsystem extends AppCompatActivity {

    CountryCodePicker cpp;
    EditText phoneNumber;
    Button login;
    FirebaseAuth mAuth;
    String PhoneNumber;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Object PhoneAuthCredential;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsystem);

        cpp = (CountryCodePicker) findViewById(R.id.ccp);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        login = (Button) findViewById(R.id.getotp);
        cpp.registerPhoneNumberTextView(phoneNumber);

        mAuth = FirebaseAuth.getInstance();

        final ProgressBar progressBar = findViewById(R.id.progress_bar);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone = phoneNumber.getText().toString();
                String Phone_number = cpp.getFullNumberWithPlus();

                if (!Phone.isEmpty())
                {
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).
                            setPhoneNumber(Phone_number)
                            .setTimeout(60L,TimeUnit.SECONDS)
                            .setActivity(loginsystem.this)
                            .setCallbacks(mCallbacks)
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
                else {
                    Toast.makeText(loginsystem.this, "please enter the OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull com.google.firebase.auth.PhoneAuthCredential phoneAuthCredential)
            {

                signIn(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)

            {

                Toast.makeText(loginsystem.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);



                        progressBar.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        Intent otpIntent = new Intent(loginsystem.this, getOtp.class);
                        otpIntent.putExtra("mobile",cpp.getFullNumberWithPlus().replace("",""));
                        startActivity(otpIntent);

                        otpIntent.putExtra("verificationId", verificationId);




            }
        };

    }

    private void signIn(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)

            {
                if (task.isSuccessful())
                {
                    sendToMain();
                }
                else
                {
                    Toast.makeText(loginsystem.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user  = mAuth.getCurrentUser();
        if (user != null)
        {
            sendToMain();
        }
    }

    private void sendToMain() {

        startActivity(new Intent(loginsystem.this, home.class));
        finish();
    }


}
