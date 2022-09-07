package com.kerem.realestate_app.Page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerem.realestate_app.R;


public class Filter_Page extends AppCompatActivity {

    TextView saleButton, rentButton, postAddButton, userSettingsButton,LogOut;
    TextView location,usernameLogin ;
    ImageView ppImage,click_addPost, click_adverts, click_userSettings, click_logout;

    private Uri mImageUri;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    String locationLogin;
    String nameData;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_page);

        location            = findViewById(R.id.location_txt);
        usernameLogin       = findViewById(R.id.usernameLogin_txt);
        saleButton          = findViewById(R.id.sale_btn);
        postAddButton       = findViewById(R.id.postAdd_btn);
        userSettingsButton  = findViewById(R.id.userSettings_btn);
        ppImage             = findViewById(R.id.pp_img);
        LogOut              = findViewById(R.id.logOut_txt);

        click_addPost       = findViewById(R.id.click_addPost_btn);
        click_adverts       = findViewById(R.id.click_advert_btn);
        click_logout        = findViewById(R.id.click_logout_btn);
        click_userSettings  = findViewById(R.id.click_userSettings_btn);


        click_addPost.setVisibility(View.INVISIBLE);
        click_adverts.setVisibility(View.INVISIBLE);
        click_userSettings.setVisibility(View.INVISIBLE);
        click_logout.setVisibility(View.INVISIBLE);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        click_logout.setVisibility(View.INVISIBLE);
                    }
                },8);
                click_logout.setVisibility(View.VISIBLE);
                Intent intent= new Intent (Filter_Page.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ppImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFileChooser();

            }
        });
                Button();

                    Bundle extras = getIntent().getExtras();
                    nameData = extras.getString("username_data");
                    usernameLogin.setText("Hi  "+nameData);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            locationLogin = snapshot.child("Users").child(nameData).child("location").getValue(String.class);
                            location.setText(locationLogin);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
    }

        public void Button () {

            saleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           click_adverts.setVisibility(View.INVISIBLE);
                        }
                    },8);
                    click_adverts.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(Filter_Page.this, Sale_Page.class);
                    startActivity(intent);
                }
            });

            postAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            click_addPost.setVisibility(View.INVISIBLE);
                        }
                    },8);
                    click_addPost.setVisibility(View.VISIBLE);

                    Intent intent3 = new Intent(Filter_Page.this, PostAdd_Page.class);
                    startActivity(intent3);

                }
            });

            userSettingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            click_userSettings.setVisibility(View.INVISIBLE);
                        }
                    },8);
                    click_userSettings.setVisibility(View.VISIBLE);
                    Intent intent4 = new Intent(Filter_Page.this, UserSettings_Page.class);

                    intent4.putExtra("name_data", String.valueOf(nameData));

                    startActivity(intent4);
                }
            });
        }
        public void openFileChooser(){

            Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1000);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == RESULT_OK && data != null && data.getData()!=null){
            mImageUri = data.getData();

            ppImage.setImageURI(mImageUri);
        }
    }
}