package com.CS213.androidchess101;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.CS213.controller.Game;
import com.CS213.view.SquareAdapter;

public class ChessActivity extends ActionBarActivity implements OnItemClickListener {

	private Game game;
	private boolean record;
	private static boolean RUN_ONCE = false;
	private String gameName;
	private EditText turnView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chess_activity);

		this.game = new Game();

		recordGame();

		final GridView chessBoardGridView = (GridView)findViewById(R.id.chessboard);

		chessBoardGridView.setAdapter(new SquareAdapter(this, game.getBoard()));
		

	}

	private void recordGame() {

		if (!RUN_ONCE) {

			RUN_ONCE = true;
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which){
					case DialogInterface.BUTTON_POSITIVE:
						record = true;

						AlertDialog.Builder alert = new AlertDialog.Builder(ChessActivity.this);
						alert.setTitle("Record Game");
						alert.setMessage("Enter a name for this game");

						// Set an EditText view to get user input 
						final EditText input = new EditText(ChessActivity.this);
						alert.setView(input);
						alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								String value = input.getText().toString();
								gameName = value;
							}
						});

						alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								record = false;
							}
						});

						alert.show();
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						record = false;
						break;
					}
				}
			};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Want to record this game?").setPositiveButton("Yes", dialogClickListener)
			.setNegativeButton("No", dialogClickListener).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chess, menu);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		
		
	}


}
