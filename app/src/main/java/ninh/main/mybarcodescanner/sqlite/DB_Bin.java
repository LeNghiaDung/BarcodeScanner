//package ninh.main.mybarcodescanner.sqlite;
//
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DB_Bin extends SQLiteOpenHelper {
//
//    public static final String TABLE_NAME2 = "Bin_PRODUCTS1";
//
//    // Table columns
//    public static final String Seri = "_seri";
//    public static final String NameProduct = "nameProduct";
//    public static final String Quantity = "quantity";
//
//    // database version
//    static final int DB_VERSION = 1;
//
//    private static final String CREATE_TABLE2 = "create table " + TABLE_NAME2 + "(" + Seri
//            + " TEXT PRIMARY KEY , " + NameProduct + " TEXT NOT NULL, " + Quantity + " INTEGER NOT NULL);";
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        super(context, DB_NAME2, null, DB_VERSION);
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//}
