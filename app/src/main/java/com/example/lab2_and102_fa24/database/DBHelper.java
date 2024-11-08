package com.example.lab2_and102_fa24.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper( Context context) {
        super(context, "TodoDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE TASKS(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, CONTENT TEXT, DATE TEXT, TYPE TEXT, STATUS INTEGER)";
        db.execSQL(sql);

        String sqlInsert = "INSERT INTO TASKS( TITLE, CONTENT, DATE,TYPE, STATUS) VALUES" +
                "( 'AND', 'Hoc AND co ban', '11/07/2024', 'De', '0')," +
                "( 'PHP', 'Hoc PHP co ban', '12/07/2024', 'De', '1')," +
                "( 'Python', 'Hoc Python co ban', '11/07/2024', 'De', '0')," +
                "( 'JS', 'Hoc JS co ban', '11/07/2024', 'De', '1')";
        db.execSQL(sqlInsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        if(oldversion != newversion){
            db.execSQL("DROP TABLE IF EXISTS TASKS");
            onCreate(db);
        }

    }
}
