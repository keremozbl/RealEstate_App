package com.kerem.realestate_app.Page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerem.realestate_app.Adverts;
import com.kerem.realestate_app.AdvertsAdapter;
import com.kerem.realestate_app.R;


import java.util.ArrayList;
import java.util.List;

public class Sale_Page extends AppCompatActivity {

    RecyclerView recview;
    AdvertsAdapter adapter;
    DatabaseReference databaseReference;
    ImageView logo;

    String Url_image;
    String a = "For Sale";
    String b = "For Rent";
    String radioChoice;
    String propertyType;

    ArrayList<Adverts> list;

    ArrayList<Adverts> sale;
    ArrayList<Adverts> rent;

    Button applyButton;

    RadioGroup radioGroup;
    RadioButton radioButton;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_page);

        applyButton         = findViewById(R.id.apply_btn);
        radioGroup          = findViewById(R.id.radiogroup);
        recview             = findViewById(R.id.recyclerView);
        logo                = findViewById(R.id.logo_img);

        databaseReference   = FirebaseDatabase.getInstance().getReference().child("Adverts");

        recview.setHasFixedSize(true);

        recview.setLayoutManager(new GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false));

        list = new ArrayList<>();
        rent = new ArrayList<>();
        sale = new ArrayList<>();

        adapter = new AdvertsAdapter(getApplicationContext(),list);
        recview.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Adverts adverts = dataSnapshot.getValue(Adverts.class);

                    list.add(adverts);

                        if (adverts.getAdvertType().equals("For Sale")) {
                            sale.add(adverts);
                            adapter.notifyDataSetChanged();
                        }
                        else if (adverts.getAdvertType().equals("For Rent")) {
                            rent.add(adverts);
                            adapter.notifyDataSetChanged();
                        }

                    adapter = new AdvertsAdapter(getApplicationContext(), list);
                    recview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        checkButton();

                if(a.equals(radioChoice)){

                        Toast.makeText(Sale_Page.this, "sale", Toast.LENGTH_SHORT).show();
                        adapter = new AdvertsAdapter(getApplicationContext(),sale);
                        recview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                }
                else if (b.equals(radioChoice)) {

                        Toast.makeText(Sale_Page.this, "rent", Toast.LENGTH_SHORT).show();
                        adapter = new AdvertsAdapter(getApplicationContext(),rent);
                        recview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                }
                else if (radioChoice.equals("All")) {

                        Toast.makeText(Sale_Page.this, "all", Toast.LENGTH_SHORT).show();
                        adapter = new AdvertsAdapter(getApplicationContext(),list);
                        recview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void checkButton() {

        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);

        radioChoice = (String) radioButton.getText();
    }
}



























