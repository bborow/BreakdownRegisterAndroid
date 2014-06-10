package pl.edu.uj.matinf.parkitna.breakdownregister.screens;

import java.util.ArrayList;

import pl.edu.uj.matinf.parkitna.breakdownregister.R;
import pl.edu.uj.matinf.parkitna.breakdownregister.fragments.BreakdownLocationFragment;
import pl.edu.uj.matinf.parkitna.breakdownregister.fragments.BreakdownPhotosFragment;
import pl.edu.uj.matinf.parkitna.breakdownregister.model.Breakdown;
import pl.uj.edu.matinf.parkitna.breakdownregister.interfaces.OnBackPressedListener;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class BreakdownParticularsActivity extends Activity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	private Breakdown obtainedBreakdown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.breakdown_particulars_layout);

//		obtainedBreakdown = getIntent().getParcelableExtra(AppConstants.ARG_EXTRA_BREAKDOWN);

		ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(BreakdownLocationFragment.newInstance());
		fragmentsList.add(BreakdownPhotosFragment.newInstance());

//		double longitude = Double.valueOf(obtainedFailuresListItem.longitude);
//		double latitude = Double.valueOf(obtainedFailuresListItem.latitude);

//		fragmentsList.add(FailureLocationFragment.newInstance(longitude,
//				latitude));

		ArrayList<Integer> pageIdTitlesList = new ArrayList<Integer>();
		pageIdTitlesList.add(R.string.breakdown_particulars_fragment_details_page_position);
		pageIdTitlesList.add(R.string.breakdown_particulars_fragment_details_page_details);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(),
				fragmentsList, pageIdTitlesList);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> fragmentsList;
		private ArrayList<Integer> pageIdTitlesList;

		public SectionsPagerAdapter(FragmentManager fm,
				ArrayList<Fragment> fragmentsList,
				ArrayList<Integer> pageIdTitlesList) {
			super(fm);
			this.fragmentsList = fragmentsList;
			this.pageIdTitlesList = pageIdTitlesList;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return fragmentsList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentsList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return getString(pageIdTitlesList.get(position)).toUpperCase();
		}
	}

//	@Override
//	public void onBackPressed() {
//		OnBackPressedListener onBackPressedFragment = (OnBackPressedListener) mSectionsPagerAdapter
//				.getItem(0);
//		onBackPressedFragment.onBackPressed();
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mSectionsPagerAdapter.getItem(0).onActivityResult(requestCode,
				resultCode, data);
	}
}
