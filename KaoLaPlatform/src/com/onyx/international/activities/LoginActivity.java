package com.onyx.international.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onyx.international.database.SharedPreferenceManager;
import com.onyx.international.kaolaplatform.R;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.ScaleUtil;
import com.onyx.international.utils.StringUtil;

public class LoginActivity extends BaseActivity {

	EditText mAccountEditText;
	EditText mPasswordEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		setTitle(R.string.login);
		
		setTopBarLeftButtonTypeBack();
		
		mAccountEditText = (EditText) findViewById(R.id.edittext_login_account);
		mPasswordEditText = (EditText) findViewById(R.id.edittext_login_password);
		Button loginButton = (Button) findViewById(R.id.button_login);

		ScaleUtil.scaleWidgetWithParentLinearLayout(mAccountEditText, 608, 70, 16, 16, 20, 0);
		ScaleUtil.scaleWidgetWithParentLinearLayout(mPasswordEditText, 608, 70, 16, 16, 20, 0);
		ScaleUtil.scaleWidgetWithParentLinearLayout(loginButton, 608, 88, 16, 16, 20, 0);
		
	}
	
	public void onLoginButtonClicked(View view) {
		final String account = mAccountEditText.getText().toString();
		final String password = mPasswordEditText.getText().toString();
		
		if (TextUtils.isEmpty(account)) {
			
			return;
		}
		
		if (TextUtils.isEmpty(password)) {
			
			return;
		}
		
		
		String[] keys = {"phone", "passwd"};
		String[] values = {account, password};
		NetworkManager.sharedManager().sendRequest("login", StringUtil.buildKeysAndValues(keys, values), new NetworkManagerCallback() {
			
			@Override
			public void onNetworkRequestSuccess(String data) {
				// TODO Auto-generated method stub

				try {
					JSONObject jsonObject = new JSONObject(data);
					final int code = jsonObject.getInt("code");
					
					String returnPhone = jsonObject.getString("phone");
					if (returnPhone.equalsIgnoreCase(account)) {
						
					} else {
						
					}
					SharedPreferenceManager.sharedManager().setString("bindPhone", returnPhone);
					UserModel.sharedModel().bindPhoneNumber = returnPhone;
					
					if (code == 1) {
						pushToMainActivity();
					} else {
						showToastMessage(R.string.login_failed);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					showToastMessage(R.string.login_failed);
					e.printStackTrace();
				}
			}
		});
	}
	
	public void onForgetPasswordButtonClicked(View view) {
		Intent intent = new Intent(this, ForgetPasswordActivity.class);
		startActivity(intent);
	}
	
	private void pushToMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
