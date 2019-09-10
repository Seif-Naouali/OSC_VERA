package com.example.android.competion.Accessibility;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.competion.ProfileAccess.GlobalProfileVR;
import com.example.android.competion.Interfaces.Api;
import com.example.android.competion.R;
import com.example.android.competion.models.Example;
import com.example.android.competion.models.Example2;
import com.example.android.competion.models.ListDist;
import com.example.android.competion.models.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


        TextInputLayout txt =findViewById(R.id.edit_text_email_container);





    }





    //handling the sign up request
    public void GoToSignUpActivity(View v)
    {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }

    public void SignIN(View v)
    {      //create a retrofit object

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
       Api api = retrofit.create(Api.class);

       //create Refrence to email and pass
        EditText email = (EditText) findViewById(R.id.edit_text_email);
        EditText password = (EditText) findViewById(R.id.edit_text_pass);

        //making test on email and pass
        if(TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText()))
        {       if(TextUtils.isEmpty(email.getText()))
            {
                email.setError("Please Enter your email");
            }
            else
        {
            password.setError("Please Enter verify your password");
        }


        }



        //


        api.LogUser(email.getText().toString(),password.getText().toString()).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if(response.isSuccessful())
                {
                    startActivity(new Intent(SignInActivity.this,GlobalProfileVR.class));
                    Log.i("verification", "onResponse: " + response.body() + " &&& " + response.headers()+ "&&&" +response.message());
                    //testing the method to get user info after login
                    api.GetUserInfoByEmail(email.getText().toString()).enqueue(new Callback<Example2>() {
                        @Override
                        public void onResponse(Call<Example2> call, Response<Example2> response) {


                            Log.d("username", "onResponse: " + response.body().getName());

                            //taking the username after login tentative    ;
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_profile_name",response.body().getName());
                            editor.putString("user_profile_address",response.body().getEmail());
                            editor.putString("user_profile_img_ref",response.body().getImageProfile());
                            editor.putString("_id",response.body().getId());
                            editor.commit();



                        }

                        @Override
                        public void onFailure(Call<Example2> call, Throwable t) {

                        }
                    });
                    //end of testing part




                }
            }
            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "Errrroor", Toast.LENGTH_SHORT).show();
            }
        });



    }
    //signin hider
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void signinhider(View v)
    {
        hideSoftKeyboard(this);

    }


}
