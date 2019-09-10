package com.example.android.competion.models;

public class Goal {
    private String Distance;
    private  String CalorieNumber;
    private  String days_number;
    private String goal_admin;
    public Goal(){}

    public Goal(String distance, String calorieNumber, String days_number, String goal_admin) {
        Distance = distance;
        CalorieNumber = calorieNumber;
        this.days_number = days_number;
        this.goal_admin = goal_admin;
    }

    public String getGoal_admin() {
        return goal_admin;
    }

    public void setGoal_admin(String goal_admin) {
        this.goal_admin = goal_admin;
    }

    public Goal(String distance, String calorieNumber, String days_number) {
        Distance = distance;
        CalorieNumber = calorieNumber;
        this.days_number = days_number;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getCalorieNumber() {
        return CalorieNumber;
    }

    public void setCalorieNumber(String calorieNumber) {
        CalorieNumber = calorieNumber;
    }

    public String getDays_number() {
        return days_number;
    }

    public void setDays_number(String days_number) {
        this.days_number = days_number;
    }


}
