package by.ansgar.drawwithme.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.entity.Room;
import by.ansgar.drawwithme.entity.User;
import by.ansgar.drawwithme.ui.adapter.RoomsAdapter;
import by.ansgar.drawwithme.ui.listeners.RoomClickListener;
import by.ansgar.drawwithme.util.FragmentUtil;

/**
 * Created by kirila on 27.3.17.
 */

public class RoomsFragment extends Fragment implements RoomClickListener {

    private static final int LAYOUT = R.layout.fragment_rooms;
    private static final String EXTRA_USER = "by.ansgar.drawwithme.ui.fragment.user";

    private RoomsAdapter mAdapter;
    private User mUser;

    @BindView(R.id.chat_rooms_recycler)
    RecyclerView mRoomsRecycler;

    public static RoomsFragment newInstance(User user) {
        RoomsFragment fragment = new RoomsFragment();
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
        Log.i("RoomsAdapter", "Room " + mUser.getName() + " selected!");
        getRooms();
        return view;
    }

    public void getRooms() {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Room room = new Room(UUID.randomUUID().toString(), "Room " + i, i);
            rooms.add(room);
        }
        updateRecycler(rooms);
    }

    public void updateRecycler(List<Room> rooms) {
        mAdapter = new RoomsAdapter(rooms, getActivity(), this);
        mRoomsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRoomsRecycler.setAdapter(mAdapter);
        mRoomsRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void roomSelected(Room room) {
        Log.i("RoomsAdapter", "Room " + room.getName() + " selected!");
        FragmentUtil.replaceFragment(getActivity(), R.id.chat_room_fragment_container, ChatFragment.newInstance(), true);
    }
}
