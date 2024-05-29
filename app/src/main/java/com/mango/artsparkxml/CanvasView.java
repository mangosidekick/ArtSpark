package com.mango.artsparkxml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View {
    private List<CardItem.Image> images;
    private Paint paint;
    private int activePointerId = INVALID_POINTER_ID;
    private static final int INVALID_POINTER_ID = -1;
    private float lastTouchX, lastTouchY;
    private int selectedImageIndex = -1;

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        images = new ArrayList<>();
        paint = new Paint();
    }

    public void setMoodboard(CardItem moodboard) {
        this.images = moodboard.getImages();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (CardItem.Image image : images) {
            drawImage(canvas, image);
        }
    }

    private void drawImage(Canvas canvas, CardItem.Image image) {
        // Load the bitmap (this should be done more efficiently in a real app)
        Bitmap bitmap = BitmapFactory.decodeFile(image.getUri());
        if (bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.postScale(image.getScaleX(), image.getScaleY());
            matrix.postTranslate(image.getX(), image.getY());
            canvas.drawBitmap(bitmap, matrix, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = event.getActionIndex();
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);

                activePointerId = event.getPointerId(0);
                lastTouchX = x;
                lastTouchY = y;

                selectedImageIndex = getImageAtPosition(x, y);

                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = event.findPointerIndex(activePointerId);
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);

                if (selectedImageIndex != -1) {
                    CardItem.Image selectedImage = images.get(selectedImageIndex);

                    // Handle movement and scaling separately
                    if (event.getPointerCount() == 1) {
                        // Move the image
                        final float dx = x - lastTouchX;
                        final float dy = y - lastTouchY;

                        selectedImage.setX(selectedImage.getX() + dx);
                        selectedImage.setY(selectedImage.getY() + dy);
                    } else if (event.getPointerCount() == 2) {
                        // Scale the image
                        float newDistX = Math.abs(event.getX(0) - event.getX(1));
                        float newDistY = Math.abs(event.getY(0) - event.getY(1));

                        float oldDistX = Math.abs(lastTouchX - event.getX(1));
                        float oldDistY = Math.abs(lastTouchY - event.getY(1));

                        selectedImage.setScaleX(selectedImage.getScaleX() * (newDistX / oldDistX));
                        selectedImage.setScaleY(selectedImage.getScaleY() * (newDistY / oldDistY));
                    }

                    invalidate();

                    lastTouchX = x;
                    lastTouchY = y;
                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                activePointerId = INVALID_POINTER_ID;
                selectedImageIndex = -1;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);

                if (pointerId == activePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    lastTouchX = event.getX(newPointerIndex);
                    lastTouchY = event.getY(newPointerIndex);
                    activePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            }
        }

        return true;
    }

    private int getImageAtPosition(float x, float y) {
        for (int i = images.size() - 1; i >= 0; i--) {
            CardItem.Image image = images.get(i);
            Bitmap bitmap = BitmapFactory.decodeFile(image.getUri());

            if (bitmap != null) {
                float imageLeft = image.getX();
                float imageTop = image.getY();
                float imageRight = imageLeft + bitmap.getWidth() * image.getScaleX();
                float imageBottom = imageTop + bitmap.getHeight() * image.getScaleY();

                if (x >= imageLeft && x <= imageRight && y >= imageTop && y <= imageBottom) {
                    return i;
                }
            }
        }
        return -1;
    }
}
