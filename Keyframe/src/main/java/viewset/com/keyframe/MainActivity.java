package viewset.com.keyframe;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
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
                doOfObject();
            }
        });
    }

    /**
     * public static PropertyValuesHolder ofKeyframe(String propertyName, Keyframe... values)
     * propertyName：动画所要操作的属性名
     * values：Keyframe的列表，PropertyValuesHolder会根据每个Keyframe的设定，定时将指定的值输出给动画。
     * <p>
     * <p>
     * Keyframe之ofFloat、ofInt与常用函数
     */
    private void doKeyFrame() {
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);

        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, frameHolder);
        animator.setDuration(1000);
        animator.start();
    }

    /**
     * Keyframe之ofFloat、ofInt与常用函数with插值器
     */
    private void doKeyFrameWithInterpolator() {
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.5f, 100f);
        frame1.setInterpolator(new BounceInterpolator());
        Keyframe frame2 = Keyframe.ofFloat(1f, 0f);
        frame2.setInterpolator(new LinearInterpolator());
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2);
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, frameHolder);
        animator.setDuration(1000);
        animator.start();
    }

    /**
     * Keyframe之ofObject
     */
    private void doOfObject() {
        Keyframe frame0 = Keyframe.ofObject(0f, new Character('A'));
        Keyframe frame1 = Keyframe.ofObject(0.1f, new Character('L'));
        Keyframe frame2 = Keyframe.ofObject(1, new Character('Z'));

        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("CharText", frame0, frame1, frame2);
        frameHolder.setEvaluator(new CharEvaluator());
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mMyTv, frameHolder);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Character character = (Character) animation.getAnimatedValue("CharText");
                mMyTv.setText(String.valueOf(character));
            }
        });
        animator.setDuration(3000);
        animator.start();
    }
}
