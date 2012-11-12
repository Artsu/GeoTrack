package fi.jyu.ties425.geotrack;

import com.example.geotrack.R;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class PointsInMapActivity extends MapActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
//        MapController mapController = mapView.getController();
//        mapController.setCenter(point)
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
}
