package com.myslideunlock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cj on 2017/7/3.
 */

public class SlideUnlockView2 extends View {

    private static final String TAG = "cj";

    private int defWidth ;
    private int defHeight ;

    private Paint paint;
    private Paint paint2;

    private boolean onBlock;

    private boolean ifOnMove;

    private float offset;
    private float lastX;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0:
                    Log.e(TAG, "没有解锁，滑回去" );
                    if(offset>0){
                        offset-=defWidth/20;
                        handler.sendEmptyMessageDelayed(0,10);
                        postInvalidate();
                    }else{
                        handler.removeCallbacksAndMessages(null);
                    }
                    break;
            }
        }
    };
    private Bitmap bg;
    private Bitmap icon;

    public SlideUnlockView2(Context context) {
        super(context);
    }

    public SlideUnlockView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.BLUE);

        bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg_slide);
        defWidth = bg.getWidth();
        Log.e(TAG, "SlideUnlockView2: "+defWidth );

        icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        defHeight = icon.getWidth();
        Log.e(TAG, "SlideUnlockView2: "+defHeight );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //如果是图片的话，图片是不会跟着设定的值变化的
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

//        if (widthMode == MeasureSpec.EXACTLY) {
//            defWidth = widthSize;
//        }
//        if (heightMode == MeasureSpec.EXACTLY) {
//            defHeight = heightSize;
//        }
        setMeasuredDimension(defWidth, defHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bg,0,0,null);

        if (ifOnMove) {

            if(offset<=0){
                offset = 0;
            }

            canvas.drawBitmap(icon,offset,0,null);

        } else {

            canvas.drawBitmap(icon,0,0,null);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();

        switch (event.getAction()) {
            // 当手指按下的时候，判断手指按下的位置是否在滑块上边
            case MotionEvent.ACTION_DOWN:
                if (ifOnBlock(event)) {
                    onBlock = true;
                    lastX = x;
                }else{
                    onBlock = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (onBlock) {
                    ifOnMove = true;
                    offset = Math.abs(x - lastX);
                    Log.e(TAG, "offset是：" + offset);
                    //往左滑，offset保持0
                    if (x <= defHeight / 2) {
                        offset = 0;
                    }
                    //超过最右端圆心，固定住。
                    if (x >= defWidth - defHeight / 2) {
                        offset = defWidth - defHeight;
                    }
                    postInvalidate();
                }

                break;
            case MotionEvent.ACTION_UP:

                if(onBlock){
                    if (x < defWidth - defHeight / 2) {
                        Log.e(TAG, "没有解锁");
                        Message message = handler.obtainMessage();
                        message.what = 0;
                        handler.sendMessage(message);
                    } else {
                        Log.e(TAG, "已经解锁");
                        onUnLockListener.unLock(true);
                    }
                }

                break;

        }
        return true;
    }


    public boolean ifOnBlock(MotionEvent event) {
        float x = event.getX();
        if (x < defHeight) {
            Log.e(TAG, "ifOnBlock: 在滑块上");
            return true;
        } else {
            Log.e(TAG, "ifOnBlock: 不在滑块上");
            return false;
        }
    }

    private SlideUnlockView.OnUnLockListener onUnLockListener;

    public void setOnUnLockListener(SlideUnlockView.OnUnLockListener onUnLockListener) {
        this.onUnLockListener = onUnLockListener;
    }

    public interface OnUnLockListener{
        void unLock(boolean lock);
    }
}
