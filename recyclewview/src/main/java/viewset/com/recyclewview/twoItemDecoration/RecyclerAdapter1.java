package viewset.com.recyclewview.twoItemDecoration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import viewset.com.recyclewview.R;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.OnClickListener;
import static android.support.v7.widget.RecyclerView.ViewHolder;

public class RecyclerAdapter1 extends Adapter<ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;
    private int mCreatedHolder;

    public static enum ITEM_TYPE {
        ITEM_TYPE_SECTION,
        ITEM_TYPE_ITEM
    }

    private int M_SECTION_ITEM_NUM = 10;

    public RecyclerAdapter1(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.e("ttt", "onCreateViewHolder" + mCreatedHolder);
//        mCreatedHolder++;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == ITEM_TYPE.ITEM_TYPE_ITEM.ordinal()) {
            return new NormalHolder(inflater.inflate(R.layout.item_layout, parent, false));
        }
        return new SectionHolder(inflater.inflate(R.layout.item_section_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Log.e("ttt", "onBindViewHolder");
        if (holder instanceof SectionHolder) {
            SectionHolder sectionHolder = (SectionHolder) holder;
            sectionHolder.mSectionTv.setText("第 " + position / M_SECTION_ITEM_NUM + "组");
        } else if (holder instanceof NormalHolder) {
            NormalHolder normalHolder = (NormalHolder) holder;
            normalHolder.mTV.setText(mDatas.get(position) + "大叔大叔大叔的大叔大叔大叔的蔷薇蔷薇");
            if (position % 2 != 0) {
                normalHolder.qqpoint.setVisibility(View.GONE);
            } else {
                normalHolder.qqpoint.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % M_SECTION_ITEM_NUM == 0) {
            return ITEM_TYPE.ITEM_TYPE_SECTION.ordinal();
        }
        return ITEM_TYPE.ITEM_TYPE_ITEM.ordinal();
    }

    public String getItem(int position) {
        if (mDatas == null && mDatas.isEmpty()) {
            throw new IllegalStateException("datas is null");
        }
        return mDatas.get(position);
    }

    public class NormalHolder extends ViewHolder {
        public TextView mTV;
        public TextView qqpoint;

        public NormalHolder(View itemView) {
            super(itemView);

            mTV = (TextView) itemView.findViewById(R.id.item_tv);
            mTV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mTV.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            qqpoint = itemView.findViewById(R.id.qqpoint);
        }
    }

    public class SectionHolder extends ViewHolder {

        public TextView mSectionTv;

        public SectionHolder(View itemView) {
            super(itemView);
            mSectionTv = (TextView) itemView.findViewById(R.id.item_section_tv);
        }
    }
}
