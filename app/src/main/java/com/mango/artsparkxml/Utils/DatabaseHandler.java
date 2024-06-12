package com.mango.artsparkxml.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.mango.artsparkxml.Model.CardItem;
import com.mango.artsparkxml.Model.ImageModel;
import com.mango.artsparkxml.Model.ProfileImageModel;
import com.mango.artsparkxml.Model.ToDoModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static final int VERSION = 1;

    // database
    private static final String NAME = "database";
    private SQLiteDatabase db;

    // todos table
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";

    //moodboard table
    private static final String MOODBOARD_TABLE = "moodboard";
    private static final String MOODBOARD_ID = "id";
    private static final String MOODBOARD_TITLE = "title";
    private static final String MOODBOARD_THUMBNAIL = "thumbnail";
    private static final String CREATE_MOODBOARD_TABLE = "CREATE TABLE "
            + MOODBOARD_TABLE + "(" + MOODBOARD_ID + " TEXT PRIMARY KEY, "
            + MOODBOARD_TITLE + " TEXT, "
            + MOODBOARD_THUMBNAIL + " BLOB)";

    // moodboard image table
    private static final String MOODBOARD_IMAGE_TABLE = "moodboard_image";
    private static final String IMAGE_ID = "id";
    private static final String IMAGE_MOODBOARD_ID = "moodboard_id";
    private static final String URI = "uri";
    private static final String IMAGE = "image";
    private static final String POINT_X = "point_x";
    private static final String POINT_Y = "point_y";
    private static final String SCALE_X = "scale_x";
    private static final String SCALE_Y = "scale_y";
    private static final String CREATE_MOODBOARD_IMAGE_TABLE =
            "CREATE TABLE " + MOODBOARD_IMAGE_TABLE +
                    "(" + IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    IMAGE_MOODBOARD_ID + " TEXT, " +
                    URI + " TEXT, " +
                    IMAGE + " BLOB, " +
                    POINT_X + " REAL, " +
                    POINT_Y + " REAL, " +
                    SCALE_X + " REAL, " +
                    SCALE_Y + " REAL," +
                    "FOREIGN KEY(" + IMAGE_MOODBOARD_ID + ") REFERENCES " + MOODBOARD_TABLE + "(" + MOODBOARD_ID + "))";


    // profile image table
    private static final String PROFILE_TABLE = "images";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE = "image";
    private static final String CREATE_TABLE_IMAGE =
            "CREATE TABLE " + PROFILE_TABLE +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IMAGE + " BLOB" + ")";

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteImage;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_TABLE_IMAGE);
        db.execSQL(CREATE_MOODBOARD_TABLE);
        db.execSQL(CREATE_MOODBOARD_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MOODBOARD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MOODBOARD_IMAGE_TABLE);

        //create tables again :D
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    //image stuff
    public void storeData(ProfileImageModel profileImageModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        Bitmap bitmapImage = profileImageModel.getImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("image", byteImage);

        long checkQuery = database.insert(PROFILE_TABLE, null, contentValues);
        if (checkQuery != -1) {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
            database.close();
        } else {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getUser() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * FROM images", null);
        return cursor;
    }

    //tasks stuff
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

    // MOODBOARD
    public void insertMoodboard(String id, String name, byte[] byteImage) {
        ContentValues values = new ContentValues();
        values.put(MOODBOARD_ID, id);
        values.put(MOODBOARD_TITLE, name);
        values.put(MOODBOARD_THUMBNAIL, byteImage);
        db.insert(MOODBOARD_TABLE, null, values);
    }

    public List<CardItem> getAllMoodboards() {
        List<CardItem> moodboardList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(MOODBOARD_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        CardItem moodboard = new CardItem();
                        moodboard.setId(cur.getString(cur.getColumnIndex(MOODBOARD_ID)));
                        moodboard.setTitle(cur.getString(cur.getColumnIndex(MOODBOARD_TITLE)));
                        moodboard.setThumbnail(cur.getBlob(cur.getColumnIndex(MOODBOARD_THUMBNAIL)));
                        moodboardList.add(moodboard);
                    }while (cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cur.close();
        }
        return moodboardList;
    }

    public CardItem getMoodboardById(String moodboardId) {
        Cursor cursorMoodboard = db.query(
                MOODBOARD_TABLE,
                new String[]{MOODBOARD_ID, MOODBOARD_TITLE, MOODBOARD_THUMBNAIL},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(moodboardId)},
                null, null, null, null);

        if (cursorMoodboard != null) {
            cursorMoodboard.moveToFirst();
        } else {
            return null;
        }

        String id = cursorMoodboard.getString(cursorMoodboard.getColumnIndex(MOODBOARD_ID));
        String title = cursorMoodboard.getString(cursorMoodboard.getColumnIndex(MOODBOARD_TITLE));
        byte[] thumbnail = cursorMoodboard.getBlob(cursorMoodboard.getColumnIndex(MOODBOARD_THUMBNAIL));

        if (cursorMoodboard != null) {
            cursorMoodboard.close();
        }

        CardItem moodboard = new CardItem(id, title);
        moodboard.setThumbnail(thumbnail);

        return moodboard;
    }

    public List<ImageModel> getImagesForMoodboard(String moodboardId) {
        List<ImageModel> images = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(MOODBOARD_IMAGE_TABLE, null, IMAGE_MOODBOARD_ID + "=?",
                new String[]{String.valueOf(moodboardId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ImageModel imageData = new ImageModel();
                imageData.setId(cursor.getLong(cursor.getColumnIndex(IMAGE_ID)));
                imageData.setUri(cursor.getString(cursor.getColumnIndex(URI)));
                imageData.setImageByteArray(cursor.getBlob(cursor.getColumnIndex(IMAGE)));
                imageData.setScaleX(cursor.getFloat(cursor.getColumnIndex(SCALE_X)));
                imageData.setScaleY(cursor.getFloat(cursor.getColumnIndex(SCALE_Y)));
                imageData.setX(cursor.getFloat(cursor.getColumnIndex(POINT_X)));
                imageData.setY(cursor.getFloat(cursor.getColumnIndex(POINT_Y)));
                images.add(imageData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return images;
    }

    // Delete a moodboard and its associated images
    public void deleteMoodboard(String moodboardId) {
        db.delete(MOODBOARD_IMAGE_TABLE, IMAGE_MOODBOARD_ID + "=?", new String[]{String.valueOf(moodboardId)});
        db.delete(MOODBOARD_TABLE, MOODBOARD_ID + "=?", new String[]{String.valueOf(moodboardId)});
    }

    // MOODBOARD IMAGE
    public void insertImage(String moodboardId, ImageModel imageModel){
        ContentValues cv = new ContentValues();
        cv.put(IMAGE_MOODBOARD_ID, moodboardId);
        Log.d("moodboardIdDB:", moodboardId);
        cv.put(URI, imageModel.getUri());
        Log.d("uriDB:", imageModel.getUri());
        cv.put(IMAGE, imageModel.getImageByteArray());
        Log.d("bytearrayDB:", String.valueOf(imageModel.getImageByteArray()));
        cv.put(POINT_X, imageModel.getX());
        Log.d("pointXDB:", String.valueOf(imageModel.getX()));
        cv.put(POINT_Y, imageModel.getY());
        cv.put(SCALE_X, imageModel.getScaleX());
        cv.put(SCALE_Y, imageModel.getScaleX());
        db.insert(MOODBOARD_IMAGE_TABLE, null, cv);
    }

    // update image: move, scale
    public void updateImage(long imageId, float pointX, float pointY, float scaleX, float scaleY) {
        ContentValues cv = new ContentValues();
        cv.put(POINT_X, pointX);
        cv.put(POINT_Y, pointY);
        cv.put(SCALE_X, scaleX);
        cv.put(SCALE_Y, scaleY);
        db.update(MOODBOARD_IMAGE_TABLE, cv, ID + "=?", new String[]{String.valueOf(imageId)});
    }

    // delete image
    public void deleteImage(String imageId){
        db.delete(MOODBOARD_IMAGE_TABLE, ID + "=?", new String[]{String.valueOf(imageId)});
    }

    // delete all image associated with moodboard
    public void deleteImagesForMoodboard(String moodboardId) {
        db.delete(MOODBOARD_IMAGE_TABLE, IMAGE_MOODBOARD_ID + "=?", new String[]{String.valueOf(moodboardId)});
    }

}

