package com.onyx.international.activities;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onyx.international.kaolaplatform.R;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.ScaleUtil;
import com.onyx.international.utils.StringUtil;

public class RegisterStepOneActivity extends BaseActivity {

	EditText mAccountEditText;
	EditText mCaptchaEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register_step_one);

		setTopBarLeftButtonTypeBack();
		
		setTitle(R.string.request_captcha);
		
		mAccountEditText = (EditText) findViewById(R.id.edittext_register_one_account);
		mCaptchaEditText = (EditText) findViewById(R.id.edittext_register_one_captcha);
		Button reqeustCaptchaButton = (Button) findViewById(R.id.button_register_one_captcha);
		Button confirmButton = (Button) findViewById(R.id.button_register_one_confirm);

		ScaleUtil.scaleWidgetWithParentRelativeLayout(mAccountEditText, 388, 70, 16, 0, 30, 0);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(mCaptchaEditText, 388, 70, 16, 0, 30, 0);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(reqeustCaptchaButton, 200, 70, 16, 0, 30, 0);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(confirmButton, 200, 70, 16, 0, 30, 0);
	}

	public void onRequestCaptchaButtonClicked(View view) {
		String mobileNumber = mAccountEditText.getText().toString();
		if (TextUtils.isEmpty(mobileNumber) || mobileNumber.length() != 11) {
			showToastMessage(R.string.mobile_number_error);
			return;
		}
		
		String[] keys = {"phone", "step"};
		String[] values = {mobileNumber, "1"};
		NetworkManager.sharedManager().sendRequest("register", StringUtil.buildKeysAndValues(keys, values), new NetworkManagerCallback() {
			
			@Override
			public void onNetworkRequestSuccess(String data) {
				// TODO Auto-generated method stub

				try {
					JSONObject jsonObject = new JSONObject(data);
					final int code = jsonObject.getInt("code");
					
					if (code == 1) {
						showToastMessage(R.string.get_captcha_success);
					} else if (code == 2) {
						showToastMessage(R.string.account_exist);
					} else {
						showToastMessage(R.string.get_captcha_failed);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					showToastMessage(R.string.get_captcha_failed);
					e.printStackTrace();
				}
			}
		});
	}
	
	public void onConfirmButtonClicked(View view) {
		final String mobileNumber = mAccountEditText.getText().toString();
		if (TextUtils.isEmpty(mobileNumber) || mobileNumber.length() != 11) {
			showToastMessage(R.string.mobile_number_error);
			return;
		}
		
		final String captchaNumber = mCaptchaEditText.getText().toString();
		
		String[] keys = {"phone", "step", "code"};
		String[] values = {mobileNumber, "2", captchaNumber};
		NetworkManager.sharedManager().sendRequest("register", StringUtil.buildKeysAndValues(keys, values), new NetworkManagerCallback() {
			
			@Override
			public void onNetworkRequestSuccess(String data) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(data);
					final int code = jsonObject.getInt("code");
					
					UserModel.sharedModel().tmpCaptchaCode = captchaNumber;
					UserModel.sharedModel().tmpPhoneNumber = mobileNumber;
					
					if (code == 1) {
						pushToRegisterStepTwoActivity();
					} else {
						showToastMessage(R.string.captcha_error);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private void pushToRegisterStepTwoActivity() {
		Intent intent = new Intent(this, RegisterStepTwoActivity.class);
		startActivity(intent);
	}
}
