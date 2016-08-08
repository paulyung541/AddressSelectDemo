package com.ysy.meituanaddressselect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yang on 2016/7/25.
 */
public class EdgeLabelView extends View {
    private TextPaint mPaint;

    private TextView mDialog;

    private boolean isDown = false;

    String[] mValues = new String[]{
            "县区", "定位", "最近", "热门", "A", "B", "C", "D",
            "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q",
            "R", "S", "T", "W", "X", "Y", "Z"
    };

    private OnSlidingTouchListener mListener;

    public EdgeLabelView(Context context) {
        this(context, null);
    }

    public EdgeLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new TextPaint();
        mPaint.setColor(Color.parseColor("#6ADACF"));
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //按下
        if (isDown) {
            canvas.drawColor(Color.parseColor("#40000000"));
        }

        int sigleHeight = getHeight() / mValues.length;
        for (int i = 0; i < mValues.length; i++) {
            String text = mValues[i];
            float xPoint = (getWidth() - mPaint.measureText(mValues[i])) / 2;
            float yPoint = sigleHeight * (i + 1);
            canvas.drawText(text, xPoint, yPoint, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int position = (int) (event.getY() / getHeight() * mValues.length);
        if (position < 0 || position >= mValues.length) {
            isDown = false;
            if (mDialog != null) {
                mDialog.setVisibility(GONE);
            }
            invalidate();
            return super.onTouchEvent(event);
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                if (mListener != null) {
                    mListener.onSlidingTouch(mValues[position]);
                }
                if (mDialog != null) {
                    mDialog.setVisibility(VISIBLE);
                    mDialog.setText(mValues[position]);
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                isDown = true;
                //todo: 在中心显示选中的名称
                if (mListener != null) {
                    mListener.onSlidingTouch(mValues[position]);
                }
                if (mDialog != null) {
                    mDialog.setVisibility(VISIBLE);
                    mDialog.setText(mValues[position]);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDown = false;
                //todo: 中心取消显示
                if (mDialog != null) {
                    mDialog.setVisibility(GONE);
                }
                if (mListener != null) {
                    mListener.onSlidingTouch(mValues[position]);
                }
                invalidate();
        }

        return super.onTouchEvent(event);
    }

    public void setDialog(TextView tv) {
        mDialog = tv;
    }

    public void setOnSlidingTouchListener(OnSlidingTouchListener listener) {
        mListener = listener;
    }

    public interface OnSlidingTouchListener {
        void onSlidingTouch(String str);
    }
}
