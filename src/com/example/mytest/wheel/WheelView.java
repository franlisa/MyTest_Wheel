package com.example.mytest.wheel;

import java.util.ArrayList;

import com.example.mytest_wheel.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;


public class WheelView extends TextView implements AnimationListener{
	    private static final int ANIMATION_DURATION = 500;
	    private int ani_duration = ANIMATION_DURATION;
	    private float height;
	    private AnimationSet mAnimatorStartSet;
	    private AnimationSet mAnimatorEndSet;
	    private Handler mHandler = new Handler(Looper.getMainLooper());
	    private Runnable mRunnable;
	    private String mText;
//	    private String[] names = {"list 1", "list 2", "list 3", "list 4", "list 5"};
	    private ArrayList<WheelItem> itemList;
	    private int index = 0;
	    private  Drawable image;
	    public WheelView(Context context) {
	        super(context);
	    }

	    public WheelView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }
	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    // TODO Auto-generated method stub
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    Log.e("fxj", "onMeasure."+height);
	    }

	    @Override
	    protected void onDraw(Canvas canvas) {
	        super.onDraw(canvas);
	        height = getHeight();
	        Log.e("fxj", "ondraw,h="+height);
	    }
	    @Override
	    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
	    // TODO Auto-generated method stub
	    super.onLayout(changed, left, top, right, bottom);
	    Log.e("fxj", "onLayout,"+height);
	    }
//	   一条轮播的内容，例如有图片url等等这样的
	   public static class WheelItem {
			public String title;
			public WheelItem(String title) {
				this.title = title;
			}
		}
	    public void setData(ArrayList<WheelItem> items){
	    	this.itemList = items;
	    }
	    //向上离开的动画
	    private void initStartAnimation() {
	    	Animation translate = new TranslateAnimation(0, 0, 0, -57);//这个57是后来根据ondraw后才设置的。本来是用-height;但是发现调用startScroll时候ondraw根本还没调到
	    	Animation alpha = new AlphaAnimation(1f, 0f);
	    	mAnimatorStartSet = new AnimationSet(true);
	    	mAnimatorStartSet.addAnimation(translate);
	    	mAnimatorStartSet.addAnimation(alpha);
	    	mAnimatorStartSet.setDuration(ani_duration);
	    	mAnimatorStartSet.setAnimationListener(this);
	    }

	    //从下进来的动画
	    private void initEndAnimation() {
	    	Animation translate = new TranslateAnimation(0, 0, 57, 0);
	    	Animation alpha = new AlphaAnimation(0f, 1f);
	    	mAnimatorEndSet = new AnimationSet(true);
	    	mAnimatorEndSet.addAnimation(translate);
	    	mAnimatorEndSet.addAnimation(alpha);
	    	mAnimatorEndSet.setDuration(ani_duration);
	    	
	    }

	    public void BeginAni(String text) {
	        if (TextUtils.isEmpty(text)) {
	            return;
	        }
	        mText = text;
	        if (null == mAnimatorStartSet) {
	            initStartAnimation();
	        }
	        this.startAnimation(mAnimatorStartSet);
	    }
	    public void startScroll(){
	    	Log.e("fxj", "startscroll,h="+height);
	    	if(itemList == null)
	    		return;
	        if(mHandler == null){
	            mHandler = new Handler(Looper.getMainLooper());
	        }
	        if(mRunnable == null){
	            mRunnable = new Runnable() {
	                @Override
	                public void run() {
	                    String text =itemList.get(index).title;
	                    //这里直接从本地拿图，其实很多时候是从网络下载，所以这里可以开一个下载线程，但一定要注意时序性，这里没把image放到wheelItem里，考虑内存
	                    image = getResources().getDrawable( R.drawable.ic_launcher );
	                   
	                    BeginAni(text);
	                    mHandler.postDelayed(mRunnable,ani_duration*4);
	                }
	            };
	        }
	        mHandler.post(mRunnable);
	    }

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
     		setText(mText);
     		if(image != null){
     			image.setBounds( 0, 0, 50, 50 );
        		setCompoundDrawables( image, null, null, null );
        		setCompoundDrawablePadding(10);
     		}
     		if (null == mAnimatorEndSet) {
	            initEndAnimation();
	        }
	       this.startAnimation(mAnimatorEndSet);
	       if (index >= itemList.size()-1) {
				index = 0;
			} else {
				index++;
			}
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		public void remove(){
			if(mHandler != null) {
				mHandler.removeCallbacks(mRunnable);
			}
		}
	   
}
	  
