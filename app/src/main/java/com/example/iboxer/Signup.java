package com.example.iboxer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class Signup extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private EditText editNickname, editMail, editPass, editConfPass;
    private CountryCodePicker editCountry;
    private Button register;
    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(this);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        editNickname = (EditText) findViewById(R.id.inputNickname);
        editMail = (EditText) findViewById(R.id.inputEmail);
        editPass = (EditText) findViewById(R.id.inputPassword);
        editConfPass = (EditText) findViewById(R.id.confPassword);

        editCountry = (CountryCodePicker) findViewById(R.id.inputCountry);
    }

    @Override
    public void onClick(View v)
    {
        registerUser();
    }

    private void registerUser()
    {
        final String email = editMail.getText().toString().trim();
        final String nickname = editNickname.getText().toString().trim();
        final String pass = editPass.getText().toString().trim();
        String confPass = editConfPass.getText().toString().trim();

        final String country = editCountry.getSelectedCountryName();


        if(email.isEmpty() || nickname.isEmpty() || pass.isEmpty()  || confPass.isEmpty())
        {
            if(nickname.isEmpty()){
                editNickname.setError("You can't leave blank space.");
                editNickname.requestFocus();
            }
            if(email.isEmpty()){
                editMail.setError("You can't leave blank space.");
                editMail.requestFocus();
            }
            if(pass.isEmpty()){
                editPass.setError("You can't leave blank space.");
                editPass.requestFocus();
            }
            if(confPass.isEmpty()){
                editConfPass.setError("You can't leave blank space.");
                editConfPass.requestFocus();
            }
            return;
        }
        if(pass.compareTo(confPass)!=0)
        {
            editConfPass.setError("Passwords must be identical.");
            editConfPass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editMail.setError("Input correct e-mail address.");
            editMail.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            editPass.setError("Password must be at least 6 characters long.");
            editPass.requestFocus();
            return;
        }
        progress.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User u = new User(nickname,email,pass,country);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        final Intent GoLogin = new Intent(Signup.this, Login.class);
                                        Toast.makeText(Signup.this, "User registered successfully",Toast.LENGTH_LONG).show();
                                        progress.setVisibility(View.INVISIBLE);
                                        startActivity(GoLogin);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(Signup.this, "Failed to register",Toast.LENGTH_LONG).show();
                                        progress.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(Signup.this, "Failed to register",Toast.LENGTH_LONG).show();
                            progress.setVisibility(View.INVISIBLE);
                        }
                    }
                });

    }
}