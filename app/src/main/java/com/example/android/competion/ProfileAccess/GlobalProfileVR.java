package com.example.android.competion.ProfileAccess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.android.competion.Interfaces.Api;
import com.example.android.competion.OnAccessProfile.Discussion.chat_room;
import com.example.android.competion.R;
import com.example.android.competion.models.Example;
import com.example.android.competion.models.Example4;
import com.example.android.competion.models.Goal;
import com.example.android.competion.models.ListDist;
import com.example.android.competion.models.bestScoreVitesseList;
import com.example.android.competion.models.bestScoredistanceList;
import com.example.android.competion.models.leader_board_model;
import com.example.android.competion.models.leaderboardModel2;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;
import devlight.io.library.ntb.NavigationTabBar;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAIF on 09.08.2019.
 */
public class GlobalProfileVR extends AppCompatActivity {
    private int[] layouts;
    private int i = 0;

    //total distance calorie and heart beat today
    private int value_today_dis = 0, value_today_calorie = 0, value_today_heart = 0, value_today_speed = 0;
    //total distance calorie and heart beat this week
    private int value_thisweek_dis = 0, value_thisweek_calorie = 0, value_thisweek_heart = 0, value_thisweek_speed = 0;

    //total distance calorie and heart beat this month
    private int value_thismonth_dis = 0, value_thismonth_calorie = 0, value_thismonth_heart = 0, value_thismonth_speed = 0;

    //networking ref ;
    private Retrofit retrofit;
    private Api api;

    //gettring the email ref & image_user_ref
    String profile_email_from_pref, profile_img_user_ref;


    //Bar entry list
//int b for test ingoals
    int b = 0;
    TextView binder_to_add_goal; //handling add goal ;
    //for acheived goals and stacked goals
    RecyclerView recyclerView, recyclerView2;

    //the goal active state boolean
    Boolean active_state = false;
    //ref for stacked list item ;
    List<Goal> goals_stacked;
    List<Goal> goalsacheivedlist;


    //golobal values for actif goal
    TextView v1_actif_distance_goal, v2_actif_calories_goal, v3_actif_days_goal;


    //barchart
    BarChart barChart;

    //linechart
    LineChart lineChart;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_up);
        setContentView(R.layout.activity_main);
        initUI();
//récuperation des valeurs totaux

        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        api = retrofit.create(Api.class);
        //here we handle the code of backend
        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        profile_email_from_pref = sharedpreferences.getString("user_profile_address", "test@esprit.tn");
        profile_img_user_ref = sharedpreferences.getString("user_profile_img_ref", "test@esprit.tn");

        api.CalculTotalTodayByEmail(profile_email_from_pref).enqueue(new Callback<Example4>() {
            @Override
            public void onResponse(Call<Example4> call, Response<Example4> response) {
                Log.d("ppppp", "onResponse: " + response.body());
                try {
                    value_today_dis = response.body().getTotalDistanceByDate();
                    value_today_dis = response.body().getTotalDistanceByDate();
                    value_today_calorie = response.body().getTotalCaloriesByDate();
                    value_today_heart = response.body().getTotalHeartBeatByDate();
                    value_today_speed = response.body().getTotalVitesseByDate();
                } catch (Exception ex) {

                }

            }

            @Override
            public void onFailure(Call<Example4> call, Throwable t) {

            }
        });
        api.CalculTotalthisweekByEmail(profile_email_from_pref).enqueue(new Callback<Example4>() {
            @Override
            public void onResponse(Call<Example4> call, Response<Example4> response) {
                try {
                    value_thisweek_dis = response.body().getTotalDistanceByDate();
                    value_thisweek_calorie = response.body().getTotalCaloriesByDate();
                    value_thisweek_heart = response.body().getTotalHeartBeatByDate();
                    value_thisweek_speed = response.body().getTotalVitesseByDate();
                } catch (Exception ex) {

                }


            }

            @Override
            public void onFailure(Call<Example4> call, Throwable t) {

            }
        });

        api.CalculTotalthismonthByEmail(profile_email_from_pref).enqueue(new Callback<Example4>() {
            @Override
            public void onResponse(Call<Example4> call, Response<Example4> response) {

                try {
                    value_thismonth_dis = response.body().getTotalDistanceByDate();
                    value_thismonth_calorie = response.body().getTotalCaloriesByDate();
                    value_thismonth_heart = response.body().getTotalHeartBeatByDate();
                    value_thisweek_speed = response.body().getTotalVitesseByDate();
                } catch (Exception ex) {

                }
            }

            @Override
            public void onFailure(Call<Example4> call, Throwable t) {

            }
        });

