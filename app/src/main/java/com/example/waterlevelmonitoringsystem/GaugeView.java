package com.example.waterlevelmonitoringsystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Typeface;


public class GaugeView extends View {
    private Paint backgroundPaint;
    private Paint gaugePaint;
    private Paint textPaint;
    private int tankCapacity;

    public GaugeView(Context context) {
        super(context);
        init();
    }

    public GaugeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.LTGRAY);
        backgroundPaint.setStyle(Paint.Style.FILL);

        gaugePaint = new Paint();
        gaugePaint.setColor(Color.parseColor("#297ccf")); // Custom color
        gaugePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

    }

    public void setTankCapacity(int capacity) {
        this.tankCapacity = capacity;
        invalidate(); // Redraw the gauge when the capacity changes
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Define the center of the view
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        // Calculate the radius of the gauge
        float radius = Math.min(centerX, centerY) - 20; // Adjust the margin as needed

        // Draw the background gauge
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);

        // Calculate the angle based on the tank capacity (adjust the range as needed)
        float angle = 180f * (tankCapacity / 100f);

        // Draw the gauge arc
        RectF rect = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rect, 180, angle, true, gaugePaint);

        // Draw a label
        String capacityText = tankCapacity + "cm";
        canvas.drawText(capacityText, centerX, centerY, textPaint);
    }
}



