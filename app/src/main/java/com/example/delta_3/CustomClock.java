package com.example.delta_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomClock extends View {
       private float mWidth;
       private float mHeight;

       private float centreX;
       private float centreY;
       private Paint textPaint;
       private Paint dial;
       private Paint min_hand;
       private Paint sec_hand;

       private float minRadius;
       private float secRadius;

       private float MIN_FACTOR=7.5f;
       private int SEC_FACTOR=30;

    float labelRadiusMin;
    float labelRadiusSec;



    boolean reset=true;
    boolean start=false;
    boolean stop=false;


    private long milliSec;
    private int seconds;
    private int minutes=0;

    private float finalSecondX;
    private float finalSecondY;
    private float finalMinuteX;
    private float finalMinuteY;

    //MainActivity object;

       public CustomClock(Context context) {
        super(context);
        init();
    }

    public CustomClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){


           milliSec=1;
           seconds=0;
           minutes=0;

           //object=new MainActivity();

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40f);

         dial=new Paint(Paint.ANTI_ALIAS_FLAG);
         dial.setColor(Color.MAGENTA);
         dial.setStyle(Paint.Style.STROKE);
         dial.setStrokeWidth(5);

         min_hand=new Paint(Paint.ANTI_ALIAS_FLAG);
         min_hand.setColor(Color.GREEN);
         min_hand.setStyle(Paint.Style.STROKE);
         min_hand.setStrokeWidth(20f);

         sec_hand=new Paint(Paint.ANTI_ALIAS_FLAG);
        sec_hand.setColor(Color.BLACK);
        sec_hand.setStyle(Paint.Style.STROKE);
        sec_hand.setStrokeWidth(6f);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);

        mWidth=w;
        mHeight=h;
        centreX=mWidth/2;
        centreY=mHeight/2;
        minRadius=Math.min(mWidth,mHeight)/4;
        secRadius=Math.min(mWidth,mHeight)/2;
        labelRadiusMin=minRadius-25;
        labelRadiusSec=secRadius-50;
    }
    public float computeX(float pos,float radius,float constant){


          float x= (float) (centreX+radius*Math.sin(Math.PI*pos/constant));
          return x;
    }
    public float computeY(float pos,float radius,float constant){


        float y= (float) (centreY-radius*Math.cos(Math.PI*pos/constant));
        return y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //dial
        canvas.drawCircle(centreX,centreY,minRadius,dial);
        canvas.drawCircle(centreX,centreY,secRadius,dial);
        canvas.drawCircle(centreX,centreY,10,textPaint);

        //numbers or labels
        for(int i=1;i<=15;i++){
            canvas.drawText(Integer.toString(i),computeX(i,labelRadiusMin,MIN_FACTOR),computeY(i,labelRadiusMin,MIN_FACTOR),textPaint);
        }
        for(int i=1;i<=60;i++){
            canvas.drawText(Integer.toString(i),computeX(i,labelRadiusSec,SEC_FACTOR),computeY(i,labelRadiusSec,SEC_FACTOR),textPaint);
        }

        if(reset){
            final float initialMinuteX=computeX(15,labelRadiusMin,MIN_FACTOR);
            final float initialMinuteY=computeY(15,labelRadiusMin,MIN_FACTOR);
            final float initialSecondX=computeX(60,labelRadiusSec,SEC_FACTOR);
            final float initialSecondY=computeY(60,labelRadiusSec,SEC_FACTOR);

            canvas.drawLine(centreX,centreY,initialSecondX,initialSecondY,sec_hand);
            canvas.drawLine(centreX,centreY,initialMinuteX,initialMinuteY,min_hand);
        }
        else if(start){

            ClockStarted(canvas);

        }
        else if(stop){
            ClockStopped(canvas);


        }

       }
       public void resetButtonClicked(){
           reset=true;
           start=false;
           stop=false;
           milliSec=1;
           seconds=0;
           minutes=0;
           //object.changeToggleName();
           finalSecondX=computeX(60,labelRadiusSec,SEC_FACTOR);
           finalSecondY=computeY(60,labelRadiusSec,SEC_FACTOR);
           finalMinuteX=computeX(15,labelRadiusMin,MIN_FACTOR);
           finalMinuteY=computeY(15,labelRadiusMin,MIN_FACTOR);
           invalidate();


       }

    public void startButtonClicked(){
        reset=false;
        start=true;
        stop=false;
        invalidate();
    }
    public void stopButtonClicked(){
        reset=false;
        start=false;
        stop=true;
        invalidate();
    }

    public void ClockStarted(Canvas canvas) {

        // c=(c%60==0)?60:(c%60);

          /* finalSecondX=computeX(c,labelRadiusSec,SEC_FACTOR);
           finalSecondY=computeY(c,labelRadiusSec,SEC_FACTOR);
           canvas.drawLine(centreX,centreY,finalSecondX,finalSecondY,sec_hand);
           c++;*/
          if(minutes<15)
        {
            seconds = (int) (milliSec / 60);
        if (seconds == 0) {
              /* finalMinuteX=computeX(15,labelRadiusMin,MIN_FACTOR);
               finalMinuteY=computeY(15,labelRadiusMin,MIN_FACTOR);
               canvas.drawLine(centreX,centreY,finalMinuteX,finalMinuteY,min_hand);*/

            finalSecondX = computeX(60, labelRadiusSec, SEC_FACTOR);
            finalSecondY = computeY(60, labelRadiusSec, SEC_FACTOR);
        } else {

               /*finalMinuteX=computeX((milliSec/60),labelRadiusMin,MIN_FACTOR);
               finalMinuteY=computeY((milliSec/60),labelRadiusMin,MIN_FACTOR);
               canvas.drawLine(centreX,centreY,finalMinuteX,finalMinuteY,min_hand);*/

            finalSecondX = computeX(seconds, labelRadiusSec, SEC_FACTOR);
            finalSecondY = computeY(seconds, labelRadiusSec, SEC_FACTOR);
        }
        canvas.drawLine(centreX, centreY, finalSecondX, finalSecondY, sec_hand);
        minutes = seconds / 60;
        if (minutes == 0) {
            finalMinuteX = computeX(15, labelRadiusMin, MIN_FACTOR);
            finalMinuteY = computeY(15, labelRadiusMin, MIN_FACTOR);
            canvas.drawLine(centreX, centreY, finalMinuteX, finalMinuteY, min_hand);
        } else {
            finalMinuteX = computeX(minutes, labelRadiusMin, MIN_FACTOR);
            finalMinuteY = computeY(minutes, labelRadiusMin, MIN_FACTOR);
            canvas.drawLine(centreX, centreY, finalMinuteX, finalMinuteY, min_hand);

        }

        milliSec++;


        invalidate();
    }
          else{
              resetButtonClicked();
          }



    }
    public void ClockStopped(Canvas canvas){
        canvas.drawLine(centreX,centreY,finalSecondX,finalSecondY,sec_hand);
        canvas.drawLine(centreX,centreY,finalMinuteX,finalMinuteY,min_hand);
       }

}