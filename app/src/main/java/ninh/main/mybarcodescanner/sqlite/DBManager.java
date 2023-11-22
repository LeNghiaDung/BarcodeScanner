package ninh.main.mybarcodescanner.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import ninh.main.mybarcodescanner.Product;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    public SQLiteDatabase database;


    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context); //KHOI TAO DATABASE
        database = dbHelper.getWritableDatabase(); // BAT CHE DO GHI DATABASE
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert( String seri, String name, Integer quantity) {
        ContentValues contentValue = new ContentValues(); //KHOI TAO
        contentValue.put(DatabaseHelper.Seri, seri);
        contentValue.put(DatabaseHelper.NameProduct, name);
        contentValue.put(DatabaseHelper.Quantity, quantity);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        // Luu columns
        String[] columns = new String[] { DatabaseHelper.Seri, DatabaseHelper.NameProduct, DatabaseHelper.Quantity };

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String _seri, String name, Integer quantity) {
        ContentValues contentValues = new ContentValues();
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {_seri+ " "};
        contentValues.put(DatabaseHelper.NameProduct, name);
        contentValues.put(DatabaseHelper.Quantity, quantity);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, selection, selectionArgs);
        return i;
    }

    public void delete(String _seri) {
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {_seri+ " "};
        database.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
    }

    public boolean checkExisted(String seri) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {seri+ " "};
        Cursor data = database.query(DatabaseHelper.TABLE_NAME,null,selection,selectionArgs,null,null,null);

        boolean exists = data != null && data.getCount() > 0;

        // Đóng cursor và kết nối đến cơ sở dữ liệu
        if (data != null) {
            data.close();
        }
        db.close();
        Toast.makeText(context, "CHECK EXISTED " + exists, Toast.LENGTH_SHORT).show();
        return exists;

    }

    public Cursor getAllData(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor data = db.query(DatabaseHelper.TABLE_NAME,null,null,null,null,null,null);
        if (data != null){
            return data;
        }
        return null;
    }
    public Cursor getData(String seri){
        Product product;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {seri+" "};
        Cursor data = db.query(DatabaseHelper.TABLE_NAME,null,selection,selectionArgs,null,null,null);
//        Cursor data = db.rawQuery("SELECT * FROM "+ DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.Seri + " = " + seri,null);
        if (data != null){
            data.moveToNext();
        }
        return data;

    }

//    public Cursor getData(String seri) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String selection = DatabaseHelper.Seri + " = ? ";
//        String[] selectionArgs = {seri + " "};
//
//        try {
//            Cursor data = db.query(DatabaseHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);
//
//            // Make sure there is data and move to the first row
//            if (data != null) {
//                data.moveToFirst();
//            }
//
//            // Do not close the database connection here
//
//            return data;
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Close the database connection if an exception occurs
//            db.close();
//            return null;
//        }
//    }
}