//cheking existance of item in actif  base ;
        checkActifGoalsExist();

//importing from db on oncreate
        //adapting a list of stacked goals

        goals_stacked = new ArrayList<Goal>();
        DatabaseReference ref_stacked_goals = FirebaseDatabase.getInstance().getReference().child("Stacked_Goals");

        ref_stacked_goals.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateList_of_stacked_goals(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateList_of_stacked_goals(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void initUI() {
        layouts = new int[]{
                R.layout.layout_home_item,
                R.layout.players_leaderboard_item,
                R.layout.instant_discussion_item,
                R.layout.goals_layout_item

        };

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);

            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(
                        getBaseContext()).inflate(layouts[position], null, false);
                if (position == 3) {//handling add Goal ACTION
                    binder_to_add_goal = view.findViewById(R.id.binder_to_add_goal);
                    CircleProgressBar goal_prog = view.findViewById(R.id.goal_prog);
                    goal_prog.setProgress(50);
                    binder_to_add_goal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (b % 2 == 0) {
                                LinearLayout Goals_details_under_creation = view.findViewById(R.id.Goals_details_under_creation);
                                Goals_details_under_creation.setVisibility(View.VISIBLE);
                                binder_to_add_goal.setText(getResources().getString(R.string.add_goals2));
                                b++;
                            } else {
                                LinearLayout Goals_details_under_creation = view.findViewById(R.id.Goals_details_under_creation);
                                Goals_details_under_creation.setVisibility(View.GONE);
                                binder_to_add_goal.setText(getResources().getString(R.string.add_goals1));
                                b++;

                            }

                        }
                    });
                    //End  of handling add Goal ACTION
                    //here we handle the add of goal ;


                    TextInputEditText Distance_goal_val = view.findViewById(R.id.Distance_goal_value);
                    TextInputEditText Calories_goal_val = view.findViewById(R.id.Calories_goal_value);
                    TextInputEditText Days_goal_val = view.findViewById(R.id.Date_of_acheivement_goal_value);
                    Button save_goal = view.findViewById(R.id.save_btn_goal);
                    save_goal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkActifGoalsExist();
                            save_goal.setBackgroundColor(getResources().getColor(R.color.home_btn_selected));
                            View vo = view.findViewById(R.id.line_shutter2);
                            vo.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
                            if (count != 0) {
                                Log.d("ffunct", "onClick: " + "there is a goal");
                                Goal inserted_Goal = new Goal(Distance_goal_val.getText().toString(), Calories_goal_val.getText().toString(), Days_goal_val.getText().toString(), "saif");
                                //adding a new stacked goals
                                DatabaseReference Instance2 = FirebaseDatabase.getInstance().getReference();
                                Instance2.child("Stacked_Goals").push().setValue(inserted_Goal);
                            } else {
                                Log.d("ffunct", "onClick: " + "Yes we can add!");
                                Goal inserted_Goal = new Goal(Distance_goal_val.getText().toString(), Calories_goal_val.getText().toString(), Days_goal_val.getText().toString(), "saif");
                                //adding a new actif goal
                                DatabaseReference Instance = FirebaseDatabase.getInstance().getReference();
                                Instance.child("Active_Goal").push().setValue(inserted_Goal);

                            }

                            //set the initial state
                            Distance_goal_val.setText("");
                            Calories_goal_val.setText("");
                            Days_goal_val.setText("");
                            LinearLayout Goals_details_under_creation = view.findViewById(R.id.Goals_details_under_creation);
                            Goals_details_under_creation.setVisibility(View.GONE);

                        }
                    });


                    //adapting a list into Recyler ;
                    goalsacheivedlist = new ArrayList<Goal>();
                    //default acheived to prevent erros
                    goalsacheivedlist.add(new Goal("0", "0", "0"));


                    recyclerView = view.findViewById(R.id.recycler_view_for_acheived_goals);
                    Goals_adapter goals_adapter = new Goals_adapter(goalsacheivedlist);
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(goals_adapter);


                    recyclerView2 = view.findViewById(R.id.recycler_view_for_future_goals);
                    Goals_adapter goals_adapter2 = new Goals_adapter(goals_stacked);
                    RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView2.setLayoutManager(manager2);
                    recyclerView2.setAdapter(goals_adapter2);


