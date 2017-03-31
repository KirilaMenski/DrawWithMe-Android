package by.ansgar.drawwithme.ui.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.entity.Message;
import by.ansgar.drawwithme.ui.adapter.ColorAdapter;
import by.ansgar.drawwithme.ui.adapter.MessagesAdapter;
import by.ansgar.drawwithme.ui.adapter.SmilesAdapter;
import by.ansgar.drawwithme.ui.custom.ResizeWidthAnimation;
import by.ansgar.drawwithme.ui.custom.TouchEventView;
import by.ansgar.drawwithme.ui.listeners.ColorListener;
import by.ansgar.drawwithme.ui.listeners.SmilesListener;
import by.ansgar.drawwithme.util.DateUtils;
import by.ansgar.drawwithme.util.KeyBoardUtils;
import by.ansgar.drawwithme.util.SmilesUtil;

/**
 * Created by kirila on 30.3.17.
 */

public class PrivateChatFragment extends Fragment implements ColorListener, Animation.AnimationListener, SmilesListener {

    private final int LAYOUT = R.layout.fragment_private_chat;
    private final int DURATION = 1000;

    private ColorAdapter mColorAdapter;
    private MessagesAdapter mMessagesAdapter;
    private SmilesAdapter mSmilesAdapter;
    private List<Message> mMessages = new ArrayList<>();
    private boolean mArrowClicked;
    private int mChatRlWidth;

    @BindView(R.id.arrow)
    ImageView mActionArrow;

    @BindView(R.id.message)
    EditText mMessage;

    @BindView(R.id.smiles_ll)
    LinearLayout mSmilesLl;

    @BindView(R.id.draw_view)
    TouchEventView mTouchEventView;

    @BindView(R.id.color_recycler)
    RecyclerView mColorRecycler;
    @BindView(R.id.message_recycler)
    RecyclerView mMessageRecycler;
    @BindView(R.id.smiles_recycler)
    RecyclerView mSmileRecycler;

    @BindView(R.id.draw_ll)
    LinearLayout mDrawLl;
    @BindView(R.id.chat_rl)
    RelativeLayout mChatRl;

    public static PrivateChatFragment newInstance() {
        PrivateChatFragment fragment = new PrivateChatFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        updateColorRecycler(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.colors))));
        updateSmileRecycler(SmilesUtil.getSmiles(getContext()));
        return view;
    }

    @OnClick(R.id.send)
    public void sendMessage() {
        //TODO
        Message message = new Message(
                DateUtils.getDate(),
                "user",
                mMessage.getText().toString());
        mMessages.add(message);
        updateMessagesRecycler(mMessages);
        mMessage.setText("");
        KeyBoardUtils.hideKeyBoard(getActivity());
        mSmilesLl.setVisibility(View.GONE);
    }

    @OnClick(R.id.smiles)
    public void showSmiles() {
        mSmilesLl.setVisibility(View.VISIBLE);
        KeyBoardUtils.hideKeyBoard(getActivity());
    }

    private void updateColorRecycler(List<String> colors) {
        mColorAdapter = new ColorAdapter(colors, getActivity(), this);
        mColorRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mColorRecycler.setAdapter(mColorAdapter);
        mColorRecycler.getAdapter().notifyDataSetChanged();
    }

    public void updateMessagesRecycler(List<Message> messages) {
        mMessagesAdapter = new MessagesAdapter(messages, getActivity());
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessageRecycler.setAdapter(mMessagesAdapter);
        mMessageRecycler.getAdapter().notifyDataSetChanged();
        mMessageRecycler.scrollToPosition(messages.size() - 1);
    }

    public void updateSmileRecycler(List<String> smiles) {
        mSmilesAdapter = new SmilesAdapter(smiles, getActivity(), this);
        mSmileRecycler.setLayoutManager(new GridLayoutManager(getContext(), 6));
        mSmileRecycler.setAdapter(mSmilesAdapter);
    }

    @OnClick(R.id.arrow)
    public void actionArrow() {
        mArrowClicked = !mArrowClicked;

        ResizeWidthAnimation a = new ResizeWidthAnimation(mChatRl);
        a.setAnimationListener(this);
        a.setDuration(DURATION);
        if (mArrowClicked) {
            mChatRlWidth = mChatRl.getWidth();
            a.setWidths(mChatRlWidth, 5);
        } else {
            a.setWidths(5, mChatRlWidth);
        }


        mChatRl.startAnimation(a);

    }

    @Override
    public void colorSelected(String color) {
        mTouchEventView.setColor(color);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mActionArrow.setImageDrawable(ContextCompat.getDrawable(getContext(), mArrowClicked ? R.drawable.ic_arrow_left : R.drawable.ic_action_right));
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void smileSelected(String path) {
        String pattern = "[" + path + "]";
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(getContext().getAssets().open(path), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        int selectionCursor = mMessage.getSelectionStart();
        mMessage.getText().insert(selectionCursor, pattern);
        selectionCursor = mMessage.getSelectionStart();

        SpannableStringBuilder builder = new SpannableStringBuilder(mMessage.getText());
        builder.setSpan(new ImageSpan(drawable), selectionCursor - pattern.length(), selectionCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mMessage.setText(builder);
        mMessage.setSelection(selectionCursor);
        mSmilesLl.setVisibility(View.GONE);
    }
}
