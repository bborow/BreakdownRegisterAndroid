package pl.edu.uj.matinf.parkitna.breakdownregister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFacade implements LocationListener{
	
	private GoogleMap map;
	private Activity activity;
	private LocationClient locationClient;
	private int zoom = 18;
	private Marker currentLocatioMarker;
	private boolean drawLocationMarkerOnMap = true; 
	
	private AlertDialog locationSourcesAlertDialog;
	private boolean wasLocationSourcesAlertDialogCanceled = false;
	private AlertDialog connectionsAlertDialog;
	private boolean wasConnectionsAlertDialogCanceled = false;
	
	private boolean wasCurrentLocationMarkerDraged = false;
	
	private Handler markerInfoWindowHandler = new Handler(new Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			currentLocatioMarker.hideInfoWindow();
			return true;
		}
	});
	
	private LatLng updatedMarkerPosition;
	
	
	
	private ConnectionCallbacks connectionCallbacks = new ConnectionCallbacks() {
		
		@Override
		public void onDisconnected() {
		}
		
		@Override
		public void onConnected(Bundle connectionHint) {
			if(!wasCurrentLocationMarkerDraged){
				locationClient.requestLocationUpdates(new LocationRequest(), GoogleMapFacade.this);
			}
		}
	};
	
	private OnConnectionFailedListener onConnectionFailedListener = new OnConnectionFailedListener() {
		
		@Override
		public void onConnectionFailed(ConnectionResult result) {
			Toast.makeText(activity, "Connection with google maps failed", Toast.LENGTH_SHORT).show();
		}
	};
	
	private class MapOnMapLongClickListener implements OnMapLongClickListener{
		
		@Override
		public void onMapLongClick(LatLng point) {
			if(currentLocatioMarker == null){
				//to do: turn off update position
				map.addMarker(new MarkerOptions().position(point).draggable(true));
			}
		}
	};
	
	private class MapOnMarkerDragListener implements OnMarkerDragListener{
		
		@Override
		public void onMarkerDragStart(Marker marker) {
			// TODO Auto-generated method stub
			wasCurrentLocationMarkerDraged = true;
			if(locationClient.isConnected())
				locationClient.removeLocationUpdates(GoogleMapFacade.this);
			
			currentLocatioMarker.hideInfoWindow();
		}
		
		@Override
		public void onMarkerDragEnd(Marker marker) {
			updatedMarkerPosition = marker.getPosition();
			
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(updatedMarkerPosition, zoom), 1000, new CancelableCallback() {
				
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					animateMarker(currentLocatioMarker, updatedMarkerPosition, 500, 100, new LinearInterpolator());
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					animateMarker(currentLocatioMarker, updatedMarkerPosition, 500, 100, new LinearInterpolator());
				}
			});
		}
		
		@Override
		public void onMarkerDrag(Marker marker) {
		}
	};
	
	private class MapOnMarkerClickListener implements OnMarkerClickListener{
		
		@Override
		public boolean onMarkerClick(Marker marker) {
			marker.showInfoWindow();
			markerInfoWindowHandler.postDelayed(null, 3000);
			return true;
		}
	};
	
	public GoogleMapFacade(GoogleMap map, Activity activity, boolean withListeners){
		this.map = map;
		this.activity = activity;
		
		if(withListeners){
			map.setOnMarkerDragListener(new MapOnMarkerDragListener());
			map.setOnMapLongClickListener(new MapOnMapLongClickListener());
			map.setOnMarkerClickListener(new MapOnMarkerClickListener());
		}
	}
	
	public void connectLocationClient(){
		locationClient = new LocationClient(activity, connectionCallbacks, onConnectionFailedListener);
		locationClient.connect();
	}
	
	public void disconnectLocationclient(){
		locationClient.disconnect();
	}
	
	public Location getLastLocation(){
		return locationClient.getLastLocation();
	}
	
	public boolean locationClientIsConnected(){
		if(locationClient == null) return false;
		return locationClient.isConnected();
	}
	
	public void setMyLocationEnabled(boolean enabled){
		map.setMyLocationEnabled(true);
	}
	
	public boolean locationSourcesAreEnabled(){
		String locationProviders = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (locationProviders == null || (locationProviders != null && locationProviders.equals(""))) {
			return false;
		}
		return true;
	}
	
	public boolean dataConnectionIsEnabled(){
		boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
	
	public void showLocationSourcesAlertDialog(){
		if(locationSourcesAlertDialog == null){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
			alertDialogBuilder.setTitle("Problem with obtaining location")
							  .setMessage("Enable location sources");
			
			alertDialogBuilder.setPositiveButton("Location settings", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				}
			});
			
			alertDialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					wasLocationSourcesAlertDialogCanceled = true;
					dialog.dismiss();
				}
			});
			
			locationSourcesAlertDialog = alertDialogBuilder.create();
		}
		if(!locationSourcesAlertDialog.isShowing()){
			if(!wasLocationSourcesAlertDialogCanceled)
				locationSourcesAlertDialog.show();
		}
	}
	
	public void showConnectionsAlertDialog(){
		if(connectionsAlertDialog == null){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
			alertDialogBuilder.setTitle("No data connection is available")
							  .setMessage("Data connection is needed to load map data (if doesn't cached) and "
							  		+ "to obtain position if gps location source is disabled");
			
			alertDialogBuilder.setPositiveButton("Location settings", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
				}
			});
			
			alertDialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					wasConnectionsAlertDialogCanceled = true;
					dialog.dismiss();
				}
			});
			
			connectionsAlertDialog = alertDialogBuilder.create();
		}
		if(!connectionsAlertDialog.isShowing()){
			if(!wasConnectionsAlertDialogCanceled)
				connectionsAlertDialog.show();
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Log.d("locationUpdate", msg);
        if(drawLocationMarkerOnMap){
	        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
	        if(currentLocatioMarker != null){
	        	currentLocatioMarker.setPosition(coordinates);
	        }else{
	        	currentLocatioMarker = map.addMarker(new MarkerOptions().position(coordinates).draggable(true));
	        }
	        
	        currentLocatioMarker.setTitle("Current position");
	        currentLocatioMarker.setSnippet("Long press and move me everywhere if needed");
	        currentLocatioMarker.showInfoWindow();

	        markerInfoWindowHandler.postDelayed(null, 5000);
	        
	        
	        updatedMarkerPosition = currentLocatioMarker.getPosition();

	        animateCameraAndMarker(currentLocatioMarker, updatedMarkerPosition, zoom);
	        
        }
	}
	
	public void animateCameraAndMarker(final Marker markerToAnimate, final LatLng targetMarkerPosition, int zoom){
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(targetMarkerPosition, zoom), 2000, new CancelableCallback() {
			
			@Override
			public void onFinish() {
				animateMarker(markerToAnimate, targetMarkerPosition, 1500, 100, new BounceInterpolator());
			}
			
			@Override
			public void onCancel() {
				animateMarker(markerToAnimate, targetMarkerPosition, 1500, 100, new BounceInterpolator());
			}
		});
	}
	
	public void addMarker(LatLng position){
		Marker marker = map.addMarker(new MarkerOptions().position(position));
		animateCameraAndMarker(marker, position, zoom);
	}
	
	public void clearMap(){
		map.clear();
	}

	public void setDrawingLocationMarkerOnMap(boolean draw){
		this.drawLocationMarkerOnMap = draw;
	}
	public LatLng getCurrentMarkerPosition(){
		if(currentLocatioMarker != null){
			return currentLocatioMarker.getPosition();
		}
		return null;
	}
	
	
	 private void animateMarker(final Marker markerToAnimate, 
			 final LatLng targetMarkerPosition, 
			 final int durationInMilis, int offsetX, final Interpolator interpolator){
		 	 
	         final Handler handler = new Handler();
	         final long start = SystemClock.uptimeMillis();
	         Projection proj = map.getProjection();
	         Point startPoint = proj.toScreenLocation(targetMarkerPosition);
	         startPoint.offset(0, -offsetX);
	         final LatLng startLatLng = proj.fromScreenLocation(startPoint);
	         handler.post(new Runnable() {
	             @Override
	             public void run() {
	                 long elapsed = SystemClock.uptimeMillis() - start;
	                 float t = interpolator.getInterpolation((float) elapsed / durationInMilis);
	                 double lng = t * targetMarkerPosition.longitude + (1 - t) * startLatLng.longitude;
	                 double lat = t * targetMarkerPosition.latitude + (1 - t) * startLatLng.latitude;
	                 markerToAnimate.setPosition(new LatLng(lat, lng));
	                 if (t < 1.0) {
	                     handler.postDelayed(this, 16);
	                 }
	             }
	         });
	 }
	 
	 public void setMapType(int type){
		 map.setMapType(type);
	 }
	 
}