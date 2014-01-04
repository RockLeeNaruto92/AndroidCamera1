package com.example.androidcamera1;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Uri imageid;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void openCameraToTakePicture(){
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoUri());
		startActivityForResult(intent, 1);
		
	}
	
	public Uri getPhotoUri(){
		if (!AppUtils.chechSDCard()) return null;
		
		File rootFolder = Environment.getExternalStorageDirectory();
		File tempPhoto = new File(rootFolder.getAbsolutePath() + "/temp.jpg");
		
		Uri tempPhotoUri = Uri.fromFile(tempPhoto);
		return tempPhotoUri;
	}
	
	public void takePicture(View view){
		Toast.makeText(MainActivity.this, "Take picture", Toast.LENGTH_SHORT).show();
		openCameraToTakePicture();
	}

	public void savePicture(View view){
		Toast.makeText(MainActivity.this, "Save picture", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1){
			if (resultCode == RESULT_OK){
				String imageId = convertImageUriToFile(getPhotoUri(), this);
				Log.d("CAMERA", imageId);
			}else Log.d("CAMERA", "Fail");
		}
		
	}
	
	 public static String convertImageUriToFile ( Uri imageUri, Activity activity )  {
	      
         Cursor cursor = null;
         int imageID = 0;
          
         try {
          
             /*********** Which columns values want to get *******/
             String [] proj={
                              MediaStore.Images.Media.DATA,
                              MediaStore.Images.Media._ID,
                              MediaStore.Images.Thumbnails._ID,
                              MediaStore.Images.ImageColumns.ORIENTATION
                            };
              
             cursor = activity.managedQuery(
                      
                             imageUri,         //  Get data for specific image URI
                             proj,             //  Which columns to return
                             null,             //  WHERE clause; which rows to return (all rows)
                             null,             //  WHERE clause selection arguments (none)
                             null              //  Order-by clause (ascending by name)
                              
                          );
                                
             //  Get Query Data
              
             int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
             int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
             int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
              
             //int orientation_ColumnIndex = cursor.
             //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
              
             int size = cursor.getCount();
              
             /*******  If size is 0, there are no images on the SD Card. *****/
              
             if (size == 0) {


   //              imageDetails.setText("No Image");
             }
             else
             {
             
                 int thumbID = 0;
                 if (cursor.moveToFirst()) {
                      
                     /**************** Captured image details ************/
                      
                     /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                     imageID     = cursor.getInt(columnIndex);
                      
                     thumbID     = cursor.getInt(columnIndexThumb);
                      
                     String Path = cursor.getString(file_ColumnIndex);
                      
                     //String orientation =  cursor.getString(orientation_ColumnIndex);
                      
                     String CapturedImageDetails = " CapturedImageDetails : \n\n"
                                                       +" ImageID :"+imageID+"\n"
                                                       +" ThumbID :"+thumbID+"\n"
                                                       +" Path :"+Path+"\n";
                      
                     // Show Captured Image detail on activity
     //                imageDetails.setText( CapturedImageDetails );
                      
                 }
             }    
         } finally {
             if (cursor != null) {
                 cursor.close();
             }
         }
          
         // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )
          
         return ""+imageID;
     }
   
	
	
}
