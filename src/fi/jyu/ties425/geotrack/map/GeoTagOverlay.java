package fi.jyu.ties425.geotrack.map;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/**
 * Overlay class to be shown on the map activity, representing a single GeoTag.
 * 
 * @author Kim Foudila, Ari-Matti Nivasalo
 *
 */
public class GeoTagOverlay<T> extends ItemizedOverlay<OverlayItem> {

	Context context;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public GeoTagOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(item.getTitle());
		builder.setMessage(item.getSnippet());
		builder.show();
		return true;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		if (shadow) {
			return; // no shadows plz
		}
		super.draw(canvas, mapView, shadow);
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
	}

	public void addAllOverlays(List<OverlayItem> overlayItems) {
		mOverlays.addAll(overlayItems);
		populate();
	}

	public void lazyPopulate() {
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

}
