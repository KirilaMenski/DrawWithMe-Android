package by.ansgar.drawwithme.ui.adapter;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import by.ansgar.drawwithme.R;
import by.ansgar.drawwithme.ui.listeners.SmilesListener;

/**
 * Created by kirila on 29.3.17.
 */

public class SmilesAdapter extends RecyclerView.Adapter<SmilesAdapter.SmilesHolder> {

    private final int LAYOUT = R.layout.item_smile;
    private final String PATH = "file:///android_asset/";
    private final String FOLDER = "smiles/";

    private List<String> mSmiles;
    WeakReference<FragmentActivity> mActivity;
    WeakReference<SmilesListener> mListener;

    public SmilesAdapter(List<String> smiles, FragmentActivity activity, SmilesListener listener) {
        mSmiles = smiles;
        mActivity = new WeakReference<>(activity);
        mListener = new WeakReference<>(listener);
    }

    @Override
    public SmilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new SmilesHolder(view);
    }

    @Override
    public void onBindViewHolder(SmilesHolder holder, int position) {
        final String smile = mSmiles.get(position);
        holder.bindViews(smile);
        holder.mSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.get().smileSelected(FOLDER + smile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSmiles.size();
    }

    public class SmilesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.smile)
        ImageView mSmile;

        public SmilesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(String smile) {
            try {
                Drawable image = Drawable.createFromStream(mActivity.get().getAssets().open(FOLDER + smile), null);
                mSmile.setImageDrawable(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
