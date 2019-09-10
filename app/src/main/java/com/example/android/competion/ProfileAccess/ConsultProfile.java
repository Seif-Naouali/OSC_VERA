package com.example.android.competion.ProfileAccess;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.android.competion.R;
import com.squareup.picasso.Picasso;

public class ConsultProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_profile);
//Setting the profile Picture
        CircleImageView  profile_img_consulted = findViewById(R.id.img_user_profile);
        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String profile_img_user_ref = sharedpreferences.getString("user_profile_img_ref", "test@esprit.tn");
        Picasso.get().load(profile_img_user_ref).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(profile_img_consulted);




    }
    public void returntoprofile(View view){

        startActivity(new Intent(ConsultProfile.this,GlobalProfileVR.class));


    }
}
