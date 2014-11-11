package com.onyx.international.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onyx.international.kaolaplatform.R;
import com.onyx.international.utils.ScaleUtil;

public class ForgetPasswordActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_forget_password);

		setTitle(R.string.forget_password);
		
		setTopBarLeftButtonTypeBack();
		
		EditText accountEditText = (EditText) findViewById(R.id.edittext_forgetpassword_account);
		Button confirmButton = (Button) findViewById(R.id.button_forgetpassword);

		ScaleUtil.scaleWidgetWithParentLinearLayout(accountEditText, 608, 70, 16, 16, 0, 0);
		ScaleUtil.scaleWidgetWithParentLinearLayout(confirmButton, 608, 88, 16, 16, 20, 0);
	}
	
	public void onConfirmButtonClicked(View view) {
		
	}
}
