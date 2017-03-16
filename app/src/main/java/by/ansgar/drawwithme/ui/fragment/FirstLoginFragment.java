package by.ansgar.drawwithme.ui.fragment;

import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.util.FragmentUtil;

/**
 * Created by kirila on 16.3.17.
 */

public class FirstLoginFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_first_login;

    private String mSex;

    @BindView(R.id.radio_group)
    RadioGroup mSexRadioGroup;
    @BindView(R.id.male_radio_btn)
    RadioButton mMaleRadioBtn;
    @BindView(R.id.female_radio_btn)
    RadioButton mFemaleRadioBtn;

    @BindView(R.id.male_img)
    ImageView mMaleImg;
    @BindView(R.id.female_img)
    ImageView mFemaleImg;

    @BindView(R.id.male_view)
    View mMaleView;
    @BindView(R.id.female_view)
    View mFemaleView;

    public static FirstLoginFragment newInstance() {
        FirstLoginFragment fragment = new FirstLoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadImg();
    }

    private void loadImg() {
        Picasso.with(getContext())
                .load(R.drawable.male)
                .into(mMaleImg);

        Picasso.with(getContext())
                .load(R.drawable.female)
                .into(mFemaleImg);
    }

    @OnClick({R.id.male_radio_btn, R.id.female_radio_btn})
    public void selectSex(RadioButton radioBtnId) {
        switch (radioBtnId.getId()) {
            case R.id.male_radio_btn:
                mSex = mMaleRadioBtn.getText().toString();
                setViewVisibility(true);
                break;
            case R.id.female_radio_btn:
                mSex = mFemaleRadioBtn.getText().toString();
                setViewVisibility(false);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.next)
    public void next() {
        if (mSex == null) {
            Toast.makeText(getContext(), getString(R.string.choose_gender), Toast.LENGTH_SHORT).show();
            return;
        }
        FragmentUtil.replaceAnimFragment(getActivity(), R.id.main_fragment_container,
                SecondLoginFragment.newInstance(mSex), false,
                R.anim.right_out, R.anim.left_out);
    }

    public void setViewVisibility(boolean isMale) {
        mMaleView.setVisibility(isMale ? View.GONE : View.VISIBLE);
        mFemaleView.setVisibility(isMale ? View.VISIBLE : View.GONE);
    }
}