// here we set the layout of actif goal // and we handle the settings for this goal;
                    v1_actif_distance_goal = view.findViewById(R.id.Distance_on_goal);
                    v2_actif_calories_goal = view.findViewById(R.id.Calorie_on_goal);
                    v3_actif_days_goal = view.findViewById(R.id.day_ongoal);
                    v1_actif_distance_goal.setText(actif_goal.getDistance());
                    v2_actif_calories_goal.setText(actif_goal.getCalorieNumber());
                    v3_actif_days_goal.setText(actif_goal.getDays_number());


                }

                if (position == 2) {

                }
                if (position == 1) {//Leaderboard Treatements ;
                    //ref to two buttons ;
                    View view2__leaderboard = view.findViewById(R.id.view3_leaderboard);
                    Button speed_btn_leaderboard = view.findViewById(R.id.speed_btn_selected);
                    View view1__leaderboard = view.findViewById(R.id.view1__leaderboard);
                    Button distance_btn_leaderboard = view.findViewById(R.id.distance_btn_leaderboard);

                    distance_btn_leaderboard.setOnClickListener(v -> {
                        TextView Value_classification = view.findViewById(R.id.Value_classification);
                        Value_classification.setText("Distance");
                        view2__leaderboard.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
                        speed_btn_leaderboard.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
                        distance_btn_leaderboard.setBackgroundColor(getResources().getColor(R.color.home_btn_selected));
                        view1__leaderboard.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
                        api.BestScoreDistanceMobile().enqueue(new Callback<bestScoredistanceList>() {
                            @Override
                            public void onResponse(Call<bestScoredistanceList> call, Response<bestScoredistanceList> response) {
                                CircleImageView first_ranking, second_ranking, third_ranking, fourth_ranking, fifth_ranking;
                                TextView name_ranked_first, name_ranked_second, name_ranked_third, name_ranked_fourth, name_ranked_fifth;
                                TextView distance_val_rank_1, distance_val_rank_2, distance_val_rank_3, distance_val_rank_4, distance_val_rank_5;
                                first_ranking = view.findViewById(R.id.first_rank_img);
                                second_ranking = view.findViewById(R.id.sec_rank_img);
                                third_ranking = view.findViewById(R.id.third_rank_pic);
                                fourth_ranking = view.findViewById(R.id.Fourth_ranking_img);
                                fifth_ranking = view.findViewById(R.id.Fifth_ranking_img);

                                name_ranked_first = view.findViewById(R.id.First_rank_name);
                                name_ranked_second = view.findViewById(R.id.Second_rank_name);
                                name_ranked_third = view.findViewById(R.id.Third_rank_name);
                                name_ranked_fourth = view.findViewById(R.id.fourth_rank_name);
                                name_ranked_fifth = view.findViewById(R.id.fifth_rank_name);


                                distance_val_rank_1 = view.findViewById(R.id.First_rank_value);
                                distance_val_rank_2 = view.findViewById(R.id.Second_rank_value);
                                distance_val_rank_3 = view.findViewById(R.id.Third_rank_value);
                                distance_val_rank_4 = view.findViewById(R.id.value_of_4);
                                distance_val_rank_5 = view.findViewById(R.id.value_of_5);


                                //adding content from database
                                List<leader_board_model> list_of_first_rank_by_dst = response.body().getBestScoreDistance();


                                Picasso.get().load(list_of_first_rank_by_dst.get(0).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(first_ranking);
                                Picasso.get().load(list_of_first_rank_by_dst.get(1).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(second_ranking);
                                Picasso.get().load(list_of_first_rank_by_dst.get(2).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(third_ranking);
                                Picasso.get().load(list_of_first_rank_by_dst.get(3).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(fourth_ranking);
                                //Picasso.get().load(list_of_first_rank_by_dst.get(4).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(fifth_ranking);

                                name_ranked_first.setText(list_of_first_rank_by_dst.get(0).getNomuser());
                                name_ranked_second.setText(list_of_first_rank_by_dst.get(1).getNomuser());
                                name_ranked_third.setText(list_of_first_rank_by_dst.get(2).getNomuser());
                                name_ranked_fourth.setText(list_of_first_rank_by_dst.get(3).getNomuser());

                                distance_val_rank_1.setText(list_of_first_rank_by_dst.get(0).getDistance() + " km/h");
                                distance_val_rank_2.setText(list_of_first_rank_by_dst.get(1).getDistance() + " km/h");
                                distance_val_rank_3.setText(list_of_first_rank_by_dst.get(2).getDistance() + " km/h");
                                distance_val_rank_4.setText(list_of_first_rank_by_dst.get(3).getDistance() + " km/h");


                            }

                            @Override
                            public void onFailure(Call<bestScoredistanceList> call, Throwable t) {

                            }
                        });
                    });


                    //working on speed

                    speed_btn_leaderboard.setOnClickListener(v -> {
                        TextView Value_classification = view.findViewById(R.id.Value_classification);
                        Value_classification.setText("speed");
                        view1__leaderboard.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
                        distance_btn_leaderboard.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
                        speed_btn_leaderboard.setBackgroundColor(getResources().getColor(R.color.home_btn_selected));
                        view2__leaderboard.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
                        api.BestScoreVitesseMobile().enqueue(new Callback<bestScoreVitesseList>() {
                            @Override
                            public void onResponse(Call<bestScoreVitesseList> call, Response<bestScoreVitesseList> response) {
                                CircleImageView first_ranking, second_ranking, third_ranking, fourth_ranking, fifth_ranking;
                                TextView name_ranked_first, name_ranked_second, name_ranked_third, name_ranked_fourth, name_ranked_fifth;
                                TextView vitesse_val_rank_1, vitesse_val_rank_2, vitesse_val_rank_3, vitesse_val_rank_4, vitesse_val_rank_5;
                                first_ranking = view.findViewById(R.id.first_rank_img);
                                second_ranking = view.findViewById(R.id.sec_rank_img);
                                third_ranking = view.findViewById(R.id.third_rank_pic);
                                fourth_ranking = view.findViewById(R.id.Fourth_ranking_img);
                                fifth_ranking = view.findViewById(R.id.Fifth_ranking_img);

                                name_ranked_first = view.findViewById(R.id.First_rank_name);
                                name_ranked_second = view.findViewById(R.id.Second_rank_name);
                                name_ranked_third = view.findViewById(R.id.Third_rank_name);
                                name_ranked_fourth = view.findViewById(R.id.fourth_rank_name);
                                name_ranked_fifth = view.findViewById(R.id.fifth_rank_name);


                                vitesse_val_rank_1 = view.findViewById(R.id.First_rank_value);
                                vitesse_val_rank_2 = view.findViewById(R.id.Second_rank_value);
                                vitesse_val_rank_3 = view.findViewById(R.id.Third_rank_value);
                                vitesse_val_rank_4 = view.findViewById(R.id.value_of_4);
                                vitesse_val_rank_5 = view.findViewById(R.id.value_of_5);


                                //adding content from database
                                List<leaderboardModel2> list_of_first_rank_by_speed = response.body().getBestScoreVitesse();


                                Picasso.get().load(list_of_first_rank_by_speed.get(0).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(first_ranking);
                                Picasso.get().load(list_of_first_rank_by_speed.get(1).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(second_ranking);
                                Picasso.get().load(list_of_first_rank_by_speed.get(2).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(third_ranking);
                                Picasso.get().load(list_of_first_rank_by_speed.get(3).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(fourth_ranking);
                                //Picasso.get().load(list_of_first_rank_by_dst.get(4).getImageProfile()).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(fifth_ranking);

                                name_ranked_first.setText(list_of_first_rank_by_speed.get(0).getNomuser());
                                name_ranked_second.setText(list_of_first_rank_by_speed.get(1).getNomuser());
                                name_ranked_third.setText(list_of_first_rank_by_speed.get(2).getNomuser());
                                name_ranked_fourth.setText(list_of_first_rank_by_speed.get(3).getNomuser());

                                vitesse_val_rank_1.setText(list_of_first_rank_by_speed.get(0).getVitesse() + " cal");
                                vitesse_val_rank_2.setText(list_of_first_rank_by_speed.get(1).getVitesse() + " cal");
                                vitesse_val_rank_3.setText(list_of_first_rank_by_speed.get(2).getVitesse() + " cal");
                                vitesse_val_rank_4.setText(list_of_first_rank_by_speed.get(3).getVitesse() + " cal");


                            }

                            @Override
                            public void onFailure(Call<bestScoreVitesseList> call, Throwable t) {

                            }
                        });
                    });


                }
                if (position == 0) {
                    //setting the profile image of the user
                    CircleImageView img_prof = view.findViewById(R.id.profile_image);
                    Picasso.get().load(profile_img_user_ref).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(img_prof);

                    //heart refrences &  Circluar dashed Configs
                    CircleProgressBar prog = view.findViewById(R.id.heart_chart);
                    TextView heart_value = view.findViewById(R.id.heart_value);

                    //Speeed Refrences
                    CircleProgressBar prog2 = view.findViewById(R.id.speed_chart);
                    TextView speed_value = view.findViewById(R.id.textView3);
                    TextView best_speed_value = view.findViewById(R.id.best_speed_value);


                    //Progress player
                    MaterialProgressBar materialProgressBar = view.findViewById(R.id.player_progress);
                    counter_for_progress(materialProgressBar, 60);

                    //here we treat  the code relative to home befoore mothing it to pos = 0
                    Button btn_today = (Button) view.findViewById(R.id.today_btn);
                    Button btn_this_week = (Button) view.findViewById(R.id.this_week_btn);

                    Button btn_this_mounth = (Button) view.findViewById(R.id.this_mounth_btn);
                    View v1 = (View) view.findViewById(R.id.view1);
                    View v2 = (View) view.findViewById(R.id.view2);
                    View v3 = (View) view.findViewById(R.id.view3);
                    //total distance day/week/month
                    TextView txt = view.findViewById(R.id.total_distance_value);

                    //total Calorie day/week/month
                    TextView txt2 = view.findViewById(R.id.total_bunedCalorie_value);
                    //handling button 1 push
                    btn_today.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            barChart.setVisibility(View.GONE);
                            lineChart.setVisibility(View.GONE);
                            btn_today.setBackgroundColor(getResources().getColor(R.color.home_btn_selected));
                            v1.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
                            btn_this_mounth.setBackgroundColor(getResources().getColor(R.color.bpTransparent));
                            btn_this_week.setBackgroundColor(getResources().getColor(R.color.bpTransparent));
                            v2.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
                            v3.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
                            //modifying distance on day
                            //distance
                            counter_for_distance_and_calorie_and_heart_and_speed(value_today_dis, txt, "km");
                            //calorie
                            counter_for_distance_and_calorie_and_heart_and_speed(value_today_calorie, txt2, "cal");
                            //heart beat
                            counter_for_Circleprogress(prog, value_today_heart);
                            counter_for_distance_and_calorie_and_heart_and_speed(value_today_heart, heart_value, "");
                            //speed part
                            counter_for_Circleprogress(prog2, value_today_speed);
                            counter_for_distance_and_calorie_and_heart_and_speed(value_today_speed, best_speed_value, "km/h");
                        }
                    });


                    //handling button 2 push
                    btn_this_week.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            barChart.setVisibility(View.VISIBLE);
                            lineChart.setVisibility(View.VISIBLE);
                            btn_this_week.setBackgroundColor(getResources().getColor(R.color.home_btn_selected));
                            v2.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
                            btn_today.setBackgroundColor(getResources().getColor(R.color.bpTransparent));
                            btn_this_mounth.setBackgroundColor(getResources().getColor(R.color.bpTransparent));
                            v1.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
                            v3.setBackgroundColor(getResources().getColor(R.color.ap_transparent));

                            //modifying distance by week
                            //distance
                            counter_for_distance_and_calorie_and_heart_and_speed(value_thisweek_dis, txt, "km");
                            //calorie
                            counter_for_distance_and_calorie_and_heart_and_speed(value_thisweek_calorie, txt2, "cal");
                            //heart beat
                            counter_for_Circleprogress(prog, value_thisweek_heart);
                            counter_for_distance_and_calorie_and_heart_and_speed(value_thisweek_heart / 7, heart_value, "");
                            //speed part
                            counter_for_Circleprogress(prog2, value_thisweek_speed);
                            counter_for_distance_and_calorie_and_heart_and_speed(value_thisweek_speed, best_speed_value, "km/h");

                        }
                    });


                    //handling button 3 push
                    btn_this_mounth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            barChart.setVisibility(View.VISIBLE);
                            lineChart.setVisibility(View.VISIBLE);
                            btn_this_mounth.setBackgroundColor(getResources().getColor(R.color.home_btn_selected));
                            v3.setBackgroundColor(getResources().getColor(R.color.avatar_selected));
                            btn_today.setBackgroundColor(getResources().getColor(R.color.bpTransparent));
                            btn_this_week.setBackgroundColor(getResources().getColor(R.color.bpTransparent));
                            v2.setBackgroundColor(getResources().getColor(R.color.ap_transparent));
                            v1.setBackgroundColor(getResources().getColor(R.color.ap_transparent));

                            //modifying distance by week
                            //distance
                            counter_for_distance_and_calorie_and_heart_and_speed(value_thismonth_dis, txt, "km");
                            //calorie
                            counter_for_distance_and_calorie_and_heart_and_speed(value_thismonth_calorie, txt2, "cal");
                            //heart beat
                            counter_for_Circleprogress(prog, value_thismonth_heart);
                            counter_for_distance_and_calorie_and_heart_and_speed(value_thisweek_heart / 31, heart_value, "");
                            //speed part
                            counter_for_Circleprogress(prog2, value_thismonth_speed);
                            counter_for_distance_and_calorie_and_heart_and_speed(value_thismonth_speed, best_speed_value, "km/h");

                        }
                    });

                    // here we introduce the chart section of distance ;
                    //
                    //
                    //this code can handle the barchart creation
                    barChart = (BarChart) view.findViewById(R.id.barchart);
                    //api refrence to extraire values per week ;


                    ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
                    api.fetchGameByDateByEmail(profile_email_from_pref).enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(Call<Example> call, Response<Example> response) {
                            List<ListDist> lst = response.body().getListeDist();
                            Float f = new Float(0f);
                            int size = lst.size();
                            if (size == 0) {
                                entries.add(new BarEntry(0f, 0f));
                                entries.add(new BarEntry(1f, 0f));
                                entries.add(new BarEntry(2f, 0f));
                                entries.add(new BarEntry(3f, 0f));
                                // gap of 2f
                                entries.add(new BarEntry(5f, 0f));
                                entries.add(new BarEntry(6f, 0f));

                            } else {
                                for (int i = 0; i < size; i++) {
                                    entries.add(new BarEntry(i, f.parseFloat(lst.get(i).getDistance())));
                                    //entries.add(new BarEntry(1f, f.parseFloat(lst.get(1).getDistance())));
                                    // entries.add(new BarEntry(2f, f.parseFloat(lst.get(2).getDistance())));
                                    //  entries.add(new BarEntry(3f, f.parseFloat(lst.get(3).getDistance())));
                                    //entries.add(new BarEntry(4f,f.parseFloat(lst.get(4).getDistance())));
                                }
                            }

                            while (size < 7) {
                                ++size;
                                entries.add(new BarEntry(size, 0f));
                            }
                            // gap of 2f
                            //entries.add(new BarEntry(5f, f.parseFloat(lst.get(5).getDistance())));
                            // entries.add(new BarEntry(6f, f.parseFloat(lst.get(6).getDistance())));
                            Log.d("sizedentrance", "instantiateItem: " + entries.size());
                            BarDataSet bardataset = new BarDataSet(entries, "Cells");
                            //hide value on top of dataset
                            bardataset.setDrawValues(false);
                            // allow highlighting for DataSet
                            bardataset.setHighlightEnabled(true);


                            ArrayList<String> labels = new ArrayList<String>();
                            labels.add("Mon");
                            labels.add("Tue");
                            labels.add("Thu");
                            labels.add("Wed");
                            labels.add("Fri");
                            labels.add("Sat");
                            labels.add("Sun");


                            BarData data = new BarData(bardataset);
                            //widht of data
                            data.setBarWidth(0.3f);


                            barChart.setData(data); // set the data and list of lables into chart
                            barChart.setFitBars(true); // make the x-axis fit exactly all bars
                            barChart.invalidate();

                            //adding the labels to axis x and place on bottom
                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                            //setting label color white
                            xAxis.setTextColor(getResources().getColor(R.color.white));
                            //Setting bar to bottom
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


// set the description of the chart
                            barChart.setContentDescription("CHart for Distance");
                            //setting the bar item color and animation
                            bardataset.setGradientColor(getResources().getColor(R.color.home_btn_selected), getResources().getColor(R.color.avatar_selected));
                            barChart.animateY(5000);
                            //setting the chart colors for grid and lines ;
                            barChart.setGridBackgroundColor(getResources().getColor(R.color.ap_transparent));
                            barChart.getXAxis().setGridColor(getResources().getColor(R.color.grid_colors_chart));
                            barChart.getAxisLeft().setGridColor(getResources().getColor(R.color.grid_colors_chart));
                            barChart.getAxisRight().setGridColor(getResources().getColor(R.color.grid_colors_chart));
                            barChart.setDrawValueAboveBar(false);
                            barChart.setClipValuesToContent(false);
                            //hinding the right bar and axis line hided
                            barChart.getAxisRight().setDrawAxisLine(false);
                            barChart.getAxisRight().setDrawLabels(false);
                            barChart.getAxisLeft().setDrawAxisLine(false);
                            //setting values and labels color to white
                            barChart.getAxisLeft().setTextColor(getResources().getColor(R.color.white));

                            barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                                @Override
                                public void onValueSelected(Entry e, Highlight h) {
                                    TextView Value_of_touched_bar = view.findViewById(R.id.parcoured_distance_value);
                                    Value_of_touched_bar.setText(h.getY() + " km");
                                    TextView day_of_touched_bar = view.findViewById(R.id.day_of_touched_bar_of_dst);
                                    BarEntry pe = (BarEntry) e;
                                    day_of_touched_bar.setText(xAxis.getFormattedLabel((int) h.getX()));
                                }

                                @Override
                                public void onNothingSelected() {

                                }
                            });


                        }

                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {
                            Log.d("hmmm", "onResponse: " + "errrrrrr");
                        }
                    });
                    //

                    //entries.add(new BarEntry(0f, 30f));
                    //entries.add(new BarEntry(1f, 80f));
                    //entries.add(new BarEntry(2f, 60f));
                    // entries.add(new BarEntry(3f, 50f));
                    // gap of 2f
                    // entries.add(new BarEntry(5f, 70f));
                    // entries.add(new BarEntry(6f, 60f));


