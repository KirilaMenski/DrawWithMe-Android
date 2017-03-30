package by.ansgar.drawwithme.ui.adapter;

import java.lang.ref.WeakReference;
import java.util.List;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.ui.listeners.ColorListener;

/**
 * Created by kirila on 30.3.17.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorHolder> {

    private final int LAYOUT = R.layout.item_color;

    private List<String> mColors;
    private WeakReference<FragmentActivity> mActivity;
    private WeakReference<ColorListener> mListener;

    public ColorAdapter(List<String> colors, FragmentActivity activity, ColorListener listener) {
        mColors = colors;
        mActivity = new WeakReference<>(activity);
        mListener = new WeakReference<>(listener);
    }

    @Override
    public ColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new ColorHolder(view);
    }

    @Override
    public void onBindViewHolder(ColorHolder holder, int position) {
        final String color = mColors.get(position);
        holder.bindView(color);
        holder.mColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.get().colorSelected(color);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mColors.size();
    }

    public class ColorHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color)
        TextView mColor;

        public ColorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(String color) {
            mColor.setBackgroundColor(Color.parseColor(color));
        }

    }
}
