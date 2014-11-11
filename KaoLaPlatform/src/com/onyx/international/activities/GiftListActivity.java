package com.onyx.international.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.onyx.international.kaolaplatform.R;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.GiftModel;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.StringUtil;
import com.onyx.international.views.GiftListView;
import com.onyx.international.views.GiftListView.GiftListViewCallback;

public class GiftListActivity extends BaseActivity implements GiftListViewCallback{

	private int mType = 0; // 0 for collect, 1 for exchanged
	private ImageFetcher mImageFetcher;
	private GiftListView mGiftListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mType = getIntent().getExtras().getInt("type");

		mImageFetcher = new ImageFetcher(this, 240);
		mGiftListView = new GiftListView(this, mType, mImageFetcher, this);

		setContentView(mGiftListView);
		
		if (0 == mType) {
			getTopBarLayout().setTitle(R.string.collect);
		} else if (1 == mType) {
			getTopBarLayout().setTitle(R.string.exchange_gift);
		} else if (2 == mType) {
			getTopBarLayout().setTitle(R.string.my_gift);
		}
		
		setTopBarLeftButtonTypeBack();
	}
	
    @Override
    protected void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
    }

	@Override
	public void onPause() {
		super.onPause();
		mImageFetcher.setExitTasksEarly(true);
	}

	@Override
	public void onGiftSelect(int giftId) {
		// TODO Auto-generated method stub
		GiftModel.sharedModel().mCurrentSelectGiftId = giftId;
		
		if (mType == 2) {
			Intent intent = new Intent(this, ExpressInfoActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, DetailInfoActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onGiftLongPressed(int giftId) {
		// TODO Auto-generated method stub
		
	}

}
