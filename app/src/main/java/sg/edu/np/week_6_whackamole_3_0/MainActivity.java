package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private TextView enterusername, enterpassword, signup;
    private Button loginbutton;
    public static UserData userdata;
    MyDBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterusername = findViewById(R.id.enterusername);
        enterpassword = findViewById(R.id.enterpassword);
        dbHandler = new MyDBHandler(this, null, null, 1);
        loginbutton = findViewById(R.id.loginbutton);
        signup = findViewById(R.id.clickhere);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = enterusername.getText().toString();
                String password = enterpassword.getText().toString();
                if(!checkUser(username, password)){
                    Log.v(TAG, FILENAME + ": Invalid user!");
                    reset();
                    return;
                }
                Log.v(TAG, FILENAME + ": Logging in with: " + username + ": " + password);
                Intent homepage = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(homepage);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signuppage = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(signuppage);
            }
        });

    }


    protected void onStop(){
        super.onStop();
        finish();
    }

    public boolean checkUser(String userName, String password){
        UserData data = dbHandler.findUser(userName);
        Log.v(TAG, FILENAME + ": Running Checks on Username: " + userName + " and Password: " + password);
        if(data == null){
            Log.v(TAG, FILENAME + ": Invalid username used!");
            Toast.makeText(getApplicationContext(), "Invalid username! Please re-enter again.",
                    Toast.LENGTH_LONG).show();
            reset();
            return false;
        }
        else if(!data.getMyPassword().equals(password)) {
            Log.v(TAG, FILENAME + ": Invalid password used!");
            Toast.makeText(getApplicationContext(), "Invalid password! Please re-enter again.",
                    Toast.LENGTH_LONG).show();
            reset();
            return false;
        }
        else{

            userdata = data;
            return true;

        }

    }

    public void reset(){
        enterusername.setHint("Enter Your Username");
        enterpassword.setHint("Enter Your Password");
    }

}
