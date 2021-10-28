package com.example.duan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    TextInputEditText txtUsername,txtFullname,txtEmail,txtPassword;
    Button btnSignup,btnLight,btnDefault,btnDark;;
    TextView txtLogin;
    ProgressBar prBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        addControls();
        addEvents();

    }



    private void addEvents() {


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname,username,password,email;

                fullname = String.valueOf(txtFullname.getText());
                username = String.valueOf(txtUsername.getText());
                password = String.valueOf(txtPassword.getText());
                email = String.valueOf(txtEmail.getText());

                if(!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    prBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "email";
                            field[2] = "username";
                            field[3] = "password";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = email;
                            data[2] = username;
                            data[3] = password;

                            PutData putData = new PutData("http://192.168.1.10/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {

                                    prBar.setVisibility(View.GONE);
                                    String result = putData.getResult();

                                    if(result.equals("Sign Up Success"))
                                    {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(),Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                    }


                                    Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"All Flied required",Toast.LENGTH_LONG).show();
                }
            }
        });


        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
        });
        btnDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

    }

    private void addControls() {

        txtFullname = (TextInputEditText) findViewById(R.id.txtFullname);
        txtEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        txtUsername = (TextInputEditText) findViewById(R.id.txtUsername);
        txtPassword = (TextInputEditText) findViewById(R.id.txtPassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        prBar = (ProgressBar) findViewById(R.id.prBar);
        btnDark = (Button) findViewById(R.id.btnDark);
        btnLight = (Button) findViewById(R.id.btnLight);
        btnDefault = (Button) findViewById(R.id.btnDefault);
    }
}