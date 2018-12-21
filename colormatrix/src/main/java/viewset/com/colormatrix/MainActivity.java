package viewset.com.colormatrix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * https://github.com/Nieyuwen/ViewStudy.git
         */
        ((FrameLayout) findViewById(R.id.fl)).addView(new MyView(this));
    }
}