//end of the chart treamtments //

                    //here we introduce the calorie Manipulation par
                    ///
                    /*1)starting by line chart for calories*/


                    lineChart = (LineChart) view.findViewById(R.id.linechart);
                    ArrayList<Entry> entries2 = new ArrayList<>();
                    entries2.add(new Entry(0f, 30f));
                    entries2.add(new Entry(1f, 80f));
                    entries2.add(new Entry(2f, 60f));
                    entries2.add(new Entry(3f, 50f));
                    // gap of 2f
                    entries2.add(new Entry(5f, 70f));
                    entries2.add(new Entry(6f, 60f));

                    LineDataSet linedataset2 = new LineDataSet(entries2, "Cells");
                    //hiding value on top
                    linedataset2.setDrawValues(false);

                    ArrayList<String> labels2 = new ArrayList<String>();
                    labels2.add("Mon");
                    labels2.add("Tue");
                    labels2.add("Thu");
                    labels2.add("Wed");
                    labels2.add("Fri");
                    labels2.add("Sat");
                    labels2.add("Sun");

                    LineData data2 = new LineData(linedataset2);
                    //widht of data

                    lineChart.setData(data2); // set the data and list of lables into chart
                    barChart.invalidate();

                    //adding the labels to axis x and place on bottom
                    XAxis xAxis2 = lineChart.getXAxis();
                    xAxis2.setValueFormatter(new IndexAxisValueFormatter(labels2));
                    //setting axis label on white color and on Bottom
                    xAxis2.setTextColor(getResources().getColor(R.color.white));
                    xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);

