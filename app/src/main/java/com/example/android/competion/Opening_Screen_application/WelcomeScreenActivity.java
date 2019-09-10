package com.example.android.competion.Opening_Screen_application;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Slide;
import androidx.transition.TransitionInflater;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.android.competion.Accessibility.SignInActivity;
import com.example.android.competion.Accessibility.SignUpActivity;
import com.example.android.competion.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.tapadoo.alerter.Alerter;

public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Alerter.create(this)
       .setTitle("Welcome")
                .setText("Enjoy your time!")
                .setIcon(R.drawable.alerter_ic_face)
                .setBackgroundColorRes(R.color.colorAccent)
                .show();
        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);

        container.startShimmer(); // If auto-start is set to false


    }




//this funtion to Acces to TIPs Activity

    public void GotoLoginAct(View v)
    {


        startActivity(new Intent(WelcomeScreenActivity.this, SignInActivity.class));
    }


}
