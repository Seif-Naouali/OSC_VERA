package com.example.android.competion.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bestScoreVitesseList {

    @SerializedName("bestScoreVitesse")
    @Expose
    private List<leaderboardModel2> bestScoreVitesse = null;

    public List<leaderboardModel2> getBestScoreVitesse() {
        return bestScoreVitesse;
    }

    public void setBestScoreVitesse(List<leaderboardModel2> bestScoreVitesse) {
        this.bestScoreVitesse = bestScoreVitesse;
    }
    public int list_size_vitesse()
    {
        return bestScoreVitesse.size();
    }

}