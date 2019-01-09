package viewset.com.recyclewview.twoItemDecoration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import viewset.com.recyclewview.R;
import viewset.com.recyclewview.one.GridActivity;
import viewset.com.recyclewview.one.StaggeredActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button bt1 = (Button)findViewById(R.id.item_decoration1);
        Button gridBtn = (Button)findViewById(R.id.grid_activity_btn);
        Button staggerBtn = (Button)findViewById(R.id.stagger_activity_btn);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,Decoration1Activity.class);
                startActivity(intent);
            }
        });

        gridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,GridActivity.class);
                startActivity(intent);
            }
        });

        staggerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,StaggeredActivity.class);
                startActivity(intent);
            }
        });


    }
}
