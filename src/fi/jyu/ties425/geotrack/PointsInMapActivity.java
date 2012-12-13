package fi.jyu.ties425.geotrack;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.geotrack.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

import fi.jyu.ties425.geotrack.map.GeoTagOverlay;
import fi.jyu.ties425.geotrack.model.GeoTag;
import fi.jyu.ties425.geotrack.util.CommonUtil;

public class PointsInMapActivity extends MapActivity {
	
	List<Overlay> mapOverlays;
	Projection projection;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
		Intent intent = getIntent();
		Parcelable[] tmpData = intent.getParcelableArrayExtra("data");
		GeoTag[] data = CommonUtil.parseParcelableArrayToGeoTagArray(tmpData);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        projection = mapView.getProjection();
        
        MapController controller = mapView.getController();
		
		int minLatitude = Integer.MAX_VALUE;
		int maxLatitude = Integer.MIN_VALUE;
		int minLongitude = Integer.MAX_VALUE;
		int maxLongitude = Integer.MIN_VALUE;
        
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		GeoTagOverlay<OverlayItem> itemizedOverlay = new GeoTagOverlay<OverlayItem>(drawable, PointsInMapActivity.this);
		for (GeoTag gt : data) {
			// convert to integer microdegrees for the map
			GeoPoint gp = new GeoPoint((int) (gt.getLat() * 1e6),
					(int) (gt.getLon() * 1e6));
			OverlayItem oi = new OverlayItem(gp, gt.getGeoTag(), gt.getDescr());
			itemizedOverlay.addOverlay(oi);

			// find max and min values for centering and zooming  the map properly
			int lat = gp.getLatitudeE6();
			int lon = gp.getLongitudeE6();
			maxLatitude = Math.max(lat, maxLatitude);
			minLatitude = Math.min(lat, minLatitude);
			maxLongitude = Math.max(lon, maxLongitude);
			minLongitude = Math.min(lon, minLongitude);
		}
		itemizedOverlay.lazyPopulate();
		mapOverlays.add(itemizedOverlay);
		
		// find the center point of the coordinates and animate to it
        controller.zoomToSpan(Math.abs(maxLatitude - minLatitude), Math.abs(maxLongitude - minLongitude));
		controller.animateTo(new GeoPoint((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2));

    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
}
