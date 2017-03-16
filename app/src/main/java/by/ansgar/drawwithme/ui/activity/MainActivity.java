package by.ansgar.drawwithme.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.ui.fragment.LoginFragment;
import by.ansgar.drawwithme.util.FragmentUtil;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;

    @BindView(R.id.main_fragment_container)
    FrameLayout mMainContainer;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
        FragmentUtil.replaceFragment(this, R.id.main_fragment_container, LoginFragment.newInstance(), false);
    }
}
