package com.example.amalinow.todos;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private TodosDbHelper dbHelper;

    private EditText editText;
    private final static String TODO_ITEM_WRITE_FAILED_MESSAGE = "Write failed";
    private final static String EDIT_TEXT_INITIAL_CONTENT = "";
    private final static int LOADER_ID = 1;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(EDIT_TEXT_INITIAL_CONTENT);
        dbHelper = new TodosDbHelper(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);

        String[] columns = new String[] {
                TodosContract.Todos.CONTENT
        };
        int[] ids = new int[] {
                R.id.textView2
        };
        adapter = new TodosAdapter(this, R.layout.todo_row, null, columns, ids, 0);
        listView.setAdapter(adapter);

        mCallbacks = this;

        LoaderManager lm = getLoaderManager();
        lm.initLoader(LOADER_ID, null, mCallbacks);
    }

    public void onButtonClicked(View view) {
        String message = editText.getText().toString();
        ContentValues values = new ContentValues();
        values.put(TodosContract.Todos.CONTENT, message);
        values.put(TodosContract.Todos.DONE, 0);
        Uri createdRow = getContentResolver().insert(TodosContract.Todos.CONTENT_URI, values);
        if (createdRow != null) {
            clearEditText();
            getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);
        } else {
            Toast toast = Toast.makeText(this, TODO_ITEM_WRITE_FAILED_MESSAGE, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void clearEditText() {
        editText.setText(EDIT_TEXT_INITIAL_CONTENT);
        editText.clearFocus();
        Utilities.hideKeyboard(this);
    }


    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TodosContract.Todos.CONTENT_URI, TodosContract.Todos.PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
                adapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}

