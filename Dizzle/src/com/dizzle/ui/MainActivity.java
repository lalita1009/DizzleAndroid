package com.dizzle.ui;

import com.dizzle.AppConstants;
import com.dizzle.R;
import com.dizzle.R.color;
import com.dizzle.R.drawable;
import com.dizzle.R.id;
import com.dizzle.R.layout;
import com.parse.Parse;
import com.parse.ParseObject;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	public TabHost tabHost;
	public TabWidget tabWidget;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		makeTabwidget();
		
	}

	@SuppressLint("NewApi")
	private void makeTabwidget() {
		tabHost = getTabHost();

		// Tab for Vendors
		TabSpec profilespec = tabHost.newTabSpec("Profile");

		View tabIndicator_profile = LayoutInflater.from(this).inflate(
				R.layout.tab_layout, getTabWidget(), false);
		TextView title = (TextView) tabIndicator_profile
				.findViewById(R.id.tab_title);
		title.setText("Profile");
		ImageView icon = (ImageView) tabIndicator_profile
				.findViewById(R.id.icon);
		icon.setImageResource(R.drawable.icobody2);
		tabIndicator_profile.setBackgroundColor(Color.parseColor(AppConstants.tabBackground));

		profilespec.setIndicator(tabIndicator_profile);
		profilespec.setContent(new Intent(this, ScreenProfile.class));

		// Tab for Vendors
		TabSpec vendorspec = tabHost.newTabSpec("Vendors");

		View tabIndicator_vendors = LayoutInflater.from(this).inflate(
				R.layout.tab_layout, getTabWidget(), false);
		TextView title1 = (TextView) tabIndicator_vendors
				.findViewById(R.id.tab_title);
		title1.setText("Vendors");
		ImageView icon1 = (ImageView) tabIndicator_vendors
				.findViewById(R.id.icon);
		icon1.setImageResource(R.drawable.icowrench);
		tabIndicator_vendors.setBackgroundColor(Color.parseColor(AppConstants.tabBackground));
		
		vendorspec.setIndicator(tabIndicator_vendors);
		vendorspec.setContent(new Intent(this, ScreenVendors.class));

		// Tab for Share
		TabSpec sharespec = tabHost.newTabSpec("Share");

		View tabIndicator_share = LayoutInflater.from(this).inflate(
				R.layout.tab_layout, getTabWidget(), false);
		TextView title2 = (TextView) tabIndicator_share
				.findViewById(R.id.tab_title);
		title2.setText("Share");
		ImageView icon2 = (ImageView) tabIndicator_share
				.findViewById(R.id.icon);
		icon2.setImageResource(R.drawable.uploadicon);

		tabIndicator_share.setBackgroundColor(Color.parseColor(AppConstants.tabBackground));
		sharespec.setIndicator(tabIndicator_share);
		sharespec.setContent(new Intent(this, ScreenShare.class));

		tabHost.addTab(profilespec);
		tabHost.addTab(vendorspec);
		tabHost.addTab(sharespec);

		tabHost.setCurrentTabByTag("Profile");

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
					View view = tabHost.getTabWidget().getChildTabViewAt(i);
					if (view != null) {
						TextView textView = (TextView) view
								.findViewById(R.id.tab_title);
						if (i == tabHost.getCurrentTab()) {
							textView.setTextColor(Color.parseColor(AppConstants.mainTheme));
						} else {
							textView.setTextColor(getResources().getColor(
									R.color.GRAY));
						}
					}

				}
			}
		});

		tabWidget = tabHost.getTabWidget();
		RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) tabWidget
				.getLayoutParams();
		lParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
		lParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
		tabWidget.setLayoutParams(lParams);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}