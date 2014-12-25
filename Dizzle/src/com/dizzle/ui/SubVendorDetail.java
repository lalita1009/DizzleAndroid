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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SubVendorDetail extends Fragment implements OnClickListener {
	private static String TAG = "SubVendorDetail";
	TextView txtPlus;
	TextView txtCancel;
	ListView listVendors;
	VendorsAdapter vendorsAdapter;

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
		Log.d(TAG, "onCreateView");
		vendorlists = DizzleManager.getInstance().getBusinessList();
		ParseObject category = DizzleManager.getInstance().getCurrentVendorCategory();
		view = inflater.inflate(R.layout.activity_editvendor, null);

		RelativeLayout	layoutActionBar = (RelativeLayout) view.findViewById(R.id.layoutActionBar);
		layoutActionBar.setBackgroundColor(Color.parseColor(AppConstants.mainTheme));
		
		txtPlus = (TextView) view.findViewById(R.id.txtPlus);
		txtCancel = (TextView) view.findViewById(R.id.txtCancel);
		listVendors = (ListView) view.findViewById(R.id.listVendors);
		
		TextView txtTitle = (TextView) view.findViewById(R.id.loginTitle);
		txtTitle.setText(category.getString("name"));

		vendorsAdapter = new VendorsAdapter(getActivity());
		listVendors.setAdapter(vendorsAdapter);
		
		txtPlus.setOnClickListener(this);
		txtCancel.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		vendorsAdapter.notifyDataSetChanged();
	}

	public void onClick(View v) {
		Log.d(TAG, "onClick");
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
					startActivity(new Intent(getActivity(), VendorAdd.class));
				}
			});

			new AlertDialog.Builder(getActivity()).setView(linear)
					.setNegativeButton("Cancel", null).show();
		} else if (v == txtCancel) {
			listener.onChangeFragment(AppConstants.SUB_VENDOR_ADD,
					AppConstants.SUB_VENDOR_MAIN);
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
				paramView = Inflater.inflate(R.layout.listitem_edit_vendor,
						paramViewGroup, false);

			final ImageView itemicon = (ImageView) paramView
					.findViewById(R.id.imgIcon);
			TextView itemname = (TextView) paramView
					.findViewById(R.id.itemname);
			TextView txtCompanyName = (TextView) paramView.findViewById(R.id.itemname_1);
			TextView txtPhoneEmail = (TextView) paramView.findViewById(R.id.txtPhoneEmail);
			TextView txtEdit = (TextView) paramView.findViewById(R.id.txtEdit);
			
			final ParseObject business = DizzleManager.getInstance().getBusinessByName(vendorlists[paramInt]);
			if(business != null)
			{
				try {
					business.refresh();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				itemname.setText(business.getString("name"));
				txtCompanyName.setText(business.getString("company"));
				txtPhoneEmail.setText( (business.getString("phoneNumber") != null ? business.getString("phoneNumber") : "")  + " " + (business.getString("email") != null ? business.getString("email") : ""));
				ParseFile iconFile = business.getParseFile("icon");
				txtEdit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						DizzleManager.getInstance().setBusiness(business);
						startActivity(new Intent(getActivity(), EditVendorActivity.class));
					}
				});
				
				
				if(iconFile == null)
				{
					ParseObject category = DizzleManager.getInstance().getCurrentVendorCategory();
					if(category != null)
					{
						iconFile = category.getParseFile("icon");
					}
					else
					{
						Log.w(TAG, "current category is null");
					}
				}
				if(iconFile != null)
				{
					iconFile.getDataInBackground(new GetDataCallback(){
						public void done(byte[] data,
			                    ParseException e) {
			                if (e == null) {
			                    Log.d("test",
			                            "We've got " + data.length + " bytes in data.");
			                    // Decode the Byte[] into
			                    // Bitmap
			                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
			                    // Get the ImageView from main.xml
			                    //ImageView image = (ImageView) findViewById(R.id.ad1);
			                   
			                    if(itemicon != null)
			                    {
			                    	itemicon.setImageBitmap(bmp);
			                    }
			                    else
			                    {
			                    	Log.w(TAG, "itemicon is null");
			                    }
			                    // Close progress dialog
			                } else {
			                    Log.d("test",
			                            "There was a problem downloading the data.");
			                }
			            }
					});
				}
			}
			else
			{
				Log.w(TAG, "Business is null");
			}
			
			// itemicon.setBackgroundResource(R.drawable.ico_email);
			
			return paramView;
		}
	}
	
	

}
