package com.example.android.competion.Accessibility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.example.android.competion.Interfaces.Api;
import com.example.android.competion.Opening_Screen_application.TipsVrActivity;
import com.example.android.competion.ProfileAccess.GlobalProfileVR;
import com.example.android.competion.ProfileAccess.UpdateMyProfile;
import com.example.android.competion.R;
import com.example.android.competion.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MyViewPagerAdapter_for_sign myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip ,btnNext;

    //utils user
    EditText register_name ,register_mail,register_pass,register_pass_confirmed,dateofbirth;
    TextInputEditText  weight ;
    //gender bts
    Button male_bt,female_bt ;
    View v,v2,v1 ; //ref
    //usefull for image selection
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    de.hdodenhof.circleimageview.CircleImageView avatar_take_photo ;
    StorageReference storageReference;
    FirebaseStorage storage;
    private  String path ;
    private  String user_gender;

//avatars
ImageView avatar1,avatar2,avatar3,avatar4,avatar5,avatar6,avatar_selection ;
    Button    go_to_tips_from_next_3 ;
    @SuppressWarnings("Convert2Lambda")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        //this section used by VRCYCLING designed in order to elimite every time the access tips

      /*  if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }*/

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        viewPager = (ViewPager) findViewById(R.id.view_pager_sign_up);
        viewPager.setOffscreenPageLimit(3);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots_sign_up);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next_step);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.signup_step_1,
                R.layout.signup_step_2,
                R.layout.signup_step_3,
        };

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter_for_sign();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

       /* btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });*/

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });






    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i]= new TextView(this);
            dots[i].setText(i+1+"");
            dots[i].setBackgroundResource(R.drawable.circle);
            dots[i].setTextSize(15);
            dots[i].setPadding(25,15,25,15);
            dots[i].setTextColor(getResources().getColor(R.color.ap_white));
            dotsLayout.addView(dots[i]);

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) dots[i].getLayoutParams();
            mlp.setMargins(5,0,5,0);





        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.ap_black));
            dots[currentPage].setBackgroundResource(R.drawable.circle_2);


    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
       // startActivity(new Intent(SignUpActivity.this, TipsActivity.class));
        //finish();








        register_name = (EditText)   v.findViewById(R.id.username);
        register_mail=(EditText)   v.findViewById(R.id.edit_text_Email);
        register_pass=(EditText)   v.findViewById(R.id.edit_text_password);
        register_pass_confirmed=(EditText)   v.findViewById(R.id.edit_text_confirm_password);
        //verifying credentials insertion
        if(TextUtils.isEmpty(register_name.getText()))
        {
            register_name.setError("Please Insert your name");
        }
        if(TextUtils.isEmpty(register_mail.getText()))
        {
            register_mail.setError("Please Insert your Email Address");

        }
        if(TextUtils.isEmpty(register_pass.getText()))
        {
            register_pass.setError("Please Insert your password");
        }
        if(TextUtils.isEmpty(register_pass_confirmed.getText()))
        {
            register_pass.setError("Please Insert your password");
        }
        //verifying Criteria of used password
        if(register_pass.length() < 6)
        {
            Toast.makeText(SignUpActivity.this, "Please Insert a 6 letters password at least !", Toast.LENGTH_LONG).show();
        }
        //end of verification :: il reste que verifie les passes matching


        //retrofit web service part
        //create a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        Api api = retrofit.create(Api.class);

        api.Registration(register_name.getText().toString(),register_mail.getText().toString(),register_pass.getText().toString(),register_pass.getText().toString(),user_gender,weight.getText().toString(),dateofbirth.getText().toString(),path).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this, "Registred! Please wait to be redericted!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, TipsVrActivity.class));
                    Log.d("RegisterNSuccess", "scs: ");

                }
                else
                    Toast.makeText(SignUpActivity.this, ""+response.message(), Toast.LENGTH_LONG).show();
                Log.d("RegisterNotSuccess", "onFailure: " + response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("RegisterNotPermit", "onFailure: ");
            }
        });



    }


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if(position == 0)
            {
                btnNext.setVisibility(View.VISIBLE);
            }
            else if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                //btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
            } else {
                // still pages are left
                //btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter_for_sign extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter_for_sign() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);

            //Registration here is only Proceeded by looking for the first layout utilities
            if(position == 0)
            {
                v = view ;
            }
            if(position == 1)
            {   v1=view;
                weight = view.findViewById(R.id.weight);
                dateofbirth = view.findViewById(R.id.date_birth_text);
                male_bt = view.findViewById(R.id.bt_male);
                female_bt=view.findViewById(R.id.bt_female);

                male_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_gender ="Male" ;
                        male_bt.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
                        female_bt.setBackgroundColor(getResources().getColor(R.color.inselected_button));
                        //Setting avatrs of males
                        avatar1.setImageResource(R.drawable.icon_profile_one);
                        avatar2.setImageResource(R.drawable.icon_profile_two);
                        avatar3.setImageResource(R.drawable.icon_profile_three);
                        avatar4.setImageResource(R.drawable.icon_profile_four);
                        avatar5.setImageResource(R.drawable.icon_profile_five);
                        avatar6.setImageResource(R.drawable.icon_profile_six);
                    }
                });

                female_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_gender ="Female";
                        female_bt.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
                        male_bt.setBackgroundColor(getResources().getColor(R.color.inselected_button));

                        //Setting avatrs of females
                        avatar1.setImageResource(R.drawable.icon_profile_one_f);
                        avatar2.setImageResource(R.drawable.icon_profile_two_f);
                        avatar3.setImageResource(R.drawable.icon_profile_three_f);
                        avatar4.setImageResource(R.drawable.icon_profile_four_f);
                        avatar5.setImageResource(R.drawable.icon_profile_five_f);
                        avatar6.setImageResource(R.drawable.icon_profile_six_f);
                    }
                });
            }

            if(position == 2)
            {

                v2=view ;
                ultimus(v2);


                //here we handle the  image upload
                avatar_take_photo = view.findViewById(R.id.avatar_take_photo);
                avatar_take_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        storage = FirebaseStorage.getInstance() ;
                        storageReference =storage.getReference();
                        chooseImage();
                    }
                });

                 go_to_tips_from_next_3 = view.findViewById(R.id.go_to_tips);
                 go_to_tips_from_next_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchHomeScreen();
                    }
                });

            }


            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

