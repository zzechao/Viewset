package viewset.com.recyclewview.twoItemDecoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import viewset.com.recyclewview.DensityUtil;
import viewset.com.recyclewview.R;

/**
 * https://blog.csdn.net/cx1229/article/details/54091669
 */
public class LinearItemDecoration2 extends RecyclerView.ItemDecoration {

    private final Bitmap mMedalBmp;
    private final Paint.FontMetrics fontMetrics;
    private final int mTopHeight;
    private final int alignBottom;
    Paint paint;

    Paint paintT;

    public LinearItemDecoration2(Context context) {
        Resources res = context.getResources();

        paint = new Paint();
        paint.setColor(Color.parseColor("#ff669900"));
        paint.setAntiAlias(true);
        BitmapFactory.Options options = new BitmapFactory.Options();

        //
        paintT = new TextPaint();
        paintT.setAntiAlias(true);
        paintT.setStyle(Paint.Style.FILL);
        paintT.setStrokeWidth(8);
        paintT.setTextSize(DensityUtil.dp2px(context, 14));
        paintT.setColor(Color.DKGRAY);
        paintT.setTextAlign(Paint.Align.CENTER);
        fontMetrics = paintT.getFontMetrics();

        // 缩小至原大小的1/2
        options.inSampleSize = 2;
        mMedalBmp = BitmapFactory.decodeResource(res, R.mipmap.jiang, options);

        //决定悬浮栏的高度等
        mTopHeight = DensityUtil.dp2px(context, 40);
        //决定文本的显示位置等
        alignBottom = DensityUtil.dp2px(context, 10);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();

        int parentWidth = parent.getMeasuredWidth();

        for (int i = 0; i != parent.getChildCount(); i++) {
            // 直接获得的child只有当前显示的，所以就算i是0的index也只是当前第一个，而不是所有第一个
            View child = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(child);
            if (isFirstInGroup(pos)) {
                // 每组第一个item都留有空间来绘制
                int top = child.getTop() - mTopHeight;
                int bottom = child.getTop();
                // 绘制背景色
                canvas.drawRect(left, top, right, bottom, paint);
                // 绘制组名,居中,paintT.setTextAlign(Paint.Align.CENTER);对应
                String groupName = getGroupName(pos);
                float textLeft = left + parentWidth / 2;
                float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
                float textBottom = top + mTopHeight / 2 + distance;
                canvas.drawText(groupName, textLeft, textBottom, paintT);

                // jiang图片的设置
                float bitLeft = left;
                float bitTop = child.getTop() - mTopHeight / 2 - mMedalBmp.getHeight() / 2;
                canvas.drawBitmap(mMedalBmp, bitLeft, bitTop, paint);
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int parentWidth = parent.getMeasuredWidth();

        canvas.drawRect(left, 0, right, mTopHeight, paint);

        String groupName = getGroupName(pos);
        float textLeft = left + parentWidth / 2;
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float textBottom = mTopHeight / 2 + distance;
        canvas.drawText(groupName, textLeft, textBottom, paintT);

        // jiang图片的设置
        float bitLeft = left;
        float bitTop = mTopHeight / 2 - mMedalBmp.getHeight() / 2;
        canvas.drawBitmap(mMedalBmp, bitLeft, bitTop, paint);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        //只有是同一组的第一个才显示悬浮栏
        if (isFirstInGroup(pos)) {
            outRect.top = mTopHeight;
        } else {
            outRect.top = 0;
        }
    }

    /**
     * 判断是不是组中的第一个位置
     *
     * @param pos
     * @return
     */
    private boolean isFirstInGroup(int pos) {
        return pos == 0 || pos % 10 == 0;
    }

    /**
     * 获取数组的名字
     *
     * @param pos
     * @return
     */
    private String getGroupName(int pos) {
        return "第" + pos / 10 + "组";
    }
}
