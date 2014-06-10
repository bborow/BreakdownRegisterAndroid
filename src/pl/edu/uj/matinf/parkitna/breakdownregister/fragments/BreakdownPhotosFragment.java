package pl.edu.uj.matinf.parkitna.breakdownregister.fragments;

import pl.edu.uj.matinf.parkitna.breakdownregister.R;
import pl.edu.uj.matinf.parkitna.breakdownregister.model.Breakdown;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class BreakdownPhotosFragment extends Fragment implements
AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory{

	
	private ImageSwitcher mSwitcher;
	
	
	public static BreakdownPhotosFragment newInstance(Breakdown breakdown){
		BreakdownPhotosFragment photosFragment = new BreakdownPhotosFragment();
		Bundle args = new Bundle();
//		args.putParcelable("obtainedBreakdown", breakdown);
		photosFragment.setArguments(args);
		return photosFragment;
	}
	
	public static BreakdownPhotosFragment newInstance(){
		return new BreakdownPhotosFragment();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mSwitcher = (ImageSwitcher) getActivity().findViewById(R.id.switcher);
		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.fade_out));

		Gallery g = (Gallery) getActivity().findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(getActivity()));
		g.setOnItemSelectedListener(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.breakdown_photos_fragment_layout, container, false);
		return rootView;
	}

	@Override
	public View makeView() {
		ImageView imageView = new ImageView(getActivity());
		imageView.setBackgroundColor(0xFF000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return imageView;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		mSwitcher.setImageResource(mImageIds[position]);	
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	private class ImageAdapter extends BaseAdapter {

		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return mThumbIds.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(mContext);

			imageView.setImageResource(mThumbIds[position]);
			imageView.setAdjustViewBounds(true);
			imageView.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			// i.setBackgroundResource(R.drawable.picture_frame);
			return imageView;
		}
	}
	
}
