package com.onyx.international.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.onyx.international.kaolaplatform.R;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.AddressInfo;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.ScaleUtil;
import com.onyx.international.utils.StringUtil;

public class AddressListActivity extends BaseActivity {

	private ListView mListView;
	private AddressListViewAdapter mListViewAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_address_list);
		
		mListView = (ListView) findViewById(R.id.listview_addresslist);
		
		mListViewAdapter = new AddressListViewAdapter(this);
		mListView.setAdapter(mListViewAdapter);
		
		setTitle(R.string.address_info);
		
		setTopBarLeftButtonTypeBack();
		
		requestAddressInfoList();
		
		setRightButton(R.string.add, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				int size = UserModel.sharedModel().addressInfoArray.size();
				if (size < 3) {
					Intent intent = new Intent(AddressListActivity.this,
							ModifyAddressInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("type", 0);
					bundle.putInt("address_id", 0);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
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
							
							mListViewAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	
	
	class AddressListViewAdapter extends BaseAdapter {
		
		class AddressInfoHolder {
			public TextView mTitleTextView;
			public TextView mNameTextView;
			public TextView mAddressTextView;
			public TextView mPhoneTextView;
			public ImageButton mEditImageButton;
			public TextView mEditTextView;
			public ImageButton mDeleteImageButton;
			public TextView mDeleteTextView;
			public TextView mDefaultTextView;
		}

		private LayoutInflater mLayoutInflater;

		public AddressListViewAdapter(Context context) {
			// TODO Auto-generated constructor stub
			mLayoutInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return UserModel.sharedModel().addressInfoArray.size();
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
		public View getView(int index, View containView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			AddressInfoHolder holder = null;
			if (containView == null) {
				containView = mLayoutInflater.inflate(R.layout.layout_address_info, null);
				holder = new AddressInfoHolder();
				holder.mTitleTextView = (TextView) containView.findViewById(R.id.textview_address_info_address_title);
				holder.mNameTextView = (TextView) containView.findViewById(R.id.textview_address_info_name_value);
				holder.mAddressTextView = (TextView) containView.findViewById(R.id.textview_address_info_address_value);
				holder.mPhoneTextView = (TextView) containView.findViewById(R.id.textview_address_info_phone_value);
				holder.mEditImageButton = (ImageButton) containView.findViewById(R.id.imageview_address_info_edit);
				holder.mEditTextView = (TextView) containView.findViewById(R.id.textview_address_info_edit);
				holder.mDeleteImageButton = (ImageButton) containView.findViewById(R.id.imageview_address_info_delete);
				holder.mDeleteTextView = (TextView) containView.findViewById(R.id.textview_address_info_delete);
				holder.mDefaultTextView = (TextView) containView.findViewById(R.id.textview_address_info_default);

				holder.mEditImageButton.setOnClickListener(mOnEditImageButtonClicked);
				holder.mDeleteImageButton.setOnClickListener(mOnDeleteImageButtonClicked);
				
				ScaleUtil.scaleWidgetWithParentLinearLayout(holder.mEditImageButton, 28, 28, 208, 0, 8, 0);
				ScaleUtil.scaleWidgetWithParentLinearLayout(holder.mDeleteImageButton, 28, 28, 30, 0, 8, 0);
				ScaleUtil.scaleWidgetWithParentLinearLayout(holder.mEditTextView, 20, 0, 0, 0);
				ScaleUtil.scaleWidgetWithParentLinearLayout(holder.mDeleteTextView, 20, 0, 0, 0);
				ScaleUtil.scaleWidgetWithParentLinearLayout(holder.mDefaultTextView, 30, 0, 0, 0);
				containView.setTag(holder);
			} else {
				holder = (AddressInfoHolder) containView.getTag();
			}

			holder.mEditImageButton.setTag(index);
			holder.mDeleteImageButton.setTag(index);
			
			
			if (index == 0) {
				holder.mDefaultTextView.setText(R.string.address_default);
			} else {
				holder.mDefaultTextView.setText(R.string.address_set_default);
			}
			AddressInfo info = UserModel.sharedModel().addressInfoArray.get(index);
			
			holder.mNameTextView.setText(info.name);
			holder.mAddressTextView.setText(info.address);
			holder.mPhoneTextView.setText(info.phoneNumber);
			
			return containView;
		}
		
	}
	
	private OnClickListener mOnEditImageButtonClicked = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			int tag = (Integer) view.getTag();
			AddressInfo info = UserModel.sharedModel().addressInfoArray.get(tag);
			Intent intent = new Intent(AddressListActivity.this, ModifyAddressInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("type", 1);
			bundle.putInt("address_id", info.id);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
	
	private OnClickListener mOnDeleteImageButtonClicked = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			int tag = (Integer) view.getTag();
			AddressInfo info = UserModel.sharedModel().addressInfoArray.get(tag);
			requestDeleteAddress(info.id);
		}
	};
	
	private void requestDeleteAddress(final int addressId) {
		String[] keys = {"type", "phone", "address_id"};
		String[] values = {"3", UserModel.sharedModel().bindPhoneNumber, String.valueOf(addressId)};
		
		NetworkManager.sharedManager().sendRequest("", StringUtil.buildKeysAndValues(keys, values), new NetworkManagerCallback() {
			
			@Override
			public void onNetworkRequestSuccess(String data) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(data);
					int code = jsonObject.getInt("code");
					if (code == 1) {
						UserModel.sharedModel().deleteAddressInfo(addressId);
						
						mListViewAdapter.notifyDataSetChanged();
						
						showToastMessage(R.string.delete_address_success);
					} else {
						showToastMessage(R.string.delete_address_failed);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					showToastMessage(R.string.delete_address_failed);
				}
			}
		});
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (mListViewAdapter != null) mListViewAdapter.notifyDataSetChanged();
	}

}
