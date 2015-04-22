package com.CS213.view;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class SquareAdapter extends BaseAdapter {

	private Context context;
	
	
	public SquareAdapter(Context c) {
		
		this.context = c;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		/*View squareContainerView = convertView;
        
		  if ( convertView == null ) {
		    //Inflate the layout
		    final LayoutInflater layoutInflater = 
		      (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    squareContainerView = 
		      layoutInflater.inflate(R.id.square, null);

		    // Background
		    final ImageView squareView = 
		        (ImageView)squareContainerView.findViewById(R.id.square_background);
		    squareView.setImageResource(this.aSquareImg[(position + position/8)%2]);

		    if (pPosition % 2 == 0) { //mock test
		        // Add The piece
		        final ImageView pieceView = 
		          (ImageView)squareContainerView.findViewById(R.id.piece);
		        pieceView.setImageResource(R.drawable.green);
		        pieceView.setTag(position);
		    }
		  }
		  return squareContainerView;*/
		
		return null;
	}

}
