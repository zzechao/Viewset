package viewset.com.setcolorfilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View7 view = new View7(this);
        view.setClickable(true);
        ((FrameLayout) findViewById(R.id.fl)).addView(view);
        //view.startAnim();
    }
}
