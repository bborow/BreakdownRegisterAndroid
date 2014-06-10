package pl.edu.uj.matinf.parkitna.breakdownregister;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;

public class Utils {
	public static Uri startCameraActivity(Activity activity, int requestCode) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String fileName = generateFileName() + ".jpg";
        File photo = new File(activity.getExternalFilesDir(null), fileName);
        Uri photoUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        activity.startActivityForResult(intent, requestCode);
        return photoUri;
	}
	
	public static String generateFileName(){
		long date = new Date().getTime();
        int rand = new Random().nextInt();
        return Long.toString(date) + Integer.toString(rand);
	}
	
	public static Pair<Integer, Integer> determineNewWidthAndHeight(Bitmap bitmap, float photoMaxLengthOnScreen){
		float photoBitmapWidth = bitmap.getWidth();
		float photoBitmapHeight = bitmap.getHeight();
		
		float proportionRatio = 0;
		
		int dstWidth = 0;
		int dstHeight = 0;
		
		if(photoBitmapWidth > photoBitmapHeight){
			proportionRatio = photoBitmapWidth / photoBitmapHeight;
			dstWidth = (int) photoMaxLengthOnScreen;
			dstHeight = (int) (photoMaxLengthOnScreen / proportionRatio);
		}
		else{
			proportionRatio = photoBitmapHeight / photoBitmapWidth;
			dstHeight = (int) photoMaxLengthOnScreen;
			dstWidth = (int) (photoMaxLengthOnScreen / proportionRatio);
		}
		
		return new Pair<Integer, Integer>(dstWidth, dstHeight);
	}
	
	public static void deletePhotos(ArrayList<String> photoPaths){
		for(String path : photoPaths){
			new File(path).delete();
		}
	}
	
	public static Bitmap getBitmapFromCamera(ContentResolver contentResolver, Uri photoUri){
        Bitmap bitmap = null;
        try{
        	bitmap = android.provider.MediaStore.Images.Media
        			.getBitmap(contentResolver, photoUri);
        } catch (Exception e) {
            Log.e("Camera", e.toString());
        }
        return bitmap;
	}
	
	public static void sleep(long milis){
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
