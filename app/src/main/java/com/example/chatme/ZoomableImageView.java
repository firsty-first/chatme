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
    private static final float MIN_SCALE_FACTOR = 0.5f;
    private static final float MAX_SCALE_FACTOR = 2.0f;
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
            float newScale = calculateNewScale(scaleFactor);
            matrix.postScale(newScale, newScale, detector.getFocusX(), detector.getFocusY());
            setImageMatrix(matrix);
            return true;
        }

        private float calculateNewScale(float scaleFactor) {
            float currentScale = getScaleX();
            float newScale = currentScale * scaleFactor;
            newScale = Math.max(MIN_SCALE_FACTOR, Math.min(newScale, MAX_SCALE_FACTOR));
            return newScale;
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initImage();
    }

    private void initImage() {
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

            scale = Math.max(MIN_SCALE_FACTOR, Math.min(scale, MAX_SCALE_FACTOR));

            float redundantXSpace = viewWidth - (scale * drawableWidth);
            float redundantYSpace = viewHeight - (scale * drawableHeight);

            matrix.postTranslate(redundantXSpace / 2, redundantYSpace / 2);
            matrix.postScale(scale, scale, viewWidth / 2, viewHeight / 2);

            Log.d("ZoomableImageView", "View dimensions: " + viewWidth + " x " + viewHeight);
            Log.d("ZoomableImageView", "Drawable dimensions: " + drawableWidth + " x " + drawableHeight);
            Log.d("ZoomableImageView", "Calculated scale: " + scale);
            Log.d("ZoomableImageView", "Redundant X space: " + redundantXSpace);
            Log.d("ZoomableImageView", "Redundant Y space: " + redundantYSpace);

            setImageMatrix(matrix);
        }
    }
}
