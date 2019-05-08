package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;
import android.database.Cursor;

import java.io.ByteArrayOutputStream;

public class MyDB extends SQLiteOpenHelper {

    Context ctx;
    SQLiteDatabase db;
    static String DBname = "DBname";
    static String tableName = "tablename";
    static int VERSION = 1;
    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        ctx = context;
        VERSION = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ tableName+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, PICTURE BLOB, TITLE STRING);");
        Toast.makeText(ctx, "TABLE IS CREATED", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(VERSION == oldVersion){
            VERSION = newVersion;
            db = getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS "+ tableName + ";");
            onCreate(db);
        }
    }

    public void insert(Bitmap url, String title) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        url.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        try {
            cv.put("PICTURE", imageInByte);
            cv.put("TITLE", title);
            db.insert(tableName, null, cv);
        }
        catch (SQLiteException e){
            e.printStackTrace();
        }
        db.close();

    }


    public Cursor getAllRows(){
        db = getReadableDatabase();
        String query = "SELECT * FROM " + tableName;
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public int deleteByID(int a) {
        db = getWritableDatabase();
        //db.execSQL("DELETE FROM " + tableName + " WHERE _id = \"" + id + "\" OR TITLE = \"" + title + "\";");
        int deleted = db.delete(tableName,"_id =? ",new String[]{String.valueOf(a)});
        return deleted;
    }

    public int deleteByTitle(String str) {
        db = getWritableDatabase();
        int deleted = db.delete(tableName,"TITLE =? ", new String[]{String.valueOf(str)});
        return deleted;
    }

    public int countRows(){
        db = getWritableDatabase();
        String query = "SELECT * FROM " + tableName;

        Cursor c = db.rawQuery(query, null);
        int size = 0;
        if(c.moveToLast()){
            size = c.getInt(0);
        }
        return size;
    }

    public Cursor getRowsRange(int a, int b) {
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE _id BETWEEN " + a + " AND " + b, null);
        return c;
    }


}
