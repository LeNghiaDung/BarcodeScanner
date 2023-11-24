package ninh.main.mybarcodescanner.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void insert( String seri, String name, Integer quantity,String date) {
        ContentValues contentValue = new ContentValues(); //KHOI TAO
        contentValue.put(DatabaseHelper.Seri, seri);
        contentValue.put(DatabaseHelper.NameProduct, name);
        contentValue.put(DatabaseHelper.Quantity, quantity);
        contentValue.put(DatabaseHelper.Date,date);

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public void insert_bin( String seri, String name, Integer quantity,String date) {
        ContentValues contentValue2 = new ContentValues(); //KHOI TAO
        contentValue2.put(DatabaseHelper.Seri, seri);
        contentValue2.put(DatabaseHelper.NameProduct, name);
        contentValue2.put(DatabaseHelper.Quantity, quantity);
        contentValue2.put(DatabaseHelper.Date,date);
        database.insert(DatabaseHelper.TABLE_NAME_BIN, null, contentValue2);
    }

    public int update(String _seri, String name, Integer quantity) {
        ContentValues contentValues = new ContentValues();
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {_seri};
        contentValues.put(DatabaseHelper.NameProduct, name);
        contentValues.put(DatabaseHelper.Quantity, quantity);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm' - 'dd.MM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        contentValues.put(DatabaseHelper.Date,currentDateandTime);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, selection, selectionArgs);
        return i;
    }

    public void delete(String _seri) {
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {_seri};
        database.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
        Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
    }

    public void delete_bin(String _seri) {
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {_seri};
        database.delete(DatabaseHelper.TABLE_NAME_BIN, selection, selectionArgs);
        Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
    }

    public boolean checkExisted(String seri) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {seri+ " "};
        Cursor data = database.query(DatabaseHelper.TABLE_NAME,null,selection,selectionArgs,null,null,null);

        boolean exists = data != null && data.getCount() > 0;

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

    public Cursor getAllData_bin(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor data = db.query(DatabaseHelper.TABLE_NAME_BIN,null,null,null,null,null,null);
        if (data != null ){
            return data;
        }
        return null;
    }
    public Cursor getData(String seri){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {seri};
        Cursor data = db.query(DatabaseHelper.TABLE_NAME,null,selection,selectionArgs,null,null,null);
//        Cursor data = db.rawQuery("SELECT * FROM "+ DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.Seri + " = " + seri,null);
        if (data != null){
            return data;
        }
        return null;
    }

    public Cursor getData_bin(String seri){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {seri};
        Cursor data = db.query(DatabaseHelper.TABLE_NAME_BIN,null,selection,selectionArgs,null,null,null);
//        Cursor data = db.rawQuery("SELECT * FROM "+ DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.Seri + " = " + seri,null);
        if (data != null){
            return data;
        }
        return null;
    }

}
