package com.example.lab2_and102_fa24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lab2_and102_fa24.database.DBHelper;
import com.example.lab2_and102_fa24.model.Task;

import java.util.ArrayList;

public class TaskDAO {
    private DBHelper dbHelper;
    private static SQLiteDatabase database;

    public TaskDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long addInfor(Task inFor){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", inFor.getTitle());
        values.put("content", inFor.getContent());
        values.put("date", inFor.getDate());
        values.put("type", inFor.getType());

        long check = database.insert("TASKS", null, values);
        if(check <= 0){
            return -1;
        }
        return 1;
    }

    public static int updateInfor(Task inFor){
//        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", inFor.getId());
        values.put("title", inFor.getTitle());
        values.put("content", inFor.getContent());
        values.put("date", inFor.getDate());
        values.put("type", inFor.getType());
        values.put("status", inFor.getStatus());

        long check = database.update("TASKS", values, "id = ?", new String[]{String.valueOf(inFor.getId())});
        if(check <= 0){
            return -1;
        }
        return 1;
    }

    public ArrayList<Task> getList(){
            ArrayList<Task> list = new ArrayList<>();
            database = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = database.rawQuery("SELECT * FROM TASKS",null);
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    do{
                        list.add(new Task(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5)));
                    }
                    while (cursor.moveToNext());

                }
            }catch (Exception e){
                Log.e("ERROR", e.getMessage());
            }
            return list;
        }
         public boolean DelInfor(int id){
            int row = database.delete("TASKS", "id = ?", new String[]{String.valueOf(id)});
            return row !=-1;
    }

//    public boolean updateInfor(int id, boolean check){
//        int status = check ?1:0;
//        ContentValues values = new ContentValues();
//        values.put("status", status);
//        long row = database.update("TASKS", values, "id=?", new String[]{String.valueOf(id)});
//        return row != -1;
//    }

    public boolean updateinfor(Task task) {
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("content", task.getContent());
        values.put("date", task.getDate());
        values.put("type", task.getType());
        long check = database.update("student", values, "id = ?", new String[]{String.valueOf(task.getId())});
        Log.e("TAG", "update: "+ task.getId());
        return check >=0;
    }

//    public boolean updateInfor(Task task){
//        ContentValues values = new ContentValues();
//        values.put("title", task.getTitle());
//        values.put("content", task.getContent());
//        values.put("date", task.getDate());
//        values.put("type", task.getType());
//        long check = database.update("TASKS",values, "id = ?", new String[]{String.valueOf(task.getId())});
//        Log.e("TAG", "update: "+ task.getId());
//        return check >=0;
//    }

    public static boolean deleteInfor(int id){
        long row = database.delete("TASKS", "id=?", new String[]{String.valueOf(id)});
        return row >=0;
    }

    public boolean updatecheck(int id, boolean checked) {
        int status1 = checked ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put("status", status1);
        long ktra =  database.update("TASKS", values, "id = ?",new String[]{String.valueOf(id)});
        return ktra >=0;
    }
}
