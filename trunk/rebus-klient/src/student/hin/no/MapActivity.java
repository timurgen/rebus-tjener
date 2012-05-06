package student.hin.no;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.os.Bundle;

public class MapActivity extends com.google.android.maps.MapActivity{
	
	MapController mapController;
	GeoPoint geoPoint;
	MapView mapView;
	MyLocationOverlay compass;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.map);
		
		GameRebus game = (GameRebus) getIntent().getExtras().getSerializable("game");
		
		mapView = (MapView)findViewById(R.id.mapView);
		mapView.displayZoomControls(true);
		mapView.setBuiltInZoomControls(true);
		
		//double lat = 68.44;
		//double longi = 17.41;
		
		game.getClass();
		
		double lat = game.getFirstPoint().getLat();
		double longi = game.getFirstPoint().getLng();
		
		geoPoint = new GeoPoint((int)(lat*1E6),(int)(longi*1E6));
		
		
		mapController = mapView.getController();
		mapController.animateTo(geoPoint);
		mapController.setZoom(15);
		
		compass = new MyLocationOverlay(this, mapView);//set compass
		mapView.getOverlays().add(compass);//add compass
	}

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

}//end of MapActivity
