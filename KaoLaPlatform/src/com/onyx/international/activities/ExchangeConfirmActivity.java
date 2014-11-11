package com.onyx.international.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onyx.international.kaolaplatform.R;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.AddressInfo;
import com.onyx.international.models.GiftModel;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.StringUtil;

public class ExchangeConfirmActivity extends BaseActivity {

	public TextView mNameTextView;
	public TextView mAddressTextView;
	public TextView mPhoneTextView;
	public Button mConfirmButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_exchange_confirm);
		
		setTopBarLeftButtonTypeBack();

		setTitle(R.string.exchange_gift);
		
		mNameTextView = (TextView) findViewById(R.id.textview_exchange_confirm_name_value);
		mAddressTextView = (TextView) findViewById(R.id.textview_exchange_confirm_address_value);
		mPhoneTextView = (TextView) findViewById(R.id.textview_exchange_confirm_phone_value);
		mConfirmButton = (Button) findViewById(R.id.button_exchange_confirm);
		
	}
	
	public void onExchangeConfirmButtonClicked(View view) {
		if (UserModel.sharedModel().addressInfoArray.size() > 0) {
			AddressInfo addressInfo = UserModel.sharedModel().addressInfoArray.get(0);
			requestExchangeInfo(addressInfo);
		} else {
			
		}
	}
	
	private void requestExchangeInfo(AddressInfo addressInfo) {
		String[] keys = {"phone", "address_id", "step", "gift_id", "code"};
		String[] values = {UserModel.sharedModel().bindPhoneNumber, String.valueOf(addressInfo.id), "2",
				String.valueOf(GiftModel.sharedModel().currentExchangedGiftId), UserModel.sharedModel().tmpCaptchaCode};
		NetworkManager.sharedManager().sendRequest("exchange", StringUtil.buildKeysAndValues(keys, values),
				new NetworkManagerCallback() {
					
					@Override
					public void onNetworkRequestSuccess(String data) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonObject = new JSONObject(data);
							final int code = jsonObject.getInt("code");
							if (code == 1) {
								showToastMessage(R.string.exchange_success);
								pushToMainActivity();
							} else {
								showToastMessage(R.string.exchange_failed);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							showToastMessage(R.string.exchange_failed);
						}
					}
				});
	}

	private void pushToMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (UserModel.sharedModel().addressInfoArray.size() > 0) {
			AddressInfo addressInfo = UserModel.sharedModel().addressInfoArray.get(0);
			mConfirmButton.setText(R.string.confirm);
			mNameTextView.setVisibility(View.VISIBLE);
			mNameTextView.setText(addressInfo.name);
			mAddressTextView.setVisibility(View.VISIBLE);
			mAddressTextView.setText(addressInfo.address);
			mPhoneTextView.setVisibility(View.VISIBLE);
			mPhoneTextView.setText(addressInfo.phoneNumber);
		} else {
			mConfirmButton.setText(R.string.add_address_info);
			mNameTextView.setVisibility(View.GONE);
			mAddressTextView.setVisibility(View.GONE);
			mPhoneTextView.setVisibility(View.GONE);
		}
	}
}
