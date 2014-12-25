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

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SubVendorsMain extends Fragment implements OnClickListener {
	TextView txtPlus;
	ListView listVendors;
	VendorsAdapter vendorsAdapter;
	private static String TAG =  "SubVendorsMain";

	String[] vendorlists = { "Accountant", "Adobe Repair",
			"Aerial Phototography", "Alarm Permits", "Animal Control",
			"Appliance", "Accountant", "Adobe Repair", "Aerial Phototography",
			"Alarm Permits", "Animal Control", "Appliance", "Accountant",
			"Adobe Repair", "Aerial Phototography", "Alarm Permits",
			"Animal Control", "Appliance" };

	View view;
	private OnChangeFragmentListener listener;
	public void setOnChangeFragmentListener(OnChangeFragmentListener listener) {
		this.listener = listener;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		vendorlists = DizzleManager.getInstance().getVendorCategories();
		
		view = inflater.inflate(R.layout.activity_mainvendors, null);
		
		RelativeLayout layoutActionBar = (RelativeLayout) view.findViewById(R.id.layoutActionBar);
		layoutActionBar.setBackgroundColor(Color
				.parseColor(AppConstants.mainTheme));

		txtPlus = (TextView) view.findViewById(R.id.txtPlus);
		listVendors = (ListView) view.findViewById(R.id.listVendors);

		VendorsAdapter vendorsAdapter = new VendorsAdapter(getActivity());
		listVendors.setAdapter(vendorsAdapter);

		listVendors.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d(TAG, "Clicked on item #" + arg2);
				String name = vendorlists[arg2];
				DizzleManager.getInstance().setVendorCategory(name);
				listener.onChangeFragment(AppConstants.SUB_VENDOR_MAIN,
						AppConstants.SUB_VENDOR_ADD);
			}

		});
		txtPlus.setOnClickListener(this);
		//overridePendingTransition(0, 0);
		
		return view;
	}

	public void onClick(View v) {
		if (v == txtPlus) {
			final LinearLayout linear = (LinearLayout) View.inflate(getActivity(),
					R.layout.dialog_vendernew, null);

			TextView txtContacts = (TextView) linear
					.findViewById(R.id.txtContacts);
			TextView txtScratch = (TextView) linear
					.findViewById(R.id.txtScratch);

			txtContacts.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_PICK,
							Contacts.CONTENT_URI);
					intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
					startActivityForResult(intent, 0);
				}
			});

			txtScratch.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					startActivity(new Intent(getActivity(),
							VendorAdd.class));
				}
			});

			new AlertDialog.Builder(getActivity()).setView(linear)
					.setNegativeButton("Cancel", null).show();
		}
	}

	class VendorsAdapter extends BaseAdapter {
		LayoutInflater Inflater;
		Context maincon;

		public VendorsAdapter(Context paramContext) {
			maincon = paramContext;
			Inflater = ((LayoutInflater) paramContext
					.getSystemService("layout_inflater"));
		}

		public int getCount() {
			return vendorlists.length;
		}

		public String getItem(int paramInt) {
			return vendorlists[paramInt];
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			if (paramView == null)
				paramView = Inflater.inflate(R.layout.listitem_vendor,
						paramViewGroup, false);

			final ImageView itemicon = (ImageView) paramView
					.findViewById(R.id.itemicon);
			ParseObject category = DizzleManager.getInstance().getCategoryByName(vendorlists[paramInt]);
			ParseFile iconFile = (ParseFile) category.get("icon");
			iconFile.getDataInBackground(new GetDataCallback(){
				public void done(byte[] data,
	                    ParseException e) {
	                if (e == null) {
	                    Log.d("test",
	                            "We've got data in data.");
	                    // Decode the Byte[] into
	                    // Bitmap
	                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
	                    
	                    // Set the Bitmap into the
	                    // ImageView
	                    itemicon.setImageBitmap(bmp);
	                } else {
	                    Log.d("test",
	                            "There was a problem downloading the data.");
	                }
	            }
			});
			TextView itemname = (TextView) paramView
					.findViewById(R.id.itemname);
			TextView itemprice = (TextView) paramView
					.findViewById(R.id.itemprice);

			// itemicon.setBackgroundResource(R.drawable.ico_email);
			itemname.setText(vendorlists[paramInt]);

			return paramView;
		}
	}
}
