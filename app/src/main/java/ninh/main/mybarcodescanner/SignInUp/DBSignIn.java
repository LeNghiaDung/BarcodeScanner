package ninh.main.mybarcodescanner.SignInUp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBSignIn extends SQLiteOpenHelper {
    public static final String DB_Name = "User.DB";
    public static final String TABLE_NAME = "User";

    public static final String UserName = "userName";
    public static final String Password = "passWord";


    public DBSignIn(@Nullable Context context) {
        super(context, DB_Name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+UserName+" Text NOT NULL, "+Password+" Text NOT NULL ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
    public boolean insert(String user, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userName", user );
        values.put("passWord", password);

        db.insert(TABLE_NAME, null, values);
        return false;
    }
    public boolean checkUserPassword(String userName, String password){
        SQLiteDatabase db = getWritableDatabase();
        String selection = DBSignIn.UserName + " = ? AND " + DBSignIn.Password + " = ?" ;
        String[] selectionArgs = {userName,password};
        Cursor cursor = db.query(TABLE_NAME,null,selection,selectionArgs,null,null,null);
        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean checkUser(String userName){
        SQLiteDatabase db = getWritableDatabase();
        String selection = DBSignIn.UserName + " = ? " ;
        String[] selectionArgs = {userName};
        Cursor cursor = db.query(TABLE_NAME,null,selection,selectionArgs,null,null,null);
        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }
}
