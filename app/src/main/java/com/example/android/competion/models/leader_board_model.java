package com.example.android.competion.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class leader_board_model {

    @SerializedName("nomuser")
    @Expose
    private String nomuser;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("imageProfile")
    @Expose
    private String imageProfile;

    public String getNomuser() {
        return nomuser;
    }

    public void setNomuser(String nomuser) {
        this.nomuser = nomuser;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

}