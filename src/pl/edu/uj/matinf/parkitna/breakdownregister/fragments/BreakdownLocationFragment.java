package pl.edu.uj.matinf.parkitna.breakdownregister.fragments;

import pl.edu.uj.matinf.parkitna.breakdownregister.GoogleMapFacade;
import pl.edu.uj.matinf.parkitna.breakdownregister.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class BreakdownLocationFragment extends Fragment {

	private GoogleMapFacade googleMapFacade; 
	
	private static final String LONGITUDE = "longitude";
	private static final String LATITUDE = "latitude";
	
	private boolean withListeners = false;
	
	public static BreakdownLocationFragment newInstance(double longitude, double latitude){
		BreakdownLocationFragment fragment = new BreakdownLocationFragment();
		Bundle args = new Bundle();
        args.putDouble(LONGITUDE, longitude);
        args.putDouble(LATITUDE, latitude);
        fragment.setArguments(args);
        return fragment;
	}
	
	public static BreakdownLocationFragment newInstance(){
		BreakdownLocationFragment fragment = new BreakdownLocationFragment();
		fragment.withListeners = true;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.breakdown_location_fragment_layout, container, false);
		GoogleMap map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();
		googleMapFacade = new GoogleMapFacade(map, getActivity(), withListeners);
//		double longitude = getArguments().getDouble(LONGITUDE);
//		double latitude = getArguments().getDouble(LATITUDE);
//		googleMapFacade.addMarker(new LatLng(latitude, longitude));
		googleMapFacade.connectLocationClient();
		setHasOptionsMenu(true);
		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.breakdown_location_fragment_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id){
		case R.id.action_type_normal:
			googleMapFacade.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.action_type_hybrid:
			googleMapFacade.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case R.id.action_type_terrain:
			googleMapFacade.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
