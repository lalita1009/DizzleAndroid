package com.dizzle.ui;

import com.dizzle.AppConstants;
import com.dizzle.R;
import com.dizzle.R.id;
import com.dizzle.R.layout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VendorAdd extends Activity implements OnClickListener {
	TextView 	txtAdd;
	TextView	txtCancel;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		setContentView(R.layout.activity_newvendor);

		RelativeLayout	layoutActionBar = (RelativeLayout) findViewById(R.id.layoutActionBar);
		layoutActionBar.setBackgroundColor(Color.parseColor(AppConstants.mainTheme));
		
		txtAdd = (TextView) findViewById(R.id.txtAdd);
		txtCancel = (TextView) findViewById(R.id.txtCancel);

		txtAdd.setOnClickListener(this);
		txtCancel.setOnClickListener(this);

	}

	public void onClick(View v) {
		if (v == txtAdd) {
			
		} else if (v == txtCancel){
			finish();
		}
	}
	
}
