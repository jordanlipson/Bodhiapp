package com.munimuni.bodhi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText finalUserEmail, finalUserPass;
    private Button loginButton;
    private TextView changeSignUpText;
    private FirebaseAuth firebaseAuth;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        finalUserEmail = (EditText)findViewById(R.id.enterUser);
        finalUserPass = (EditText)findViewById(R.id.enterPass);
        loginButton = (Button)findViewById(R.id.buttonLogin);
        changeSignUpText = (TextView)findViewById(R.id.clickSignUp);
        forgotPassword = (TextView)findViewById(R.id.forgotPass);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(finalUserEmail.getText().toString(), finalUserPass.getText().toString());
            }
        });

        changeSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PasswordActivity.class));
            }
        });

    }

    private void validate(String userEmail, String userPass){

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkEmailVerification();
                }else{
                    Toast.makeText(LoginActivity.this, "Login Failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(LoginActivity.this, MainActivity.class));

        if (emailflag){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else{
            Toast.makeText(this, "Verify your email.", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
