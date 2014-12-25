package com.dizzle.ui;

import com.dizzle.DizzleManager;
import com.dizzle.R;
import com.dizzle.R.anim;
import com.dizzle.R.id;
import com.dizzle.R.layout;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	Button btnLogin;
	EditText editEmail;
	EditText editPassword;
	TextView txtForgot;
	ImageView	imgBack;
	LinearLayout	layoutPassword;
	private static String TAG = "LoginActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    
		setContentView(R.layout.activity_login);

		layoutPassword = (LinearLayout) findViewById(R.id.layoutPassword);
		editEmail = (EditText) findViewById(R.id.editEmail);
		editPassword = (EditText) findViewById(R.id.editPassword);
		
		//Debug User
		editEmail.setText("jason@dizzle.com");
		editPassword.setText("abc123");

		btnLogin = (Button) findViewById(R.id.btnLogin);
		txtForgot = (TextView) findViewById(R.id.txtForgot);

		imgBack = (ImageView) findViewById(R.id.imgBack);
		
		btnLogin.setOnClickListener(this);
		txtForgot.setOnClickListener(this);
		imgBack.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v == btnLogin) {
			Log.d(TAG, "onClick (login)");
			LogInCallback cb = new LogInCallback() {
				@Override
				  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					      // Hooray! The user is logged in.
					    	DizzleManager.getInstance().setCurrentUser(user);
					    	DizzleManager.getInstance().fetchVendorCategories();
					    	startActivity(new Intent(LoginActivity.this, MainActivity.class));
							overridePendingTransition(R.anim.slide_in_bottom,
									R.anim.slide_out_bottom);
							Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_LONG).show();
							
					    } else {
					      // Signup failed. Look at the ParseException to see what happened.
					    	Toast.makeText(LoginActivity.this, "Login failed (" + e.getLocalizedMessage() + ")", Toast.LENGTH_LONG).show();
					    }
					  }
		
					};
			DizzleManager.getInstance().login(editEmail.getText().toString(), editPassword.getText().toString(), cb);
			
		} else if (v == txtForgot) {
			
			layoutPassword.setVisibility(View.GONE);
			btnLogin.setText("Reset Password");
			imgBack.setVisibility(View.VISIBLE);
			
		} else if (v == imgBack) {
			
			layoutPassword.setVisibility(View.VISIBLE);
			btnLogin.setText("Log In");
			imgBack.setVisibility(View.INVISIBLE);
			
		}
	}
}
