package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private static final String FILENAME = "Main3Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private UserData userData = MainActivity.userdata;
    private MyDBHandler dbHandler;
    private Button loginbutton;
    private ArrayList<Integer> levelList, scoreList;
    private RecyclerView rv;
    private CustomScoreAdaptor adaptor;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        loginbutton = findViewById(R.id.backtologin);
        dbHandler = new MyDBHandler(this, null, null, 1);
        String userName = userData.getMyUserName();

        rv = findViewById(R.id.customscoreview);
        adaptor = new CustomScoreAdaptor(userData, this);
        rv.setAdapter(adaptor);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        Log.v(TAG, FILENAME + ": Show level for User: "+ userName);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginpage = new Intent(Main3Activity.this, MainActivity.class);
                startActivity(loginpage);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String username = userData.getMyUserName();
        UserData newUserData = dbHandler.findUser(username);
        adaptor.scoreList = newUserData.getScores();
        adaptor.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
