package com.dizzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.dizzle.ui.LoginActivity;
import com.dizzle.ui.MainActivity;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DizzleManager {

	private static DizzleManager sInstance;
	private static String TAG = "DizzleManager";
	
	private ParseUser mUser;
	private ParseObject mAgent;
	private Context mContext;
	private List<ParseObject> mVendorList;
	private String[] mVendorCategoryNames;
	private HashMap<String, ParseObject> mVendorCategories;
	private HashMap<String, ArrayList<ParseObject>> mBusinessesByCategoryName;
	private HashMap<String, ParseObject> mBusinessesByName;
	private ParseObject mCategory, mBusiness;
	
	public static DizzleManager getInstance()
	{
		if(sInstance == null)
		{
			sInstance = new DizzleManager();
			return sInstance;
		}
		else
		{
			return sInstance;
		}
		
	}
	
	public void setContext(Context ctx)
	{
		mContext = ctx;
	}
	
	public void setVendorCategory(String category)
	{
		mCategory = mVendorCategories.get(category);
	}
	
	public void setBusiness(ParseObject business)
	{
		Log.d(TAG, "setBusiness");
		mBusiness = business;
		if(mBusiness != null)
		{
			Log.d(TAG, "Set business to " + mBusiness.getString("company") + " ID#" + mBusiness.getObjectId());
		}
		else
		{
			Log.w(TAG, "Set business to null");
		}
	}
	
	public ParseObject getBusiness()
	{
		return mBusiness;
	}
	
	public ArrayList<ParseObject> getBusinesses()
	{
		return mBusinessesByCategoryName.get(mCategory.getString("name"));
	}
	
	
	public void setCurrentUser(ParseUser user)
	{
		mUser = user;
		if(mUser.has("agent"))
		{
			mAgent = (ParseObject) mUser.get("agent");
			if(mAgent == null)
			{
				Log.w(TAG, "agent object is null");
			}
		}
		else
		{
			Log.w(TAG, "User doesn't have agent key");
		}
	}
	
	public void fetchVendorCategories()
	{
		Log.d(TAG, "fetchVendorCategories");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("VendorListObject");
		if(mAgent == null)
		{
			Log.w(TAG, "Agent is null");
			return;
		}
		query.whereEqualTo("agent", mAgent);
		query.include("category");
		query.include("business");
		mVendorList = null;
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
		    public void done(List<ParseObject> categoryList, ParseException e) {
		        if (e == null) {
		            Log.d(TAG, "Retrieved " + categoryList.size() + " scores");
		            mVendorList = categoryList;
		            ArrayList<String> categories = new ArrayList<String>();
		            for(int i = 0; i < mVendorList.size(); i++)
		            {
		            	ParseObject vendor = (ParseObject)mVendorList.get(i);
		            	ParseObject category = vendor.getParseObject("category");
		            	ParseObject business = vendor.getParseObject("business");
		            	String key = category.getString("name");
		            	
		            	if(!categories.contains(category.getString("name") ) )
		            	{
		            		categories.add(key);
		            		mVendorCategories.put(key, category);
		            		ArrayList<ParseObject> bizList = new ArrayList<ParseObject>();
		            		mBusinessesByCategoryName.put(key, bizList);
		            		Log.d(TAG, "Added " + key);
		            	}
		            	ArrayList<ParseObject> list = mBusinessesByCategoryName.get(key);
		            	mBusinessesByName.put(business.getString("name"), business);
		            	list.add(business);
		            }
		            Collections.sort(categories);
		            mVendorCategoryNames = new String[categories.size()];
		            mVendorCategoryNames =  categories.toArray(mVendorCategoryNames);
		            Log.d(TAG,"Categories array has " + mVendorCategoryNames.length + " elements.");
		            
		            
		        } else {
		            Log.d(TAG, "Error: " + e.getMessage());
		        }
		    }
		});
	}
	
	public String[] getVendorCategories()
	{
		Log.d(TAG, "getVendorCategories");
		if(mVendorCategoryNames == null)
		{
			Log.w(TAG, "categories == null");
		}
		return mVendorCategoryNames;
	}
	
	public ParseObject getBusinessByName(String name)
	{
		if(mBusinessesByName == null)
		{
			Log.w(TAG, "mBusinessesByName is null");
			return null;
		}
		ParseObject business = mBusinessesByName.get(name);
		return business;
	}
	
	public void addBusinessMapping(String newName, ParseObject business)
	{
		mBusinessesByName.put(newName, business);
	}
	
	public ParseObject getCurrentVendorCategory()
	{
		return mCategory;
	}
	
	public String[] getBusinessList()
	{
		ArrayList<ParseObject> businesses = mBusinessesByCategoryName.get(mCategory.getString("name"));
		ArrayList<String> businessNames = new ArrayList<String>();
		for(int i = 0; i < businesses.size(); i++)
		{
			ParseObject biz = businesses.get(i);
			businessNames.add(biz.getString("name"));
		}
		
		Collections.sort(businessNames);
		String[] businessNamesList = new String[businessNames.size()];
		businessNamesList =  businessNames.toArray(businessNamesList);
		
		return businessNamesList;
	}
	
	public ParseObject getAgent()
	{
		return mAgent;
	}
	
	public void login(String email, String password, LogInCallback cb)
	{
		ParseUser.logInInBackground(email, password, cb);
	}
	
	public ParseObject getCategoryByName(String name)
	{
		if(mVendorCategories != null)
		{
			return mVendorCategories.get(name);
		}
		
		Log.w(TAG, "No category found with name " + name);
		return null;
	}
	
	private DizzleManager()
	{
		Log.d(TAG, "__ctor()");
		mVendorCategories = new HashMap<String, ParseObject>();
		mBusinessesByCategoryName = new HashMap<String, ArrayList<ParseObject>>();
		mBusinessesByName = new HashMap<String, ParseObject>();
	}
}
