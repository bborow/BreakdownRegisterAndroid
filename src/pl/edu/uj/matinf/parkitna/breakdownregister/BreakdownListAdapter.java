package pl.edu.uj.matinf.parkitna.breakdownregister;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import pl.edu.uj.matinf.parkitna.breakdownregister.model.Breakdown;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BreakdownListAdapter extends BaseAdapter {

	private ArrayList<Breakdown> breakdownList;
	private Activity activity;
	private LayoutInflater layoutInflater;
	
	public BreakdownListAdapter(Activity activity, ArrayList<Breakdown> breakdownList){
		this.activity = activity;
		this.breakdownList = breakdownList;
		layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return breakdownList.size();
	}

	@Override
	public Breakdown getItem(int position) {
		return breakdownList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.breakdown_list_item, parent, false);
		}
		
		TextView titleTextView = (TextView) convertView.findViewById(R.id.title_textview);
		titleTextView.setText(breakdownList.get(position).getTitle());
		
		TextView identifierTextView = (TextView) convertView.findViewById(R.id.identifier_textview);
		identifierTextView.setText(breakdownList.get(position).getIdentifier());
		
		TextView stateTextView = (TextView) convertView.findViewById(R.id.state_textview);
		stateTextView.setText(breakdownList.get(position).getState());
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy'/'HH:mm:ss' ('E')'");
		
		String formatedDate = "";
		Date dateToFormat = new Date();
		dateToFormat.setTime(breakdownList.get(position).getAddedDateInMilis());
		formatedDate = simpleDateFormat.format(dateToFormat);
		
		TextView addedDateTextView = (TextView) convertView.findViewById(R.id.added_date_textview);
		addedDateTextView.setText(formatedDate);
		
		TextView solutionDateTextView = (TextView) convertView.findViewById(R.id.solution_date_textview);
		if(breakdownList.get(position).getSolutionDateInMilis() != 0){
			dateToFormat.setTime(breakdownList.get(position).getSolutionDateInMilis());
			formatedDate = simpleDateFormat.format(dateToFormat);
			solutionDateTextView.setText(formatedDate);
		}
		else{
			solutionDateTextView.setText("___");
		}
		return convertView;
	}
	
	public void sort(Comparator<Breakdown> comparator){
		Collections.sort(breakdownList, comparator);
	}
	
	public void add(Breakdown breakdown){
		breakdownList.add(breakdown);
	}
	
	public void remove(int position){
		breakdownList.remove(position);
	}
	
	public void set(int position, Breakdown breakdown){
		breakdownList.set(position, breakdown);
	}
}
