package com.example.chatme;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import androidx.appcompat.widget.AppCompatImageView;

public class ZoomableImageView extends AppCompatImageView {

    private Matrix matrix;
    private ScaleGestureDetector scaleGestureDetector;

    public ZoomableImageView(Context context) {
        super(context);
        init();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initImage();
    }

    public ZoomableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        matrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        canvas.setMatrix(matrix);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            setImageMatrix(matrix);
            return true;
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initImage();
    }

    private void initImage() {
        // Reset the matrix to start with the image centered and scaled to fit the screen
        if (getDrawable() != null) {
            matrix.reset();
            float scale;
            float viewWidth = getWidth();
            float viewHeight = getHeight();
            float drawableWidth = getDrawable().getIntrinsicWidth();
            float drawableHeight = getDrawable().getIntrinsicHeight();

            if (drawableWidth * viewHeight > viewWidth * drawableHeight) {
                scale = viewHeight / drawableHeight;
            } else {
                scale = viewWidth / drawableWidth;
            }

            float redundantXSpace = viewWidth - (scale * drawableWidth);
            float redundantYSpace = viewHeight - (scale * drawableHeight);

            matrix.postTranslate(redundantXSpace / 2, redundantYSpace / 2);
            matrix.postScale(scale, scale, viewWidth / 2, viewHeight / 2);

            // Log additional information for debugging
            Log.d("ZoomableImageView", "View dimensions: " + viewWidth + " x " + viewHeight);
            Log.d("ZoomableImageView", "Drawable dimensions: " + drawableWidth + " x " + drawableHeight);
            Log.d("ZoomableImageView", "Calculated scale: " + scale);
            Log.d("ZoomableImageView", "Redundant X space: " + redundantXSpace);
            Log.d("ZoomableImageView", "Redundant Y space: " + redundantYSpace);

            setImageMatrix(matrix);
        }
    }

}
