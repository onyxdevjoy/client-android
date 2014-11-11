package com.onyx.international.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.huewu.pla.lib.MultiColumnPullToRefreshListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.onyx.international.database.SharedPreferenceManager;
import com.onyx.international.kaolaplatform.R;
import com.onyx.international.logger.Logger;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.AddressInfo;
import com.onyx.international.models.Gift;
import com.onyx.international.models.GiftModel;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.ScaleUtil;
import com.onyx.international.utils.StringUtil;
import com.onyx.international.views.GiftListView;
import com.onyx.international.views.GiftListView.GiftListViewCallback;
import com.onyx.international.views.ScaleImageView;
import com.onyx.international.views.SlideLayout;

public class MainActivity extends Activity implements GiftListViewCallback{

	Button mMainpageButton;
	Button mUsercenterButton;
	Button mCollectButton;
	Button mSettingButton;
	Button mCurrentButton;
	
	SlideLayout mSlideLayout;
	ImageFetcher mImageFetcher;
	
	LayoutInflater mLayoutInflater;
	MultiColumnPullToRefreshListView mMainPageListView;
	FrameLayout mMainPageLayout;
	LinearLayout mUsercenterLayout;
	LinearLayout mSettingLayout;
	GiftListView mCollectLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		
		mSlideLayout = (SlideLayout) findViewById(R.id.slidelayout_main);
		mSlideLayout.setTopBarImage(R.drawable.logo);
		
		LinearLayout leftbarLayout = (LinearLayout) findViewById(R.id.layout_leftbar);
		ScaleUtil.scaleWidget(leftbarLayout, 180, 920);
		
		mMainpageButton = (Button) findViewById(R.id.button_mainpage);
		ScaleUtil.scaleWidget(mMainpageButton, 180, 180);
		mUsercenterButton = (Button) findViewById(R.id.button_usercenter);
		ScaleUtil.scaleWidget(mUsercenterButton, 180, 180);
		mCollectButton = (Button) findViewById(R.id.button_collect);
		ScaleUtil.scaleWidget(mCollectButton, 180, 180);
		mSettingButton = (Button) findViewById(R.id.button_setting);
		ScaleUtil.scaleWidget(mSettingButton, 180, 180);
		
		mImageFetcher = new ImageFetcher(this, 240);
//		mImageFetcher.setLoadingImage(R.drawable.ic_launcher);
		
		mMainpageButton.setSelected(true);
		mMainpageButton.setEnabled(false);
		mCurrentButton = mMainpageButton;
		
		// Mainpage
		mMainPageLayout = new FrameLayout(this);
		LinearLayout.LayoutParams mainpageLayoutParams = new LinearLayout.LayoutParams(-1, -1);
		mMainPageLayout.setLayoutParams(mainpageLayoutParams);
		mSlideLayout.addContainerView(mMainPageLayout);
		
		mMainPageListView = new MultiColumnPullToRefreshListView(this);
		FrameLayout.LayoutParams mainpageListLayoutParams = new FrameLayout.LayoutParams(-1, -1);
		mMainPageListView.setLayoutParams(mainpageListLayoutParams);
		mMainPageListView.setOnItemClickListener(new MainPageListOnItemClickedListener());
		mMainPageLayout.addView(mMainPageListView);
		
		FrameLayout.LayoutParams mainpageExchangeButtonLayoutParams = new FrameLayout.LayoutParams(ScaleUtil.scale(108)
				, ScaleUtil.scale(108));
		mainpageExchangeButtonLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		mainpageExchangeButtonLayoutParams.setMargins(0, 0, 0, ScaleUtil.scale(50));
		Button exchangeButton = new Button(this);
		exchangeButton.setBackgroundResource(R.drawable.btn_exchange);
		exchangeButton.setLayoutParams(mainpageExchangeButtonLayoutParams);
		exchangeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, QuickExchangeActivity.class);
				startActivity(intent);
			}
		});
		mMainPageLayout.addView(exchangeButton);
		
		getMainPageData(1);
		
		// Usercenter
		initUsercenterLayout();
		
		// Collect
