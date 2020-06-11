package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {



    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private EditText username, password;
    private Button sigunpbutton, cancelbutton;
    MyDBHandler dbHandler;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        username = findViewById(R.id.createusername);
        password = findViewById(R.id.createpassword);
        sigunpbutton = findViewById(R.id.createbutton);
        cancelbutton = findViewById(R.id.cancelbutton);
        final ArrayList<Integer> levelList = new ArrayList<>();
        final ArrayList<Integer> scoreList = new ArrayList<>();
        sigunpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signupusername= username.getText().toString();
                String signuppassword = username.getText().toString();
                UserData userData = dbHandler.findUser(signupusername);
                if(signupusername.isEmpty() || signuppassword.isEmpty()){
                    Log.v(TAG, FILENAME + ": Details are not completed.");
                    Toast.makeText(Main2Activity.this, "Please complete all the details.", Toast.LENGTH_SHORT).show();
                }
                else if(userData != null){
                    Log.v(TAG, FILENAME + ": User already exist during new user creation!");
                    Toast.makeText(Main2Activity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                }
                else{
                    UserData data = new UserData();
                    data.setMyUserName(signupusername);
                    data.setMyPassword(signuppassword);
                    for(int i = 1; i <= 10; i++){
                        levelList.add(i);
                        scoreList.add(0);
                    }
                    data.setLevels(levelList);
                    data.setScores(scoreList);
                    dbHandler.addUser(data);
                    MainActivity.userdata = data;
                    Log.v(TAG, FILENAME + ": New user created successfully!");
                    Toast.makeText(Main2Activity.this, "Account created successfully!",
                            Toast.LENGTH_SHORT).show();
                    Intent homepage = new Intent(Main2Activity.this, Main3Activity.class);
                    startActivity(homepage);
                }

            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginpage = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(loginpage);
            }
        });
    }


    protected void onStop() {
        super.onStop();
        finish();
    }
}
