package by.ansgar.drawwithme.ui.adapter;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.entity.Message;
import by.ansgar.drawwithme.util.SmilesUtil;

/**
 * Created by kirila on 28.3.17.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageHolder> {

    private static final int LAYOUT = R.layout.item_message;

    private List<Message> mMessages;
    private WeakReference<FragmentActivity> mActivity;

    public MessagesAdapter(List<Message> messages, FragmentActivity activity) {
        mMessages = messages;
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.bindHolder(message);
        setMessage(message.getMessage(), holder);
    }

    private void setMessage(String message, MessageHolder holder) {
        Pattern pattern = Pattern.compile(SmilesUtil.SMILE_PATTERN);
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {

            String resource = matcher.group().replace("[","").replace("]", "");
            Drawable drawable = null;
            try {
                drawable = Drawable.createFromStream(mActivity.get().getAssets().open(resource), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            SpannableStringBuilder builder = new SpannableStringBuilder(holder.mMessage.getText());
            builder.setSpan(new ImageSpan(drawable), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mMessage.setText(builder);
        }

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.message_author)
        TextView mAuthor;
        @BindView(R.id.message_date)
        TextView mDate;
        @BindView(R.id.message)
        TextView mMessage;

        public MessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(Message message) {
            mAuthor.setText(message.getAuthor());
            mDate.setText(message.getDate());
            mMessage.setText(message.getMessage());
        }

    }
}
