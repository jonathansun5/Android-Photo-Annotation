package com.example.jonathansun5.photoannotation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawableView extends View {
    public int width;
    public  int height;
    private boolean isEditable;
    private Path drawPath;
    private Paint drawPaint;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

    public DrawableView(Context context) {
        super(context);
    }
    public DrawableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.canvasPaint = new Paint(Paint.DITHER_FLAG);
        setupDrawing();
    }
    public DrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.height = h;
        this.width = w;
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }
    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        int paintColor = Color.RED;
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setDither(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStrokeWidth(10);
    }
    public void setDrawingEnabled(boolean isEditable){
        this.isEditable = isEditable;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isEditable){
            float touchX = event.getX();
            float touchY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawPath.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drawPath.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    drawPath.lineTo(touchX, touchY);
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath = new Path();
                    break;
                default:
                    return false;
            }
        } else{
            return false;
        }
        invalidate();
        return true;
    }

    public void clearCanvas() {
        canvasBitmap.eraseColor(Color.TRANSPARENT);
        drawPath.reset();
        invalidate();
    }

    public void onClickEraser(boolean isEraserOn) {
        Canvas tDrawCanvas = new Canvas();
        Bitmap tCanvasBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        tDrawCanvas.setBitmap(tCanvasBitmap);
        if (isEraserOn) {
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else {
            drawPaint.setXfermode(null);
        }
        drawCanvas.drawPath(drawPath, drawPaint);
    }

    public void setPaintColor(int color) {
        drawPaint.setColor(color);
    }

    public void setBrushEraserSize(int size) {
        drawPaint.setStrokeWidth(size);
    }
}
