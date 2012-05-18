package student.hin.no;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import db.Game;

public class MapActivity extends com.google.android.maps.MapActivity{
	
	//Variabler
	MapController mapController;
	GeoPoint geoPoint;
	MapView mapView;
	MyLocationOverlay compass;
	private Thread thread = null;
	Game game;
	
	public static String TREASURE_PROXIMITY_ALERT = "student.hin.no.ALERT";
	
	private static final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1;		//I meter
	private static final long MINIMUM_TIME_BETWEEN_UPDATE = 1000;	//I Milliseconds
	//private static final long POINT_RADIUS = 1000; // I Meter
	//private static final long PROX_ALERT_EXPIRATION = -1;	//Fungerer

	private double latitude;
	private double longitude;
	private float distance;
	private Location location;
	
	private Location rebusLocation;
	
	//CHANGES 11.05
	static final String PROX_ALER_INTENT = "student.hin.no.PBR";
	private ProximityIntentReceiver proximityIntentReceiver;
	
	private db.GamePunkt gamePunkt;
	private LocationManager locationManager;
	private ConnectionHandler connectionhandler;
	private Long time;
	
	private Date date;
	Timer timerGame = new Timer();
	private Alarm alarmbleat = new Alarm();
	//End of Variabler
	
	/**
	 * Sender spill result til tjenester
	 * Runnable kaller metoden som startes i bakgrunnstråden
	 */
	private Runnable bakgrunnsSendResult = new Runnable() {
		public void run() {
			kontaktServlet();
			thread.interrupt();
			MapActivity.this.finish();
		}
	};
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.map);		
		
		//Skafe game object fra GamesAllActivity
		game = (Game) getIntent().getExtras().getSerializable("game");
		game.getClass();
		
		//Alarm, vekker applikasjonen når "game" blir avsluttet
		time = game.getStartDate() + game.getVarighet() * 60000; //*60000 til å konvertere minutter i millisekunder
				
		
		
		changeGamePunkt();
				
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
												LocationManager.GPS_PROVIDER,
												MINIMUM_TIME_BETWEEN_UPDATE,
												MINIMUM_DISTANCECHANGE_FOR_UPDATE,
												new MyLocationListener()
		);
		
		//skaffe bruker koordinater
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		String provider = LocationManager.GPS_PROVIDER;
		
		location = locationManager.getLastKnownLocation(provider);

		mapView = (MapView)findViewById(R.id.mapView);
		mapView.displayZoomControls(true);
		mapView.setBuiltInZoomControls(true);
		
		populateCoordinatesFromLastKnownLocation();
		//Toast.makeText(MapActivity.this,"Latitude: " + latitude + " Longitude: "+ longitude,Toast.LENGTH_LONG).show();	
		
		initMyLocation();
		
		//CHANGES 11.05
		/*
		IntentFilter filter = new IntentFilter(PROX_ALER_INTENT);
		this.registerReceiver(new ProximityIntentReceiver(), filter);
		this.setProximityAlert();
		*/
		IntentFilter filter = new IntentFilter(PROX_ALER_INTENT);
		proximityIntentReceiver = new ProximityIntentReceiver();
		this.registerReceiver(proximityIntentReceiver, filter);
		this.setProximityAlert();
		
		timerGame.schedule(new Task(), 0, 1000);
		
	}//end of onCreate

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		alarmbleat.SetAlarm(getApplicationContext(), time, false, true, "");
		super.onPause();
		compass.disableCompass();
	}//end of onPause

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		compass.enableCompass();
		alarmbleat.CancelAlarm(getApplicationContext());
	}//end of onResume

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}//end of isRouteDisplayed
	
	@Override
	protected void onStop()
	{
		alarmbleat.SetAlarm(getApplicationContext(), time, false, true, "");
	    unregisterReceiver(proximityIntentReceiver);
	    super.onStop();
	}
	
	/**
	 * Menu og onClick til menu
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	//creates a menu inflater
    	MenuInflater inflater = getMenuInflater();
    	//generates a Menu from a menu resource file
    	//R.menu.main_menu represents the ID of the XML resource file
    	inflater.inflate(R.menu.map_menu, menu);
    	return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    { 	
    	//check selected menu item
    	// R.id.exit is @+id/exit
    	if(item.getItemId() == R.id.menuHelp){
    		Toast.makeText(MapActivity.this,gamePunkt.getRebus() , Toast.LENGTH_LONG).show();
    		return true;
    	}
    	if(item.getItemId() == R.id.menuDistance){
    		Toast.makeText(MapActivity.this,"Distance from Point:" + distance, Toast.LENGTH_LONG).show();
    		return true;
    	}
    	if(item.getItemId() == R.id.menuCoordinates){
    		Toast.makeText(MapActivity.this,"MyLat "+ location.getLatitude() + " MyLong "+ location.getLongitude(), Toast.LENGTH_LONG).show();
    		return true;
    	}
    	if(item.getItemId() == R.id.menuExit){
    		this.finish();
    		return true;
    	}
    	
    	return false;
    }

	
	//CHANGES <-- 10.05 
	
	/**
	 * Bruker koordinates
	 */
	private void populateCoordinatesFromLastKnownLocation()
	{
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location!=null)
		{
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			//Log.i("populateCoordinatesFromLastKnownLocation", "All set");
		}//end of if
		
	}//end of populateCoordinatesFromLastKnownLocation
	
	/*
	private Location retrievelocationFromPreferences()
	{
		Location location = new Location("POINT_LOCATION");
		
		//gamePunkt = game.getNextPunkt();
		
		location.setLatitude(game.getFirstPoint().getLat());
		location.setLongitude(game.getFirstPoint().getLng());
		return location;
	}
	*/
	
	public class MyLocationListener implements LocationListener 
	{
		@Override
		public void onLocationChanged(Location location) {
			//Location pointLocation = retrievelocationFromPreferences();
			Location pointLocation = rebusLocation;
			distance = location.distanceTo(pointLocation);
			geoPoint = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));
			
			//Toast.makeText(MapActivity.this, geoPoint.getLatitudeE6() + " " + geoPoint.getLongitudeE6(), Toast.LENGTH_LONG).show();
			//POKAZIVAEM POZICIU
			mapController = mapView.getController();
			mapController.animateTo(geoPoint);
			
			//Toast.makeText(MapActivity.this,"Distance from Point:"+distance, Toast.LENGTH_LONG).show();
			//Toast.makeText(MapActivity.this,"MyLat "+ location.getLatitude() + " MyLong "+ location.getLongitude(), Toast.LENGTH_LONG).show();
			Toast.makeText(MapActivity.this,"Lat: "+ pointLocation.getLatitude() + " long: " + pointLocation.getLongitude(), Toast.LENGTH_LONG).show();
			Toast.makeText(MapActivity.this,gamePunkt.getRebus() , Toast.LENGTH_LONG).show();
			
			//Proveriem poziciu
			if(distance < gamePunkt.getRadius())
			{
				Toast.makeText(MapActivity.this, "Bingo", Toast.LENGTH_LONG).show();
				changeGamePunkt();
				setProximityAlert();
			}
			else
			{
				Toast.makeText(MapActivity.this,"Distance from Point:" + distance, Toast.LENGTH_LONG).show();
			}
			
		}

		@Override
		public void onProviderDisabled(String provider) {}

		@Override
		public void onProviderEnabled(String provider) {}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
		
	}//end of myLocationListener
	
	
	/**
	 * Tegner posisjon og kompas
	 */
	private void initMyLocation() {
		compass = new MyLocationOverlay(this, mapView);//set compass
		mapView.getOverlays().add(compass);//add compass
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(this, mapView);
		myLocationOverlay.enableMyLocation();
		mapView.getOverlays().add(myLocationOverlay);
	}
	
	//CHANGES
	
	//CHANGES 11.05
	
	/**
	 * Set proximity alarm (Fungerer)
	 */
	private void setProximityAlert()
	{
		String serviceString = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(serviceString);
			
		double lat = gamePunkt.getLat();
		double lng = gamePunkt.getLng();
		float radius = gamePunkt.getRadius();
		long expiration = -1;
		
		Intent intent = new Intent(PROX_ALER_INTENT);
		PendingIntent proximityIntent = PendingIntent.getBroadcast(this, -1, intent, 0);
		locationManager.addProximityAlert(lat, lng, radius, expiration, proximityIntent);
		
	}//end of setProximityAlert
	
	/**
	 * Hente neste punkt fra spill, hvis det finnes ikke noe punkter gjører noe (NADO IZMENIT')
	 */
	private void changeGamePunkt()
	{
		try {
			gamePunkt = game.getNextPunkt();
			rebusLocation = new Location("REBUS_LOCATION");
			rebusLocation.setLatitude(gamePunkt.getLat());
			rebusLocation.setLongitude(gamePunkt.getLng());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(MapActivity.this,"FERDIG", Toast.LENGTH_LONG).show();
			
			thread = new Thread(null, bakgrunnsSendResult, "logging inn");
			thread.start();
			
			Intent resultIntent = new Intent(MapActivity.this, ResultActivity.class);
			resultIntent.putExtra("game", game);
			startActivity(resultIntent);
			
		}
	}
	
	/**
	 * Sender informasjon til tjenester
	 */
	private void kontaktServlet() {
		
		connectionhandler = new ConnectionHandler();
		connectionhandler.endGame(getApplicationContext(), game.getId().toString(), Long.toString(System.currentTimeMillis() - game.getStartDate()), Integer.toString(game.getCurrentPoint()));
		
	}//end of kontaktServlet()
	
	/**
	 * class Task brukes til å kontrolere spill tid
	 */
	public class Task extends TimerTask
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			date = new Date();
			if (game.getStartDate() + game.getVarighet() * 60000 < date.getTime())
			{
				thread = new Thread(null, bakgrunnsSendResult, "logging inn");
				thread.start();
				
				Intent resultIntent = new Intent(MapActivity.this, ResultActivity.class);
				resultIntent.putExtra("game", game);
				startActivity(resultIntent);
				
				//thread.interrupt();
				timerGame.cancel();
			}
			//Log.d("From alarm time time", String.valueOf(date.getTime()));
			//Log.d("Timer", String.valueOf(game.getStartDate() + game.getVarighet() * 60000));
		}
	}
		
}//end of MapActivity
