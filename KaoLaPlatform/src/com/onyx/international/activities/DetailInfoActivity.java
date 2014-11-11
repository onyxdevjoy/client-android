package com.onyx.international.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.onyx.international.kaolaplatform.R;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.Gift;
import com.onyx.international.models.GiftModel;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.ScaleUtil;
import com.onyx.international.utils.StringUtil;

public class DetailInfoActivity extends BaseActivity {

	private ImageFetcher mImageFetcher;
	private Button mCollectButton;
	private TextView mCollectTextView;
	private ImageButton mThumbImageButton;
	private ImageButton mRecommendImageButton1;
	private ImageButton mRecommendImageButton2;
	private ImageButton mRecommendImageButton3;
	private ImageButton mRecommendImageButton4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_detailinfo);
		
		setLeftButtonTypeBack(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DetailInfoActivity.this.finish();
			}
		});

		mImageFetcher = new ImageFetcher(this, 240);
		
		Gift gift = GiftModel.sharedModel().getGift(GiftModel.sharedModel().mCurrentSelectGiftId);
		
		setTitle(gift.name);

		mThumbImageButton = (ImageButton) findViewById(R.id.imagebutton_detailinfo_thumb);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(mThumbImageButton, 250, 250, 16, 0, 16, 0);
		mThumbImageButton.setImageResource(R.drawable.image_1);
		mImageFetcher.loadImage(gift.imageArray.get(0), mThumbImageButton);
		
		TextView nameTextView = (TextView) findViewById(R.id.textview_detailinfo_title);
		ScaleUtil.scaleWidgetWithParentLinearLayout(nameTextView, 30, 0, 20, 0);
		nameTextView.setText(gift.name);
		TextView remainCountTextView = (TextView) findViewById(R.id.textview_detailinfo_remaincount);
		ScaleUtil.scaleWidgetWithParentLinearLayout(remainCountTextView, 30, 0, 10, 0);
		remainCountTextView.setText(getString(R.string.remain_count, gift.remainCount));
		TextView exchangedCountTextView = (TextView) findViewById(R.id.textview_detailinfo_exchangedcount);
		ScaleUtil.scaleWidgetWithParentLinearLayout(exchangedCountTextView, 30, 0, 110, 0);
		exchangedCountTextView.setText(getString(R.string.exchanged_count, gift.exchangeCount));
		
		TextView exchangeNeedTextView = (TextView) findViewById(R.id.textview_detailInfo_exchange_need);
		exchangeNeedTextView.setText(gift.gameRequired);

		TextView descTextView = (TextView) findViewById(R.id.textview_detailInfo_desc);
		descTextView.setText(gift.description);
		
		mCollectButton = (Button) findViewById(R.id.button_detailInfo_collect);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(mCollectButton, 250, 80, 16, 0, 0, 0);
		Button exchangeButton = (Button) findViewById(R.id.button_detailInfo_exchange);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(exchangeButton, 350, 80, 10, 16, 0, 0);
		
		ImageView collectImageView = (ImageView) findViewById(R.id.imageview_detailInfo_collect);
		ImageView exchangeImageView = (ImageView) findViewById(R.id.imageview_detailInfo_exchange);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(exchangeImageView, 38, 38, 96, 0, 20, 0);
		
		mCollectTextView = (TextView) findViewById(R.id.textview_detailInfo_collect);
		TextView exchangeTextView = (TextView) findViewById(R.id.textview_detailInfo_exchange);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(exchangeTextView, 142, 0, 20, 0);
		
