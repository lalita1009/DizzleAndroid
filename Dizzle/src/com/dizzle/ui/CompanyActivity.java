package com.dizzle.ui;

import com.dizzle.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class CompanyActivity extends Activity{
	
	Context mcContext;
	TextView txtPlus;
	EditText company_ewm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_xml);
		mcContext = this;
		txtPlus = (TextView) findViewById(R.id.txtPlus);
		company_ewm = (EditText) findViewById(R.id.company_ewm);
		txtPlus.setOnClickListener(addDone);
		
		AndroidKeyboard.hideKeyboard(mcContext);
	}
	
	OnClickListener addDone = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	};

}