//setting the date of birth using date picker
    public void PicaDate(View v )
    {
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setYearOptional(true);

        dpb.show();
         final TextInputEditText date_text = findViewById(R.id.date_birth_text) ;
         dpb.addDatePickerDialogHandler(new DatePickerDialogFragment.DatePickerDialogHandler() {
             @Override
             public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {

                 date_text.setText(year+ "-"+monthOfYear+"-"+dayOfMonth);

             }
         });


    }

//Setting for WEIGHT Picker
   /* public void PicaWeight(View v )
    {
        NumberPickerBuilder npb = new NumberPickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setLabelText("LBS.");
        npb.show();
        final TextView weight_txt = findViewById(R.id.weight_text) ;
        npb.addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {
            @Override
            public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {

                weight_txt.setText(number+"");

            }
        });


    }*/


    /***
     * made by Saif 18-08-2109
     */
   //layout 3 handler function
   //here we define some variable of for layout avatar
    public int image_avatar_res_id = -1 ;
    SharedPreferences sharedpreferences ;

   //here we modify the background of item
    public void ultimus(View v2)
    { //first we make a ref for all avattar and we set listners





        Button next_to_tips  = v2.findViewById(R.id.go_to_tips);
        avatar1 = (ImageView) v2.findViewById(R.id.avatar_1);
        avatar2 = (ImageView) v2.findViewById(R.id.avatar_2);
        avatar3 = (ImageView) v2.findViewById(R.id.avatar_3);
        avatar4 = (ImageView) v2.findViewById(R.id.avatar_4);
        avatar5 = (ImageView) v2.findViewById(R.id.avatar_5);
        avatar6 = (ImageView) v2.findViewById(R.id.avatar_6);




        avatar_selection = (ImageView) v2.findViewById(R.id.avatar_take_photo);

        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
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

                next_to_tips.setBackgroundColor(getResources().getColor(R.color.avatar_selected));

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

                next_to_tips.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
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

                next_to_tips.setBackgroundColor(getResources().getColor(R.color.avatar_selected));

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

                next_to_tips.setBackgroundColor(getResources().getColor(R.color.avatar_selected));

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

                next_to_tips.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
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
                next_to_tips.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
            }
        });
        avatar_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_avatar_res_id = 0 ;

            }
        });
        //saving res to shared pref
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("prof_avatar_res",image_avatar_res_id);
        editor.commit();







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
                avatar_take_photo.setImageBitmap(bitmap);
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


            StorageReference ref = storageReference.child("images/"+UUID.randomUUID() );

            //databaseReference.child("StorageRef").child(EmailRef).setValue(nameIMG);
            //   mref.child("Profile").child(userName).setValue(nameIMG);


            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                             go_to_tips_from_next_3.setBackgroundColor(getResources().getColor(R.color.avatar_selected));

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
                            Toast.makeText(SignUpActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
public void signuphider(View v)
{
    hideSoftKeyboard(this);

}




}
