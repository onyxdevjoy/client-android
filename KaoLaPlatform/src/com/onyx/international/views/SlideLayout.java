package com.onyx.international.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.onyx.international.kaolaplatform.R;
import com.onyx.international.utils.ScaleUtil;

public class SlideLayout extends BaseActivityView{

	private int MAX_X;
	private int MIN_X;
	private boolean mSlideLocked;
	private View mCoverView;
	private Context mContext;
	
	public SlideLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.init();
	}

	public SlideLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.init();
	}

	public SlideLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.init();
	}

	private void init() {
		MAX_X = ScaleUtil.scale(180);
		MIN_X = 0;
		mCoverView = new View(this.mContext);
		mCoverView.setBackgroundResource(android.R.color.transparent);
//		mCoverView.getBackground().setAlpha(0);
		mCoverView.setVisibility(View.GONE);
		LinearLayout.LayoutParams coverViewLayoutParams = new LinearLayout.LayoutParams(
				-1, -1);
		mCoverView.setLayoutParams(coverViewLayoutParams);
		mCoverView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				slideLeft();
			}
		});
		this.addView(mCoverView);

		setTopBarLeftButton(R.drawable.btn_leftbar, 84, 58, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				slide();
			}
		});
	}
	
	public void slide() {
		float x = this.getLeft();
		
		if (x == 0) {
			slideRight();
		} else {
			slideLeft();
		}
	}
	
	private void slideX(final int toX) {
		if (mSlideLocked)
			return;
		
		mSlideLocked = true;

		TranslateAnimation translateAnimation = new TranslateAnimation(0, toX, 0, 0);
		translateAnimation.setDuration(200);
		translateAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				mSlideLocked = false;
				if (toX > 0) {
					mCoverView.setVisibility(View.VISIBLE);
				} else {
					mCoverView.setVisibility(View.GONE);
				}

				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) SlideLayout.this.getLayoutParams();
				layoutParams.setMargins(toX, 0, -toX, 0);
				SlideLayout.this.setLayoutParams(layoutParams);
				
				SlideLayout.this.clearAnimation();
			}
		});
		this.startAnimation(translateAnimation);
	}
	
	public void slideRight() {
		this.slideX(this.MAX_X);
	}
	
	public void slideLeft() {
		this.slideX(this.MIN_X);
	}
}
