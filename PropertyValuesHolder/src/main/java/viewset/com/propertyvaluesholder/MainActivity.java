package viewset.com.propertyvaluesholder;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    Button start, cancel;

    TextView mMyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.iv_phone);

        start = findViewById(R.id.button);
        cancel = findViewById(R.id.button2);

        mMyTv = findViewById(R.id.textView);

        mMyTv.setText("sss");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOfObjectAnim();
            }
        });
    }

    void doPropertyValuesHolder() {
        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0xffffffff, 0xffff00ff, 0xffffff00, 0xffffffff);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, rotationHolder, colorHolder);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private void doOfObjectAnim() {
        PropertyValuesHolder charHolder = PropertyValuesHolder.ofObject("Text", new CharEvaluator(), new Character('A'), new Character('Z'));
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mMyTv, charHolder);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Character character = (Character) animation.getAnimatedValue("Text");
                mMyTv.setText(String.valueOf(character));
            }
        });
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

}
