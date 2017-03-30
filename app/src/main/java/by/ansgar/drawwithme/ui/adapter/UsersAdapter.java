package by.ansgar.drawwithme.ui.adapter;

import java.lang.ref.WeakReference;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.entity.User;
import by.ansgar.drawwithme.ui.listeners.UserListener;

/**
 * Created by kirila on 28.3.17.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {

    private static final int LAYOUT = R.layout.item_members;

    private List<User> mUsers;
    private WeakReference<FragmentActivity> mActivity;
    private WeakReference<UserListener> mListener;

    public UsersAdapter(List<User> members, FragmentActivity activity, UserListener listener) {
        mUsers = members;
        mActivity = new WeakReference<>(activity);
        mListener = new WeakReference<>(listener);
    }

    @Override
    public UsersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(final UsersHolder holder, int position) {
        final User user = mUsers.get(position);
        holder.bindViews(user);
        holder.mUserLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.get().userClicked(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UsersHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gender_icon)
        ImageView mGenderIcon;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.user_ll)
        LinearLayout mUserLl;

        public UsersHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(User user) {
            mUserName.setText(user.getName());
            mGenderIcon.setImageDrawable(
                    ContextCompat.getDrawable(mActivity.get(),
                            user.getSex() == 1 ? R.drawable.ic_male_icon_60dp
                                    : R.drawable.ic_female_icon_60dp));
        }

    }
}
