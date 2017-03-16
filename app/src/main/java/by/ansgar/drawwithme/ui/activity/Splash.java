package by.ansgar.drawwithme.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import by.ansgar.drawwithme.R;

/**
 * Created by kirila on 16.3.17.
 */

public class Splash extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_splash;
    private static final int DELAY = 1500;

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRunnable = this;
                finish();
                startActivity(MainActivity.newIntent(Splash.this));
            }
        }, DELAY);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }
}
