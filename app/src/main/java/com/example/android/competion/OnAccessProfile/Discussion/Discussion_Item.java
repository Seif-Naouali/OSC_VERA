package com.example.android.competion.OnAccessProfile.Discussion;

public class Discussion_Item {

    private  String sender_img_url;
    private String sender_name,message_body,message_time ;
    private int image_res_default =-1 ;
    public Discussion_Item(){}

    public Discussion_Item(String sender_img_url, String sender_name, String message_body, String message_time) {
        this.sender_img_url = sender_img_url;
        this.sender_name = sender_name;
        this.message_body = message_body;
        this.message_time = message_time;
    }

    public Discussion_Item(String sender_name, String message_body, String message_time,int image_res_default) {
        this.image_res_default = image_res_default;
        this.sender_name = sender_name;
        this.message_body = message_body;
        this.message_time = message_time;
    }

    public String getSender_img_url() {
        return sender_img_url;
    }

    public void setSender_img_url(String sender_img_url) {
        this.sender_img_url = sender_img_url;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public int getImage_res_default() {
        return image_res_default;
    }

    public void setImage_res_default(int image_res_default) {
        this.image_res_default = image_res_default;
    }
}

