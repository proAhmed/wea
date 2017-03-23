package com.wearable.mivors.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wearable.mivors.myapplication.adapter.UserAdapter;
import com.wearable.mivors.myapplication.connector.GetContactByEmail;
import com.wearable.mivors.myapplication.controller.OnLoadingComplete;
import com.wearable.mivors.myapplication.controller.StoreData;
import com.wearable.mivors.myapplication.controller.Utility;
import com.wearable.mivors.myapplication.model.Row;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText edEmail;
    Button btnLogin;
    OnLoadingComplete onLoadingComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!new StoreData(LoginActivity.this).getUserEmail().equals("0")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            edEmail = (EditText) findViewById(R.id.edEmail);
            btnLogin = (Button) findViewById(R.id.btnLogin);

////////////////////////////  enter valid email that is saved in the system
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utility.isNetworkConnected(LoginActivity.this)) {
                        if (edEmail.getText().toString().equals("")) {
                            Toast.makeText(LoginActivity.this, "kindly enter your email", Toast.LENGTH_SHORT).show();
                        } else if (!edEmail.getText().toString().contains("@")) {
                            Toast.makeText(LoginActivity.this, "kindly enter correct email", Toast.LENGTH_SHORT).show();
                        } else {
                            onLoadingComplete = new OnLoadingComplete() {
                                @Override
                                public void onSuccess(Object object) {
                                    final ArrayList<Integer> arrayListss = (ArrayList<Integer>) object;
                                    if(arrayListss.size()>0){
                                        new StoreData(LoginActivity.this).setUserEmail(edEmail.getText().toString());
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Please Enter right mail ", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                        @Override
                                        public void onFailure() {

                                        }
                                    };

                            new GetContactByEmail(LoginActivity.this, onLoadingComplete).execute(edEmail.getText().toString());


                        }
                    }else {
                        Toast.makeText(LoginActivity.this,"Connect to internet",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
