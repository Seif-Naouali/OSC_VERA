package com.example.android.competion.ProfileAccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.example.android.competion.Accessibility.SignUpActivity;
import com.example.android.competion.Interfaces.Api;
import com.example.android.competion.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UpdateMyProfile extends AppCompatActivity {
    TextInputEditText date_text;
    String Gender_updated = null ;
    ImageView avatar1,avatar2,avatar3,avatar4,avatar5,avatar6,avatar_selection ;
    int  image_avatar_res_id;
    String path;
    String user_name_updated ,date_of_birth_updated,weight_updated;

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_profile);





        storageReference = FirebaseStorage.getInstance().getReference();




        Button Update_btn =  findViewById(R.id.update_btn);
        Update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_api();
            }
        });

    }

    public void PickerOfDate(View v)
    {
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setYearOptional(true);

        dpb.show();
        date_text = findViewById(R.id.date_of_birth_modified) ;
        dpb.addDatePickerDialogHandler(new DatePickerDialogFragment.DatePickerDialogHandler() {
            @Override
            public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {

                date_text.setText(year+ "-"+monthOfYear+"-"+dayOfMonth);

            }

        });

        //avatar selections
        avatar1 = (ImageView) findViewById(R.id.avatar_mod_1);
        avatar2 = (ImageView) findViewById(R.id.avatar_mod_2);
        avatar3 = (ImageView) findViewById(R.id.avatar_mod_3);
        avatar4 = (ImageView) findViewById(R.id.avatar_mod_4);
        avatar5 = (ImageView) findViewById(R.id.avatar_mod_5);
        avatar6 = (ImageView) findViewById(R.id.avatar_mod_6);




        avatar_selection = (ImageView) findViewById(R.id.avatar_take_photo_mod);

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_avatar_res_id = avatar1.getId();
                //setting slected back green
                avatar1.setColorFilter(getResources().getColor(R.color.home_btn_selected));
                //setting other back transpaert
                avatar2.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar3.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar4.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar5.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar6.setColorFilter(getResources().getColor(R.color.ap_transparent));

            }
        });
        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_avatar_res_id = avatar2.getId();
                avatar2.setColorFilter(getResources().getColor(R.color.home_btn_selected));

                avatar1.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar3.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar4.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar5.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar6.setColorFilter(getResources().getColor(R.color.ap_transparent));


            }
        });

        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_avatar_res_id = avatar3.getId();
                avatar3.setColorFilter(getResources().getColor(R.color.home_btn_selected));

                avatar1.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar2.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar4.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar5.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar6.setColorFilter(getResources().getColor(R.color.ap_transparent));


            }
        });

        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_avatar_res_id = avatar4.getId();
                avatar4.setColorFilter(getResources().getColor(R.color.home_btn_selected));

                avatar1.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar2.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar3.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar5.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar6.setColorFilter(getResources().getColor(R.color.ap_transparent));



            }
        });

        avatar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_avatar_res_id = avatar5.getId();
                avatar5.setColorFilter(getResources().getColor(R.color.home_btn_selected));



                avatar1.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar2.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar3.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar4.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar6.setColorFilter(getResources().getColor(R.color.ap_transparent));


            }
        });
        avatar6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_avatar_res_id = avatar6.getId();

                avatar6.setColorFilter(getResources().getColor(R.color.home_btn_selected));

                avatar1.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar2.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar3.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar4.setColorFilter(getResources().getColor(R.color.ap_transparent));
                avatar5.setColorFilter(getResources().getColor(R.color.ap_transparent));

            }
        });


        avatar_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });



    }

    public  void  Male_Gender_updated(View v)
    {
        Button BT_Male = findViewById(R.id.bt_male_modified);
        Button BT_Female = findViewById(R.id.bt_female_modified);
        BT_Male.setBackgroundColor(getResources().getColor(R.color.home_btn_selected));
        BT_Female.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
        Gender_updated="Male";

    }

    public  void  Female_Gender_updated(View v)
    {
        Button BT_Female = findViewById(R.id.bt_female_modified);
        Button BT_Male = findViewById(R.id.bt_male_modified);
        BT_Female.setBackgroundColor(getResources().getColor(R.color.home_btn_selected));
        BT_Male.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
        Gender_updated="Female";

    }


    // a speific part to upload profile image that will be uploaded next
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                avatar_selection.setImageBitmap(bitmap);
                uploadImage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID() );

            //databaseReference.child("StorageRef").child(EmailRef).setValue(nameIMG);
            //   mref.child("Profile").child(userName).setValue(nameIMG);


            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateMyProfile.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    path = uri.toString();
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateMyProfile.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    public void update_api ()
    {
        System.out.println("aaaaaaaaaa");
        //create a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        Api api = retrofit.create(Api.class);
        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String id  = sharedpreferences.getString("_id", "0000");
        HashMap<String,String> h1 = new   HashMap<String,String>();

        TextInputEditText u1 =findViewById(R.id.name_modified);
        user_name_updated = u1.getText().toString() ;
        TextInputEditText u2 =findViewById(R.id.date_of_birth_modified);
        date_of_birth_updated = u2.getText().toString() ;
        TextInputEditText u3 =findViewById(R.id.weight_modified);
        weight_updated = u3.getText().toString() ;


        h1.put("name",user_name_updated);
        h1.put("birthday",date_of_birth_updated);
        h1.put("poids",weight_updated);
        h1.put("gender",Gender_updated);
        //h1.put("imageProfile",path);

        api.UpdateProfile(id,h1).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Toast.makeText(UpdateMyProfile.this, "ProfileUpdated", Toast.LENGTH_SHORT).show();
                Log.d("reees", "onResponse: "+response.message()+"&&" +response.body());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("TAG", "onFailure: ", t );
                Toast.makeText(UpdateMyProfile.this, "ProfileUpdated Errrorrr", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
