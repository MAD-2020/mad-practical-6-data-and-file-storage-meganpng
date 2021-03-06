package sg.edu.np.week_6_whackamole_3_0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {

    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private UserData userData;
    private Context context;
    private String username;
    ArrayList<Integer> levelList = new ArrayList<>();
    ArrayList<Integer> scoreList = new ArrayList<>();
    public CustomScoreAdaptor(UserData userdata, Context conText){
        this.userData = userdata;
        this.context = conText;
        username = userdata.getMyUserName();
        levelList = userdata.getLevels();
        scoreList = userdata.getScores();
    }


    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select, parent, false);
        return new CustomScoreViewHolder(v);
    }

    public void onBindViewHolder(CustomScoreViewHolder holder, final int position){
        Log.v(TAG, FILENAME + " Showing level " + levelList.get(position) + " with highest score: " + scoreList.get(position));
        final String level = String.valueOf(levelList.get(position));
        final String score = String.valueOf(scoreList.get(position));
        holder.level.setText("Level: " + level);
        holder.highestscore.setText("Highest Score: " + score);
        Log.v(TAG, FILENAME+ ": Load level " + (position + 1) +" for: " + username);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent levelpage = new Intent(context, Main4Activity.class);
                levelpage.putExtra("Level", levelList.get(position));
                levelpage.putExtra("Username", username);
                context.startActivity(levelpage);

            }
        });

    }

    public int getItemCount(){
        Log.v(TAG, FILENAME +": Getting number of items from ISBN List");
        return levelList.size();
    }
}