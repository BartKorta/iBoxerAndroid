package com.example.iboxer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText emailEdit, passwordEdit;
    Button signup;
    private Button signin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        signup=(Button)findViewById(R.id.signup_click);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        emailEdit=(EditText)findViewById(R.id.loginMail);
        passwordEdit=(EditText)findViewById(R.id.loginPassword);
        signin=(Button)findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();

        final Intent GoSignUp = new Intent(Login.this, Signup.class);
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signup.setTextColor(Color.parseColor("#ffffff"));
                startActivity(GoSignUp);
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Singin();
            }
        });
    }
    private void Singin()
    {
        final String email = emailEdit.getText().toString().trim();
        final String password = passwordEdit.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) {
                emailEdit.setError("You can't leave blank space.");
                emailEdit.requestFocus();
            }
            if (password.isEmpty()) {
                passwordEdit.setError("You can't leave blank space.");
                passwordEdit.requestFocus();
            }
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEdit.setError("Input correct e-mail address.");
            emailEdit.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    final Intent GoToMenu = new Intent(Login.this, Menu.class);
                    startActivity(GoToMenu);
                    finish();
                }
                else{
                    Toast.makeText(Login.this,"Failed to login.\nPlease check your credentials!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}




