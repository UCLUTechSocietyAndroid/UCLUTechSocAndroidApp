package com.uclutechsocietyandroid.locationapp;
//package  package i had initially



import java.io.IOException;
import java.util.List;
import java.util.Locale;

//import com.example.mgeolocation.MainActivity.mylocationlistener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends MapActivity   { //extend MapActivity to provide important map capabilities
	

	
	
	MediaPlayer songLoc; // for playing a song
	
	MapView map;         
	
	long start;
	long stop;

	int firstStart=0;        // indicating the start of the application 
	
	
	CustomPinpoint custom;   //pinpoint for user location   
	OverlayItem overlayItem; // overlay item for user location
	
	Drawable d;              // drawable which will be on the map as a user location

	
	 
	
	
	MyLocationOverlay compass;
	MapController controller;
	
	 final Context context = this;
	 
	 GeoPoint touchedPoint;
	 
	 int x;
	 int y;
	 
	 List<Overlay> overlayList;
	 
	 //LOCATION IMPORTANT ***************************
	LocationManager lm; //helps manage the location
	String towers;
	
	int lat=0;
	int longi=0;
	
	 
	 LocationListener ll;
	 
	  //private List mapOverlays;   instead overlayList
		
	  private Projection projection;
	 
	  private MapController mc;
	 
	  private MapView mapView;
	 
	 // GeoPoint gP; different GeoPoint
	 
	  //private GeoPoint gP2;
	 
	  private MapsDraw myoverlay;
	 
	  

	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		songLoc = MediaPlayer.create(this, R.raw.acdc);  // indicating a song is going to be used
		
		setContentView(R.layout.activity_main); //loads content of the layout file
		
		map=(MapView)findViewById(R.id.mvMain); 
	    map.setBuiltInZoomControls(true);       // is used for a zoom
	
	   
	    
	     Touchy t = new Touchy(); // event touchy when user touch the map
	     
	     overlayList = map.getOverlays(); //something to do with initializing map
	     overlayList.add(t);
	     compass = new MyLocationOverlay(MainActivity.this, map);
	     overlayList.add(compass);
	     controller = map.getController();
	    
	    
	     
	     LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	 	
	 	 
	     d = getResources().getDrawable(R.drawable.ic_launcher); //indicating a drawable
	    
	     
	    lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
	  
		
	    ll = new mylocationlistener();
	    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll); // using only gps provider
	    		
	    
	    
	    //*****************************************************************************************
	    // draw the area of the song
//mapView = (MapView) findViewById(R.id.mapview1);//Creating an instance of MapView    I used map instead of mapView
	    
	    // mapView.setBuiltInZoomControls(true);//Enabling the built-in Zoom Controls
	    
	     
	    
	    // gP = new GeoPoint(33695043, 73000000);//Creating a GeoPoint   instead i am creating:
	     
	 /*   GeoPoint songLocation1 = new GeoPoint((int)(51.526978 *1E6), (int)(-0.132831 *1E6)); 
	    GeoPoint songLocation2 = new GeoPoint((int)(51.526978 *1E6), (int)(-0.134325 *1E6)); 
		GeoPoint songLocation3 = new GeoPoint((int)(51.5226 *1E6), (int)(-0.132831 *1E6)); 
	    GeoPoint songLocation4 = new GeoPoint((int)(51.5226 *1E6), (int)(-0.134325 *1E6)); 
	   */ 
	     
	    
	   //  mc = map.getController();
	    // mc.setCenter(songLocation1);
	    
	     //mc.setZoom(9);//Initializing the MapController and setting the map to center at the
	    
	     //defined GeoPoint
	    	     
	     // = map.getOverlays();
	    
	     projection = map.getProjection();
	    
	     
	     myoverlay = new MapsDraw();
	 
	     //mapOverlays.add(myoverlay);

	   overlayList.add(myoverlay);

	    
		
	}

	/*
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		//compass.disableCompass();
		super.onPause();
		lm.removeUpdates(ll); // cancel updates in case of pause, will want change probably
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//compass.enableCompass();
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll); //provider.minTime.minDistance.listener-refering to the listener implemented before this activity
		
	}
	*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {  //inside every mapactivity this method is required 
		// TODO Auto-generated method stub
		return false; //this method is required for some accounting from the Maps to see whether you are displaying any route information
	}
	
	
	
	
	//touch events
	class Touchy extends Overlay {
		
		public boolean onTouchEvent(MotionEvent e, MapView m){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				start=e.getEventTime();
				x = (int) e.getX();
				y = (int) e.getY();
			touchedPoint = map.getProjection().fromPixels(x, y);
			
			}
			
			if (e.getAction() == MotionEvent.ACTION_UP){
			stop = e.getEventTime();	
			}
			
			if ((stop-start)>3000) {
				//perform an action alertDialog
				
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				//set title
			alertDialogBuilder.setTitle("Do you need any help?");
			
			//set dialog message
			alertDialogBuilder
			    .setMessage("Vlad, press an option!")
			    .setCancelable(false)
			    .setPositiveButton("stop the music", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//if this button is clicked,close
					    //current activity
						songLoc.pause();
						/*
						OverlayItem overlayItem = new OverlayItem(touchedPoint,"What's up","2nd String");
						CustomPinpoint custom = new CustomPinpoint(d, MainActivity.this);
						custom.isertPinPoint(overlayItem);
						overlayList.add(custom);*/
						
						
						
					}
			    	
			    })
			
			    .setNeutralButton("get address", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//if this button is clicked,
					    //it will try to get the address of the clicked point
						Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
						try
						{
							List<Address> address = geocoder.getFromLocation(touchedPoint.getLatitudeE6()/1E6,
									touchedPoint.getLongitudeE6()/1E6,1);
						if (address.size()>0){
							String display="";
							for(int i =0; i<address.get(0).getMaxAddressLineIndex();i++){
								
								display+=address.get(0).getAddressLine(i)+"\n";
								
							}
							Toast t =Toast.makeText(getBaseContext(), display, Toast.LENGTH_LONG);
							t.show();
						}
						
						} catch (IOException e){
							e.printStackTrace();
							
						}finally{
					}
			    	
					}
			    })
			    
			.setNegativeButton("Tagle view", new DialogInterface.OnClickListener() {
               
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//if this button is clicked
					//just close box and do nothing
					if (map.isSatellite()) {
						map.setSatellite(false);
						
					}else {
						map.setSatellite(true);
					}
					
				}
				
			});
			
			//create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			
			//show it
			alertDialog.show();
			return true;
			}
			
			return false;
		}
		
	}


	class mylocationlistener implements LocationListener {

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			double exactLati;
			double exactLongi;
			
			if (location != null) {
				
				
	    	exactLati = location.getLatitude();
	        exactLongi = location.getLongitude();
				 
			lat = (int) (exactLati *1E6);
			longi = (int) (exactLongi *1E6);
			
			
			
			// if in ucl
			if (exactLati>=51.5226 && exactLati<=51.526978 
					&& exactLongi>=-0.134325 && exactLongi<=-0.132831  ){ //
				
					songLoc.start();
							
			}else {
				
				songLoc.stop();
			}
			
			
			compass.enableCompass();
			
			GeoPoint ourLocation = new GeoPoint(lat,longi);
			
			//new
			//GeoPoint songLocation1 = new GeoPoint((int)(51.526978 *1E6), (int)(-0.132831 *1E6)); 
			//GeoPoint songLocation2 = new GeoPoint((int)(51.526978 *1E6), (int)(-0.134325 *1E6)); 
			//GeoPoint songLocation3 = new GeoPoint((int)(51.5226 *1E6), (int)(-0.132831 *1E6)); 
			//GeoPoint songLocation4 = new GeoPoint((int)(51.5226 *1E6), (int)(-0.134325 *1E6)); 
			
			
			
			
			controller.animateTo(ourLocation);
		    controller.setZoom(19);
		    controller.zoomIn();
		    
			
		    
			
			
			//new
			//songAreaPinpoint = new CustomPinpoint(songArea, MainActivity.this);
			//overlaySongArea = new OverlayItem(songLocation,"Whats up","2ndString");
			
			
			
			if (firstStart==1){
				custom.deletePinPoint(overlayItem);	
			}
			 custom = new CustomPinpoint(d, MainActivity.this);
			 overlayItem = new OverlayItem(ourLocation,"What's up","2nd String");
			
			//else{
				//custom =  CustomPinpoint(d, MainActivity.this);
			//}
			firstStart=1;
			
			
			custom.isertPinPoint(overlayItem);
			
			overlayList.add(custom);
			
			
			
			
			}
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}


