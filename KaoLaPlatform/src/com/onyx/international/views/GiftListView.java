package com.onyx.international.views;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.onyx.international.kaolaplatform.R;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.Gift;
import com.onyx.international.models.GiftModel;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.ScaleUtil;

public class GiftListView extends LinearLayout implements OnItemClickListener, OnItemLongClickListener {

	public interface GiftListViewCallback {
		public void onGiftSelect(int giftId);
		public void onGiftLongPressed(int giftId);
	}
	
	
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private ImageFetcher mImageFetcher;
	private ListView mListView;
	private GiftListAdapter mAdapter;
	private int mType;
	private GiftListViewCallback mCallback;
	
	public GiftListView(Context context, int type, ImageFetcher imageFetcher, GiftListViewCallback callback) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		mType = type;
		mImageFetcher = imageFetcher;
		mLayoutInflater = LayoutInflater.from(context);
		mCallback = callback;
		
		mAdapter = new GiftListAdapter();
		mListView = new ListView(context);
		mListView.setDividerHeight(1);
		mListView.setDivider(null);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		LinearLayout.LayoutParams listViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		this.addView(mListView, listViewParams);
		mListView.setAdapter(mAdapter);
		getGiftList();
	}

	public void onResume() {
		int count = 0;
		if (0 == mType) {
			count = GiftModel.sharedModel().collectGiftArray.size();
		} else if (1 == mType) {
			count = GiftModel.sharedModel().exchangeGiftArray.size();
		} else if (2 == mType) {
			count = GiftModel.sharedModel().exchangeGiftArray.size();
		} else {
			count = 0;
		}
		if (count == 0) {
			getGiftList();
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	private void getGiftList() {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		String urlSubffix = "";
		if (0 == mType) {
			urlSubffix = "list_collect";
			params.put("phone", UserModel.sharedModel().bindPhoneNumber);
		} else if (1 == mType) {
			urlSubffix = "index";
			params.put("phone", UserModel.sharedModel().bindPhoneNumber);
			params.put("page", "1");
			params.put("size", "200");
		} else if (2 == mType) {
			urlSubffix = "index";
			params.put("phone", UserModel.sharedModel().bindPhoneNumber);
			params.put("page", "1");
			params.put("size", "200");
		}
		NetworkManager.sharedManager().sendRequest(urlSubffix, params,
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

								if (0 == mType) {
									GiftModel.sharedModel().addCollectGift(gift);
								} else if (1 == mType) {
									GiftModel.sharedModel().addExchangedGift(gift);
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						refreshListView();
					}
				});
	}

	public void refreshListView() {
		// TODO Auto-generated method stub
		mAdapter.notifyDataSetChanged();
	}

	class GiftListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (0 == mType) {
				return GiftModel.sharedModel().collectGiftArray.size();
			} else if (1 == mType) {
				return GiftModel.sharedModel().exchangeGiftArray.size();
			} else if (2 == mType) {
				return GiftModel.sharedModel().exchangeGiftArray.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			if (0 == mType) {
				return GiftModel.sharedModel().collectGiftArray.indexOf(arg0);
			} else if (1 == mType) {
				return GiftModel.sharedModel().exchangeGiftArray.indexOf(arg0);
			} else if (2 == mType) {
				return GiftModel.sharedModel().exchangeGiftArray.indexOf(arg0);
			} else {
				return null;
			}
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		class GiftItemView {
			public ImageView mImageView;
			public TextView mTitleTextView;
			public TextView mContentTextView;
		}
		
		@Override
		public View getView(int index, View contentView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			int giftId = 0;
			if (0 == mType) {
				giftId = GiftModel.sharedModel().collectGiftArray.get(index);
			} else if (1 == mType) {
				giftId = GiftModel.sharedModel().exchangeGiftArray.get(index);
			} else if (2 == mType) {
				giftId = GiftModel.sharedModel().exchangeGiftArray.get(index);
			} 
			
			GiftItemView view ;
			if (contentView == null) {
				view = new GiftItemView();
				
				contentView = mLayoutInflater.inflate(R.layout.layout_item_gift, null);
				view.mImageView = (ImageView) contentView.findViewById(R.id.imageview_item_gift);
				view.mTitleTextView = (TextView) contentView.findViewById(R.id.textview_item_gift_title);
				view.mContentTextView = (TextView) contentView.findViewById(R.id.textview_item_gift_content);
				ScaleUtil.scaleWidgetWithParentRelativeLayout(view.mImageView, 150, 150,
						30, 30, 30, 30);
				ScaleUtil.scaleWidgetWithParentRelativeLayout(view.mTitleTextView, 10, 0, 30, 0);
				ScaleUtil.scaleWidgetWithParentRelativeLayout(view.mContentTextView, 10, 0, 40, 0);
				contentView.setTag(view);
			} else {
				view = (GiftItemView) contentView.getTag();
			}
			
			if (giftId != 0) {
				Gift gift = GiftModel.sharedModel().getGift(giftId);
				view.mTitleTextView.setText(gift.name);
				view.mContentTextView.setText(gift.description);
				
				if (gift.isExchanged) {
					view.mContentTextView.setTextColor(Color.rgb(113, 113, 113));
					view.mContentTextView.setText(R.string.has_exchanged);
				} else {
					view.mContentTextView.setTextColor(Color.rgb(111, 145, 71));
					view.mContentTextView.setText(mContext.getString(R.string.remain_count, gift.remainCount));
				}
				mImageFetcher.loadImage(gift.imageArray.get(0), view.mImageView);
			}
			
			
			return contentView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub

		int giftId = 0;
		if (0 == mType) {
			giftId = GiftModel.sharedModel().collectGiftArray.get(index);
		} else if (1 == mType) {
			giftId = GiftModel.sharedModel().exchangeGiftArray.get(index);
		} else if (2 == mType) {
			giftId = GiftModel.sharedModel().exchangeGiftArray.get(index);
		} 
		if (mCallback != null) {
			mCallback.onGiftSelect(giftId);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int index,
			long arg3) {
		// TODO Auto-generated method stub
		int giftId = 0;
		if (0 == mType) {
			giftId = GiftModel.sharedModel().collectGiftArray.get(index);
		} else if (1 == mType) {
			giftId = GiftModel.sharedModel().exchangeGiftArray.get(index);
		} else if (2 == mType) {
			giftId = GiftModel.sharedModel().exchangeGiftArray.get(index);
		} 
		if (mCallback != null) {
			mCallback.onGiftLongPressed(giftId);
		}
		return true;
	}
}
