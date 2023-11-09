package ninh.main.mybarcodescanner.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.widget.Toast;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

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

    public void insert( String seri , String name, Integer quantity) {
        ContentValues contentValue = new ContentValues(); //KHOI TAO
        contentValue.put(DatabaseHelper.Seri, seri);
        contentValue.put(DatabaseHelper.NameProduct, name);
        contentValue.put(DatabaseHelper.Quantity, quantity);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
        Cursor cursor1 = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        while (cursor1.moveToNext()) {
            String bienTamThoi = cursor1.getString(0);
            String bienTamThoi1 = cursor1.getString(1);
            int bienTamThoi2 = cursor1.getInt(2);
            Toast.makeText(context, bienTamThoi + bienTamThoi1 + bienTamThoi2 + " ", Toast.LENGTH_SHORT).show();
        }
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
        contentValues.put(DatabaseHelper.NameProduct, name);
        contentValues.put(DatabaseHelper.Quantity, quantity);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.Seri + " = " + _seri, null);
        return i;
    }

    public void delete(String _seri) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.Seri + "=" + _seri, null);
    }

//    public boolean checkExisted(String seri){
//        String query = "SELECT * FROM my_table WHERE column_name = ?";
//        Cursor data = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.Seri + " =  " + seri, null);
////        Cursor data = database.query(DatabaseHelper.TABLE_NAME, new String[] { "COUNT(id)" }, )
//        while(data.moveToNext()){
//            String seriProduct = data.getString(0);
//            if (seri.compareTo(seriProduct)==0){
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean checkExisted(String seri) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection =  DatabaseHelper.Seri + " = ? ";
        String[] selectionArgs = {seri};
        Cursor data = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.Seri + " =  " + seri + " ", null);
        // Thực hiện truy vấn
//        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean exists = data != null && data.getCount() > 0;

        // Đóng cursor và kết nối đến cơ sở dữ liệu
        if (data != null) {
            data.close();
        }
        db.close();
        Toast.makeText(context, "CHECK EXISTED " + exists, Toast.LENGTH_SHORT).show();
        return exists;

    }

}
