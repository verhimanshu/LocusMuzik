package music.player;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;

public class OverlayAddress extends Overlay{

	private static final int CONTAINER_RADIUS=4;
	private static final int CONTAINER_STATIC_OFFSET=1;
	private Address address;
	private GeoPoint geopoint;
	
	public OverlayAddress(Address address){
		super();
		assert(null!=address);
		this.setAddress(address);
		
		Double convertLatitude=address.getLatitude()*1E6;
		Double convertLongitude=address.getLongitude()*1E6;
		
		setGeopoint(new GeoPoint(convertLatitude.intValue(),convertLongitude.intValue()));
		
	}

	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow){
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
		
		Point LocationPoint=new Point();
		Projection projection=mapView.getProjection();
		projection.toPixels(getGeopoint(), LocationPoint);
		Paint containerPaint=new Paint();
		containerPaint.setAntiAlias(true);
		
		int containerX=LocationPoint.x;
		int containerY=LocationPoint.y;
		
		if(shadow){
			containerX += CONTAINER_STATIC_OFFSET;
			containerY += CONTAINER_STATIC_OFFSET;
			containerPaint.setARGB(90, 0, 0, 0);
			canvas.drawCircle(containerX, containerY, CONTAINER_RADIUS, containerPaint);
		}
		else{
			containerPaint.setColor(Color.RED);
			canvas.drawCircle(containerX, containerY, CONTAINER_RADIUS, containerPaint);
		}
	}

	
	
	
	
	
	
	public GeoPoint getGeopoint() {
		// TODO Auto-generated method stub
		return geopoint;
	}


	public void setAddress(Address address2) {
		// TODO Auto-generated method stub
		this.address=address2;
	}

	public void setGeopoint(GeoPoint geopoint) {
		// TODO Auto-generated method stub
		this.geopoint=geopoint;
		
	}
	
	
	
	
}
