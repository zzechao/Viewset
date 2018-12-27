package viewset.com.qqpoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View1 view = new View1(this);
        view.setClickable(true);
        Button button = new Button(this);
        button.setText("reset");
        ((FrameLayout) findViewById(R.id.fl)).addView(view);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        ((FrameLayout) findViewById(R.id.fl)).addView(button, layoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.reset();
            }
        });
    }
}
