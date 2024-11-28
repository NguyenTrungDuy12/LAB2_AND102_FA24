package com.example.lab2_and102_fa24.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "ToDoList.db";
    public static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql = "CREATE TABLE toDoList(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "TITLE TEXT, CONTENT TEXT, DATE TEXT, TYPE TEXT, STATUS INTEGER)";
            sqLiteDatabase.execSQL(sql);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS toDoList";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }



}
