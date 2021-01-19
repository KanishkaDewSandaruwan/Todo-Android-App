package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "todo";
    private  static final String TABLE_NAME = "todo";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLE_CREATE_QUERY = "CREATE TABLE "
                +TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE+" TEXT,"
                +DESCRIPTION+" TEXT,"
                +STARTED+" TEXT,"
                +FINISHED+" TEXT);";

        db.execSQL(TABLE_CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addTodos(ToDo toDo){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,toDo.getTitle());
        contentValues.put(DESCRIPTION,toDo.getDesc());
        contentValues.put(STARTED,toDo.getStarted());
        contentValues.put(FINISHED,toDo.getFinished());

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }

    public int getCount(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String getQuery = "SELECT * FROM "+TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(getQuery,null);
        return cursor.getCount();
    }

    public List<ToDo> getAllTodos(){
        List<ToDo> toDoList = new ArrayList();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String getAllData_Query = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(getAllData_Query,null);

        if(cursor.moveToFirst()){
            do{
                ToDo toDo = new ToDo();

                toDo.setId(cursor.getInt(0));
                toDo.setTitle(cursor.getString(1));
                toDo.setDesc(cursor.getString(2));
                toDo.setStarted(cursor.getString(3));
                toDo.setFinished(cursor.getLong(4));

                toDoList.add(toDo);
            }while(cursor.moveToNext());
        }
        return toDoList;

    }

    public void deleteTodo(int position){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,ID+"=?",new String[]{String.valueOf(position)});
        sqLiteDatabase.close();

    }

    public void updateTodo(ToDo toDo){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,toDo.getTitle());
        contentValues.put(DESCRIPTION,toDo.getDesc());
        contentValues.put(STARTED,toDo.getStarted());
        contentValues.put(FINISHED,toDo.getFinished());

        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+" = ? ",new String[]{String.valueOf(toDo.getId())});
        sqLiteDatabase.close();

    }

    public ToDo getSingleTodo(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{ID, TITLE, DESCRIPTION, STARTED, FINISHED}, ID + " = ?",new String[]{String.valueOf(id)}, null, null, null);

        ToDo toDo;
        if (cursor != null) {
            cursor.moveToFirst();
            toDo = new ToDo(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getLong(4)
            );
            return toDo;
        }
        return null;
    }

    public void updateFinished(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        long date = System.currentTimeMillis();
        cv.put(FINISHED,date);

        sqLiteDatabase.update(TABLE_NAME,cv,ID+" = ? ",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void cancelFinish(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FINISHED,0);
        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+" = ? ",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
}
