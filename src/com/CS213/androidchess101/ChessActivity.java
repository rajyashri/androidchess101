package com.CS213.androidchess101;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.CS213.controller.Game;
import com.CS213.model.PlayerColor;
import com.CS213.model.Square;
import com.CS213.view.SquareAdapter;

public class ChessActivity extends ActionBarActivity implements OnItemClickListener {

	private Game game;
	private boolean record;
	private static boolean RUN_ONCE = false;
	private String gameName;
	private TextView turnView;
	private GridView chessboard;
	private View[] squaresSelected;
	private int[] squarePositions;
	private SquareAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chess_activity);

		if (!RUN_ONCE) {

			RUN_ONCE = true;
			recordGame();
			this.game = new Game();
			squaresSelected = new View[2];
			squarePositions = new int[2];
			adapter = new SquareAdapter(this, game.getBoard());
			turnView = (TextView)findViewById(R.id.turnView);

		}


		final GridView chessBoardGridView = (GridView)findViewById(R.id.chessboard);

		chessBoardGridView.setAdapter(adapter);

		chessBoardGridView.setOnItemClickListener(this);

		this.chessboard = chessBoardGridView;


	}

	private void recordGame() {


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

		//selecting a piece to move.
		if (squaresSelected[0] == null) {

			Square selectedSquare = game.getBoard()[position/8][position%8];

			if (selectedSquare.getPiece() == null) return;
			
			if (selectedSquare.getPiece().getPlayer().getColor() != game.getCurrentPlayer().getColor()) return;

			squaresSelected[0] = view;
			squarePositions[0] = position;

			view.setBackgroundColor(Color.BLUE);

		}
		//selecting a square to move to
		else {

			squaresSelected[1] = view;
			squarePositions[1] = position;

			if (game.move(squarePositions[0], squarePositions[1])) {
				
				adapter.notifyDataSetChanged();
				chessboard.setAdapter(adapter);
				changeTurnText();
				
			} else {
				Toast toast = Toast.makeText(this, "Illegal Move", Toast.LENGTH_SHORT);
				toast.show();
			}

			squaresSelected[0].setBackgroundColor(updateColor(squarePositions[0]));
			squaresSelected[0] = null;
			squaresSelected[1] = null;

		}

	}


	private void changeTurnText() {

		if (turnView.getText().toString().compareTo(getResources().getString(R.string.white_turn)) == 0) {
			
			turnView.setText(getResources().getString(R.string.black_turn));
		}
		else {
			
			turnView.setText(getResources().getString(R.string.white_turn));
		}
	}

	private int updateColor(int position) {

		//background brown or light brown depending of the position
		int col = position/8 %2;
		if (col == 0)
		{
			if (position%2 == 0)
				return Color.parseColor("#DFAE74");
			else
				return Color.parseColor("#6B4226");

		}
		else
		{
			if (position%2 == 0)
				return Color.parseColor("#6B4226");
			else
				return Color.parseColor("#DFAE74");
		}
	}
}