// set the description of the chart
                    lineChart.setContentDescription("Chart for Calories");
                    //setting the line item color and animation
                    linedataset2.setGradientColor(getResources().getColor(R.color.home_btn_selected), getResources().getColor(R.color.avatar_selected));
                    lineChart.animateY(5000);
                    //setting the chart colors for grid and lines ;
                    lineChart.setGridBackgroundColor(getResources().getColor(R.color.ap_transparent));
                    lineChart.getXAxis().setGridColor(getResources().getColor(R.color.grid_colors_chart));
                    lineChart.getAxisLeft().setGridColor(getResources().getColor(R.color.grid_colors_chart));
                    lineChart.getAxisRight().setGridColor(getResources().getColor(R.color.grid_colors_chart));
                    lineChart.setClipValuesToContent(false);
                    //hiding the right bar and axis line hided
                    lineChart.getAxisRight().setDrawAxisLine(false);
                    lineChart.getAxisRight().setDrawLabels(false);
                    lineChart.getAxisLeft().setDrawAxisLine(false);
                    //setting values and labels color to white
                    lineChart.getAxisLeft().setTextColor(getResources().getColor(R.color.white));

                    lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            TextView value_of_touched_point = view.findViewById(R.id.consumed_calorie_value);
                            value_of_touched_point.setText(h.getY() + " cal");
                            TextView day_of_touched_point = view.findViewById(R.id.day_of_touched_point_calorie);
                            day_of_touched_point.setText(xAxis2.getFormattedLabel((int) h.getX()));

                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });


                    //here we handle the code of backend
                    SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                    String profile_name_from_pref = sharedpreferences.getString("user_profile_name", "Vr-Playeer");

                    TextView profile_name = view.findViewById(R.id.profile_name);
                    profile_name.setText(profile_name_from_pref);


                    //here we handle the on go profile
                    CircleImageView profile_image = view.findViewById(R.id.profile_image);
                    profile_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(), ConsultProfile.class));
                        }
                    });


                    //end layout home _treatements


                }


                container.addView(view);
                return view;
            }


        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(

                        getResources().

                                getDrawable(R.drawable.barprofile_icon_home),
                        Color.parseColor(colors[0]))
                        .

                                selectedIcon(getResources().

                                        getDrawable(R.drawable.barprofile_icon_home))
                        .

                                title("Home")
                        .

                                badgeTitle("Home")
                        .

                                build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(

                        getResources().

                                getDrawable(R.drawable.leaderbord_icon),
                        Color.parseColor(colors[0]))
                        .

                                title("LeaderBoard")
                        .

                                badgeTitle("LeaderBoard")
                        .

                                build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(

                        getResources().

                                getDrawable(R.drawable.barprofile_icon_chat),
                        Color.parseColor(colors[0]))
                        .

                                selectedIcon(getResources().

                                        getDrawable(R.drawable.barprofile_icon_chat))
                        .

                                title("Discussion")
                        .

                                badgeTitle("Discussion")
                        .

                                build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(

                        getResources().

                                getDrawable(R.drawable.barprofile_icon_alerts),
                        Color.parseColor(colors[0]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .

                                title("Notifications")
                        .

                                badgeTitle("Notifications")
                        .

                                build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset,
                                       final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                // here we can hide badges or make a modification on them
                //navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new

                                             Runnable() {
                                                 @Override
                                                 public void run() {
                                                     for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                                                         final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                                                         navigationTabBar.postDelayed(new Runnable() {
                                                             @Override
                                                             public void run() {
                                                                 model.showBadge();
                                                             }
                                                         }, i * 100);
                                                     }
                                                 }
                                             }, 500);
    }


    //handling req
    public void GoDiscussionVr(View v) {
        startActivity(new Intent(GlobalProfileVR.this, chat_room.class));
    }


    //handling real time counter
    public void counter_for_distance_and_calorie_and_heart_and_speed(
            int distance, TextView txt, String typevalue) {
        Runnable run = new Runnable() {
            @Override
            public void run() {

                while (i < distance) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    txt.post(new Runnable() {
                        @Override
                        public void run() {
                            txt.setText(i + " " + typevalue);
                        }
                    });
                    i++;
                }
            }
        };
        Thread t = new Thread(run);
        t.start();
        i = 0;
    }

    public void counter_for_progress(ProgressBar b, int distance) {
        Runnable run = new Runnable() {
            @Override
            public void run() {

                while (i < distance) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    b.post(new Runnable() {
                        @Override
                        public void run() {
                            b.setProgress(i);
                        }
                    });
                    i++;
                }
            }
        };
        Thread t = new Thread(run);
        t.start();
        i = 0;
    }

    public void counter_for_Circleprogress(CircleProgressBar b, int distance) {
        Runnable run = new Runnable() {
            @Override
            public void run() {

                while (i < distance) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    b.post(new Runnable() {
                        @Override
                        public void run() {
                            b.setProgress(i);
                        }
                    });
                    i++;
                }
            }
        };
        Thread t = new Thread(run);
        t.start();
        i = 0;
    }

    int count = 0;
    Goal actif_goal;
    String j1 = "";

    public void checkActifGoalsExist() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Active_Goal");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.getChildrenCount();
                actif_goal = (Goal) dataSnapshot.getValue(Goal.class);
                j1 = (String) dataSnapshot.getValue(Goal.class).getDistance();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void updateList_of_stacked_goals(DataSnapshot snap) {

        Goal stacked_goal_item = (Goal) snap.getValue(Goal.class);
        goals_stacked.add(stacked_goal_item);


    }

    public void ModifyProfile(View v) {
        startActivity(new Intent(getApplicationContext(), UpdateMyProfile.class));
    }
}



