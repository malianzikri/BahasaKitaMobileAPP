package com.bahasakita.mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText pswd,email;
    TextView privacyPolicy;
    Button lin,sup;
    TextInputLayout email_layout, password_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        lin =  findViewById(R.id.lin);
        privacyPolicy = findViewById(R.id.privacyPolicy);
        email = findViewById(R.id.email);
        pswd = findViewById(R.id.pswrdd);
        email_layout = findViewById(R.id.emailTextWrapper);
        password_layout = findViewById(R.id.passwordTextWrapper);
        email_layout.setErrorEnabled(true);
        password_layout.setErrorEnabled(true);

        email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email_layout.setError(null);
            }


        });

        pswd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                password_layout.setError(null);
            }


        });





        //UNTUK SPAN kalimat privacy policy
        String text = privacyPolicy.getText().toString();

        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsPD1 = new ForegroundColorSpan(Color.parseColor("#000000"));
        ForegroundColorSpan fcsPD2 = new ForegroundColorSpan(Color.parseColor("#000000"));
        ss.setSpan(fcsPD1, 35,53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsPD2, 57,72, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        privacyPolicy.setText(ss);




        //EVEN HANDLER UNTUK BUTTON SIGN IN
        lin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //validate inputan
                boolean vEmail = validateEmail();
                boolean  vPass = validatePassword();

                //validate login
                String dataEmail = email.getText().toString();
                String dataPass = pswd.getText().toString();

                if(vEmail && vPass){


                    if(!dataEmail.equals("user@mail.com") && !dataPass.equals("123456")){
                        email_layout.setError("Wrong Email");
                        password_layout.setError("Wrong Password");
                    }else if(!dataEmail.equals("user@mail.com")){
                        email_layout.setError("Wrong Email");
                    }else if(!dataPass.equals("123456")){
                        password_layout.setError("Wrong Password");
                    }else{
                        email_layout.setError(null);
                        password_layout.setError(null);
                        Toast.makeText(SignInActivity.this, "SIGN IN SUCCESS", Toast.LENGTH_SHORT).show();
                       /* Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(it);*/
                    }


                }

            }
        });
        //END OF EVENT HANDLER UNTUK BUTTON SIGN IN

        sup = findViewById(R.id.sup);
        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(it);
            }
        });



    }

    //FUNCTION FOR EMAIL VALIDATION
    private boolean validateEmail(){
        String emailInput = email.getText().toString().trim();
        boolean v;
        if(emailInput.isEmpty()){
            email_layout.setError("Please provide an e-mail address ");
            v=false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            email_layout.setError("Please enter a valid email address");
            v=false;
        }else{
            email_layout.setError(null);
            v=true;
        }
        return v;
    }

    private boolean validatePassword(){
        String passwordInput = pswd.getText().toString().trim();
        boolean v;
        if(passwordInput.isEmpty()){
            password_layout.setError("Please enter your password");
            v=false;
        }else if(passwordInput.length() < 6){
            password_layout.setError("Password must be at least 6 characters");
            v=false;
        }else{
            password_layout.setError(null);
            v=true;
        }
        return v;
    }

}