//		initCollectLayout();
		
		// Setting 
		initSettingLayout();
		
		requestAddressInfoList();
	}

	private void requestAddressInfoList() {
		String[] keys = {"phone"};
		String[] values = {UserModel.sharedModel().bindPhoneNumber};
		NetworkManager.sharedManager().sendRequest("list_address", StringUtil.buildKeysAndValues(keys, values),
				new NetworkManagerCallback() {

					@Override
					public void onNetworkRequestSuccess(String data) {
						// TODO Auto-generated method stub
						try {
							JSONArray jsonArray = new JSONArray(data);
							UserModel.sharedModel().clearAddressInfo();
							
							for (int i=0; i<jsonArray.length(); ++i) {
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								AddressInfo info = new AddressInfo();
								info.address = jsonObject.getString("address");
								info.id = jsonObject.getInt("address_id");
								info.phoneNumber = jsonObject.getString("address_phone");
								info.postCode = jsonObject.getInt("code");
								info.name = jsonObject.getString("name");
								UserModel.sharedModel().addAddressInfo(info);
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	private void initSettingLayout() {
		mSettingLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.layout_setting, null);
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		mSettingLayout.setLayoutParams(layoutParams);
		mSlideLayout.addContainerView(mSettingLayout);
		
		View widgetView;
		widgetView = findViewById(R.id.layout_setting_account);
		ScaleUtil.scaleWidget(widgetView, ViewGroup.LayoutParams.MATCH_PARENT, 88);
		widgetView = findViewById(R.id.layout_setting_push);
		ScaleUtil.scaleWidget(widgetView, ViewGroup.LayoutParams.MATCH_PARENT, 88);
		widgetView = findViewById(R.id.layout_setting_user_guide);
		ScaleUtil.scaleWidget(widgetView, ViewGroup.LayoutParams.MATCH_PARENT, 88);
		widgetView = findViewById(R.id.layout_setting_service_item);
		ScaleUtil.scaleWidget(widgetView, ViewGroup.LayoutParams.MATCH_PARENT, 88);
		widgetView = findViewById(R.id.button_setting_user_guide);
		ScaleUtil.scaleWidget(widgetView, ViewGroup.LayoutParams.MATCH_PARENT, 88);
		widgetView = findViewById(R.id.button_setting_service_item);
		ScaleUtil.scaleWidget(widgetView, ViewGroup.LayoutParams.MATCH_PARENT, 88);
		widgetView = findViewById(R.id.togglebutton_setting_push);
		ScaleUtil.scaleWidget(widgetView, 104, 67);
		widgetView = findViewById(R.id.button_setting_logout);
		ScaleUtil.scaleWidget(widgetView, ViewGroup.LayoutParams.MATCH_PARENT, 88);
		
		TextView bindPhoneTextView = (TextView) findViewById(R.id.textview_setting_bindphone);
		bindPhoneTextView.setText(UserModel.sharedModel().bindPhoneNumber);
	}

	private void initCollectLayout() {
		mCollectLayout = new GiftListView(this, 0, mImageFetcher, this);
		mSlideLayout.addContainerView(mCollectLayout);
	}

	private void initUsercenterLayout() {
		mUsercenterLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.layout_usercenter, null);
		mSlideLayout.addContainerView(mUsercenterLayout);

		ImageView exchangegiftImageView = (ImageView) findViewById(R.id.imageview_usercenter_exchangegift);
		ScaleUtil.scaleWidget(exchangegiftImageView, 58, 58);
		ImageView exchangegiftArrowImageView = (ImageView) findViewById(R.id.imageview_usercenter_arrow_1);
		ScaleUtil.scaleWidget(exchangegiftArrowImageView, 16, 25);
		ImageView addressInfoImageView = (ImageView) findViewById(R.id.imageview_usercenter_addressinfo);
		ScaleUtil.scaleWidget(addressInfoImageView, 58, 58);
		ImageView addressInfoArrowImageView = (ImageView) findViewById(R.id.imageview_usercenter_arrow_2);
		ScaleUtil.scaleWidget(addressInfoArrowImageView, 16, 25);
		ImageView expressInfoImageView = (ImageView) findViewById(R.id.imageview_usercenter_expressinfo);
		ScaleUtil.scaleWidget(expressInfoImageView, 58, 58);
		ImageView expressInfoArrowImageView = (ImageView) findViewById(R.id.imageview_usercenter_arrow_3);
		ScaleUtil.scaleWidget(expressInfoArrowImageView, 16, 25);
		ImageView bigGiftbagImageView = (ImageView) findViewById(R.id.imageview_usercenter_big_giftbag);
		ScaleUtil.scaleWidget(bigGiftbagImageView, 58, 58);
		ImageView bigGiftbagArrowImageView = (ImageView) findViewById(R.id.imageview_usercenter_arrow_4);
		ScaleUtil.scaleWidget(bigGiftbagArrowImageView, 16, 25);
	}
	
	private void getMainPageData(int index) {
		String[] keys = {"phone", "page", "size"};
		String[] values = {UserModel.sharedModel().bindPhoneNumber, String.valueOf(index), "20"};
		NetworkManager.sharedManager().sendRequest("index", StringUtil.buildKeysAndValues(keys, values),
				new NetworkManagerCallback() {
					
					@Override
					public void onNetworkRequestSuccess(String data) {
						// TODO Auto-generated method stub
						try {
							JSONArray jsonArray = new JSONArray(data);
							int length = jsonArray.length();
							for (int i=0; i<length; ++i) {
								JSONObject object = jsonArray.getJSONObject(i);
								Gift gift = GiftModel.parseGiftFromJson(object);
								GiftModel.sharedModel().addMainPageGift(gift);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						DEBUG("count=" + GiftModel.sharedModel().mainPageGift.size());
						
						refreshListView();
					}
				});
	}
	
	private void refreshListView() {
		mMainPageListView.setAdapter(new MainPageListAdapter());
	}
	
	public void onMainPageButtonClicked(View view) {
		mSlideLayout.slide();
		mSlideLayout.setTopBarImage(R.drawable.logo);
		mCurrentButton.setEnabled(true);
		mCurrentButton.setSelected(false);
		mCurrentButton = mMainpageButton;
		mCurrentButton.setEnabled(false);
		mCurrentButton.setSelected(true);

		mMainPageLayout.setVisibility(View.VISIBLE);
		mUsercenterLayout.setVisibility(View.GONE);
		if (mCollectLayout != null) mCollectLayout.setVisibility(View.GONE);
		mSettingLayout.setVisibility(View.GONE);
	}
	
	public void onUsercenterButtonClicked(View view) {
		mSlideLayout.slide();
		mSlideLayout.setTopBarTitle(R.string.usercenter);
		mCurrentButton.setEnabled(true);
		mCurrentButton.setSelected(false);
		mCurrentButton = mUsercenterButton;
		mCurrentButton.setEnabled(false);
		mCurrentButton.setSelected(true);

		mMainPageLayout.setVisibility(View.GONE);
		mUsercenterLayout.setVisibility(View.VISIBLE);
		if (mCollectLayout != null) mCollectLayout.setVisibility(View.GONE);
		mSettingLayout.setVisibility(View.GONE);
	}
	
	public void onCollectButtonClicked(View view) {
		mSlideLayout.slide();
		mSlideLayout.setTopBarTitle(R.string.collect);
		mCurrentButton.setEnabled(true);
		mCurrentButton.setSelected(false);
		mCurrentButton = mCollectButton;
		mCurrentButton.setEnabled(false);
		mCurrentButton.setSelected(true);

		mMainPageLayout.setVisibility(View.GONE);
		mUsercenterLayout.setVisibility(View.GONE);
		if (mCollectLayout == null) {
			this.initCollectLayout();
		} else {
			mCollectLayout.onResume();
		}
		mCollectLayout.setVisibility(View.VISIBLE);
		mSettingLayout.setVisibility(View.GONE);
	}
	
	public void onSettingButtonClicked(View view) {
		mSlideLayout.slide();
		mSlideLayout.setTopBarTitle(R.string.setting);
		mCurrentButton.setEnabled(true);
		mCurrentButton.setSelected(false);
		mCurrentButton = mSettingButton;
		mCurrentButton.setEnabled(false);
		mCurrentButton.setSelected(true);

		mMainPageLayout.setVisibility(View.GONE);
		mUsercenterLayout.setVisibility(View.GONE);
		if (mCollectLayout != null) mCollectLayout.setVisibility(View.GONE);
		mSettingLayout.setVisibility(View.VISIBLE);
	}

	class MainPageListOnItemClickedListener implements OnItemClickListener {

		@Override
		public void onItemClick(PLA_AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			Integer giftId = GiftModel.sharedModel().mainPageGift.get(position - 1);
			GiftModel.sharedModel().mCurrentSelectGiftId = giftId;
			Intent intent = new Intent(MainActivity.this, DetailInfoActivity.class);
			startActivity(intent);
		}
	}
	
	class MainPageListAdapter extends BaseAdapter {

		class MainPageViewHolder {
			public ScaleImageView mImageView;
			public TextView mTitleTextView;
			public TextView mCountTextView;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return GiftModel.sharedModel().mainPageGift.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int index, View containerView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			MainPageViewHolder holder = null;
			if (containerView == null) {
				containerView = mLayoutInflater.inflate(R.layout.layout_waterfall, null);
				holder = new MainPageViewHolder();
				holder.mImageView = (ScaleImageView) containerView.findViewById(R.id.imageview_waterfall);
				holder.mTitleTextView = (TextView) containerView.findViewById(R.id.textview_waterfall_name);
				holder.mCountTextView = (TextView) containerView.findViewById(R.id.textview_waterfall_count);
				containerView.setTag(holder);
			} else {
				holder = (MainPageViewHolder) containerView.getTag();
			}
			
			int giftId = GiftModel.sharedModel().mainPageGift.get(index);
			Gift gift = GiftModel.sharedModel().getGift(giftId);
			holder.mTitleTextView.setText(gift.name);
			holder.mCountTextView.setText(getString(R.string.remain_count, gift.remainCount));
			holder.mImageView.setImageWidth(gift.thumbImageSizeWidth);
			holder.mImageView.setImageHeight(gift.thumbImageSizeHeight);
			mImageFetcher.loadImage(gift.imageArray.get(0), holder.mImageView);
			
			return containerView;
		}
		
	}
	
	class SettingAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
    @Override
    protected void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
    }
    
    private void DEBUG(String message) {
    	Logger.getLogger(this.getClass()).debug(message);
    }
    
    public void onExchangeGiftButtonClicked(View view) {
    	Intent intent = new Intent(this, GiftListActivity.class);
    	Bundle bundle = new Bundle();
    	bundle.putInt("type", 1);
    	intent.putExtras(bundle);
    	startActivity(intent);
    	DEBUG("onExchangeGiftButtonClicked");
    }
    
    public void onAddressInfoButtonClicked(View view) {
    	DEBUG("onAddressInfoButtonClicked");
    	Intent intent = new Intent(this, AddressListActivity.class);
    	startActivity(intent);
    }
    
    public void onExpressInfoButtonClicked(View view) {
    	DEBUG("onExpressInfoButtonClicked");
    	Intent intent = new Intent(this, GiftListActivity.class);
    	Bundle bundle = new Bundle();
    	bundle.putInt("type", 2);
    	intent.putExtras(bundle);
    	startActivity(intent);
    	DEBUG("onExchangeGiftButtonClicked");
    }
    
    public void onBigGiftbagButtonClicked(View view) {
    	DEBUG("onBigGiftbagButtonClicked");
    	Intent intent = new Intent(this, GiftListActivity.class);
    	Bundle bundle = new Bundle();
    	bundle.putInt("type", 1);
    	intent.putExtras(bundle);
    	startActivity(intent);
    	DEBUG("onExchangeGiftButtonClicked");
    }
    
    public void onSettingUserGuideClicked(View view) {
    	Intent intent = new Intent(this, WebViewActivity.class);
    	Bundle bundle = new Bundle();
    	bundle.putString("url", "");
    	bundle.putString("title", getString(R.string.user_guide));
    	intent.putExtras(bundle);
    	startActivity(intent);
    }
    
    public void onSettingServiceItemClicked(View view) {
    	Intent intent = new Intent(this, WebViewActivity.class);
    	Bundle bundle = new Bundle();
    	bundle.putString("url", "");
    	bundle.putString("title", getString(R.string.service_item));
    	intent.putExtras(bundle);
    	startActivity(intent);
    }
    
    
    public void onSettingLogoutButtonClicked(View view) {
    	SharedPreferenceManager.sharedManager().setString("bindPhone", "");
    	Intent intent = new Intent(this, WelcomeActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
    }

	@Override
	public void onGiftSelect(int giftId) {
		// TODO Auto-generated method stub
		GiftModel.sharedModel().mCurrentSelectGiftId = giftId;
		Intent intent = new Intent(MainActivity.this, DetailInfoActivity.class);
		startActivity(intent);
	}


	@Override
	public void onGiftLongPressed(final int giftId) {
		// TODO Auto-generated method stub
			new AlertDialog.Builder(this).setTitle(R.string.app_name)
			.setMessage(R.string.delete_collect_confirm)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					requestDeleteCollect(giftId);
				}
			})
			.setNegativeButton("取消", null)
			.show();
	}
	
	private void requestDeleteCollect(final int giftId) {
		String keys[] = {"phone", "step", "gift_id"};
		String values[] = {UserModel.sharedModel().bindPhoneNumber, "2", String.valueOf(giftId)};
		NetworkManager.sharedManager().sendRequest("collect", StringUtil.buildKeysAndValues(keys, values),
				new NetworkManagerCallback() {
					
					@Override
					public void onNetworkRequestSuccess(String data) {
						// TODO Auto-generated method stub

						try {
							JSONObject jsonObject = new JSONObject(data);
							final int code = jsonObject.getInt("code");
							if (code == 1) {
								GiftModel.sharedModel().deleteCollectGfit(giftId);
								mCollectLayout.refreshListView();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
}
