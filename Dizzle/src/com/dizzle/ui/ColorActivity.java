package com.dizzle.ui;

import com.dizzle.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ColorActivity extends Activity{

	ColorPicker colorPicker;
	Button show_coloricon;
	TextView show_textcolorcode;

	TextView txtCancel,txtPlus;

	String hexColor="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_xml);

		colorPicker = (ColorPicker) findViewById(R.id.colorPicker);
		show_coloricon = (Button) findViewById(R.id.show_coloricon);
		show_textcolorcode = (TextView) findViewById(R.id.show_textcolorcode);
		txtCancel = (TextView) findViewById(R.id.txtCancel);
		txtPlus = (TextView) findViewById(R.id.txtPlus);
		txtCancel.setOnClickListener(addCancel);
		txtPlus.setOnClickListener(addReset);
		useHandler();
	}

	OnClickListener addCancel = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mHandler.removeCallbacks(mRunnable);
			if(hexColor!=""){
				Intent returnIntent = new Intent();
				returnIntent.putExtra("hexColor",hexColor);
				setResult(RESULT_OK,returnIntent);
				finish();
			}
		}
	};

	OnClickListener addReset = new OnClickListener() {

		@Override
		public void onClick(View v) {
			colorPicker.setColor(Color.WHITE);

		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mHandler.removeCallbacks(mRunnable);
	}



	Handler mHandler;
	public void useHandler() {
		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, 100);
	}

	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			Log.e("Handlers", "Calls");
			/** Do something **/
			int color = colorPicker.getColor();
			//int argb = Color.argb(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
			hexColor = "#" + Integer.toHexString(color).substring(2);
			show_textcolorcode.setText(hexColor);
			show_coloricon.setBackgroundColor(color);
			mHandler.postDelayed(mRunnable, 100);
		}
	};

}
