package com.example.amalinow.todos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amalinow on 2017-09-03.
 */

final class TodosDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "Todos.db";

    TodosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TodosContract.Todos.TABLE_NAME + " (" +
                TodosContract.Todos.ID + " INTEGER PRIMARY KEY NOT NULL," +
                TodosContract.Todos.CONTENT + " TEXT NOT NULL," +
                TodosContract.Todos.DONE + " INT NOT NULL" +
                ")";
        db.execSQL(sql);

        insertInitialItem(db, "Install Todos app", true);
        insertInitialItem(db, "Create some tasks to do in near future", false);
        insertInitialItem(db, "Run Todos app", true);
    }

    private void insertInitialItem(SQLiteDatabase db, String content, Boolean done) {
        ContentValues values = new ContentValues();
        values.put(TodosContract.Todos.CONTENT, content);
        values.put(TodosContract.Todos.DONE, done ? 1 : 0);
        db.insert(TodosContract.Todos.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TodosContract.Todos.TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
