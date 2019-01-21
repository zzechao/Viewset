package viewset.com.recyclewview.threeLayoutManager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import viewset.com.recyclewview.R;
import viewset.com.recyclewview.twoItemDecoration.LinearItemDecoration1;

public class LayoutManager2Activity extends AppCompatActivity {

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutmanager1);

        generateDatas();
        final RecyclerView mRecyclerView = findViewById(R.id.linear_recycler_view);

        LayoutManager2 layoutManager2 = new LayoutManager2();
        mRecyclerView.setLayoutManager(layoutManager2);

        mRecyclerView.addItemDecoration(new LinearItemDecoration1(this));

        RecyclerAdapter3 adapter = new RecyclerAdapter3(this, mDatas);
        mRecyclerView.setAdapter(adapter);
    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}