class MapsDraw extends Overlay{


public MapsDraw(){

 
}


public void draw(Canvas canvas, MapView mapv, boolean shadow){

	 super.draw(canvas, mapv, shadow);

	 

	//Configuring the paint brush

	 

	Paint mPaint = new Paint();

	 mPaint.setDither(true);

	 mPaint.setColor(Color.RED);

	 mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

	 mPaint.setStrokeJoin(Paint.Join.ROUND);

	 mPaint.setStrokeCap(Paint.Cap.ROUND);

	 mPaint.setStrokeWidth(4);

	 

	GeoPoint songLocation1 = new GeoPoint((int)(51.526978 *1E6), (int)(-0.132831 *1E6)); 
	GeoPoint songLocation2 = new GeoPoint((int)(51.526978 *1E6), (int)(-0.134325 *1E6)); 
	GeoPoint songLocation3 = new GeoPoint((int)(51.5226 *1E6), (int)(-0.132831 *1E6)); 
	GeoPoint songLocation4 = new GeoPoint((int)(51.5226 *1E6), (int)(-0.134325 *1E6)); 
	 
	 
	 //GeoPoint gP1 = new GeoPoint(34159000,73220000);//starting point Abbottabad
	 //GeoPoint gP2 = new GeoPoint(33695043,73050000);//End point Islamabad

	 //GeoPoint gP4 = new GeoPoint(33695043, 73050000);//Start point Islamabad
	 //GeoPoint gP3 = new GeoPoint(33615043, 73050000);//End Point Rawalpindi

	 

	Point p1 = new Point();

	 Point p2 = new Point();

	 Path path1 = new Path();

	 

	 Point p3 = new Point();

	 Point p4 = new Point();

	 Path path2 = new Path();

	 
	 Path path3 = new Path();
	 Path path4 = new Path();
	
	 
	 projection.toPixels(songLocation2, p3);

	 projection.toPixels(songLocation1, p4);

	 

	path1.moveTo(p4.x, p4.y);//Moving to Abbottabad location

	 path1.lineTo(p3.x,p3.y);//Path till Islamabad

	 

	 projection.toPixels(songLocation3, p1);

	 projection.toPixels(songLocation4, p2);

	 

	path2.moveTo(p2.x, p2.y);//Moving to Islamabad location

	 path2.lineTo(p1.x,p1.y);//Path to Rawalpindi

path3.moveTo(p1.x,p1.y );
path3.lineTo(p4.x, p4.y);

path4.moveTo(p2.x, p2.y );
path4.lineTo(p3.x, p3.y);


	 canvas.drawPath(path1, mPaint);//Actually drawing the path
	 canvas.drawPath(path2, mPaint);
	 canvas.drawPath(path3, mPaint);
	 canvas.drawPath(path4, mPaint);
	 

	 }


}




}
