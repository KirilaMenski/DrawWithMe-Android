package by.ansgar.drawwithme;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import by.ansgar.drawwithme.util.DWMPreference;

/**
 * Created by kirila on 16.3.17.
 */

public class DWMApp extends MultiDexApplication {

    private static Context sContext;

    public void onCreate() {
        super.onCreate();
        sContext = this;
        DWMPreference.init(sContext);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return sContext;
    }
}
