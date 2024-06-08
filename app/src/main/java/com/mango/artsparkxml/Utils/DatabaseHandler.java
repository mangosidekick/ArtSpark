package com.mango.artsparkxml.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mango.artsparkxml.Model.ImageModel;
import com.mango.artsparkxml.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";

    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";

    private static final String MOODBOARD_IMAGE_TABLE = "moodboard_image";
    private static final String IMAGE_ID = "id";
    private static final String MOODBOARD_ID = "moodboard_id";
    private static final String URI = "uri";
    private static final String IMAGE = "image";
    private static final String POINT_X = "point_x";
    private static final String POINT_Y = "point_y";
    private static final String SCALE_X = "scale_x";
    private static final String SCALE_Y = "scale_y";
    private static final String CREATE_MOODBOARD_IMAGE_TABLE =
            "CREATE TABLE " + MOODBOARD_IMAGE_TABLE +
                    "(" + IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MOODBOARD_ID + " TEXT, " +
                    URI + " TEXT, " +
                    IMAGE + " BLOB, " +
                    POINT_X + " REAL, " +
                    POINT_Y + " REAL, " +
                    SCALE_X + " REAL, " +
                    SCALE_Y + " REAL)";

    private SQLiteDatabase db;


    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_MOODBOARD_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MOODBOARD_IMAGE_TABLE);

        //create tables again :D
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
    }

    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }while (cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void insertImage(String moodboardId, ImageModel imageModel){
        ContentValues cv = new ContentValues();
        cv.put(MOODBOARD_ID, moodboardId);
        cv.put(URI, imageModel.getUri());
        cv.put(IMAGE, imageModel.getImageByteArray());
        cv.put(POINT_X, imageModel.getX());
        cv.put(POINT_Y, imageModel.getY());
        cv.put(SCALE_X, imageModel.getScaleX());
        cv.put(SCALE_Y, imageModel.getScaleX());
        db.insert(MOODBOARD_IMAGE_TABLE, null, cv);
    }

    // update image: move, scale

    // delete image

}


