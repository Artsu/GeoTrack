package fi.jyu.ties425.geotrack;

import com.example.geotrack.R;

import fi.jyu.ties425.geotrack.model.GeoTag;
import fi.jyu.ties425.geotrack.widget.GeoTagAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ListActivity extends Activity {

	private ListView listView1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		GeoTag data[] = new GeoTag[] {
				new GeoTag("asdf", "1"),
				new GeoTag("assdfsdfsdf", "2"),
				new GeoTag("asasddf", "3"),
				new GeoTag("das", "4"),
				new GeoTag("aasdsdf", "5") };

		GeoTagAdapter adapter = new GeoTagAdapter(this,
				R.layout.geotag_listview_item_row, data);

		listView1 = (ListView) findViewById(R.id.tagListView);

		listView1.setAdapter(adapter);
	}

}
