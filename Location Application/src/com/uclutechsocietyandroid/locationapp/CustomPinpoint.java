package com.uclutechsocietyandroid.locationapp;
//package com.example.maps; package i had initially

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomPinpoint extends ItemizedOverlay<OverlayItem> {

  private ArrayList<OverlayItem> pinpoints = new ArrayList<OverlayItem>(); // you will put OverlayItem objects you want on the map
	Context myContext;     // is a reference to the application's Context, big thing which adds up all cool staff
	
	
	public CustomPinpoint(Drawable defaultMarker) {   // THE CONSTUCTOR MUST DEFINE THE DEFAULT MARKER FOR EACH OF THE OVERLAY OBJECTS
		super(boundCenter(defaultMarker));            // for the drawable to be actually drawn it should have its bounds defined
		// TODO Auto-generated constructor stub       // most commonly you want the center point to be at the bottom of the image to be a point
	}                                                 // its actually attached to the map coordinated

	
	public CustomPinpoint(Drawable m, Context context) {  // this passes the Drawable m up to the default constructor to bound its coordinates
	                                                      // and then initialize myContext with the given application's context
		// TODO Auto-generated constructor stub           
	this(m);
	myContext=context;
	}

	
	/*@Override 
	protected boolean onTap(int index) {
	
		OverlayItem item = pinpoints.get(index);
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(myContext);
		dialog.setTitle("Hi!");
		dialog.setMessage("Choose an option!");
		
		dialog.setPositiveButton("Stop Music", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//if this button is clicked,close
			    //current activity
				
				songLoc.stop();
				
				
			}
	    	
	    });
	
		dialog.show();
		return true;
	}*/
	
	
	
	@Override
	protected OverlayItem createItem(int i) {         // when populate method from insertPinPoint executes it will call createItem(int)
		// TODO Auto-generated method stub            // in the ItemizedOverlay to retrieve each OverlayItem 
		return pinpoints.get(i);                      // so this method read from the Array list and return the OverlayItem from the 
	}                                                 // position specified by the given integer

	
	@Override                                           
	public int size() {                               // this override the size() of the array. how many objects are in the array
		// TODO Auto-generated method stub
		return pinpoints.size();
	}
	
	
	public void isertPinPoint(OverlayItem item){      // adding new OverlayItem objects to the array list
		pinpoints.add(item);                          
		this.populate();                              // populate will read each of the OverlayItem objects and prepare them to be drawn
		
	}
	
	public void deletePinPoint(OverlayItem item){
		pinpoints.remove(item);
		//this.populate();
	}

}
