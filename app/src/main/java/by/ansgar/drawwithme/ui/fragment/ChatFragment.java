package by.ansgar.drawwithme.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.entity.Message;
import by.ansgar.drawwithme.entity.User;
import by.ansgar.drawwithme.ui.adapter.MessagesAdapter;
import by.ansgar.drawwithme.ui.adapter.UsersAdapter;
import by.ansgar.drawwithme.util.DateUtils;
import by.ansgar.drawwithme.util.KeyBoardUtils;

/**
 * Created by kirila on 27.3.17.
 */

public class ChatFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_chat;
    private static final String EXTRA_USER = "by.ansgar.drawwithme.ui.fragment.user";

    private MessagesAdapter mMessagesAdapter;
    private UsersAdapter mUsersAdapter;
    private List<Message> mMessages = new ArrayList<>();
    private List<User> mUsers = new ArrayList<>();
    private User mUser;

    @BindView(R.id.message_recycler)
    RecyclerView mMessageRecycler;
    @BindView(R.id.members_recycler)
    RecyclerView mUsersRecycler;

    @BindView(R.id.message)
    EditText mMessage;

    public static ChatFragment newInstance(User user) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        mUser = (User) getArguments().getSerializable(EXTRA_USER);
        mUsers.add(mUser);
        updateUsersRecycler(mUsers);
        return view;
    }

    @OnClick(R.id.send)
    public void sendMessage() {
        Message message = new Message(
                DateUtils.getDate(),
                mUser.getName(),
                mMessage.getText().toString());
        mMessages.add(message);
        updateMessagesRecycler(mMessages);
        mMessage.setText("");
        KeyBoardUtils.hideKeyBoard(getActivity());
    }

    public void updateMessagesRecycler(List<Message> messages) {
        mMessagesAdapter = new MessagesAdapter(messages, getActivity());
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessageRecycler.setAdapter(mMessagesAdapter);
        mMessageRecycler.getAdapter().notifyDataSetChanged();
        mMessageRecycler.scrollToPosition(messages.size() - 1);
    }

    public void updateUsersRecycler(List<User> users) {
        mUsersAdapter = new UsersAdapter(users, getActivity());
        mUsersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsersRecycler.setAdapter(mUsersAdapter);
    }

}
