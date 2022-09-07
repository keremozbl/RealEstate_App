package com.kerem.realestate_app.Page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerem.realestate_app.R;

import java.util.HashMap;

public class UserSettings_Page extends AppCompatActivity {

    EditText changeUsername, newPassword, newPassword2;
    Button saveButton;

    String passwordNew, passwordNew2;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings_page);

        Bundle extras = getIntent().getExtras();
        String nameData = extras.getString("name_data");

        newPassword     = findViewById(R.id.newPassword1_et);
        newPassword2    = findViewById(R.id.newPassword2_et);
        saveButton      = findViewById(R.id.SaveButton_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(nameData);

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                passwordNew     = newPassword.getText().toString().trim();
                passwordNew2    = newPassword2.getText().toString().trim();

                if ( !(passwordNew.equals(passwordNew2))){
                    Toast.makeText(UserSettings_Page.this, "the password you entered must same ", Toast.LENGTH_SHORT).show();
                }else{

                    databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String password     = newPassword.getText().toString();

                        HashMap hashMap = new HashMap();
                        hashMap.put("password",password);

                        databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {

                            @Override
                            public void onSuccess(Object o) {

                                Toast.makeText(UserSettings_Page.this, "update successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                }
            }
        });
    }
}