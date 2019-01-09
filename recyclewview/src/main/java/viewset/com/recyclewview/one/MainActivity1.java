package viewset.com.recyclewview.one;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import viewset.com.recyclewview.R;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Button linearBtn = (Button)findViewById(R.id.linear_activity_btn);
        Button gridBtn = (Button)findViewById(R.id.grid_activity_btn);
        Button staggerBtn = (Button)findViewById(R.id.stagger_activity_btn);


        linearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this,LinearActivity.class);
                startActivity(intent);
            }
        });

        gridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this,GridActivity.class);
                startActivity(intent);
            }
        });

        staggerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this,StaggeredActivity.class);
                startActivity(intent);
            }
        });


    }
}
