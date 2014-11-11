package com.onyx.international.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ScaleUtil {

	public final static float DEFAULT_WIDTH = 640.0f;
	public final static float DEFAULT_HEIGHT = 960.0f;
	private static int mScreenWidth;
	private static int mScreenHeight;
	private static float mScale = 1.0f;

	public static void init(Activity activity) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		mScreenWidth = displayMetrics.widthPixels;
		mScreenHeight = displayMetrics.heightPixels;

		float widthScale = displayMetrics.widthPixels / DEFAULT_WIDTH;
		float heightScale = displayMetrics.heightPixels / DEFAULT_HEIGHT;
		mScale = widthScale;//widthScale > heightScale ? heightScale : widthScale;
	}

	public static float scale(float value) {
		return value * mScale;
	}

	public static int scale(int value) {
		float result = value * mScale;
		return (int) (result);
	}

	public static void scaleWidget(View view, int width, int height) {
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (width > 0) layoutParams.width = scale(width);
		else layoutParams.width = width;
		if (height > 0) layoutParams.height = scale(height);
		else layoutParams.height = height;
		view.setLayoutParams(layoutParams);
	}

	public static void scaleWidgetWithParentRelativeLayout(View view, int width, int height,
			int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
		layoutParams.width = scale(width);
		layoutParams.height = scale(height);
		layoutParams.leftMargin = scale(leftMargin);
		layoutParams.rightMargin = scale(rightMargin);
		layoutParams.topMargin = scale(topMargin);
		layoutParams.bottomMargin = scale(bottomMargin);
		view.setLayoutParams(layoutParams);
	}

	public static void scaleWidgetWithParentRelativeLayout(View view, int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
		layoutParams.leftMargin = scale(leftMargin);
		layoutParams.rightMargin = scale(rightMargin);
		layoutParams.topMargin = scale(topMargin);
		layoutParams.bottomMargin = scale(bottomMargin);
		view.setLayoutParams(layoutParams);
	}

	public static void scaleWidgetWithParentLinearLayout(View view, int width, int height,
			int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
		layoutParams.width = scale(width);
		layoutParams.height = scale(height);
		layoutParams.leftMargin = scale(leftMargin);
		layoutParams.rightMargin = scale(rightMargin);
		layoutParams.topMargin = scale(topMargin);
		layoutParams.bottomMargin = scale(bottomMargin);
		view.setLayoutParams(layoutParams);
	}

	public static void scaleWidgetWithParentLinearLayout(View view, int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
		layoutParams.leftMargin = scale(leftMargin);
		layoutParams.rightMargin = scale(rightMargin);
		layoutParams.topMargin = scale(topMargin);
		layoutParams.bottomMargin = scale(bottomMargin);
		view.setLayoutParams(layoutParams);
	}
	
	public static int getScreenWidth() {
		return mScreenWidth;
	}
	
	public static int getScreenHeight() {
		return mScreenHeight;
	}
}
