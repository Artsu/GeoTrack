package fi.jyu.ties425.geotrack.widget;

import com.example.geotrack.R;

import fi.jyu.ties425.geotrack.model.GeoTag;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GeoTagAdapter extends ArrayAdapter<GeoTag>{

    Context context; 
    int layoutResourceId;    
    GeoTag data[] = null;
	
	public GeoTagAdapter(Context context, int layoutResourceId, GeoTag[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new WeatherHolder();
//            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.tagLocation = (TextView)row.findViewById(R.id.tagLocation);
            holder.tagTime = (TextView)row.findViewById(R.id.tagTime);
            
            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }
        
        GeoTag tag = data[position];
        
        holder.tagLocation.setText(tag.getGeoTag());
        holder.tagTime.setText(tag.getTimeObtained());
//        holder.imgIcon.setImageResource(tag.icon);
        
        return row;
    }
    
    static class WeatherHolder
    {
//        ImageView imgIcon;
        TextView tagLocation;
        TextView tagTime;
    }
	
}
