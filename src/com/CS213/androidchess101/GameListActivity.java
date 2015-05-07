package com.CS213.androidchess101;

import com.CS213.model.PlayedGames;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GameListActivity extends ActionBarActivity {

	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list_activity);
		
		final ListView v = (ListView)findViewById(R.id.listView);
		
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 android.R.layout.simple_list_item_1, android.R.id.text1,
                 PlayedGames.gameNames.toArray(new String[PlayedGames.gameNames.size()]));
		 
		 v.setAdapter(adapter);
		 listView = v;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*private static void initDisplayButton() {
		
		public display() {
			
			//Here's all I need to do. Get focused for like 2 hours andfigure how to display my board. Then i'll e like seen you before
			
		}
	}*/
	
}
