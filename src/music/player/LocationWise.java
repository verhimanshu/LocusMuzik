package music.player;

import java.io.IOException;
import java.util.List;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LocationWise extends MapActivity{

	private MyLocationOverlay myLocationOverlay;
	private MapView mapView;
	public static Address address;
	public static String addressString;
	public static double destLatitude;
	public static double destLongitude;
	Button useLocation;
	EditText getAddress;
	public Bundle DatabaseInfo=new Bundle();
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationwise);
		setupViews();
		
		
	}

	
	
	
	public void mapCurrentAddress(){
		addressString=getAddress.getText().toString();
		Geocoder g=new Geocoder(this);
		List<Address> addresses;
		
		
			try {
				if(g.getFromLocationName(addressString, 1)==null){
					Toast.makeText(getApplicationContext(), "No address found!!", Toast.LENGTH_LONG).show();
				}
				addresses=g.getFromLocationName(addressString, 1);
				
				
				if(addresses.size()>0){
					address=addresses.get(0);
					List<Overlay> mapOverlays=mapView.getOverlays();
					OverlayAddress addressOverlay=new OverlayAddress(address);
					mapOverlays.add(myLocationOverlay);
					mapOverlays.add(addressOverlay);
					mapView.invalidate();
					final MapController mapController=mapView.getController();
					destLatitude=address.getLatitude();
					destLongitude=address.getLongitude();
					
					
					
					mapController.animateTo(addressOverlay.getGeopoint(), new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mapController.setZoom(16);
						}
					});
					useLocation.setEnabled(true);
				}
				else{
					Toast.makeText(getApplicationContext(), "No Address provided!", Toast.LENGTH_LONG).show();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}

	// TODO Auto-generated method stub
	

	
	
	private void setupViews() {
		// TODO Auto-generated method stub
		useLocation=(Button)findViewById(R.id.UseLocation);
		Button mapAddress=(Button)findViewById(R.id.MapAddress);
		useLocation.setEnabled(false);
		getAddress=(EditText)findViewById(R.id.EnterLocation);
		mapView=(MapView)findViewById(R.id.map);
		mapView.setBuiltInZoomControls(true);
		myLocationOverlay =new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);
		mapView.invalidate();
		
		useLocation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent myIntent=new Intent(LocationWise.this,Select_Genre.class);
				myIntent.putExtra("latitude", destLatitude);
				myIntent.putExtra("longitude", destLongitude);
				myIntent.putExtra("Address", addressString);
				startActivity(myIntent);				
			}
		});
		
		mapAddress.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapCurrentAddress();
			}
		});
	}




	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	

}
