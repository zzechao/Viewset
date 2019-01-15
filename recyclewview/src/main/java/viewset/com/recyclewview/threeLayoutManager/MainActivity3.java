package viewset.com.recyclewview.threeLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import viewset.com.recyclewview.R;
import viewset.com.recyclewview.twoItemDecoration.Decoration2Activity;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button bt1 = findViewById(R.id.item_layoutmanager1);
        Button bt2 = findViewById(R.id.item_layoutmanager2);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, LayoutManager1Activity.class);
                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, Decoration2Activity.class);
                startActivity(intent);
            }
        });
    }
}
