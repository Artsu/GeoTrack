package fi.jyu.ties425.geotrack;

import com.example.geotrack.R;

import fi.jyu.ties425.geotrack.model.GeoTag;
import fi.jyu.ties425.geotrack.util.CommonUtil;
import fi.jyu.ties425.geotrack.widget.GeoTagAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;

public class ListActivity extends Activity {

	private ListView listView1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		Intent intent = getIntent();
		Parcelable[] tmpData = intent.getParcelableArrayExtra("data");
		GeoTag[] data = CommonUtil.parseParcelableArrayToGeoTagArray(tmpData);
		
		GeoTagAdapter adapter = new GeoTagAdapter(this,
				R.layout.geotag_listview_item_row, data);

		listView1 = (ListView) findViewById(R.id.tagListView);

		listView1.setAdapter(adapter);
	}

}
