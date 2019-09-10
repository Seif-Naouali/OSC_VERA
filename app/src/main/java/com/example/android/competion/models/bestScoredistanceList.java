package com.example.android.competion.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bestScoredistanceList {

    @SerializedName("bestScoreDistance")
    @Expose
    private List<leader_board_model> bestScoreDistance = null;

    public List<leader_board_model> getBestScoreDistance() {
        return bestScoreDistance;
    }

    public void setBestScoreDistance(List<leader_board_model> bestScoreDistance) {
        this.bestScoreDistance = bestScoreDistance;
    }

    public int list_size_distance()
    {
        return bestScoreDistance.size();
    }

}