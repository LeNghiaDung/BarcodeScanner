package ninh.main.mybarcodescanner.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "PRODUCTS";
    public static final String TABLE_NAME_BIN = "PRODUCTS_BIN";

    // Table columns
    public static final String Seri = "_seri";
    public static final String NameProduct = "nameProduct";
    public static final String Quantity = "quantity";
    public static final String Date = "date";

    // Database Information
    static final String DB_NAME = "PRODUCTS.DB";

    // database version
    static final int DB_VERSION = 1;

    public DBManager dbManager;

    // Creating table query

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + Seri
            + " TEXT PRIMARY KEY , " + NameProduct + " TEXT NOT NULL, " + Quantity + " INTEGER NOT NULL,"+Date+" TEXT NOT NULL);";

    private static final String CREATE_TABLE_BIN = "create table " + TABLE_NAME_BIN + "(" + Seri
            + " TEXT PRIMARY KEY , " + NameProduct + " TEXT NOT NULL, " + Quantity + " INTEGER NOT NULL,"+Date+" TEXT NOT NULL);";

    //CONTRUCTOR
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    //TAO BANG PRODUCTS
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_BIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BIN);
        onCreate(db);
    }

    public Cursor getData(String sql, Object o){
        SQLiteDatabase database = getReadableDatabase();
        try {
            return database.rawQuery(sql, null);
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
