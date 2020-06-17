package sg.edu.np.week_6_whackamole_3_0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final String FILENAME = "MyDBHandler.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WhackAMole.db";
    public static final String TABLE_ACCOUNTS = "Accounts";
    public static final String COLUMN_NAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_LEVEL = "Level";
    public static final String COLUMN_SCORE = "Score";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS +
                "(" + COLUMN_NAME + " TEXT," + COLUMN_PASSWORD
                + " TEXT," + COLUMN_LEVEL + " INTEGER,"
                + COLUMN_SCORE + " INTEGER" + ")";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
        Log.v(TAG, "DB Created: " + CREATE_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        onCreate(db);
    }

    public void addUser(UserData userData) {
        ContentValues values = new ContentValues();
        int levelsize = userData.getLevels().size();
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < levelsize; i++) {
            values.put(COLUMN_NAME, userData.getMyUserName());
            values.put(COLUMN_PASSWORD, userData.getMyPassword());
            values.put(COLUMN_LEVEL, userData.getLevels().get(i));
            values.put(COLUMN_SCORE, userData.getScores().get(i));

            db.insert(TABLE_ACCOUNTS, null, values);
        }
        db.close();
        Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
    }

    public UserData findUser(String username) {
        String query = "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE "
                + COLUMN_NAME
                + " = \"" + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserData userData = new UserData();
        Log.v(TAG, FILENAME + ": Find user from database: " + query);

        if (cursor.moveToFirst()) {

            ArrayList<Integer> levelList = new ArrayList<>();
            ArrayList<Integer> scoreList = new ArrayList<>();
            userData.setMyUserName(cursor.getString(0));
            userData.setMyPassword(cursor.getString(1));
            do{
                levelList.add(cursor.getInt(2));
                scoreList.add(cursor.getInt(3));
            }while (cursor.moveToNext());
            userData.setLevels(levelList);
            userData.setScores(scoreList);
            cursor.close();
            Log.v(TAG, FILENAME + ": QueryData: " + userData.getLevels() + userData.getScores());
        } else {
            userData = null;
            Log.v(TAG, FILENAME + ": No data found!");
        }
        db.close();
        return userData;
    }

    public boolean deleteAccount(String username) {
        boolean result = false;

        String query = "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE "
                + COLUMN_NAME + " = \""
                + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        UserData userData = new UserData();
        if (cursor.moveToFirst()) {
            userData.setMyUserName(cursor.getString(0));
            db.delete(TABLE_ACCOUNTS, COLUMN_NAME + " = ?",
                    new String[] { String.valueOf(userData.getMyUserName()) });
            cursor.close();
            result = true;
        }
        Log.v(TAG, FILENAME+ ": Deleting data from Database: " + query);
        db.close();
        return result;
    }
}