package sg.edu.np.week_6_whackamole_3_0;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreViewHolder extends RecyclerView.ViewHolder {
    /* Hint:
        1. This is a customised view holder for the recyclerView list @ levels selection page
     */
    TextView level, levelno, highestscore, score;
    ConstraintLayout constraintLayout;
    private static final String FILENAME = "CustomScoreViewHolder.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    public CustomScoreViewHolder(final View itemView){
        super(itemView);
        level = itemView.findViewById(R.id.levelname);
        levelno = itemView.findViewById(R.id.levelno);
        highestscore = itemView.findViewById(R.id.highestscore);
        score = itemView.findViewById(R.id.intscore);
        constraintLayout = itemView.findViewById(R.id.levellayout);
        /* Hint:
        This method dictates the viewholder contents and links the widget to the objects for manipulation.
         */
    }
}
