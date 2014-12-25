package com.dizzle.ui;

import com.dizzle.R;
import com.dizzle.R.id;
import com.dizzle.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScreenShare extends Activity implements OnClickListener {
	Button btnContacts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		setContentView(R.layout.activity_share);

		btnContacts = (Button) findViewById(R.id.btnContacts);
		
		btnContacts.setOnClickListener(this);

	}

	public void onClick(View v) {
		if (v == btnContacts) {
			
			Intent intent=new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

			// Add data to the intent, the receiving app will decide what to do with it.
			intent.putExtra(Intent.EXTRA_SUBJECT, "Dizzle Link Share");
			intent.putExtra(Intent.EXTRA_TEXT, "That is just dizzle link:");
			
			startActivity(Intent.createChooser(intent, "Share link"));
		} 
	}
}
