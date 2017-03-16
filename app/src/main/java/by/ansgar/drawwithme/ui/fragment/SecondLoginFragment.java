package by.ansgar.drawwithme.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;

/**
 * Created by kirila on 16.3.17.
 */

public class SecondLoginFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_second_login;
    private static final String EXTRA_SEX = "by.ansgar.drawwithme.ui.fragment.sex";


    public static SecondLoginFragment newInstance(String sex) {
        SecondLoginFragment fragment = new SecondLoginFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_SEX, sex);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


}
