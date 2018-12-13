package viewset.com.animatorset;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mTv1, mTv2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.btn);
        mTv1 = findViewById(R.id.tv_1);
        mTv2 = findViewById(R.id.tv_2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //doPlaySequentiallyAnimator();

                //doPlayTogetherAnimator();

                doAnimatorSetBuilder();
            }
        });
    }

    /**
     * 依次执行
     */
    private void doPlaySequentiallyAnimator() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mTv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mTv1, "translationY", 0, mTv1.getBottom(), 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mTv2, "translationY", 0, mTv2.getBottom(), 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    private void doPlayTogetherAnimator() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mTv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mTv1, "translationY", 0, mTv1.getBottom() - mTv1.getMeasuredHeight(), 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mTv2, "translationY", 0, mTv2.getBottom() - mTv2.getMeasuredHeight(), 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    /**
     * //和前面动画一起执行
     * public Builder with(Animator anim)
     * //执行前面的动画后才执行该动画
     * public Builder before(Animator anim)
     * //执行先执行这个动画再执行前面动画
     * public Builder after(Animator anim)
     * //延迟n毫秒之后执行动画
     * public Builder after(long delay)
     */
    private void doAnimatorSetBuilder() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mTv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mTv1, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = animatorSet.play(tv1BgAnimator);
        builder.with(tv1TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }
}

