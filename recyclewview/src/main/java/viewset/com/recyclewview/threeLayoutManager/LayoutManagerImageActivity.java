package viewset.com.recyclewview.threeLayoutManager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import viewset.com.recyclewview.R;

public class LayoutManagerImageActivity extends AppCompatActivity {

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutmanager1);

        //generateDatas();
        final RecyclerView mRecyclerView = findViewById(R.id.linear_recycler_view);

        ImageLayoutManager layoutManager = new ImageLayoutManager();
        mRecyclerView.setLayoutManager(layoutManager);

        ImageRecyclerAdapter adapter = new ImageRecyclerAdapter();
        mRecyclerView.setAdapter(adapter);
    }
}
