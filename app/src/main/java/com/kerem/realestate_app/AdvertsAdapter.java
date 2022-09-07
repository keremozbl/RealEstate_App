package com.kerem.realestate_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kerem.realestate_app.Page.Adverts_Page;
import com.kerem.realestate_app.Page.PostAdd_Page;

import java.util.ArrayList;


public class AdvertsAdapter extends RecyclerView.Adapter<AdvertsAdapter.myViewHolder> {

    private Context context;
    private ArrayList<Adverts> list;

    public AdvertsAdapter(Context context, ArrayList<Adverts> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_layout,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Adverts adverts = list.get(position);

        Glide.with(context).load(adverts.getImageUrl()).into(holder.imageView);

        holder.tittle.setText(adverts.getTittle());
        holder.address.setText(adverts.getAddress());
        holder.advertType.setText(adverts.getAdvertType());
        holder.propertyType.setText(adverts.getPropertyType());
        holder.price.setText(adverts.getPrice() + " $");
        holder.m2.setText(adverts.getM2() + " m²");

        context = holder.mainLayout.getContext();

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Adverts_Page.class);

                intent.putExtra("tittleData",adverts.getTittle());
                intent.putExtra("addressData",adverts.getAddress());
                intent.putExtra("m2Data",adverts.getM2());
                intent.putExtra("propertyTypeData",adverts.getPropertyType());
                intent.putExtra("advertTypeData",adverts.getAdvertType());
                intent.putExtra("priceData",adverts.getPrice());
                intent.putExtra("imageURLData",adverts.getImageUrl());
                intent.putExtra("descriptionData",adverts.getDescription());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView tittle, advertType, propertyType, price, m2, url, address, description;
        ImageView imageView;
        ConstraintLayout mainLayout;//

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView       = itemView.findViewById(R.id.advert_img);

            //url           = itemView.findViewById(R.id.url_txt);

            address         = itemView.findViewById(R.id.address_txt);
            tittle          = itemView.findViewById(R.id.tittle_txt);
            advertType      = itemView.findViewById(R.id.advertType_txt);
            propertyType    = itemView.findViewById(R.id.propertyType_txt);
            price           = itemView.findViewById(R.id.price_txt);
            m2              = itemView.findViewById(R.id.m2_txt);

            mainLayout=itemView.findViewById(R.id.mainLayout);//

        }
    }
}
