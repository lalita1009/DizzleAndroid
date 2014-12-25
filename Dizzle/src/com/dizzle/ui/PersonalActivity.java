package com.dizzle.ui;

import com.dizzle.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class PersonalActivity extends Activity{
	
	Context mContext;
	TextView txtPlus;
	EditText persoanl_meg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_act_xml);
		
		mContext = this;
		
		txtPlus = (TextView) findViewById(R.id.txtPlus);
		persoanl_meg = (EditText) findViewById(R.id.persoanl_meg);
		txtPlus.setOnClickListener(addDone);
		
		AndroidKeyboard.hideKeyboard(mContext);
	}
	
	OnClickListener addDone = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	};

}
