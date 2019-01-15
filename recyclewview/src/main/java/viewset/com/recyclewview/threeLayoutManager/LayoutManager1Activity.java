package viewset.com.recyclewview.threeLayoutManager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import viewset.com.recyclewview.R;
import viewset.com.recyclewview.one.RecyclerAdapter;
import viewset.com.recyclewview.twoItemDecoration.LinearItemDecoration1;

public class LayoutManager1Activity extends AppCompatActivity {

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutmanager1);

        generateDatas();
        final RecyclerView mRecyclerView = findViewById(R.id.linear_recycler_view);

        LayoutManager1 layoutManager1 = new LayoutManager1();
        mRecyclerView.setLayoutManager(layoutManager1);

        mRecyclerView.addItemDecoration(new LinearItemDecoration1(this));

        RecyclerAdapter adapter = new RecyclerAdapter(this, mDatas);
        mRecyclerView.setAdapter(adapter);
    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}
