package by.ansgar.drawwithme.util;

import static android.content.Context.MODE_PRIVATE;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kirila on 16.3.17.
 */
public class DWMPreference {

    private static SharedPreferences mPreference;
    private static DWMPreference mInstance;

    WeakReference<Context> mContext;

    public DWMPreference(Context context) {
        this.mContext = new WeakReference<>(context);
        this.mPreference = getPrefs(mContext.get());
    }

    public static SharedPreferences getPrefs(Context ctx) {
        if (mPreference == null) {
            return mPreference = ctx.getSharedPreferences("Prefs", MODE_PRIVATE);
        } else {
            return mPreference;
        }
    }

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (DWMPreference.class) {
                if (mInstance == null) {
                    mInstance = new DWMPreference(context);
                }
            }
        }
    }

}
