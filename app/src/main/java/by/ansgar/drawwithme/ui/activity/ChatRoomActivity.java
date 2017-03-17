package by.ansgar.drawwithme.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;

/**
 * Created by kirila on 17.3.17.
 */

public class ChatRoomActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_chat_room;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ChatRoomActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
    }
}
