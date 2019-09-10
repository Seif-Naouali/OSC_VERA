package com.example.android.competion.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example4 {
    @SerializedName("totalVitesseByDate")
    @Expose
    private Integer totalVitesseByDate;
    @SerializedName("listDistanceByDate")
    @Expose
    private List<String> listDistanceByDate = null;
    @SerializedName("totalDistanceByDate")
    @Expose
    private Integer totalDistanceByDate;
    @SerializedName("listCaloriesByDate")
    @Expose
    private List<String> listCaloriesByDate = null;
    @SerializedName("totalCaloriesByDate")
    @Expose
    private Integer totalCaloriesByDate;
    @SerializedName("listHeartBeatByDate")
    @Expose
    private List<Integer> listHeartBeatByDate = null;
    @SerializedName("totalHeartBeatByDate")
    @Expose
    private Integer totalHeartBeatByDate;
    @SerializedName("listScoreByDate")
    @Expose
    private List<Integer> listScoreByDate = null;
    @SerializedName("totalScoreByDate")
    @Expose
    private Integer totalScoreByDate;
    @SerializedName("listTempsByDate")
    @Expose
    private List<String> listTempsByDate = null;
    @SerializedName("listeTempsEnMinutes")
    @Expose
    private List<Integer> listeTempsEnMinutes = null;
    @SerializedName("totalTemps")
    @Expose
    private Integer totalTemps;

    public List<String> getListDistanceByDate() {
        return listDistanceByDate;
    }

    public void setListDistanceByDate(List<String> listDistanceByDate) {
        this.listDistanceByDate = listDistanceByDate;
    }

    public Integer getTotalDistanceByDate() {
        return totalDistanceByDate;
    }

    public void setTotalDistanceByDate(Integer totalDistanceByDate) {
        this.totalDistanceByDate = totalDistanceByDate;
    }

    public List<String> getListCaloriesByDate() {
        return listCaloriesByDate;
    }

    public void setListCaloriesByDate(List<String> listCaloriesByDate) {
        this.listCaloriesByDate = listCaloriesByDate;
    }

    public Integer getTotalCaloriesByDate() {
        return totalCaloriesByDate;
    }

    public void setTotalCaloriesByDate(Integer totalCaloriesByDate) {
        this.totalCaloriesByDate = totalCaloriesByDate;
    }

    public List<Integer> getListHeartBeatByDate() {
        return listHeartBeatByDate;
    }

    public void setListHeartBeatByDate(List<Integer> listHeartBeatByDate) {
        this.listHeartBeatByDate = listHeartBeatByDate;
    }

    public Integer getTotalHeartBeatByDate() {
        return totalHeartBeatByDate;
    }

    public void setTotalHeartBeatByDate(Integer totalHeartBeatByDate) {
        this.totalHeartBeatByDate = totalHeartBeatByDate;
    }

    public List<Integer> getListScoreByDate() {
        return listScoreByDate;
    }

    public void setListScoreByDate(List<Integer> listScoreByDate) {
        this.listScoreByDate = listScoreByDate;
    }

    public Integer getTotalScoreByDate() {
        return totalScoreByDate;
    }

    public void setTotalScoreByDate(Integer totalScoreByDate) {
        this.totalScoreByDate = totalScoreByDate;
    }

    public List<String> getListTempsByDate() {
        return listTempsByDate;
    }

    public void setListTempsByDate(List<String> listTempsByDate) {
        this.listTempsByDate = listTempsByDate;
    }

    public List<Integer> getListeTempsEnMinutes() {
        return listeTempsEnMinutes;
    }

    public void setListeTempsEnMinutes(List<Integer> listeTempsEnMinutes) {
        this.listeTempsEnMinutes = listeTempsEnMinutes;
    }

    public Integer getTotalTemps() {
        return totalTemps;
    }

    public void setTotalTemps(Integer totalTemps) {
        this.totalTemps = totalTemps;
    }

    public Integer getTotalVitesseByDate() {
        return totalVitesseByDate;
    }

    public void setTotalVitesseByDate(Integer totalVitesseByDate) {
        this.totalVitesseByDate = totalVitesseByDate;
    }
}