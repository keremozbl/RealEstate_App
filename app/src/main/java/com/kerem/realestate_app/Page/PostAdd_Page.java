package com.kerem.realestate_app.Page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kerem.realestate_app.Model;
import com.kerem.realestate_app.R;

public class PostAdd_Page extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    Spinner advertTypeSpinner, propertyTypeSpinner;

    EditText addressEt,m2Et,priceEt,descriptionEt, tittleEt;

    ImageView imageAdd;
    private Uri mImageUri;

    ProgressBar progressBar;
    Button saveAdvertButton, uploadAdvertButton;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    String address;
    String m2;
    String price;
    String description;
    String tittle;
    String advertType;
    String propertyType;
    String modelId;
    Task<Void> model1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add_page);

        progressBar         =   findViewById(R.id.progressBar);

        imageAdd            =   findViewById(R.id.imageAdd_img);

        saveAdvertButton    =   findViewById(R.id.saveAdvert_btn);
        uploadAdvertButton  =   findViewById(R.id.uploadAdvert_btn);

        addressEt           =   findViewById(R.id.address_et);
        m2Et                =   findViewById(R.id.m2_et);
        priceEt             =   findViewById(R.id.price_et);
        descriptionEt       =   findViewById(R.id.description_et);
        tittleEt            =   findViewById(R.id.tittle_et);

        advertTypeSpinner   =   findViewById(R.id.advertType_spn);
        propertyTypeSpinner =   findViewById(R.id.category_spn);

        progressBar.setVisibility(View.INVISIBLE);
        uploadAdvertButton.setVisibility(View.INVISIBLE);

        saveAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                address     = addressEt.getText().toString();
                m2          = m2Et.getText().toString();
                price       = priceEt.getText().toString();
                description = descriptionEt.getText().toString();
                tittle      = tittleEt.getText().toString();

                int tittleLength = tittle.length();

                advertType      = advertTypeSpinner.getSelectedItem().toString().trim();
                propertyType    = propertyTypeSpinner.getSelectedItem().toString().trim();

                    if (address.isEmpty() || m2.isEmpty() || price.isEmpty() || description.isEmpty() || tittle.isEmpty()){

                        Toast.makeText(PostAdd_Page.this, "Fields mustn't empty", Toast.LENGTH_SHORT).show();}

                    if ( tittleLength > 20){
                        Toast.makeText(PostAdd_Page.this, "tittle length not be more 20 letter", Toast.LENGTH_SHORT).show();}

                    if (mImageUri == null) {
                        Toast.makeText(PostAdd_Page.this, "Please select image", Toast.LENGTH_SHORT).show();

                    }else{

                       // uploadAdvertButton.setVisibility(View.VISIBLE);
                        databaseReference.child("Adverts").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(snapshot.hasChild(tittle)){

                                    Toast.makeText(PostAdd_Page.this,"This advert is available. Please change tittle",Toast.LENGTH_LONG).show();

                                }else{

                                    uploadAdvertButton.setVisibility(View.INVISIBLE);
                                    if (mImageUri != null){

                                        uploadtoFirebase(mImageUri);  }
                                        Toast.makeText(PostAdd_Page.this, "Advert registration completed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
            }
        });

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*;");
                startActivityForResult(intent,2);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){

            mImageUri = data.getData();
            imageAdd.setImageURI(mImageUri);
        }
    }
        public void uploadtoFirebase(Uri uri){

        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Model model       = new Model(uri.toString());

                            modelId    = databaseReference.child("Image").push().getKey();

                            databaseReference.child("Image").child(modelId).setValue(model);
                            databaseReference.child("Adverts").child(tittle).setValue(model);

                            databaseReference.child("Adverts").child(tittle).child("tittle").setValue(tittle);
                            databaseReference.child("Adverts").child(tittle).child("advertType").setValue(advertType);
                            databaseReference.child("Adverts").child(tittle).child("propertyType").setValue(propertyType);
                            databaseReference.child("Adverts").child(tittle).child("address").setValue(address);
                            databaseReference.child("Adverts").child(tittle).child("m2").setValue(m2);
                            databaseReference.child("Adverts").child(tittle).child("price").setValue(price);
                            databaseReference.child("Adverts").child(tittle).child("description").setValue(description);

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(PostAdd_Page.this, "Upload successfully", Toast.LENGTH_LONG).show();

                            imageAdd.setImageResource(R.drawable.add_photo);
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    progressBar.setVisibility(View.VISIBLE);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(PostAdd_Page.this, "Uploading failed", Toast.LENGTH_LONG).show();
                }
            });
    }
            private String getFileExtension(Uri mUri){

            ContentResolver cr = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(mUri));

        }
}





















