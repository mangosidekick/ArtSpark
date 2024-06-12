package com.mango.artsparkxml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.PointF;
import android.media.Image;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.mango.artsparkxml.Model.CardItem;
import com.mango.artsparkxml.Model.ImageModel;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View {
    private List<ImageModel> images = new ArrayList<>();
    private Paint paint = new Paint();

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private ImageModel currentImageData = null;
    private float lastTouchX, lastTouchY;
    private boolean isScaling = false;

    public CanvasView(Context context) {
        super(context);
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void addImage(Bitmap bitmap, String uri, float pointX, float pointY, float scaleX, float scaleY) {
        ImageModel imageData = new ImageModel();
        imageData.setBitmap(bitmap);
        imageData.setUri(uri);
        imageData.setX(pointX);
        imageData.setY(pointY);
        imageData.setScaleX(scaleX);
        imageData.setScaleY(scaleY);
        images.add(imageData);
        invalidate(); //trigger redraw
    }

    public List<ImageModel> getImages() {
        return images;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // DEFAULT color of the canvas
        int backgroundColor = Color.WHITE;
        canvas.drawColor(backgroundColor);

        for (ImageModel imageData : images) {
            canvas.save();
            canvas.translate(imageData.getX(), imageData.getY());
            canvas.scale(imageData.getScaleX(), imageData.getScaleY());
            canvas.drawBitmap(imageData.getBitmap(), 0, 0, paint);
            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);

        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = event.getX();
                final float y = event.getY();
                currentImageData = findTouchedImage(x, y);
                if (currentImageData != null) {
                    lastTouchX = x;
                    lastTouchY = y;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (currentImageData != null && !isScaling) {
                    final float x = event.getX();
                    final float y = event.getY();
                    final float dx = x - lastTouchX;
                    final float dy = y - lastTouchY;

                    currentImageData.setX(currentImageData.getX() + dx);
                    currentImageData.setY(currentImageData.getY() + dy);

                    lastTouchX = x;
                    lastTouchY = y;

                    invalidate(); // Redraw the view
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                currentImageData = null;
                break;
            }
        }

        return true;
    }

    private ImageModel findTouchedImage(float x, float y) {
        for (int i = images.size() - 1; i >= 0; i--) {
            ImageModel imageData = images.get(i);
            // Simple bounding box check, can be replaced with more precise hit detection
            float left = imageData.getX();
            float top = imageData.getY();
            float right = left + imageData.getBitmap().getWidth() * imageData.getScaleX();
            float bottom = top + imageData.getBitmap().getHeight() * imageData.getScaleY();
            if (x >= left && x <= right && y >= top && y <= bottom) {
                return imageData;
            }
        }
        return null;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (currentImageData != null) {
                float scaleFactor = detector.getScaleFactor();
                currentImageData.setScaleX(currentImageData.getScaleX() * scaleFactor);
                currentImageData.setScaleY(currentImageData.getScaleY() * scaleFactor);
                invalidate();
                isScaling = true;
            }
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            isScaling = true;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            isScaling = false;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // Implement double-tap behavior if needed
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            // Implement single-tap behavior if needed
            return true;
        }
    }
}
