package com.kerem.realestate_app.Page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerem.realestate_app.R;
import org.w3c.dom.Text;

public class Adverts_Page extends AppCompatActivity {

    TextView tittle, address, m2, propertyType, advertType, description, price;
    ImageView advertImage;

    String tittleData;
    String m2Data;
    String propertyTypeData;
    String advertTypeData;
    String priceData;
    String addressData;
    String imageURLData;
    String descriptionData;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adverts_page);

        tittle          = findViewById(R.id.tittle2_txt);
        address         = findViewById(R.id.a);
        m2              = findViewById(R.id.a5);
        propertyType    = findViewById(R.id.a4);
        advertType      = findViewById(R.id.a3);
        description     = findViewById(R.id.a2);
        price           = findViewById(R.id.price2_txt);
        advertImage     = findViewById(R.id.advertsPage_img);

        tittleData          = getIntent().getStringExtra("tittleData");
        m2Data              = getIntent().getStringExtra("m2Data");
        propertyTypeData    = getIntent().getStringExtra("propertyTypeData");
        advertTypeData      = getIntent().getStringExtra("advertTypeData");
        priceData           = getIntent().getStringExtra("priceData");
        imageURLData        = getIntent().getStringExtra("imageURLData");
        descriptionData     = getIntent().getStringExtra("descriptionData");
        addressData         = getIntent().getStringExtra("addressData");

        tittle          . setText(tittleData);
        address         . setText(addressData);
        m2              . setText(m2Data+" m²");
        propertyType    . setText(propertyTypeData);
        advertType      . setText(advertTypeData);
        price           . setText(priceData+" $");
        description     . setText(descriptionData);

        Glide.with(Adverts_Page.this).load(imageURLData).into(advertImage);
    }
}