package viewset.com.onmeasure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private int[] imgs = {R.mipmap.dog1, R.mipmap.dog2, R.mipmap.dog3, R.mipmap.dog4};

    WaterFallLayout2 wfl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Random random = new Random();
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = random.nextInt(3);
                int img = imgs[p];
                wfl.addImage(img);
            }
        });

        wfl = findViewById(R.id.wfl);
        wfl.setOnItemClick(new WaterFallLayout2.OnItemClick() {
            @Override
            public void itemClick(int position) {
                Toast.makeText(MainActivity2.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
