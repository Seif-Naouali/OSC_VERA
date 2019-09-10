package com.example.android.competion.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example2 {

    @SerializedName("oldPhoto")
    @Expose
    private List<Object> oldPhoto = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("imageProfile")
    @Expose
    private String imageProfile;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("poids")
    @Expose
    private Integer poids;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<Object> getOldPhoto() {
        return oldPhoto;
    }

    public void setOldPhoto(List<Object> oldPhoto) {
        this.oldPhoto = oldPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPoids() {
        return poids;
    }

    public void setPoids(Integer poids) {
        this.poids = poids;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}