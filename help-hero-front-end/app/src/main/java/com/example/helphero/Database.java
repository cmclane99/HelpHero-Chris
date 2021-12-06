package com.example.helphero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public static final String TASK_TABLE = "TASK_TABLE";
    public static final String COLUMN_TASK_TITLE = "TASK_TITLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public Database(@Nullable Context context) {
        super(context, "tasks.db", null, 1);
    }

    //called the first time database is accessed
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + TASK_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TASK_TITLE + " TEXT, " + COLUMN_USERNAME + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);

    }
    //called when new version of the data base
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public boolean addOne(TaskModel task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK_TITLE,task.getTaskTitle());
        cv.put(COLUMN_USERNAME, task.getTaskCreator());

        long insert = db.insert(TASK_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteOne(TaskModel task){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TASK_TABLE + " WHERE " + COLUMN_ID + " = " + task.getTaskId();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public List<TaskModel> getAll(String taskCreator){
        List<TaskModel>  returnList = new ArrayList<>();

        // Selects all tasks created by the logged in user
        String queryString = "SELECT * FROM " + TASK_TABLE + " WHERE " + COLUMN_USERNAME + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {taskCreator});
        if(cursor.moveToLast()){
            //loop through the results new task object to add to the return list
            do{
                int taskId = cursor.getInt(0);
                String taskTitle = cursor.getString(1);

                TaskModel newTask = new TaskModel(taskId, taskTitle);
                returnList.add(newTask);
            }while(cursor.moveToPrevious());
        }else{
            //failure
        }
        cursor.close();
        db.close();
        return returnList;
    }


}
