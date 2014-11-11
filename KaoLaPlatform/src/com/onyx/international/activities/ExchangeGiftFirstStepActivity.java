package com.onyx.international.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ExchangeGiftFirstStepActivity extends BaseActivity {

	private ImageFetcher mImageFetcher;
	private ImageView mImageView;
	private TextView mContentTextView;
	private Button mNextStepButton;
	private EditText mEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_exchange_gift_first_step);
		
		mImageView = (ImageView) findViewById(R.id.imageview_exchanged_gift_first_step);
		mContentTextView = (TextView) findViewById(R.id.textview_exchanged_gift_first_step_content);
		mNextStepButton = (Button) findViewById(R.id.button_exchanged_gift_first_step);
		mEditText = (EditText) findViewById(R.id.edittext_exchanged_gift);

		mImageFetcher = new ImageFetcher(this, 240);
		
		Gift gift = GiftModel.sharedModel().getGift(GiftModel.sharedModel().mCurrentSelectGiftId);
		
		setTitle(gift.name);
		
		mContentTextView.setText(gift.description);

		ScaleUtil.scaleWidgetWithParentLinearLayout(mImageView, 98, 98, 15, 15);

		mImageFetcher.loadImage(gift.imageArray.get(0), mImageView);
		
		setTopBarLeftButtonTypeBack();

		ScaleUtil.scaleWidgetWithParentLinearLayout(mEditText, 600, 80, 20, 20, 20, 20);
		ScaleUtil.scaleWidgetWithParentLinearLayout(mNextStepButton, 608, 88, 16, 16, 10, 10);
		
	}

	public void onNextStepButtonClicked(View view) {
		final String captcha = mEditText.getText().toString();
		if (TextUtils.isEmpty(captcha)) {
			showToastMessage(R.string.toast_captcha_not_null);
			return;
		}
		
		String[] keys = {"phone", "code", "step"};
		String[] values = {UserModel.sharedModel().bindPhoneNumber, captcha, "1"};
		NetworkManager.sharedManager().sendRequest("exchange", StringUtil.buildKeysAndValues(keys, values),
				new NetworkManagerCallback() {
					
					@Override
					public void onNetworkRequestSuccess(String data) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonObject = new JSONObject(data);
							UserModel.sharedModel().tmpCaptchaCode = captcha;
							int giftId = jsonObject.getInt("gift_id");
							if (giftId == GiftModel.sharedModel().mCurrentSelectGiftId) {
								GiftModel.sharedModel().currentExchangedGiftId = GiftModel.sharedModel().mCurrentSelectGiftId;
								Intent intent = new Intent(ExchangeGiftFirstStepActivity.this, ExchangeConfirmActivity.class);
								startActivity(intent);
							} else {
								showToastMessage(R.string.exchange_captcha_error);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							showToastMessage(R.string.exchange_captcha_error);
						}
						GiftModel.sharedModel().currentExchangedGiftId = GiftModel.sharedModel().mCurrentSelectGiftId;
						Intent intent = new Intent(ExchangeGiftFirstStepActivity.this, ExchangeConfirmActivity.class);
						startActivity(intent);
					}
				});
	}
}
