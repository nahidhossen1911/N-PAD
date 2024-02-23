package com.appscraftbd.npad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class SQLite extends SQLiteOpenHelper {

    public static final String DATA_BASE_NAME = "note_info";
    public static final int DB_VERSION = 1;


    public SQLite( Context context) {
        super(context, DATA_BASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE Table note_text (Id INTEGER primary key autoincrement , Title TEXT , Body TEXT , Date TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists note_text");
        onCreate(db);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getInsertData(String title, String body){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("d");
        }
        int up = Integer.parseInt(""+date.format(formatter));
        int today = up+1;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String currentTime = date.format(formatter1);

        ContentValues contentValues = new ContentValues();
        contentValues.put("Title",title);
        contentValues.put("Body",body);
        contentValues.put("Date",currentTime);

        sqLiteDatabase.insert("note_text",null,contentValues);
    }
    public Cursor getAllData(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from note_text ORDER By Id DESC",null);
        return  cursor;

    }public void dataDelete(String sid){


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("note_text","Id = ?",new String[]{sid});


    }

}
