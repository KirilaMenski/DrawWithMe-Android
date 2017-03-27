package by.ansgar.drawwithme.ui.adapter;

import java.lang.ref.WeakReference;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.entity.Room;
import by.ansgar.drawwithme.ui.listeners.RoomClickListener;

/**
 * Created by kirila on 27.3.17.
 */
public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomsHolder> {

    private static final int LAYOUT = R.layout.item_room;

    private List<Room> mRooms;
    private WeakReference<FragmentActivity> mActivity;
    private WeakReference<RoomClickListener> mListener;

    public RoomsAdapter(List<Room> rooms, FragmentActivity fragmentActivity, RoomClickListener listener) {
        mRooms = rooms;
        mActivity = new WeakReference<>(fragmentActivity);
        mListener = new WeakReference<>(listener);
    }

    @Override
    public RoomsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new RoomsHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomsHolder holder, int position) {
        final Room room = mRooms.get(position);
        holder.bindView(room);
        holder.mRoomLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.get().roomSelected(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public class RoomsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_room)
        TextView mRoomName;
        @BindView(R.id.members_count)
        TextView mMembersCount;
        @BindView(R.id.room_ll)
        LinearLayout mRoomLl;

        public RoomsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Room room) {
            mRoomName.setText(room.getName());
            mMembersCount.setText(mActivity.get().getString(R.string.members_count, room.getMembersCount()));
        }

    }
}
