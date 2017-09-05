package com.example.amalinow.todos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;

/**
 * Created by amalinow on 2017-09-05.
 */
class TodosAdapter extends SimpleCursorAdapter {

    private Context context;

    public TodosAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Cursor cursor = getCursor();
        cursor.moveToPosition(position);

        final int id = cursor.getInt(cursor.getColumnIndex(TodosContract.Todos.ID));
        final boolean done = cursor.getLong(cursor.getColumnIndex(TodosContract.Todos.DONE)) != 0;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.todo_row, null);
        }
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox2);
        checkBox.setChecked(done);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(TodosContract.Todos.DONE, !done);
                context.getContentResolver().update(TodosContract.Todos.CONTENT_URI, values,
                        TodosContract.Todos.ID + "=?", new String[] {String.valueOf(id)});
                context.getContentResolver().notifyChange(TodosContract.Todos.CONTENT_URI, null);
            }
        });
        return super.getView(position, convertView, parent);
    }
}
