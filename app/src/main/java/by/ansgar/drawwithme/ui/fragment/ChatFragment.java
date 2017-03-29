package by.ansgar.drawwithme.ui.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.entity.Message;
import by.ansgar.drawwithme.entity.User;
import by.ansgar.drawwithme.ui.adapter.MessagesAdapter;
import by.ansgar.drawwithme.ui.adapter.SmilesAdapter;
import by.ansgar.drawwithme.ui.adapter.UsersAdapter;
import by.ansgar.drawwithme.ui.listeners.SmilesListener;
import by.ansgar.drawwithme.util.DateUtils;
import by.ansgar.drawwithme.util.KeyBoardUtils;
import by.ansgar.drawwithme.util.SmilesUtil;

/**
 * Created by kirila on 27.3.17.
 */

public class ChatFragment extends Fragment implements SmilesListener {

    private static final int LAYOUT = R.layout.fragment_chat;
    private static final String EXTRA_USER = "by.ansgar.drawwithme.ui.fragment.user";

    private MessagesAdapter mMessagesAdapter;
    private UsersAdapter mUsersAdapter;
    private SmilesAdapter mSmilesAdapter;
    private List<Message> mMessages = new ArrayList<>();
    private List<User> mUsers = new ArrayList<>();
    private User mUser;

    @BindView(R.id.message_recycler)
    RecyclerView mMessageRecycler;
    @BindView(R.id.members_recycler)
    RecyclerView mUsersRecycler;
    @BindView(R.id.smiles_recycler)
    RecyclerView mSmileRecycler;

    @BindView(R.id.message)
    EditText mMessage;

    @BindView(R.id.smiles_ll)
    LinearLayout mSmilesLl;

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
        updateSmileRecycler(SmilesUtil.getSmiles(getContext()));
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

    @OnClick(R.id.smiles)
    public void showSmiles() {
        mSmilesLl.setVisibility(View.VISIBLE);
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

    public void updateSmileRecycler(List<String> smiles) {
        mSmilesAdapter = new SmilesAdapter(smiles, getActivity(), this);
        mSmileRecycler.setLayoutManager(new GridLayoutManager(getContext(), 10));
        mSmileRecycler.setAdapter(mSmilesAdapter);
    }

    @Override
    public void smileSelected(String path) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(getContext().getAssets().open(path), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        int selectionCursor = mMessage.getSelectionStart();
        mMessage.getText().insert(selectionCursor, ".");
        selectionCursor = mMessage.getSelectionStart();

        SpannableStringBuilder builder = new SpannableStringBuilder(mMessage.getText());
        builder.setSpan(new ImageSpan(drawable), selectionCursor - ".".length(), selectionCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mMessage.setText(builder);
        mMessage.setSelection(selectionCursor);
        mSmilesLl.setVisibility(View.GONE);
    }
}
