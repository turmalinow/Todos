package com.example.amalinow.todos;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by amalinow on 2017-09-04.
 */

final class TodosContract {

    public static final String CONTENT_AUTHORITY = "com.example.amalinow.todos.content_provider";

    public static final String PATH_TODOS = "todos";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class Todos implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TODOS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/com.example.amalinow.todos.provider.todos";

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.dir/com.example.amalinow.todos.provider.todo";

        public static final String TABLE_NAME = "todos";

        public static final String ID = BaseColumns._ID;
        public static final String CONTENT = "content";
        public static final String DONE = "done";

        public static final String[] PROJECTION = new String[] {
                Todos.ID,
                Todos.CONTENT,
                Todos.DONE
        };
    }

}
