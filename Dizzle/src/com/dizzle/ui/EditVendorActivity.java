package com.dizzle.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.dizzle.R;
import com.image.utility.RoundedImage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditVendorActivity extends Activity{

	Context mContext;
	TextView txtCancel,loginTitle,txtPlus;
	TextView edit_vandore_photo,edit_vandore_background,edit_vandore_logo;
	TextView edit_vandore_color,edit_vandore_personal,edit_vandore_company;
	ImageView edit_vandor_logoimg,edit_vandor_backgroundimg,edit_vandore_photoimg,edit_vandore_img;
	EditText edit_vandore_slogan;
	
	RelativeLayout relativelayout_photo,relativelayout_background;
	RelativeLayout relativelayout_logo,relativelayout_color;
	RelativeLayout relativelayout_personal,relativelayout_company;
	
	//Camera Code
	Bitmap image; //photo,
	public static Bitmap imageBitmap;
	private static final int CAMERA_REQUEST = 11;
	private static final int SELECT_FILE = 12;
	//	private static int RESULT_LOAD_IMAGE = 1;
	int currentVersion = Build.VERSION.SDK_INT;
	String path = android.os.Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "Dizzle_editProfile" + File.separator;
	Uri cameraImagePath;
	String absPath;
	String filePath = "null";
	boolean imageselectedflag=false;
	int flag=0;
	String[] extention, extention1;	Uri imageUri;
	String picturePath= Environment.getExternalStorageDirectory().getPath();
	static String fileName = null;
	int rotate;
	//End Of Code
	int select_type=0;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_vandor_act);

		mContext = this;
		txtCancel = (TextView) findViewById(R.id.txtCancel);
		loginTitle = (TextView) findViewById(R.id.loginTitle);
		txtPlus = (TextView) findViewById(R.id.txtPlus);
		edit_vandore_photo = (TextView) findViewById(R.id.edit_vandore_photo);
		edit_vandore_slogan = (EditText) findViewById(R.id.edit_vandore_slogan);
		edit_vandore_background = (TextView) findViewById(R.id.edit_vandore_background);
		edit_vandore_logo = (TextView) findViewById(R.id.edit_vandore_logo);
		edit_vandore_personal = (TextView) findViewById(R.id.edit_vandore_personal);
		edit_vandore_company = (TextView) findViewById(R.id.edit_vandore_company);
		edit_vandore_color = (TextView) findViewById(R.id.edit_vandore_color);
		edit_vandore_photoimg = (ImageView) findViewById(R.id.edit_vandore_photoimg);
		edit_vandor_logoimg = (ImageView) findViewById(R.id.edit_vandor_logoimg);
		edit_vandor_backgroundimg = (ImageView) findViewById(R.id.edit_vandor_backgroundimg);
		edit_vandore_img = (ImageView) findViewById(R.id.edit_vandore_img);
		
		relativelayout_photo = (RelativeLayout) findViewById(R.id.relativelayout_photo);
		relativelayout_background = (RelativeLayout) findViewById(R.id.relativelayout_background);
		relativelayout_logo = (RelativeLayout) findViewById(R.id.relativelayout_logo);
		relativelayout_color = (RelativeLayout) findViewById(R.id.relativelayout_color);
		relativelayout_personal = (RelativeLayout) findViewById(R.id.relativelayout_personal);
		relativelayout_company = (RelativeLayout) findViewById(R.id.relativelayout_company);
		

		txtCancel.setOnClickListener(addCancel);
		//edit_vandore_photo.setOnClickListener(addPhoto);
		//edit_vandore_background.setOnClickListener(addBackground);
		//edit_vandore_logo.setOnClickListener(addLogo);
		//edit_vandore_color.setOnClickListener(addColor);
		//edit_vandore_personal.setOnClickListener(addPersonal);
		//edit_vandore_company.setOnClickListener(addCompany);
		relativelayout_photo.setOnClickListener(addPhoto);
		relativelayout_background.setOnClickListener(addBackground);
		relativelayout_logo.setOnClickListener(addLogo);
		relativelayout_color.setOnClickListener(addColor);
		relativelayout_personal.setOnClickListener(addPersonal);
		relativelayout_company.setOnClickListener(addCompany);
		
		AndroidKeyboard.hideKeyboard(mContext);
	}

	OnClickListener addCancel = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};

	OnClickListener addPhoto = new OnClickListener() {

		@Override
		public void onClick(View v) {
			select_type = 1;
			showDialogForImageSelect(select_type,"Add Photo");
		}
	};

	OnClickListener addBackground = new OnClickListener() {

		@Override
		public void onClick(View v) {
			select_type = 2;
			showDialogForImageSelect(select_type,"Add Background");

		}
	};

	OnClickListener addLogo = new OnClickListener() {

		@Override
		public void onClick(View v) {
			select_type = 3;
			showDialogForImageSelect(select_type,"Add Logo");
		}
	};
	

	public void showDialogForImageSelect(int type,String title){
		final CharSequence[] items = {"Take Photo", "Choose from Gallery","Cancel"};
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(title);
		SharedPreferences mPref = mContext.getSharedPreferences("LoginUser", 0);
		Editor edit = mPref.edit().putBoolean("isTakingPic", true);
		edit.commit();
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int position) {
				try{
					if (items[position].equals("Take Photo")) {
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						fileName = System.currentTimeMillis()+".jpg";
						File style = new File(path);
						if(!style.exists()){style.mkdir();}
						File f = new File(path,fileName);
						absPath= f.getAbsolutePath();
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
						savePref(absPath);
						startActivityForResult(intent, CAMERA_REQUEST);

					} else if (items[position].equals("Choose from Gallery")) {
						Intent i = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(i, SELECT_FILE);
					} 
					else{
						SharedPreferences mPref = mContext.getSharedPreferences("isTakingPic", 0);
						Editor edit = mPref.edit().putBoolean("isTakingPic", false);
						edit.commit();
						//	fm.popBackStack();;
						dialog.dismiss();
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		builder.setCancelable(false);
		builder.show();
	}

	private void savePref(String str){
		SharedPreferences sf = mContext.getSharedPreferences("LoginUser", 1);
		SharedPreferences.Editor editor = sf.edit();
		editor.putString("imageURI",str);
		editor.commit();
	}

	private String getImageURI(){
		SharedPreferences sf = mContext.getSharedPreferences("LoginUser", 1);
		String uri = sf.getString("imageURI", null);
		return uri;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try{
			SharedPreferences mPref = mContext.getSharedPreferences("isTakingPic", 0);
			Editor edit = mPref.edit().putBoolean("isTakingPic", false);
			edit.commit();
			if(requestCode == SELECT_FILE && resultCode==RESULT_OK){
				if(flag==0) {
					if(data!=null){
						Uri selectedImage = data.getData();
						String[] filePathColumn = { MediaStore.Images.Media.DATA };
						Cursor cursor = mContext.getContentResolver().query(selectedImage,
								filePathColumn, null, null, null);
						cursor.moveToFirst();
						int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
						picturePath = cursor.getString(columnIndex);
						image = ImageDecoder.decodeFile(picturePath); 
						if(image!=null){
							image=Bitmap.createScaledBitmap(image , 100, 100, false);
							ByteArrayOutputStream bytes = new ByteArrayOutputStream();
							image.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
							Bitmap bm = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
							imageBitmap = bm;
							if(select_type==1){
								edit_vandore_photoimg.setImageBitmap(RoundedImage.GetBitmapClippedCircle(imageBitmap));
							}
							if(select_type==2){
								edit_vandor_backgroundimg.setImageBitmap(imageBitmap);
							}
							if(select_type==3){
								edit_vandor_logoimg.setImageBitmap(imageBitmap);
							}
							filePath = picturePath;
						}	
						else
						{
							Toast.makeText(mContext,"Wrong Path",Toast.LENGTH_LONG).show();;
							imageselectedflag=false;
						}
						cursor.close();
					}
				}
			}
			if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
				if(flag==0){
					try{           
						String URI = getImageURI();
						try {
							File file = new File(URI);
							ExifInterface exif = new ExifInterface(
									file.getAbsolutePath());
							int orientation = exif.getAttributeInt(
									ExifInterface.TAG_ORIENTATION,
									ExifInterface.ORIENTATION_NORMAL);
							switch (orientation) {
							case ExifInterface.ORIENTATION_ROTATE_270:
								rotate = 270;
								ImageOrientation(file,rotate);
								break;
							case ExifInterface.ORIENTATION_ROTATE_180:
								rotate = 180;
								ImageOrientation(file,rotate);
								break;
							case ExifInterface.ORIENTATION_ROTATE_90:
								rotate = 90;
								ImageOrientation(file,rotate);
								break;

							case 1:
								rotate = 90;
								ImageOrientation(file,rotate);
								break;

							case 2:
								rotate = 0; 
								ImageOrientation(file,rotate);
								break;
							case 4:
								rotate = 180;
								ImageOrientation(file,rotate);
								break;

							case 0:
								rotate = 90;
								ImageOrientation(file,rotate);
								break;
							}


						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					catch(Exception e){ 
						Log.e("Error - ",e.getMessage());
					}
				}
			}
			if(requestCode==11 && resultCode==RESULT_OK){	   
				setImageFromSDCard();	 
			}
			
			if(requestCode==14 && resultCode==RESULT_OK){
				edit_vandore_img.setBackgroundColor(Color.parseColor(data.getStringExtra("hexColor")));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void ImageOrientation(File file,int rotate){
		try {
			FileInputStream fis = new FileInputStream(file);
			filePath = file.getAbsolutePath();
			Bitmap photo = BitmapFactory.decodeStream(fis);
			Matrix matrix = new Matrix();
			matrix.preRotate(rotate); // clockwise by 90 degrees
			photo = Bitmap.createBitmap(photo , 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.PNG, 100, out);
			Bitmap bm = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
			photo=bm;

			if(select_type==1){
				edit_vandore_photoimg.setImageBitmap(RoundedImage.GetBitmapClippedCircle(photo));
			}
			if(select_type==2){
				edit_vandor_backgroundimg.setImageBitmap(photo);
			}

			if(select_type==3){
				edit_vandor_logoimg.setImageBitmap(photo);
			}

			imageBitmap = photo;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
	}

	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private void setImageFromSDCard(){
		if(currentVersion>15){
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			if (cameraImagePath != null) {
				Cursor imageCursor = mContext.getContentResolver().query(
						cameraImagePath, filePathColumn, null, null, null);
				if (imageCursor != null && imageCursor.moveToFirst()){
					int columnIndex = imageCursor.getColumnIndex(filePathColumn[0]);
					filePath = imageCursor.getString(columnIndex);
					imageCursor.close();
					//imageFilePathUri = filePath != null ? Uri.parse(filePath) : null;
				}
			}  
		}
		else{
			String struri = getImageURI();
			filePath = struri;
		}
		Bitmap bitmap = ImageDecoder.decodeFile(filePath);
		if(bitmap!=null){
			bitmap=Bitmap.createScaledBitmap(bitmap , 100, 100, false);
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
			imageBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

			if(select_type==1){
				edit_vandore_photoimg.setImageBitmap(RoundedImage.GetBitmapClippedCircle(imageBitmap));
			}
			
			if(select_type==2){
				edit_vandor_backgroundimg.setImageBitmap(imageBitmap);
			}

			if(select_type==3){
				edit_vandor_logoimg.setImageBitmap(imageBitmap);
			}

			bitmap.recycle();
		}
	}
	
	OnClickListener addColor = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			findColor();
		}
	};

	private void findColor(){
		Intent IColorAct = new Intent(mContext,ColorActivity.class);
		startActivityForResult(IColorAct, 14);
	}
	
	OnClickListener addPersonal = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent Ipersonal = new Intent(mContext,PersonalActivity.class);
			startActivity(Ipersonal);
		}
	};
	
	OnClickListener addCompany = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent Icompany = new Intent(mContext,CompanyActivity.class);
			startActivity(Icompany);
		}
	};
	
}
