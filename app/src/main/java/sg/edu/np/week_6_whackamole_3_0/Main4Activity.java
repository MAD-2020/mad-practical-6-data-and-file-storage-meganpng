package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Main4Activity extends AppCompatActivity {
    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    TextView scoreTextView;
    int intscore, level;
    DecimalFormat df = new DecimalFormat("#");
    String strscore = df.format(intscore);
    String username;
    Button back;
    ArrayList<Button> buttonList = new ArrayList<>();
    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;
    MyDBHandler myDBHandler = new MyDBHandler(this, null, null, 1);

    private void readyTimer(){
        readyTimer = new CountDownTimer(10000, 1000){
            @Override
            public void onTick(long millisUntilFinished){
                Log.v(TAG, "Ready CountDown!" + (millisUntilFinished/ 1000 + 1));
                Toast.makeText(getApplicationContext(), "Get Ready In " +
                                (millisUntilFinished/1000 + 1) + " seconds",
                        Toast.LENGTH_SHORT).show();
            }

            public void onFinish(){
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO",
                        Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                readyTimer.cancel();
            }
        };
        readyTimer.start();
    }

    private void placeMoleTimer(){
        int interval = 11000 - (level * 1000);
        newMolePlaceTimer = new CountDownTimer(Long.MAX_VALUE, interval){
            @Override
            public void onTick(long millisUntilFinished){
                Log.v(TAG, "New Mole Location!");
                setNewMole();
            }

            public void onFinish(){
                newMolePlaceTimer.start();
            }
        };
        newMolePlaceTimer.start();
    }

    private static final int[] BUTTON_IDS = {
            R.id.topleft, R.id.topmiddle, R.id.topright, R.id.middleleft,
            R.id.middle, R.id.middleright, R.id.bottomleft, R.id.bottommiddle,
            R.id.bottomright
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        back = findViewById(R.id.back);
        scoreTextView = findViewById(R.id.score);
        Intent receiving = getIntent();
        level = receiving.getIntExtra("Level", 0);
        username = receiving.getStringExtra("Username");
        Log.v(TAG, "Current User Score: " + String.valueOf(intscore));
        for(final int id : BUTTON_IDS){
            final Button button = findViewById(id);
            buttonList.add(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCheck(button);
                    setNewMole();
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserScore();
                Intent goback = new Intent(Main4Activity.this, Main3Activity.class);
                goback.putExtra("Username", username);
                startActivity(goback);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }



    private void doCheck(Button checkButton)
    {
        if(checkButton.getText() == "*"){
            intscore += 1;
            strscore = df.format(intscore);
            scoreTextView.setText(strscore);
            Log.v(TAG, "Hit, score added!\n");
            setNewMole();
        }else {
            intscore -= 1;
            strscore = df.format(intscore);
            scoreTextView.setText(strscore);
            Log.v(TAG, "Missed, score deducted!\n");
            setNewMole();
        }
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        int secondLocation = ran.nextInt(9);
        while(secondLocation == randomLocation){
            secondLocation = ran.nextInt(9);
        }
        for(Button button: buttonList){
            button.setText("O");
        }
        buttonList.get(randomLocation).setText("*");

        if(level > 5){
            buttonList.get(secondLocation).setText("*");
        }
    }

    private void updateUserScore()
    {
        newMolePlaceTimer.cancel();
        readyTimer.cancel();
        Log.v(TAG, FILENAME + ": Update User Score...");
        UserData userData = myDBHandler.findUser(username);
        if(userData.getScores().get(level -1) < intscore){
            myDBHandler.deleteAccount(username);
            userData.getScores().set(level - 1, intscore);
            myDBHandler.addUser(userData);
        }
        else{

        }

    }

}
