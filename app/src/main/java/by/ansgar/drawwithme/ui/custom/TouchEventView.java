package by.ansgar.drawwithme.ui.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import by.ansgar.drawwithme.R;

/**
 * Created by kirila on 31.3.17.
 */

public class TouchEventView extends View {

    private final float PENCIL_WIDTH = 5f;
    private final float ERASER_WIDTH = 50f;

    private boolean mToolsPencil = true;
    private boolean mToolsHand;
    private boolean mToolsFilling;
    private boolean mToolsEraser;

    private Path mDrawPath;
    private Paint mDrawPaint, mCanvasPaint;
    private int mPaintColor = Color.BLACK;
    private Canvas mDrawCanvas;
    private Bitmap mCanvasBitmap;

    public TouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpDrawing();
    }

    private void setUpDrawing() {
        mDrawPath = new Path();
        mDrawPaint = new Paint();
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setStrokeWidth(PENCIL_WIDTH);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
        mCanvasPaint = new Paint(Paint.DITHER_FLAG);

        mCanvasBitmap = Bitmap.createBitmap((int) getResources().getDimension(R.dimen.draw_width),
                (int) getResources().getDimension(R.dimen.draw_height), Bitmap.Config.ARGB_8888);
        mDrawCanvas = new Canvas(mCanvasBitmap);
        setBackgroundColor(Color.WHITE);
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        mDrawCanvas = new Canvas(mCanvasBitmap);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mCanvasBitmap, 0, 0, mCanvasPaint);
        canvas.drawPath(mDrawPath, mDrawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDrawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                mDrawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                mDrawCanvas.drawPath(mDrawPath, mDrawPaint);
                mDrawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void setColor(String color) {
        invalidate();
        mPaintColor = Color.parseColor(color);
        mDrawPaint.setColor(mPaintColor);
    }

    public void setToolsPencil(boolean toolsPencil, String color) {
        invalidate();
        mToolsPencil = toolsPencil;
        mToolsEraser = false;
        mToolsFilling = false;
        mToolsHand = false;
        mPaintColor = Color.parseColor(color);
        mDrawPaint.setColor(mPaintColor);
        mDrawPaint.setStrokeWidth(PENCIL_WIDTH);
    }

    public void setToolsHand(boolean toolsHand) {
        invalidate();
        mToolsHand = toolsHand;
    }

    public void setToolsFilling(boolean toolsFilling) {
        invalidate();
        mToolsFilling = toolsFilling;
    }

    public void setToolsEraser(boolean toolsEraser) {
        invalidate();
        mToolsEraser = toolsEraser;
        mToolsFilling = false;
        mToolsHand = false;
        mToolsPencil = false;
        mDrawPaint.setColor(Color.WHITE);
        mDrawPaint.setStrokeWidth(ERASER_WIDTH);
    }
}
