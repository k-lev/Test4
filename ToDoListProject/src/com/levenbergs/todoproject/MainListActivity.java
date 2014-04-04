package com.levenbergs.todoproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainListActivity extends ListActivity {

	public static final String TAG = MainListActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);

		// Have parse.com collect analytics
		ParseAnalytics.trackAppOpened(getIntent());

		// Check to see if the user logged in.  If so, show MainListActivity
		// If not, go back to login screen
		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null) {
			navigateToLogin();

		}
		else {
			// show who the user is in the log
			Log.e(TAG, currentUser.getUsername());	
			
			// TODO:  do something user here (PLACEHOLDER)
			TextView emptyTextView = (TextView) getListView().getEmptyView();
			emptyTextView.setText(getString(R.string.no_items));
		
		}
		
	}

	/*
	 * Method called when it is appropriate to leave mainActivity and go to LoginActivity
	 */
	private void navigateToLogin() {
		// User is not logged in; go back to (or create) the loginActivity 
		Intent intent = new Intent(this, LoginActivity.class);
		// set flags so user can't use back button to end up back in SignUpActivity
		// These flags clear the MainView from the task stack, so that when
		// the back button is tapped, the user won't go from login screen
		// to the inbox (main view) without having logged in.
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		// menu option to logout
		if (id == R.id.action_logout) {
			ParseUser.logOut();
			navigateToLogin();
		}
		return super.onOptionsItemSelected(item);
	}

}
