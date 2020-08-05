package tech.greedylabs.eraserhardness;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class MyCustomView extends View
{
    private Bitmap mSourceBitmap;
    private Canvas mSourceCanvas = new Canvas();
    private Paint mDestPaint = new Paint();
    private Path mDestPath = new Path();
    private int hardness;

    public void setHardness(int hardness) {
        this.hardness = hardness;
        if(getHardness()==101)
        {
            mDestPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            mDestPaint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
        }
        else {
            mDestPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
            mDestPaint.setMaskFilter(new BlurMaskFilter(40, BlurMaskFilter.Blur.NORMAL));
            mDestPaint.setARGB(255-getHardness(), 255,255,255);
        }
    }

    public int getHardness() {
        return hardness;
    }

    public MyCustomView(Context context)
    {
        super(context);
        //convert drawable file into bitmap
        Bitmap rawBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);

        //convert bitmap into mutable bitmap
        mSourceBitmap = Bitmap.createBitmap(rawBitmap.getWidth(), rawBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        float left = 0;
        float top = 0;
        RectF dst = new RectF(left, top, left + dpToPixels(getResources().getDisplayMetrics(), 200) , top + dpToPixels(getResources().getDisplayMetrics(), 200) );
        mSourceCanvas.setBitmap(mSourceBitmap);
        mSourceCanvas.drawBitmap(rawBitmap, null, dst, null);
        mDestPaint.setAlpha(0);
        mDestPaint.setAntiAlias(true);
        mDestPaint.setStyle(Paint.Style.STROKE);
        mDestPaint.setStrokeJoin(Paint.Join.ROUND);
        mDestPaint.setStrokeCap(Paint.Cap.ROUND);
        mDestPaint.setStrokeWidth(40);
    }

    public static int dpToPixels(final DisplayMetrics display_metrics, final float dps) {
        final float scale = display_metrics.density;
        return (int) (dps * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        //Draw path
        mSourceCanvas.drawPath(mDestPath, mDestPaint);

        //Draw bitmap
        canvas.drawBitmap(mSourceBitmap, 0, 0, null);

        super.onDraw(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mDestPath.moveTo(xPos, yPos);
                break;

            case MotionEvent.ACTION_MOVE:
                mDestPath.lineTo(xPos, yPos);
                break;

            default:
                return false;
        }

        invalidate();
        return true;
    }
}
