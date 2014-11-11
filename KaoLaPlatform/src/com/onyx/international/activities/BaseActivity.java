package com.onyx.international.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.onyx.international.logger.Logger;
import com.onyx.international.utils.ScaleUtil;
import com.onyx.international.views.TopBarLayout;

public class BaseActivity extends Activity {

	@SuppressWarnings("unused")
	private View mContentView = null;
	private TopBarLayout mTopBarLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		relayoutContentView(layoutResID);
	}

	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub

		LinearLayout parentLayout = new LinearLayout(this);
		LinearLayout.LayoutParams parentLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		parentLayout.setOrientation(LinearLayout.VERTICAL);
		parentLayout.setLayoutParams(parentLayoutParams);
		
		mTopBarLayout = new TopBarLayout(this);
		LinearLayout.LayoutParams topBarLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				ScaleUtil.scale(88));
		topBarLayoutParams.gravity = Gravity.TOP;
		parentLayout.addView(mTopBarLayout, topBarLayoutParams);
		
		parentLayout.addView(view, parentLayoutParams);
		
		mContentView = parentLayout;
		
		super.setContentView(parentLayout);
	}
	
	public void relayoutContentView(int layoutResID) {
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		ViewGroup contentViewGroup = (ViewGroup) layoutInflater.inflate(layoutResID, null);
		contentViewGroup.setBackgroundColor(0xfff7f7f5);
		
		LinearLayout parentLayout = new LinearLayout(this);
		LinearLayout.LayoutParams parentLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		parentLayout.setOrientation(LinearLayout.VERTICAL);
		parentLayout.setLayoutParams(parentLayoutParams);
		
		mTopBarLayout = new TopBarLayout(this);
		LinearLayout.LayoutParams topBarLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				ScaleUtil.scale(88));
		topBarLayoutParams.gravity = Gravity.TOP;
		parentLayout.addView(mTopBarLayout, topBarLayoutParams);
		
		parentLayout.addView(contentViewGroup, parentLayoutParams);
		
		mContentView = parentLayout;
		
		super.setContentView(parentLayout);
	}
	
	public void setTopBarTitle(int stringResID) {
		mTopBarLayout.setTitle(stringResID);
	}
	
	public void setTopBarLeftButtonTypeBack() {
		mTopBarLayout.setLeftButtonTypeBack(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				BaseActivity.this.finish();
			}
		});
	}
	
	public void setTopBarLeftButton(int drawableResID, int width, int height, OnClickListener onClickListener) {
		mTopBarLayout.setLeftButton(drawableResID, width, height, onClickListener);
	}
	
	public TopBarLayout getTopBarLayout() {
		return mTopBarLayout;
	}
    
    protected void DEBUG(String message) {
    	Logger.getLogger(this.getClass()).debug(message);
    }
    
    public void setTitle(int resId) {
    	mTopBarLayout.setTitle(resId);
	}
    
    public void setTitle(String titleStr) {
    	mTopBarLayout.setTitle(titleStr);
	}
    
    public void setRightButton(int stringResID, android.view.View.OnClickListener onClickListener) {
    	mTopBarLayout.setRightButtonTitle(stringResID, onClickListener);
    }
    
    public void setLeftButtonTypeBack(android.view.View.OnClickListener onClickListener) {
    	mTopBarLayout.setLeftButtonTypeBack(onClickListener);
    }
    
    protected void showToastMessage(String message) {
    	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    protected void showToastMessage(int messageResId) {
    	Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
