package com.example.ex4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.MotionEventCompat;

public class Joystick extends View {

    private Paint paint;
    private int xJoystick;
    private int yJoystick;
    private int x;
    private int y;
    int radius;
    private boolean isMoving = false;
    boolean firstTime = true;

    //makes the joystick:
    public Joystick(Context context) {
        super(context);
    }

    //initalize first the joystick with primary values:
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstTime) {//first values not to be changed
            firstTime = false;
            radius = (int) (getWidth() / 2.5);
            xJoystick = getWidth() / 2;
            yJoystick = getHeight() / 2;
            x = getWidth();
            y = getHeight();
            paint = new Paint();
        }
        //drawing the Big Circle:

        paint.setStyle(Paint.Style.FILL);
        //paint for canvas:
        paint.setColor(Color.parseColor("#219CF3"));
        canvas.drawPaint(paint);
        //paint for Big Circle:
        paint.setColor(Color.parseColor("#E5E5E5"));
        canvas.drawCircle(x / 2, y / 2, radius, paint);
        //drawing the joystick:
        //paint for joystick:
        paint.setColor(Color.parseColor("#4bb017"));
        canvas.drawCircle(xJoystick, yJoystick, radius / 3, paint);
    }

    //moveable joystick and the aileron and elevator are updated in the simulator accordingly:
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                int pointerIndex = MotionEventCompat.getActionIndex(event);
                int tmpx = (int) event.getX();
                int tmpy = (int) event.getY();
                //see if we touched in the area of the inner circle:
                if ((Math.abs((Math.abs(tmpx) - Math.abs((x / 2)))) <= (radius / 3)) && (Math.abs((Math.abs(tmpy) - Math.abs((y / 2)))) <= (radius / 3))) {
                    isMoving = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!isMoving) {
                    return true;
                }
                int tmpx = (int) event.getX();
                int tmpy = (int) event.getY();
                double ail = (double) event.getX();//to be changed if needed
                double elev = (double) event.getY();//to be changed if needed
                //prevents the joystick to move out of the main circle:
                if (Math.abs(tmpx - (x / 2)) <= radius && Math.abs(tmpy - (y / 2)) <= radius) {
                    xJoystick = tmpx;
                    yJoystick = tmpy;
                    invalidate();
                    //now setting the aileron and elevator in the range of -1 to 1:
                    Connect connect = Connect.getInstance();
                    double dRadius = (double) radius;
                    double dx = (double) x;
                    double dy = (double) y;
                    ail = (Math.abs(ail - (dx / 2.0))) / dRadius;
                    elev = (Math.abs(elev - (dy / 2.0))) / dRadius;
                    //assigning sign to aileron and elevator:
                    if ((tmpx - (x / 2)) < 0) {
                        ail *= -1.0;
                    }
                    if ((tmpy - (y / 2)) > 0) {
                        elev *= -1.0;
                    }
                    //if was a bug that the joystick went out of the circle we give the max values:
                    if (ail > 1.0)
                        ail = 1.0;
                    else if (ail < -1.0)
                        ail = -1.0;
                    if (elev > 1.0)
                        elev = 1.0;
                    else if (elev < -1.0)
                        elev = -1.0;
                    //sending the values:
                    connect.setValue("aileron", ail);
                    connect.setValue("elevator", elev);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
            }
            case MotionEvent.ACTION_CANCEL: {
                isMoving = false;
                xJoystick = getWidth() / 2;
                yJoystick = getHeight() / 2;
                Connect connect = Connect.getInstance();
                connect.setValue("aileron", 0.0);
                connect.setValue("elevator", 0.0);
                invalidate();
                break;
            }

        }
        return true;
    }

}
