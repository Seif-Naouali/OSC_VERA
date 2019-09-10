package com.example.android.competion.ProfileAccess;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.competion.R;
import com.example.android.competion.models.Goal;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class Goals_adapter extends RecyclerView.Adapter {
    List<Goal> Goals_list;

    public Goals_adapter(List<Goal> goals_list) {
        Goals_list = goals_list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ongoals_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder)holder;
        Goal data = Goals_list.get(position);
        viewHolder.distance.setText(data.getDistance()+"");
        viewHolder.calorie.setText(data.getCalorieNumber()+"");
        viewHolder.days.setText(data.getDays_number()+"");

    }




    @Override
    public int getItemCount() {
        return Goals_list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView distance,calorie,days;
        LinearLayout parent;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.goal_parent);
            distance = itemView.findViewById(R.id.Distance_on_goal_acheived);
            calorie = itemView.findViewById(R.id.Calorie_ongoal__acheived);
            days = itemView.findViewById(R.id.days_ongoal_acheived);
        }
    }

}