//		TextView giftDescTextView = (TextView) findViewById(R.id.textview_detailInfo_desc);
//		giftDescTextView.setText(gift.description);

		mRecommendImageButton1 = (ImageButton) findViewById(R.id.imagebutton_detailinfo_recommend_1);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(mRecommendImageButton1, 140, 140, 16, 0, 0, 0);
		mRecommendImageButton2 = (ImageButton) findViewById(R.id.imagebutton_detailinfo_recommend_2);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(mRecommendImageButton2, 140, 140, 16, 0, 0, 0);
		mRecommendImageButton3 = (ImageButton) findViewById(R.id.imagebutton_detailinfo_recommend_3);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(mRecommendImageButton3, 140, 140, 16, 0, 0, 0);
		mRecommendImageButton4 = (ImageButton) findViewById(R.id.imagebutton_detailinfo_recommend_4);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(mRecommendImageButton4, 140, 140, 16, 0, 0, 0);
		
		
		if (gift.isCollect) {
			mCollectButton.setEnabled(false);
			mCollectTextView.setText(R.string.has_collected);
			ScaleUtil.scaleWidgetWithParentRelativeLayout(mCollectTextView, 126, 0, 20, 0);
			ScaleUtil.scaleWidgetWithParentRelativeLayout(collectImageView, 38, 38, 60, 0, 20, 0);
		} else {
			mCollectButton.setEnabled(true);
			mCollectTextView.setText(R.string.collect);
			ScaleUtil.scaleWidgetWithParentRelativeLayout(mCollectTextView, 126, 0, 20, 0);
			ScaleUtil.scaleWidgetWithParentRelativeLayout(collectImageView, 38, 38, 80, 0, 20, 0);
		}
		
		requestWishList();
	}
	
	private void requestWishList() {

		NetworkManager.sharedManager().sendRequest("list_wish", null, new NetworkManagerCallback() {
			
			@Override
			public void onNetworkRequestSuccess(String data) {
				// TODO Auto-generated method stub
				try {
					JSONArray jsonArray = new JSONArray(data);
					List<Gift> wishArray = new ArrayList<Gift>();
					for (int i = 0; i < jsonArray.length(); ++ i) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						Gift gift = new Gift();
						gift.id = jsonObject.getInt("id");
						gift.name = jsonObject.getString("content");
						gift.imageArray.add(jsonObject.getString("image"));
						gift.wishCount = jsonObject.getInt("number");
						wishArray.add(gift);
					}
					updateWishGiftViews(wishArray);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private void updateWishGiftViews(List<Gift> wishArray) {
		int size = wishArray.size();
		if (size > 0) {
			mRecommendImageButton1.setVisibility(View.VISIBLE);
			Gift gift = wishArray.get(0);
			mImageFetcher.loadImage(gift.imageArray.get(0), mRecommendImageButton1);
		}
		if (size > 1) {
			mRecommendImageButton2.setVisibility(View.VISIBLE);
			Gift gift = wishArray.get(1);
			mImageFetcher.loadImage(gift.imageArray.get(0), mRecommendImageButton2);
		}
		if (size > 2) {
			mRecommendImageButton3.setVisibility(View.VISIBLE);
			Gift gift = wishArray.get(2);
			mImageFetcher.loadImage(gift.imageArray.get(0), mRecommendImageButton3);
		}
		if (size > 3) {
			mRecommendImageButton4.setVisibility(View.VISIBLE);
			Gift gift = wishArray.get(3);
			mImageFetcher.loadImage(gift.imageArray.get(0), mRecommendImageButton4);
		}
	}
	
	public void onExchangedButtonClicked(View view) {
		Intent intent = new Intent(this, ExchangeGiftFirstStepActivity.class);
		startActivity(intent);
	}
	
	public void onCollectButtonClicked(View view) {

		Gift gift = GiftModel.sharedModel().getGift(GiftModel.sharedModel().mCurrentSelectGiftId);
		String[] keys = {"phone", "gift_id"};
		String[] values = {UserModel.sharedModel().bindPhoneNumber, String.valueOf(gift.id)};
		NetworkManager.sharedManager().sendRequest("collect", StringUtil.buildKeysAndValues(keys, values), 
				new NetworkManagerCallback() {
					
					@Override
					public void onNetworkRequestSuccess(String data) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonObject = new JSONObject(data);
							final int code = jsonObject.getInt("code");
							if (code == 1) {
								Gift gift = GiftModel.sharedModel().getGift(GiftModel.sharedModel().mCurrentSelectGiftId);
								gift.isCollect = true;
								mCollectButton.setEnabled(false);
								mCollectTextView.setText(R.string.has_collected);
//								ScaleUtil.scaleWidgetWithParentRelativeLayout(mCollectTextView, 38, 38, 60, 0, 20, 0);
							} else {
								
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	
	public void onThumbImageButtonClicked(View view) {
		Intent intent = new Intent(this, ImagesShowActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        mImageFetcher.setExitTasksEarly(false);
	}
}
