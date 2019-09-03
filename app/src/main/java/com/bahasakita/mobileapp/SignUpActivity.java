package com.bahasakita.mobileapp;


import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignUpActivity extends AppCompatActivity
{
    EditText mail,pswd,name;
    TextView privacyPolicy;
    Button lin,sup;
    TextInputLayout emailInputWrapper, nameInputWrapper, passwordInputWrapper;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sup = findViewById(R.id.sup);
        lin = findViewById(R.id.lin);
        name = findViewById(R.id.name);
        pswd = findViewById(R.id.pswrdd);
        mail = findViewById(R.id.email);
        privacyPolicy=findViewById(R.id.privacyPolicy);
        nameInputWrapper=findViewById(R.id.nameInputWrapper);
        emailInputWrapper = findViewById(R.id.emailInputWrapper);
        passwordInputWrapper=findViewById(R.id.passwordInputWrapper);
        //UNTUK SPAN kalimat privacy policy
        String text = privacyPolicy.getText().toString();

        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsPD1 = new ForegroundColorSpan(Color.parseColor("#000000"));
        ForegroundColorSpan fcsPD2 = new ForegroundColorSpan(Color.parseColor("#000000"));
        ss.setSpan(fcsPD1, 35,53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsPD2, 57,72, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        privacyPolicy.setText(ss);

        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                boolean vName=validateName();
                boolean vEmail = validateEmail();
                boolean vPass = validatePassword();

                if(vName && vEmail && vPass){
                    mAuth.createUserWithEmailAndPassword(mail.getText().toString().trim(), pswd.getText().toString().trim())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Log.d("berhasil", "createUserWithEmail:success");
                                        final FirebaseUser user = mAuth.getCurrentUser();
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener() {
                                                    @Override
                                                    public void onComplete(@NonNull Task task) {
                                                        // Re-enable button

                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(SignUpActivity.this,
                                                                    "Verification email sent to " + user.getEmail(),
                                                                    Toast.LENGTH_SHORT).show();
                                                            Intent it = new Intent(SignUpActivity.this,SignInActivity.class);
                                                            startActivity(it);
                                                        } else {
                                                            Log.e("tes", "sendEmailVerification", task.getException());
                                                            Toast.makeText(SignUpActivity.this,
                                                                    "Failed to send verification email.",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("eror", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });


                }



            }
        });




        lin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(it);
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }


    //FUNCTION FOR EMAIL VALIDATION
    private boolean validateEmail(){
        String emailInput = mail.getText().toString().trim();
        boolean v;
        if(emailInput.isEmpty()){
            emailInputWrapper.setError("Please provide an e-mail address ");
            v=false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            emailInputWrapper.setError("Please enter a valid email address");
            v=false;
        }else{
            emailInputWrapper.setError(null);
            v=true;
        }
        return v;
    }

    private boolean validatePassword(){
        String passwordInput = pswd.getText().toString().trim();
        boolean v;
        if(passwordInput.isEmpty()){
            passwordInputWrapper.setError("Please create a password");
            v=false;
        }else if(passwordInput.length() < 6){
            passwordInputWrapper.setError("Password must be at least 6 characters");
            v=false;
        }else{
            passwordInputWrapper.setError(null);
            v=true;
        }
        return v;
    }

    private boolean validateName(){
        String nameInput = name.getText().toString().trim();
        boolean v;
        if(nameInput.isEmpty()){
            nameInputWrapper.setError("Please enter your full name");
            v=false;
        }else{
            nameInputWrapper.setError(null);
            v=true;
        }
        return v;
    }

}
