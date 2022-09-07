package com.kerem.realestate_app.Page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerem.realestate_app.R;

public class SignUp_Page extends AppCompatActivity {

    EditText mailSignUp, passwordSignUp, usernameSignUp;
    Button signUpBtn;
    Spinner locationSpinner;

    String usernameSave,passwordSave,locationSave,mailSave;

    private FirebaseAuth yetki;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        yetki = FirebaseAuth.getInstance();

        mailSignUp      = findViewById(R.id.mailSignUp_et);
        passwordSignUp  = findViewById(R.id.passwordSignUp_et);
        usernameSignUp  = findViewById(R.id.userNameSignUp_et);

        locationSpinner = findViewById(R.id.location_spn);

        signUpBtn       = findViewById(R.id.signUpButton_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mailSave        = mailSignUp.getText().toString().trim();
                passwordSave    = passwordSignUp.getText().toString().trim();
                usernameSave    = usernameSignUp.getText().toString().trim();
                locationSave    = locationSpinner.getSelectedItem().toString().trim();

                if (usernameSave.isEmpty() || passwordSave.isEmpty() || mailSave.isEmpty()) {

                   Toast.makeText(SignUp_Page.this, "Fields mustn't empty", Toast.LENGTH_LONG).show();

                }else {

                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(usernameSave)){

                                Toast.makeText(SignUp_Page.this,"Username is already registered",Toast.LENGTH_LONG).show();

                            }else {

                                databaseReference.child("Users").child(usernameSave).child("username").setValue(usernameSave);
                                databaseReference.child("Users").child(usernameSave).child("mail").setValue(mailSave);
                                databaseReference.child("Users").child(usernameSave).child("location").setValue(locationSave);
                                databaseReference.child("Users").child(usernameSave).child("password").setValue(passwordSave);

                                Toast.makeText(SignUp_Page.this, "registry is successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUp_Page.this, MainActivity.class));

                                finish();
                            }
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






















