package com.onyx.international.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.onyx.international.kaolaplatform.R;
import com.onyx.international.utils.ScaleUtil;

public class TopBarLayout extends FrameLayout {

	private Context mContext;
	private Button mLeftButton;
	private Button mRightButton;
	private TextView mTitleTextView;
	private ImageView mTitleImageView;
	
	public TopBarLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.initializer(context);
	}

	public TopBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.initializer(context);
	}

	public TopBarLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.initializer(context);
	}

	private void initializer(Context context) {
		mContext = context;
		
		this.setBackgroundColor(0xffe8e7e3);
		
		mLeftButton = new Button(mContext);
		FrameLayout.LayoutParams leftButtonLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		leftButtonLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		leftButtonLayoutParams.leftMargin = ScaleUtil.scale(15);
		mLeftButton.setBackgroundColor(0x00000000);
		mLeftButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.title_text_size));
		this.addView(mLeftButton, leftButtonLayoutParams);
		
		mRightButton = new Button(mContext);
		FrameLayout.LayoutParams rightButtonLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		rightButtonLayoutParams.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
		rightButtonLayoutParams.leftMargin = ScaleUtil.scale(15);
		mRightButton.setBackgroundColor(0x00000000);
		mRightButton.setTextColor(0xff6f9147);
		mRightButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.title_text_size));
		this.addView(mRightButton, rightButtonLayoutParams);

		mTitleTextView = new TextView(mContext);
		FrameLayout.LayoutParams titleTextViewLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		titleTextViewLayoutParams.gravity = Gravity.CENTER;
		mTitleTextView.setTextColor(0xff6f9147);
		mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.title_text_size));
		this.addView(mTitleTextView, titleTextViewLayoutParams);

		mTitleImageView = new ImageView(mContext);
		FrameLayout.LayoutParams titleImageViewLayoutParams = new FrameLayout.LayoutParams(ScaleUtil.scale(72),
				ScaleUtil.scale(72));
		titleImageViewLayoutParams.gravity = Gravity.CENTER;
		this.addView(mTitleImageView, titleImageViewLayoutParams);
	}
	
	public void setTitleImage(int resID) {
		mTitleImageView.setVisibility(View.VISIBLE);
		mTitleTextView.setVisibility(View.GONE);
		mTitleImageView.setImageResource(resID);
	}

	public void setTitle(int stringResID) {
		mTitleImageView.setVisibility(View.GONE);
		mTitleTextView.setVisibility(View.VISIBLE);
		mTitleTextView.setText(stringResID);
	}

	public void setTitle(String titleStr) {
		mTitleImageView.setVisibility(View.GONE);
		mTitleTextView.setVisibility(View.VISIBLE);
		mTitleTextView.setText(titleStr);
	}
	
	public void setRightButtonTitle(int stringResID, OnClickListener onClickListener) {
		mRightButton.setText(stringResID);
		mRightButton.setOnClickListener(onClickListener);
	}
	
	public void setLeftButton(int drawableResID, int width, int height,
			OnClickListener onClickListener) {
		FrameLayout.LayoutParams leftButtonLayoutParams = (FrameLayout.LayoutParams) mLeftButton.getLayoutParams();
		leftButtonLayoutParams.width = ScaleUtil.scale(width);
		leftButtonLayoutParams.height = ScaleUtil.scale(height);
		mLeftButton.setBackgroundResource(drawableResID);
		mLeftButton.setOnClickListener(onClickListener);
	}

	public void setLeftButtonTypeBack(OnClickListener onBackButtonClickListener) {
		mLeftButton.setBackgroundColor(0x00000000);
		mLeftButton.setOnClickListener(onBackButtonClickListener);
		FrameLayout.LayoutParams leftButtonLayoutParams = (FrameLayout.LayoutParams) mLeftButton.getLayoutParams();
		leftButtonLayoutParams.width = ScaleUtil.scale(90);
		leftButtonLayoutParams.height = ScaleUtil.scale(88);
		
		ImageView backImageView = new ImageView(mContext);
		backImageView.setImageResource(R.drawable.ic_back);
		FrameLayout.LayoutParams backImageViewLayoutParams = new FrameLayout.LayoutParams(ScaleUtil.scale(22), 
				ScaleUtil.scale(41));
		backImageViewLayoutParams.leftMargin = ScaleUtil.scale(15);
		backImageViewLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		this.addView(backImageView, backImageViewLayoutParams);
		
		TextView backTextView = new TextView(mContext);
		backTextView.setTextColor(0xff717171);
		backTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.title_text_size));
		backTextView.setText(R.string.back);
		FrameLayout.LayoutParams backTextViewLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 
				FrameLayout.LayoutParams.WRAP_CONTENT);
		backTextViewLayoutParams.leftMargin = ScaleUtil.scale(45);
		backTextViewLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		this.addView(backTextView, backTextViewLayoutParams);
	}
}
