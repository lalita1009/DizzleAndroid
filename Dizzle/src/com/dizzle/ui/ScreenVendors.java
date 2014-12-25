package com.dizzle.ui;

import com.dizzle.AppConstants;
import com.dizzle.R;
import com.dizzle.R.id;
import com.dizzle.R.layout;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class ScreenVendors extends Activity implements OnChangeFragmentListener {
	FragmentManager manager;
	FragmentTransaction transaction;
	private static String TAG = "ScreenVendors";
	
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment);

		manager = getFragmentManager();

		SubVendorsMain fragments = new SubVendorsMain();
		fragments.setOnChangeFragmentListener(this);

		manager.beginTransaction().add(R.id.viewFragments, fragments)
				.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).commit();
	}
	
	protected void onResume(){
		AppConstants.gListener = this;
		super.onResume();
	}
	
	@Override
	public void onChangeFragment(int prevID, int nextID) {
		if (nextID == AppConstants.SUB_VENDOR_MAIN) {
			
			SubVendorsMain fragments = new SubVendorsMain();
			fragments.setOnChangeFragmentListener(this);

			manager.beginTransaction().replace(R.id.viewFragments, fragments)
					.commit();

		} else if (nextID == AppConstants.SUB_VENDOR_ADD) {
			
			SubVendorDetail fragments = new SubVendorDetail();
			fragments.setOnChangeFragmentListener(this);

			manager.beginTransaction().replace(R.id.viewFragments, fragments)
					.commit();
			
		}
	}
}
