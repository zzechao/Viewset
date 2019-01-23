package viewset.com.recyclewview.twoItemDecoration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import viewset.com.recyclewview.R;

public class Decoration1Activity extends AppCompatActivity {

    private ArrayList<String> mDatas = new ArrayList<>();

    private QQPointView qqPointView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdecoration1);

        generateDatas();
        final RecyclerView mRecyclerView = findViewById(R.id.linear_recycler_view);

        qqPointView = new QQPointView(this);
        qqPointView.setClickable(true);


        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        LinearItemDecoration1 itemDecoration1 = new LinearItemDecoration1(this);
        mRecyclerView.addItemDecoration(itemDecoration1);

        RecyclerAdapter2 adapter = new RecyclerAdapter2(this, mDatas);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}
