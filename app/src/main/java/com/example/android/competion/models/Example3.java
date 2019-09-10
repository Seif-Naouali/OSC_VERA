package com.example.android.competion.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example3 {

    @SerializedName("listeH")
    @Expose
    private List<String> listeH = null;
    @SerializedName("listeM")
    @Expose
    private List<String> listeM = null;
    @SerializedName("listeS")
    @Expose
    private List<String> listeS = null;
    @SerializedName("totalS")
    @Expose
    private String totalS;
    @SerializedName("totalM")
    @Expose
    private String totalM;
    @SerializedName("totalH")
    @Expose
    private String totalH;

    public List<String> getListeH() {
        return listeH;
    }

    public void setListeH(List<String> listeH) {
        this.listeH = listeH;
    }

    public List<String> getListeM() {
        return listeM;
    }

    public void setListeM(List<String> listeM) {
        this.listeM = listeM;
    }

    public List<String> getListeS() {
        return listeS;
    }

    public void setListeS(List<String> listeS) {
        this.listeS = listeS;
    }

    public String getTotalS() {
        return totalS;
    }

    public void setTotalS(String totalS) {
        this.totalS = totalS;
    }

    public String getTotalM() {
        return totalM;
    }

    public void setTotalM(String totalM) {
        this.totalM = totalM;
    }

    public String getTotalH() {
        return totalH;
    }

    public void setTotalH(String totalH) {
        this.totalH = totalH;
    }

}