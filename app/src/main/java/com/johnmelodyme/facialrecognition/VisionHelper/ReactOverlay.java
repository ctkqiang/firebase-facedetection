package com.johnmelodyme.facialrecognition.VisionHelper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class ReactOverlay extends GraphicOverlay.Graphic{
    private int RECT_COLOUR = Color.GREEN;
    private float STROKE_WIDTH = 0x1.0p2f; // 4.0f
    private Paint RECT_PAINT;
    private GraphicOverlay GRAPHIC_OVERLAY;
    private Rect RECT;

    public ReactOverlay(GraphicOverlay overlay, Rect rect) {
        super(overlay);

        RECT_PAINT = new Paint();
        RECT_PAINT.setColor(RECT_COLOUR);
        RECT_PAINT.setStyle(Paint.Style.STROKE);
        RECT_PAINT.setStrokeWidth(STROKE_WIDTH);

        this.GRAPHIC_OVERLAY = overlay;
        this.RECT = rect;
        
        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        RectF RECT_F;
        RECT_F = new RectF();

        RECT_F.left = translateX(RECT_F.left);
        RECT_F.right = translateX(RECT_F.right);
        RECT_F.top = translateY(RECT_F.top);
        RECT_F.bottom = translateY(RECT_F.bottom);

        canvas.drawRect(RECT_F, RECT_PAINT);
    }
}
