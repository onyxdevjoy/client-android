package com.onyx.international.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.onyx.international.utils.ScaleUtil;

@SuppressLint("NewApi")
public class BaseActivityView extends RelativeLayout {

	protected Context mContext;
	private TopBarLayout mTopBarLayout;
	private LinearLayout mContainerLayout;
	
	public BaseActivityView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.initializer(context);
	}

	public BaseActivityView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.initializer(context);
	}
	
	public BaseActivityView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.initializer(context);
	}

	private void initializer(Context context) {
		this.mContext = context;
		mTopBarLayout = new TopBarLayout(this.mContext);
		RelativeLayout.LayoutParams topBarLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				ScaleUtil.scale(88));
		topBarLayoutParams.addRule(RelativeLayout.ALIGN_TOP);
		final int ID_TOP_BAR = 0x100000;
		mTopBarLayout.setId(ID_TOP_BAR);
		this.addView(mTopBarLayout, topBarLayoutParams);
		
		mContainerLayout = new LinearLayout(this.mContext);
		RelativeLayout.LayoutParams containerLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		mContainerLayout.setLayoutParams(containerLayoutParams);
		containerLayoutParams.addRule(RelativeLayout.BELOW, ID_TOP_BAR);
		this.addView(mContainerLayout, containerLayoutParams);
	}
	
	public void addContainerView(View view) {
		mContainerLayout.addView(view);
	}

	public void setTopBarTitle(int stringResID) {
		mTopBarLayout.setTitle(stringResID);
	}
	
	public void setTopBarImage(int resID) {
		mTopBarLayout.setTitleImage(resID);
	}

	public void setTopBarLeftButton(int drawableResID, int width, int height, OnClickListener onClickListener) {
		mTopBarLayout.setLeftButton(drawableResID, width, height, onClickListener);
	}
}
