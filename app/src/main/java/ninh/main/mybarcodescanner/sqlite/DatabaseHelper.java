package ninh.main.mybarcodescanner.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    //CREATE TABLE && UPGRADE

    // Table Name
    public static final String TABLE_NAME = "PRODUCTS";

    // Table columns
    public static final String Seri = "_seri";
    public static final String NameProduct = "nameProduct";
    public static final String Quantity = "quantity";

    // Database Information
    static final String DB_NAME = "PRODUCTS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + Seri
            + " LONG PRIMARY KEY , " + NameProduct + " TEXT NOT NULL, " + Quantity + " INTEGER NOT NULL);";

    //CONTRUCTOR
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    //TAO BANG PRODUCTS
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
}
