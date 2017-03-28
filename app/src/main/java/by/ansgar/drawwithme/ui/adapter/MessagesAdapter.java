package by.ansgar.drawwithme.ui.adapter;

import java.lang.ref.WeakReference;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.entity.Message;

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
