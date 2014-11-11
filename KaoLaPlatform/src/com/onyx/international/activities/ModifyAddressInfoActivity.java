package com.onyx.international.activities;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.onyx.international.kaolaplatform.R;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.AddressInfo;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.StringUtil;

public class ModifyAddressInfoActivity extends BaseActivity {

	private EditText mNameEditText;
	private EditText mAddressEditText;
	private EditText mPhoneEditText;
	private int mType; // 0 for add new address info, 1 for modify
	private int mAddressId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_modify_address_info);

		mNameEditText = (EditText) findViewById(R.id.edittext_modify_address_info_name);
		mAddressEditText = (EditText) findViewById(R.id.edittext_modify_address_info_address);
		mPhoneEditText = (EditText) findViewById(R.id.edittext_modify_address_info_phone);
		
		setTopBarLeftButtonTypeBack();
		
		Bundle data = getIntent().getExtras();
		mType = data.getInt("type");
		
		if (mType == 0) {
			setTitle(R.string.add_address_info);
		} else {
			setTitle(R.string.modify_address_info);
			
			mAddressId = data.getInt("address_id");
			AddressInfo info = UserModel.sharedModel().getAddressInfo(mAddressId);
			mNameEditText.setText(info.name);
			mAddressEditText.setText(info.address);
			mPhoneEditText.setText(info.phoneNumber);
		}
		
		setRightButton(R.string.save, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				requestSaveInfo();
			}
		});
	}

	private void requestSaveInfo() {
		String name = mNameEditText.getText().toString();
		String address = mAddressEditText.getText().toString();
		String phone = mPhoneEditText.getText().toString();
		
		String[] keys = {"name", "address", "address_id", "address_phone", "phone"};
		String[] values = {name, address, String.valueOf(mAddressId), phone, UserModel.sharedModel().bindPhoneNumber};

		Map<String, String> params = StringUtil.buildKeysAndValues(keys, values);
		if (mType == 0) {
			params.put("type", "1");
		} else {
			params.put("type", "2");
		}
		
		NetworkManager.sharedManager().sendRequest("address", params, new NetworkManagerCallback() {
			
			@Override
			public void onNetworkRequestSuccess(String data) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(data);
					int code = jsonObject.getInt("code");
					if (code == 1) {
						String name = mNameEditText.getText().toString();
						String address = mAddressEditText.getText().toString();
						String phone = mPhoneEditText.getText().toString();
						
						AddressInfo info = new AddressInfo();
						info.id = mAddressId;
						info.name = name;
						info.address = address;
						info.phoneNumber = phone;
						UserModel.sharedModel().addAddressInfo(info);
						ModifyAddressInfoActivity.this.finish();
						
					} else {
						
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
