package com.example.asus1.chnagefontprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SliderFont extends View {

    private Context mContext;
    private Drawable mThumb;
    private Paint mThumbPaint;
    private Paint mProgressPaint;

    private int mWidth;
    private int mHeight;
    private  int i = 0;
    private int mFontSize = 19;
    private int mSpec = 0;
    private int mCenterX;
    private int mCenterY;
    private float mLastX;
    private float mLastY;
    private int mPWidth;
    private int mSliderWidth;
    private int mOffsetLeft;
    private int mOffsetRight;
    private int mIndex;
    private int mLineHight = 30;
    private float[] fontSize = new float[]{
            12,14,16,19,22,24,26
    };


    private static final String TAG = "SliderFont";

    public SliderFont(Context context) {
        this(context,null);
    }

    public SliderFont(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SliderFont(Context context, @Nullable AttributeSet attrs
            , int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        //得到Thumb
        mThumb = mContext.getResources().getDrawable(R.mipmap.ic_thumb);
        mThumbPaint = new Paint();
        mProgressPaint = new Paint();
        //初始化字体所在的位置
        mIndex = 0;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST){
            mWidth = 500;
        }else {
            mWidth = width;
        }

        if(heightMode == MeasureSpec.AT_MOST){
            mHeight = 50;
        }else {
            mHeight = height;
        }


        setMeasuredDimension(mWidth,mHeight);
    }

    public void setParentWidth(int width){
        mPWidth = width-80;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mProgressPaint.setColor(mContext.getResources().getColor(R.color.font_line));
        mProgressPaint.setStyle(Paint.Style.FILL);
        for(i = 0;i<7;i++){
            //画竖着的线
            canvas.drawRect(mSpec*i+mOffsetLeft,(mHeight-mLineHight)/2,
                    mSpec*i+3f+mOffsetLeft,
                    (mHeight-mLineHight)/2+mLineHight,mProgressPaint);
            if(i!=6){
                //画横着的线
                canvas.drawRect(mSpec*i+mOffsetLeft,(mHeight-mLineHight)/2+mLineHight/2,
                        mSpec*(i+1)+mOffsetLeft,
                        (mHeight-mLineHight)/2+mLineHight/2+3f,mProgressPaint);
            }

        }
        //画thumb
        mThumb.setBounds(mCenterX-mHeight/2+5,mCenterY-mHeight/2+5,
                mCenterX+mHeight/2-5,mCenterY+mHeight/2-5);
        mThumb.draw(canvas);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //得到Slider的长度
        mSliderWidth = mWidth -getPaddingLeft()-getPaddingRight();
        //得到偏移量
        mOffsetLeft = getPaddingLeft();
        if(mOffsetLeft <=5){
            mOffsetLeft = 10;
        }
        //得到每一段的距离
        mSpec = mSliderWidth/(fontSize.length-1);
        //Thumb的中心位置
        mCenterX = mSpec*mIndex+mOffsetLeft;
        mCenterY = (mHeight-30)/2+15;
    }

    public void setCenter(float center){
        //当手指不滑动的时候，设置thumb的中心点的x坐标
        center =  center-(mPWidth-mWidth)/2;
        mCenterX = (int) (center);
    }

    public int adJustCenter(float local){
        //调整thumb所在的位置，
        //让thumb永久在点上
        local = local -(mPWidth-mWidth)/2;
        mIndex = (int) local/mSpec;
        if(mIndex<=0){
            mIndex = 0;
        }
        if(mIndex>=6){
            mIndex = 6;
        }
        mCenterX = mSpec*mIndex+mOffsetLeft;
        invalidate();

        return mIndex;
    }

    public float getFontSize(int index){
        return fontSize[index];
    }

    public int getIndex(){
        return  mIndex;
    }


    public void move(float spec){
        //当手势为MOVE时，改变thumb的位置
        mCenterX+=spec;
        if(mCenterX<=mOffsetLeft){
            mCenterX = mOffsetLeft;
        }
        if(mCenterX >=mWidth-mOffsetLeft){
            mCenterX = mWidth-mOffsetLeft;
        }
    }


}
