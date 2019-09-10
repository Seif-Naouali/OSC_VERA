package com.example.android.competion.OnAccessProfile.Discussion;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.android.competion.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends ArrayAdapter<Discussion_Item> {
    public myadapter(Context context,  ArrayList<Discussion_Item> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Bulle} object located at this position in the list
        Discussion_Item currentDiscussion = getItem(position);
        //we refer here the user name of connected user in order to verify compatibility
        SharedPreferences sharedpreferences2 = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String profile_name_from_pref= sharedpreferences2.getString("user_profile_name","Vr-Playeer") ;
        if(!currentDiscussion.getSender_name().matches(profile_name_from_pref))
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item2, parent, false);
        }
        else
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }





        //end added  part
        TextView  discussion_sender = (TextView) listItemView.findViewById(R.id.message_sender_name);
        TextView  discussion_body = (TextView) listItemView.findViewById(R.id.message_body_text);
        TextView  discussion_time = (TextView) listItemView.findViewById(R.id.text_message_time);

        //if images slected avatar
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int res_img= sharedpreferences.getInt("prof_avatar_res",-1) ;
        CircleImageView sender_img = (CircleImageView) listItemView.findViewById(R.id.image_message_profile);
        if(res_img != -1)
        {
            sender_img.setImageResource(res_img);
        }



        discussion_sender.setText(currentDiscussion.getSender_name());
        discussion_body.setText(currentDiscussion.getMessage_body());
        discussion_time.setText(currentDiscussion.getMessage_time());
        //if image has url

        Picasso.get().load(currentDiscussion.getSender_img_url()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(sender_img);


        return listItemView;
    }
}