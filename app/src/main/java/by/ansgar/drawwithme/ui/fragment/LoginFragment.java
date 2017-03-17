package by.ansgar.drawwithme.ui.fragment;

import com.squareup.picasso.Picasso;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.ui.activity.ChatRoomActivity;
import by.ansgar.drawwithme.ui.activity.MainActivity;

import static by.ansgar.drawwithme.util.DisplayMetricUtil.getScreenHeight;

/**
 * Created by kirila on 16.3.17.
 */

public class LoginFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_login;

    private static final int DURATION = 1500;

    private int mDrawableRes;
    private int mDisplayHeight;
    private boolean mIsMale;

    MainActivity mActivity;

    @BindView(R.id.next)
    TextView mNext;

    @BindView(R.id.nick_name)
    EditText mNickName;

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

    @BindView(R.id.gender_ll)
    LinearLayout mGenderLl;
    @BindView(R.id.nick_name_ll)
    LinearLayout mNickNameLl;

    @BindView(R.id.male_rl)
    RelativeLayout mMaleRl;
    @BindView(R.id.female_rl)
    RelativeLayout mFemaleRl;

    @BindView(R.id.male_space)
    Space mMaleSpace;
    @BindView(R.id.female_space)
    Space mFemaleSpace;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mNickNameLl.getLayoutParams();
        mDisplayHeight = getScreenHeight(getActivity());
        params.setMargins(0, -1 * mDisplayHeight, 0, 0);
        mNickNameLl.setLayoutParams(params);
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
                .resize(400, mMaleImg.getHeight())
                .into(mMaleImg);

        Picasso.with(getContext())
                .load(R.drawable.female)
                .resize(400, mFemaleImg.getHeight())
                .into(mFemaleImg);
    }

    @OnClick({R.id.male_radio_btn, R.id.female_radio_btn})
    public void selectSex(RadioButton radioBtnId) {
        switch (radioBtnId.getId()) {
            case R.id.male_radio_btn:
                mDrawableRes = R.drawable.male;
                mIsMale = true;
                setViewVisibility(true);
                break;
            case R.id.female_radio_btn:
                mDrawableRes = R.drawable.female;
                mIsMale = false;
                setViewVisibility(false);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.next)
    public void next() {
        if (!mActivity.isNextClicked()) {
            if (mDrawableRes == 0) {
                Toast.makeText(getContext(), getString(R.string.choose_gender), Toast.LENGTH_SHORT).show();
                return;
            }
            mActivity.setNextClicked(true);
            startAnimation();
        } else {
            if (mNickName.getText().toString() == null || mNickName.getText().toString().length() < 4) {
                Toast.makeText(getContext(), getString(R.string.input_your_name), Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(ChatRoomActivity.newIntent(getContext()));
            getActivity().finish();
        }
    }

    public void startAnimation() {
        int nickNameStart = -1 * mDisplayHeight;
        int nickNameEnd = nickNameStart + mNickNameLl.getHeight() / 4;

        int start = mMaleRl.getTop();
        int end = mMaleRl.getHeight();

        int radioGroupStart = mSexRadioGroup.getTop();

        mMaleSpace.setVisibility(mIsMale ? View.VISIBLE : View.GONE);
        mFemaleSpace.setVisibility(mIsMale ? View.GONE : View.VISIBLE);

        ObjectAnimator nickNameLlAnimation = ObjectAnimator.ofFloat(mNickNameLl, "y",
                mActivity.isNextClicked() ? nickNameStart : nickNameEnd,
                mActivity.isNextClicked() ? nickNameEnd : nickNameStart)
                .setDuration(DURATION);

        nickNameLlAnimation.start();

        ObjectAnimator radioGroupAnimation = ObjectAnimator.ofFloat(mSexRadioGroup, "y",
                mActivity.isNextClicked() ? radioGroupStart : end,
                mActivity.isNextClicked() ? end : radioGroupStart)
                .setDuration(DURATION);

        ObjectAnimator maleAnimation = ObjectAnimator.ofFloat(mMaleRl, "y",
                mActivity.isNextClicked() ? start : end,
                mActivity.isNextClicked() ? end : start)
                .setDuration(DURATION);

        ObjectAnimator femaleAnimation = ObjectAnimator.ofFloat(mFemaleRl, "y",
                mActivity.isNextClicked() ? start : end,
                mActivity.isNextClicked() ? end : start)
                .setDuration(DURATION);

        mNext.setText(mActivity.isNextClicked() ? getString(R.string.start) : getString(R.string.next));

        if (mIsMale) {
            femaleAnimation.start();
        } else {
            maleAnimation.start();
        }

        radioGroupAnimation.start();

    }

    public void setViewVisibility(boolean isMale) {
        mMaleView.setVisibility(isMale ? View.GONE : View.VISIBLE);
        mFemaleView.setVisibility(isMale ? View.VISIBLE : View.GONE);
    }
}
