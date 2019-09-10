package com.example.android.competion.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {


    @SerializedName("liste_dist")
    @Expose
    private List<ListDist> listeDist = null;

    public List<ListDist> getListeDist() {
        return listeDist;
    }

    public void setListeDist(List<ListDist> listeDist) {
        this.listeDist = listeDist;
    }



}