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
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {


    TextInputEditText txtUsername,txtPassword;
    Button btnLogin,btnSignup,btnLight,btnDefault,btnDark;
    ProgressBar prBar;

    MaterialButtonToggleGroup btg_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControl();
        addEven();


    }

    private void addControl() {
        txtUsername = (TextInputEditText) findViewById(R.id.txtUsername);
        txtPassword = (TextInputEditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        prBar = (ProgressBar) findViewById(R.id.prBar);
        btg_theme = (MaterialButtonToggleGroup) findViewById(R.id.btg_theme);
        btnDark = (Button) findViewById(R.id.btnDark);
        btnLight = (Button) findViewById(R.id.btnLight);
        btnDefault = (Button) findViewById(R.id.btnDefault);
    }

    private void addEven() {


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

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname,username,password,email;

                username = String.valueOf(txtUsername.getText());
                password = String.valueOf(txtPassword.getText());


                if(!username.equals("") && !password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    prBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("http://192.168.1.5/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {

                                    prBar.setVisibility(View.GONE);
                                    String result = putData.getResult();

                                    if(result.equals("Login Success"))
                                    {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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


    }
}