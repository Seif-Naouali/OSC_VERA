package com.example.android.competion.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class leaderboardModel2 {

    @SerializedName("nomuser")
    @Expose
    private String nomuser;
    @SerializedName("vitesse")
    @Expose
    private Integer vitesse;
    @SerializedName("imageProfile")
    @Expose
    private String imageProfile;

    public String getNomuser() {
        return nomuser;
    }

    public void setNomuser(String nomuser) {
        this.nomuser = nomuser;
    }

    public Integer getVitesse() {
        return vitesse;
    }

    public void setVitesse(Integer vitesse) {
        this.vitesse = vitesse;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

}