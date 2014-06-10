package pl.edu.uj.matinf.parkitna.breakdownregister.screens;

import pl.edu.uj.matinf.parkitna.breakdownregister.BreakdownListAdapter;
import pl.edu.uj.matinf.parkitna.breakdownregister.R;
import pl.edu.uj.matinf.parkitna.breakdownregister.database.DatabaseAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class BreakdownListActivity extends ListActivity{

	private DatabaseAdapter databaseAdapter;
	private ListView listView;
	private BreakdownListAdapter listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.breakdown_list_activity_layout);
		databaseAdapter = new DatabaseAdapter(getApplicationContext());
		listView = getListView();
		databaseAdapter.open();
		listAdapter = new BreakdownListAdapter(this, databaseAdapter.selectAll());
		listView.setAdapter(listAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.breakdown_list_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_add_breakdown:
			Intent breakdownDetailsIntent = new Intent(getApplicationContext(), BreakdownParticularsActivity.class);
			startActivity(breakdownDetailsIntent);
			break;
		case R.id.action_import_breakdown:
			break;
		case R.id.action_sort_by_title:
			break;
		case R.id.action_sort_by_status:
			break;
		case R.id.action_sort_by_added_date:
			break;
		case R.id.action_sort_by_solution_date:
			break;
		}
		return true;
	}
	
	@Override
	protected void onDestroy() {
		databaseAdapter.close();
		super.onDestroy();
	}
}
