package fi.jyu.ties425.geotrack;

import com.example.geotrack.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import fi.jyu.ties425.geotrack.model.GeoTag;
import fi.jyu.ties425.geotrack.util.CommonUtil;

public class PointsInMapActivity extends MapActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
		Intent intent = getIntent();
		Parcelable[] tmpData = intent.getParcelableArrayExtra("data");
		GeoTag[] data = CommonUtil.parseParcelableArrayToGeoTagArray(tmpData);
        
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
