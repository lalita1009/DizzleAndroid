package com.dizzle.ui;

import com.dizzle.DizzleManager;
import com.dizzle.R;
import com.dizzle.R.layout;
import com.parse.Parse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGTH = 1000;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);
		 
		Parse.initialize(this, "ZlJhw8ecCF5Wp5Lw2wPXFqWteJsaABn4IbY58zq7", "cZ3QI1BtIBm6XidhfWi8D0q2nNGUzL8JfYiWZEe6");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				DizzleManager dizzle = DizzleManager.getInstance();
				dizzle.setContext(getApplicationContext());
				
				/* Create an Intent that will start the Menu-Activity. */
				Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}
}
