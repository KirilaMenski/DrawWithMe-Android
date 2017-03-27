package by.ansgar.drawwithme.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.entity.User;
import by.ansgar.drawwithme.ui.fragment.RoomsFragment;
import by.ansgar.drawwithme.util.FragmentUtil;

/**
 * Created by kirila on 17.3.17.
 */

public class ChatActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_chat_room;
    private static final String EXTRA_USER = "by.ansgar.drawwithme.ui.activity.user";

    private User mUser;

    public static Intent newIntent(Context context, User user){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
        mUser = (User) getIntent().getSerializableExtra(EXTRA_USER);
        FragmentUtil.replaceFragment(this, R.id.chat_room_fragment_container, RoomsFragment.newInstance(mUser), false);
    }
}
