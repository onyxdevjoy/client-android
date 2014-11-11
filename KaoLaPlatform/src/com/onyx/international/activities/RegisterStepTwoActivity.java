package com.onyx.international.activities;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.onyx.international.database.SharedPreferenceManager;
import com.onyx.international.kaolaplatform.R;
import com.onyx.international.manager.NetworkManager;
import com.onyx.international.manager.NetworkManagerCallback;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.ScaleUtil;
import com.onyx.international.utils.StringUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class RegisterStepTwoActivity extends BaseActivity {

	private EditText mPasswordEditText;
	private EditText mPasswordRepeatEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register_step_two);
		
		setTopBarLeftButtonTypeBack();
		
		mPasswordEditText = (EditText) findViewById(R.id.edittext_register_step_two_password);
		mPasswordRepeatEditText = (EditText) findViewById(R.id.edittext_register_step_two_password_repeat);
		Button registerButton = (Button) findViewById(R.id.button_register_step_two);

		ScaleUtil.scaleWidgetWithParentLinearLayout(mPasswordEditText, 608, 70, 16, 16, 20, 0);
		ScaleUtil.scaleWidgetWithParentLinearLayout(mPasswordRepeatEditText, 608, 70, 16, 16, 20, 0);
		ScaleUtil.scaleWidgetWithParentLinearLayout(registerButton, 608, 88, 16, 16, 20, 0);
	}
	
	public void onRegisterButtonClicked() {
		final String password = mPasswordEditText.getText().toString();
		final String passwordRepeat = mPasswordRepeatEditText.getText().toString();
		
		if (TextUtils.isEmpty(password)) {
			showToastMessage(R.string.password_not_null);
			return;
		}
		
		if (TextUtils.isEmpty(passwordRepeat)) {
			showToastMessage(R.string.password_repeat_not_null);
			return;
		}
		
		if (!password.equals(passwordRepeat)) {
			showToastMessage(R.string.password_not_equal);
			return;
		}
		
		String[] keys = {"phone", "step", "code", "passwd"};
		String[] values = {UserModel.sharedModel().tmpPhoneNumber, "3", UserModel.sharedModel().tmpCaptchaCode, password};
		NetworkManager.sharedManager().sendRequest("register", StringUtil.buildKeysAndValues(keys, values), new NetworkManagerCallback() {
			
			@Override
			public void onNetworkRequestSuccess(String data) {
				// TODO Auto-generated method stub

				try {
					JSONObject jsonObject = new JSONObject(data);
					final int code = jsonObject.getInt("code");
					
					String returnPhone = jsonObject.getString("phone");
					if (returnPhone.equalsIgnoreCase(UserModel.sharedModel().tmpPhoneNumber)) {
						
					} else {
						
					}

					SharedPreferenceManager.sharedManager().setString("bindPhone", returnPhone);
					UserModel.sharedModel().bindPhoneNumber = returnPhone;
					
					if (code == 1) {
						pushToMainActivity();
					} else {
						showToastMessage(R.string.register_failed);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					showToastMessage(R.string.register_failed);
					e.printStackTrace();
				}
			}
		});
	}
	
	private void pushToMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
