package com.onyx.international.activities;

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
import com.onyx.international.models.Gift;
import com.onyx.international.models.GiftModel;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.ScaleUtil;
import com.onyx.international.utils.StringUtil;

public class QuickExchangeActivity extends BaseActivity {

	private EditText mCaptchaEditText;
	private Button mConfirmButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_quick_exchange);

		setTitle(R.string.exchange_gift);
		
		setTopBarLeftButtonTypeBack();
		
		mCaptchaEditText = (EditText) findViewById(R.id.edittext_quick_exchange);
		mConfirmButton = (Button) findViewById(R.id.button_quick_exchange);

		ScaleUtil.scaleWidgetWithParentLinearLayout(mCaptchaEditText, 608, 70, 16, 16, 20, 0);
		ScaleUtil.scaleWidgetWithParentLinearLayout(mConfirmButton, 608, 88, 16, 16, 20, 0);
	}

	public void onConfirmButtonClicked(View view) {
		String captcha = mCaptchaEditText.getText().toString();
		if (TextUtils.isEmpty(captcha)) {
			showToastMessage(R.string.toast_captcha_not_null);
			return;
		}
		String[] keys = {"phone", "code", "step"};
		String[] values = {UserModel.sharedModel().bindPhoneNumber, captcha, "1"};
		NetworkManager.sharedManager().sendRequest("", StringUtil.buildKeysAndValues(keys, values),
				new NetworkManagerCallback() {
					
					@Override
					public void onNetworkRequestSuccess(String data) {
						// TODO Auto-generated method stub
						Gift gift = null;
						try {
							gift = GiftModel.parseGiftFromJson(new JSONObject(data));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if (gift == null || gift.id <= 0) {
							showToastMessage(R.string.exchange_captcha_error);
						} else {
							GiftModel.sharedModel().addOrUpdateGift(gift);
							GiftModel.sharedModel().mCurrentSelectGiftId = gift.id;
							pushToExchangeConfirmActivity();
						}
					}
				});
	}
	
	private void pushToExchangeConfirmActivity() {
		Intent intent = new Intent(this, ExchangeConfirmActivity.class);
		startActivity(intent);
	}
}
