package com.example.lab2_and102_fa24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab2_and102_fa24.database.DbHelper;
import com.example.lab2_and102_fa24.model.ToDo;


import java.util.ArrayList;
import java.util.List;

public class toDoDao {
    private final DbHelper dbHelper;
    private SQLiteDatabase mDatabase;

    public toDoDao(Context context) {
        dbHelper = new DbHelper(context, "toDo.db", null, 1);
        mDatabase = dbHelper.getWritableDatabase();
    }

    public boolean insertToDo(ToDo obj) {
        ContentValues values = new ContentValues();
        values.put("title", obj.getTitle());
        values.put("content", obj.getContent());
        values.put("date", obj.getDate());
        values.put("type", obj.getType());

        long check = mDatabase.insert("toDoList", null, values);
        return check != -1;
    }

    //Add new method
    public List<ToDo> getAllToDo() {
        List<ToDo> list = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM toDoList", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ToDo item = new ToDo();
                item.setId(cursor.getInt(0));
                item.setTitle(cursor.getString(1));
                item.setContent(cursor.getString(2));
                item.setDate(cursor.getString(3));
                item.setType(cursor.getString(4));
                item.setStatus(cursor.getInt(5));
                list.add(item);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    //Update method
    public boolean updateToDoList(ToDo obj) {
        ContentValues values = new ContentValues();
        values.put("title", obj.getTitle());
        values.put("content", obj.getContent());
        values.put("date", obj.getDate());
        values.put("type", obj.getType());
        int check = mDatabase.update("toDoList", values, "TITLE=?",
                new String[]{String.valueOf(obj.getTitle())});
        return check != -1;

    }


    //Delete method
    public boolean deleteToDoList(String title) {
        int check = mDatabase.delete("toDoList", "TITLE=?",
                new String[]{String.valueOf(title)});
        return check != -1;
    }



}
