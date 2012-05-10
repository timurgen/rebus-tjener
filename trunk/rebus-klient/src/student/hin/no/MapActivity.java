package student.hin.no;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MapActivity extends com.google.android.maps.MapActivity{
	
	MapController mapController;
	GeoPoint geoPoint;
	MapView mapView;
	MyLocationOverlay compass;
	
	GameRebus game;
	
	public static String TREASURE_PROXIMITY_ALERT = "studetn.hin.no.ALERT";
	
	//New changes
	private static final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1;		//Metri
	private static final long MINIMUM_TIME_BETWEEN_UPDATE = 1000;	//In Milliseconds
	
	private static final long POINT_RADIUS = 1000; // in Meters
	private static final long PROX_ALERT_EXPIRATION = -1;	//NET VREMENI ISTECHENIYA

	private double latitude;
	private double longitude;
	
	private Location rebusLocation;
	
	//Ne sovsem poniatnaya peremennaya
	private static final String PROX_ALER_INTENT = "student.hin.no.ProximityAlert";
	
	private LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.map);
		
		//CHANGES <-- 10.05 
		
		game = (GameRebus) getIntent().getExtras().getSerializable("game");
		game.getClass();
		
		//Poluchaem koordinati pervoy tochki!!!
		rebusLocation = new Location("REBUS_LOCATION");
		//rebusLocation.setLatitude(game.getNextPunkt().getLat());
		
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
		
		//double lat = 68.44;
		//double longi = 17.41;
		
		
		
		//Changes <--
		populateCoordinatesFromLastKnownLocation();
		//Toast.makeText(MapActivity.this,"Latitude: " + latitude + " Longitude: "+ longitude,Toast.LENGTH_LONG).show();	
		
		//Changes -->
		
		//double lat = game.getFirstPoint().getLat();
		//double lng = game.getFirstPoint().getLng();
		
		//double lat = location.getLatitude();
		//double lng = location.getLongitude();
		
		//geoPoint = new GeoPoint((int)(lat*1E6),(int)(lng*1E6));
		
		
		//mapController = mapView.getController();
		//mapController.animateTo(geoPoint);
		//mapController.setZoom(15);
		
		initMyLocation();
		
		
	}//end of on create

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		compass.disableCompass();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		compass.enableCompass();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}//end of isRouteDisplayed
	
	private void setProximityAlert()
	{
		String serviceString = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(serviceString);
		
		double lat = game.getFirstPoint().getLat();
		double lng = game.getFirstPoint().getLng();;
		float radius = game.getFirstPoint().getRadiusFloat();
		long expiration = -1;
		
		Intent intent = new Intent(TREASURE_PROXIMITY_ALERT);
		PendingIntent proximityIntent = PendingIntent.getBroadcast(this, -1, intent, 0);
		locationManager.addProximityAlert(lat, lng, radius, expiration, proximityIntent);
		
	}//end of setProximityAlert
	
	//CHANGES <-- 10.05 
	
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
	
	private Location retrievelocationFromPreferences()
	{
		Location location = new Location("POINT_LOCATION");
		location.setLatitude(game.getFirstPoint().getLat());
		location.setLongitude(game.getFirstPoint().getLng());
		return location;
	}
	
	private Location retrieveNextLocationFromPreferences() throws Exception
	{
		Location location = new Location("POINT_LOCATION");
		location.setLatitude(game.getNextPunkt().getLat());
		location.setLongitude(game.getNextPunkt().getLng());
		return location;
	}
	
	public class MyLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location location) {
			Location pointLocation = retrievelocationFromPreferences();
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
			if(distance < game.getFirstPoint().getRadiusFloat())
			{
				Toast.makeText(MapActivity.this, "VOT mi I v TOCHKE", Toast.LENGTH_LONG).show();
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
	 * Risuet poziciu na karte i kompas =)
	 */
	private void initMyLocation() {
		compass = new MyLocationOverlay(this, mapView);//set compass
		mapView.getOverlays().add(compass);//add compass
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(this, mapView);
		myLocationOverlay.enableMyLocation();
		mapView.getOverlays().add(myLocationOverlay);
	}
	
	//CHANGES
	
	

}//end of MapActivity
