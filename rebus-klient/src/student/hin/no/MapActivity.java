package student.hin.no;

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
import android.provider.AlarmClock;
import android.util.Log;
import android.widget.Toast;

import db.Game;

public class MapActivity extends com.google.android.maps.MapActivity{
	
	MapController mapController;
	GeoPoint geoPoint;
	MapView mapView;
	MyLocationOverlay compass;
	private Thread thread = null;
	//GameRebus game;
	Game game;
	
	
	public static String TREASURE_PROXIMITY_ALERT = "studetn.hin.no.ALERT";
	
	//New changes
	private static final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1;		//I meter
	private static final long MINIMUM_TIME_BETWEEN_UPDATE = 1000;	//I Milliseconds
	
	private static final long POINT_RADIUS = 1000; // I Meter
	private static final long PROX_ALERT_EXPIRATION = -1;	//NET VREMENI ISTECHENIYA

	private double latitude;
	private double longitude;
	
	private Location rebusLocation;
	
	//CHANGES 11.05
	private static final String PROX_ALER_INTENT = "student.hin.no.PBR";
	//private GamePunktRebus gamePunkt;
	private db.GamePunkt gamePunkt;
	
	
	private LocationManager locationManager;
	
	private ConnectionHandler connectionhandler;
	
	private Long time;
	
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
		
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		
		
		//CHANGES <-- 10.05 
		
		game = (Game) getIntent().getExtras().getSerializable("game");
		game.getClass();
		
		
		time = game.getStartDate() + game.getVarighet() * 60000; //*60000 til å konvertere minutter i millisekunder
		Intent timeIntent = new Intent(this, TimeReceiver.class);		
		PendingIntent pendingTimeIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1253, timeIntent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);		
	
		alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingTimeIntent);
	
		
		//CHANGES 11.05 - poluchaem pervuu poziciu i naznachaem ee tochke v dal'neyshem budem meniat' poziciu pri dostizenii!!!
		
		changeGamePunkt();
				
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
												LocationManager.GPS_PROVIDER,
												MINIMUM_TIME_BETWEEN_UPDATE,
												MINIMUM_DISTANCECHANGE_FOR_UPDATE,
												new MyLocationListener()
		);
		
		//CHANGES -->
		
		//Poluchaem nashi koordinati
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		String provider = LocationManager.GPS_PROVIDER;
		
		Location location = locationManager.getLastKnownLocation(provider);

		mapView = (MapView)findViewById(R.id.mapView);
		mapView.displayZoomControls(true);
		mapView.setBuiltInZoomControls(true);
		
		populateCoordinatesFromLastKnownLocation();
		//Toast.makeText(MapActivity.this,"Latitude: " + latitude + " Longitude: "+ longitude,Toast.LENGTH_LONG).show();	
		
	
		
		//double lat = game.getFirstPoint().getLat();
		//double lng = game.getFirstPoint().getLng();
		
		//double lat = location.getLatitude();
		//double lng = location.getLongitude();
		
		//geoPoint = new GeoPoint((int)(lat*1E6),(int)(lng*1E6));
		
		
		//mapController = mapView.getController();
		//mapController.animateTo(geoPoint);
		//mapController.setZoom(15);
		
		initMyLocation();
		
		//CHANGES 11.05
		IntentFilter filter = new IntentFilter(TREASURE_PROXIMITY_ALERT);
		this.registerReceiver(new ProximityIntentReceiver(), filter);
		this.setProximityAlert();
		
		
	}//end of on create

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		compass.disableCompass();
	}//end of onPause

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		compass.enableCompass();
	}//end of onResume

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}//end of isRouteDisplayed
	
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
			Log.i("populateCoordinatesFromLastKnownLocation", "All set");
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
	
	private Location retriveLocation()
	{
		Location location = new Location("GAME_POINT");
		
		try 
		{
			gamePunkt = game.getNextPunkt();
			location.setLatitude(gamePunkt.getLat());
			location.setLatitude(gamePunkt.getLng());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}//end of catch
		
		
		return location;
	}//end of retriveLocation
	
	/*
	private Location retrieveNextLocationFromPreferences() throws Exception
	{
		Location location = new Location("POINT_LOCATION");
		location.setLatitude(game.getNextPunkt().getLat());
		location.setLongitude(game.getNextPunkt().getLng());
		return location;
	}
	
	*/
	
	public class MyLocationListener implements LocationListener 
	{
		@Override
		public void onLocationChanged(Location location) {
			//Location pointLocation = retrievelocationFromPreferences();
			Location pointLocation = rebusLocation;
			float distance = location.distanceTo(pointLocation);
			geoPoint = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));
			
			//Toast.makeText(MapActivity.this, geoPoint.getLatitudeE6() + " " + geoPoint.getLongitudeE6(), Toast.LENGTH_LONG).show();
			//POKAZIVAEM POZICIU
			mapController = mapView.getController();
			mapController.animateTo(geoPoint);
			
			//Toast.makeText(MapActivity.this,"Distance from Point:"+distance, Toast.LENGTH_LONG).show();
			Toast.makeText(MapActivity.this,"MyLat "+ location.getLatitude() + " MyLong "+ location.getLongitude(), Toast.LENGTH_LONG).show();
			Toast.makeText(MapActivity.this,"Lat: "+ pointLocation.getLatitude() + " long: " + pointLocation.getLongitude(), Toast.LENGTH_LONG).show();
			
			//Proveriem poziciu
			if(distance < gamePunkt.getRadius())
			{
				Toast.makeText(MapActivity.this, "VOT mi I v TOCHKE", Toast.LENGTH_LONG).show();
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
		
		Intent intent = new Intent(TREASURE_PROXIMITY_ALERT);
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
			//kontaktServlet();
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
	
	

}//end of MapActivity
