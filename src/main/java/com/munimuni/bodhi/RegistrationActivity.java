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

public class RegistrationActivity extends AppCompatActivity {

    private EditText userEmail, userPass;
    private Button createAccountButton;
    private TextView changeLoginText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // The first thing users will see:
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    // Upload data to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPass.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this, "Registration complete!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, PersonalDetailsActivity.class));
                            }else{
                                Toast.makeText(RegistrationActivity.this, "Error. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        changeLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }

    private void setupUIViews() {
        userEmail = (EditText)findViewById(R.id.createUser);
        userPass = (EditText)findViewById(R.id.createPass);
        createAccountButton = (Button)findViewById(R.id.buttonSignUp);
        changeLoginText = (TextView)findViewById(R.id.clickLogin);
    }

    private Boolean validate(){
        boolean result = false;

        String email = userEmail.getText().toString();
        String password = userPass.getText().toString();

        if (email.isEmpty() && password.isEmpty()){
            Toast.makeText(this, "Please enter both your email and password.", Toast.LENGTH_SHORT);
        }else{
            result = true;
        }

        return result;
    }
}
