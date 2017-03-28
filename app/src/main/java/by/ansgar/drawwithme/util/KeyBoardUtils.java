package by.ansgar.drawwithme.util;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by kirila on 28.3.17.
 */

public class KeyBoardUtils {

    public static void hideKeyBoard(FragmentActivity fragmentActivity) {
        WeakReference<Activity> activity = new WeakReference<Activity>(fragmentActivity);
        View view = activity.get().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

}
