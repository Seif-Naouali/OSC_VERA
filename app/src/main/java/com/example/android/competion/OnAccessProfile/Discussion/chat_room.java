package com.example.android.competion.OnAccessProfile.Discussion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.competion.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roger.catloadinglibrary.CatLoadingView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class chat_room  extends AppCompatActivity{

    private EditText message_body_sended ;
    private ImageView btn_send;
    private   DatabaseReference mynewref;
    private  String user_sender,currentTime,messagecontent,img_url_of_sender;
    private  String room;
    private Discussion_Item new_bullet;
    public ArrayList<Discussion_Item> post_list ;
    private  ListView listView ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        post_list =new ArrayList<Discussion_Item>();
        message_body_sended = (EditText)findViewById(R.id.message_text_edtx) ;
        btn_send =(ImageView) findViewById(R.id.btn_send);
        //a testpart

         room= "chatroombySaif" ;
         //sender credentials
         //img_url_of_sender = "https://s3.amazonaws.com/user-content.stoplight.io/8987/1541019969018";
        SharedPreferences sharedpreferences2 = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String profile_name_from_pref= sharedpreferences2.getString("user_profile_name","Vr-Playeer") ;
         user_sender = profile_name_from_pref ;


        mynewref = FirebaseDatabase.getInstance().getReference().child(room);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                currentTime = sdf.format(new Date());
                messagecontent = message_body_sended.getText().toString();
                //ref for image ressource avatar from sharedprefs
                SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                int res_img= sharedpreferences.getInt("prof_avatar_res",-1) ;
                if(res_img !=-1)
                {
                    new_bullet = new Discussion_Item(user_sender,messagecontent,currentTime,res_img);
                }
                else{
                    String profile_img_user_ref =sharedpreferences.getString("user_profile_img_ref","test@esprit.tn") ;
                    new_bullet = new Discussion_Item(profile_img_user_ref,user_sender, messagecontent, currentTime) ;
                }
                mynewref.push().setValue(new_bullet);
               // myadapter adp = new myadapter(getApplicationContext(),post_list);
                //listView = findViewById(R.id.mylist);
                //listView.setAdapter(adp);
                //message_body_sended.setText("");
                message_body_sended.setText("");


            }
        });
        myadapter adp = new myadapter(getApplicationContext(),post_list);
        listView = findViewById(R.id.mylist);
        listView.setAdapter(adp);




        mynewref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              append_chat_conversation(dataSnapshot);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    private  void append_chat_conversation(DataSnapshot dataSnapshot)
    {

           Discussion_Item chat_msg = (Discussion_Item)dataSnapshot.getValue(Discussion_Item.class);
           post_list.add(chat_msg);


    }




}