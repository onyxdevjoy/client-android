package com.onyx.international.activities;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Program.TextureType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.onyx.international.database.SharedPreferenceManager;
import com.onyx.international.kaolaplatform.R;
import com.onyx.international.models.UserModel;
import com.onyx.international.utils.ScaleUtil;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);

		ScaleUtil.init(this);

		SharedPreferenceManager.sharedManager().init(this);
		String bindPhoneNumber = SharedPreferenceManager.sharedManager().getString("bindPhone");
		if (!TextUtils.isEmpty(bindPhoneNumber)) {
			UserModel.sharedModel().bindPhoneNumber = bindPhoneNumber;
			pushToMainActivity();
			return;
		}
		
		setContentView(R.layout.activity_welcome);
		
		Button loginButton = (Button) findViewById(R.id.button_welcome_login);
		Button registerButton = (Button) findViewById(R.id.button_welcome_register);
		

		ScaleUtil.scaleWidgetWithParentRelativeLayout(loginButton, 240, 76, 70, 0, 684, 0);
		ScaleUtil.scaleWidgetWithParentRelativeLayout(registerButton, 240, 76, 330, 0, 684, 0);
	}

	public void onLoginButtonClicked(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	
	public void onRegisterButtonClicked(View view) {
		Intent intent = new Intent(this, RegisterStepOneActivity.class);
		startActivity(intent);
	}

	private void pushToMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}

