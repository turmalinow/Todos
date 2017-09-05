package com.example.amalinow.todos;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by amalinow on 2017-09-04.
 */

class Utilities {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusView = activity.getCurrentFocus();
        if (focusView == null) {
            focusView = new View(activity);
        }
        imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
    }
}
