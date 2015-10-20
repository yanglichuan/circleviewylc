package com.example.administrator.mycircleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.SoftReference;

public class MycircleView extends View {
    public MycircleView(Context context) {
        super(context);
    }

    private Paint mPaint;
    private Bitmap  softbitmap;
    public MycircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        softbitmap =
                BitmapFactory.decodeResource(context.getResources(), R.drawable.mmmm);

    }

    public MycircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private int mWidth = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(mWidth, mWidth);
    }

    private Bitmap createCircleImage(Bitmap source, int min)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        float width = source.getWidth();
        float height = source.getHeight();
        float tt = Math.min(width, height);
        float scacle  = min/tt;
        source = scacle(source, scacle);

        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);

        if(!source.isRecycled()){
            source.recycle();
        }

        return target;
    }

    private static Bitmap scacle(Bitmap bitmap, float sc) {
        Matrix matrix = new Matrix();
        matrix.postScale(sc,sc); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        bitmap.recycle();
        return resizeBmp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

            Bitmap bb =  createCircleImage(softbitmap,mWidth);
            canvas.drawBitmap(bb, null, new RectF(0,0,mWidth,mWidth), mPaint);


}
}
