package viewset.com.animatorxml;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mTv1, mTv2;
    private int top;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.btn);
        mTv1 = findViewById(R.id.tv_1);
        mTv2 = findViewById(R.id.tv_2);

        top = mTv1.getTop();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSetAnimator();
            }
        });
    }

    private void doXmlAnimator() {
        ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.animator_1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int offset = (int) animation.getAnimatedValue();
                mTv1.layout(mTv1.getLeft(), top + offset, mTv1.getRight(), top + mTv1.getHeight() + offset);
            }
        });
        valueAnimator.start();
    }

    private void doObjectanimator() {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.objectanimator);
        animator.setTarget(mTv1);
        animator.start();
    }

    /**
     * <set
     *   android:ordering=["together" | "sequentially"]>
     *
     *     <objectAnimator
     *         android:propertyName="string"
     *         android:duration="int"
     *         android:valueFrom="float | int | color"
     *         android:valueTo="float | int | color"
     *         android:startOffset="int"
     *         android:repeatCount="int"
     *         android:repeatMode=["repeat" | "reverse"]
     *         android:valueType=["intType" | "floatType"]/>
     *
     *     <animator
     *         android:duration="int"
     *         android:valueFrom="float | int | color"
     *         android:valueTo="float | int | color"
     *         android:startOffset="int"
     *         android:repeatCount="int"
     *         android:repeatMode=["repeat" | "reverse"]
     *         android:valueType=["intType" | "floatType"]/>
     *
     *     <set>
     *         ...
     *     </set>
     * </set>
     */
    private void doSetAnimator() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.set_animator);
        set.setTarget(mTv1);
        set.start();
    }
}

