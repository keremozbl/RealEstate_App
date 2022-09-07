package com.kerem.realestate_app.Page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerem.realestate_app.R;


public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button loginButton;
    Context context;
    TextView signUp;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    String usernameSave,passwordSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username     =   findViewById(R.id.userName_et);
        password     =   findViewById(R.id.password_et);

        loginButton  =   findViewById(R.id.loginButton_btn);

        signUp       =   findViewById(R.id.signUp_txt);

        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignUp_Page.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passwordSave = password.getText().toString().trim();
                usernameSave = username.getText().toString().trim();

                if( usernameSave.isEmpty() || passwordSave.isEmpty() ){

                    Toast.makeText(MainActivity.this, "Please enter your username and password", Toast.LENGTH_SHORT).show();

                }else{

                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(usernameSave)){

                                String getPassword = snapshot.child(usernameSave).child("password").getValue(String.class);

                                if(getPassword.equals(passwordSave)){

                                    Toast.makeText(MainActivity.this,"Successfully logged in",Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(MainActivity.this, Filter_Page.class);
                                        intent.putExtra("username_data",usernameSave);

                                    startActivity(intent);
                                    finish();

                                }else{ Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_LONG).show(); }

                            }else{ Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_LONG).show(); }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }});
                }
            }
        });
    }
}





























