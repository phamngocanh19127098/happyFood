package com.example.duan;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Post extends AppCompatActivity {
    TextInputEditText textInputEditTextFood, textInputEditTextAddress, textInputEditTextDescription, textInputEditTextUsername;
    Button buttonPost;
    TextView textViewPost;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        textInputEditTextFood =findViewById(R.id.fullname);
        textInputEditTextAddress=findViewById(R.id.email);
        textInputEditTextDescription = findViewById(R.id.username);
        textInputEditTextUsername = findViewById(R.id.password);
        buttonPost= findViewById(R.id.buttonSignUp);
        textViewPost= findViewById(R.id.loginText);
        progressBar= findViewById(R.id.progress);
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String food,address,description,username;
                food= String.valueOf(textInputEditTextFood.getText().toString());
                address= String.valueOf(textInputEditTextAddress.getText().toString());
                description= String.valueOf(textInputEditTextDescription.getText().toString());

                username= String.valueOf(textInputEditTextUsername.getText().toString());

                if(!food.equals("")&&!address.equals("")&&!description.equals("")&&!username.equals("")){
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "food";
                            field[1] = "address";
                            field[2] = "description";
                            field[3] = "username";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = food;
                            data[1] = address;
                            data[2]= description;
                            data[3] = username;
                            PutData putData = new PutData("http://192.168.1.6/createapost/post.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("Post Success")){
                                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else Toast.makeText(getApplicationContext(), "All file required",Toast.LENGTH_SHORT).show();

            }
        });

    }
}