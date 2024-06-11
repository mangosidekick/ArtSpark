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
import android.view.MotionEvent;
import android.view.View;

import com.mango.artsparkxml.Model.CardItem;
import com.mango.artsparkxml.Model.ImageModel;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View {
    private List<ImageModel> images = new ArrayList<>();
    private Paint paint = new Paint();
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private float[] matrixValues = new float[9];
    private float dX, dY;
    private float scale = 1f;
    private float oldDist = 1f;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private int mode = NONE;

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                } else if (mode == ZOOM && event.getPointerCount() == 2) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private int getImageAtPosition(float x, float y) {
        for (int i = images.size() - 1; i >= 0; i--) {
            ImageModel image = images.get(i);
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
