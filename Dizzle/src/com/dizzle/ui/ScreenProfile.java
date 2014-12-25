package com.dizzle.ui;

import com.dizzle.AppConstants;
import com.dizzle.DizzleManager;
import com.dizzle.R;
import com.dizzle.R.id;
import com.dizzle.R.layout;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScreenProfile extends Activity implements OnClickListener {
	TextView txtTitle;
	TextView txtEdit;
	TextView txtLogout;
	TextView txtUrl;
	TextView txtPhoneNumber;
	TextView txtEmailAddress;
	TextView txtMailingAddress;
	ImageView	imgPhoto;
	ImageView imgBackgroundPhoto;
	ProgressDialog mDialog;

	private static String TAG = "ScreenProfile";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_profile);

		RelativeLayout	layoutActionBar = (RelativeLayout) findViewById(R.id.layoutActionBar);
		layoutActionBar.setBackgroundColor(Color.parseColor(AppConstants.mainTheme));

		txtTitle = (TextView) findViewById(R.id.txtAgentName);
		ParseObject agent = DizzleManager.getInstance().getAgent();
		if(agent == null)
		{
			Log.w(TAG, "Agent object is null!!!");
			return;
		}
		if(!agent.isDataAvailable())
		{
			try {
				agent.fetchIfNeeded();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(agent.has("firstName") && agent.has("lastName"))
		{
			txtTitle.setText(agent.getString("firstName") + " " + agent.getString("lastName") );
		}
		txtEdit = (TextView) findViewById(R.id.txtEdit);
		txtLogout = (TextView) findViewById(R.id.txtLogout);
		txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
		txtPhoneNumber.setText(agent.getString("phoneNumber"));
		txtEmailAddress = (TextView) findViewById(R.id.txtEmailAddress);
		txtEmailAddress.setText(agent.getString("email"));
		txtMailingAddress = (TextView) findViewById(R.id.txtMailingAddress);
		txtMailingAddress.setText(agent.getString("officeName") + "\n" + agent.getString("street") + "\n" + agent.getString("city") + ", " + agent.getString("state") + " " + agent.getString("postalCode") );
		txtUrl = (TextView) findViewById(R.id.txtUrl);
		txtUrl.setText(agent.getString("website"));
		imgPhoto = (RoundedImageView) findViewById(R.id.imgPhoto);
		ParseFile backgroundImageFile = (ParseFile) agent.get("backgroundPhoto");
		mDialog = ProgressDialog.show(ScreenProfile.this, "","Downloading Image...", true);
		backgroundImageFile.getDataInBackground(new GetDataCallback(){
			public void done(byte[] data,
					ParseException e) {
				if (e == null) {
					Log.d("test",
							"We've got data in data.");
					// Decode the Byte[] into
					// Bitmap
					Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
					// Get the ImageView from main.xml
					//ImageView image = (ImageView) findViewById(R.id.ad1);
					ImageView ad1 = (ImageView) findViewById(R.id.imgBackgroundPhoto);
					// Set the Bitmap into the
					// ImageView
					ad1.setImageBitmap(bmp);
					// Close progress dialog
					mDialog.dismiss();
				} else {
					Log.d("test",
							"There was a problem downloading the data.");
				}
			}
		});

		txtEdit.setOnClickListener(this);
		txtLogout.setOnClickListener(this);

	}

	public void onClick(View v) {
		if (v == txtEdit) {
			startActivity(new Intent(ScreenProfile.this, EditVendorActivity.class));
		} else if (v == txtLogout) {
			startActivity(new Intent(ScreenProfile.this, LoginActivity.class));
			finish();
		}
	}
}
