package com.example.asus1.chnagefontprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private RelativeLayout mFontP;
    private SliderFont mFontSlider;
    private TextView mTextView;
    private boolean mChangeFont = false;
    private float mLastX;
    private int mFontIndex;
    private int mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFontP = findViewById(R.id.relative);
        mFontSlider = findViewById(R.id.slider);
        mTextView = findViewById(R.id.tv_textview);
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        //其实最好的是得到mFontP的宽度，但是现在mFontP还没有绘制
        mFontSlider.setParentWidth(mScreenWidth);
        mFontSlider.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_MOVE){

            //判断Touch的位置是否在SliderFont上
            if(y>mFontP.getY() &&y<mFontP.getY()+mFontP.getHeight()+30 || mChangeFont){
                mChangeFont = true;
                float specX = x-mLastX;
                mFontSlider.move(specX);
                mLastX = x;
                mFontSlider.invalidate();
            }

        }else if(event.getAction() == MotionEvent.ACTION_DOWN){

            if(y>mFontP.getY() &&y<mFontP.getY()+mFontP.getHeight()){
                mChangeFont = true;
                mFontSlider.setCenter(x);
                mLastX = x;
                mFontSlider.invalidate();
            }

        }else if(event.getAction() == MotionEvent.ACTION_UP){
            //如果上面的事件确实滑动了SliderFont，就进行thumb调整
            if(mChangeFont){

                mFontIndex =  mFontSlider.adJustCenter(x);
                float fontSize = mFontSlider.getFontSize(mFontIndex);
               mTextView.setTextSize(fontSize);
                mLastX = x;
            }
            mChangeFont = false;

        }
        return true;
    }
}
