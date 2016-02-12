package music.player;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.MyLocationOverlay;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore.Audio.Genres;
import android.util.Log;
import android.widget.Toast;

public class LocationService extends Service{

	LocationManager lm;
	LocationListener ll;
	DatabaseHelper dbHelper=new DatabaseHelper(this);
	List<String> arrayGenre=new ArrayList<String>();
	List<Double> arrayLatitude=new ArrayList<Double>();
	List<Double> arrayLongitude=new ArrayList<Double>();
	Location locDest;
	String genre,GenreSend;
	Bundle genrelist;
	ArrayList<String> ArraygenreList=new ArrayList<String>();
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		ll=new MyLocationListener();
		
	//	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 500, ll);
	//	if(lm==null){
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, ll);
		
			Toast.makeText(getApplicationContext(), "Locating through network provider..", Toast.LENGTH_SHORT).show();
//		}
		
		
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub

		/*ArraygenreList = intent.getStringArrayListExtra("GenreList");
		if(ArraygenreList==null){
			Toast.makeText(getApplicationContext(), "GenreList Service is NULL", Toast.LENGTH_LONG).show();
		}
		else{
			
		}*/
		
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	public class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			SQLiteDatabase datab = dbHelper.getWritableDatabase();
			
			
			
			if(location!=null){
				Cursor curLat;
				Cursor curLon;
				Cursor curGen;
				
				curLat=datab.rawQuery("SELECT latitude FROM songType", null);
				curLon=datab.rawQuery("SELECT longitude FROM songType", null);
				curGen=datab.rawQuery("Select genre FROM songType", null);
				
				curLat.moveToFirst();
				curLon.moveToFirst();
				curGen.moveToFirst();
				int q=0;
				while(!curLat.isAfterLast()){
					arrayLatitude.add(curLat.getDouble(curLat.getColumnIndex("latitude")));
					Log.d("Number of rows", Integer.toString(q));
					q++;
					curLat.moveToNext();
				}
				while(!curLon.isAfterLast()){
					arrayLongitude.add(curLon.getDouble(curLon.getColumnIndex("longitude")));
					curLon.moveToNext();
				}
				while(!curGen.isAfterLast()){
					arrayGenre.add(curGen.getString(curGen.getColumnIndex("genre")));
					curGen.moveToNext();
				}
				
				
				Log.d("Size of Array:", Integer.toString(arrayLongitude.size()));
				
				
				for(int i=0; i < arrayLatitude.size(); i++){
					locDest=new Location("B");
					
					locDest.setLatitude(arrayLatitude.get(i));
					locDest.setLongitude(arrayLongitude.get(i));
					
					float distanceLeft = location.distanceTo(locDest);
					
					genre = arrayGenre.get(i);
					
					if(distanceLeft<1000){
						/*Toast.makeText(getApplicationContext(), "Distance less than 1 km", Toast.LENGTH_LONG).show();
						for(int j=0; j<ArraygenreList.size(); j++){
							if(genre.equals(ArraygenreList.get(i))){
								Toast.makeText(getApplicationContext(), "Genre Found", Toast.LENGTH_LONG).show();
							}
						}*/
						
						Toast.makeText(getApplicationContext(), "Distance less than 1 km", Toast.LENGTH_LONG).show();
						Intent inte=new Intent(getApplicationContext(),MainActivity.class);
						
						for(int w=0; w < arrayLatitude.size(); w++){
							arrayLatitude.remove(w);
							arrayLongitude.remove(w);
							arrayGenre.remove(w);
						}
						
					//	inte.setAction("music.player.genrereceiver");
						inte.putExtra("GenreFromService", genre);
					//	inte.addCategory(Intent.CATEGORY_DEFAULT);
						inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(inte);
						
						
						
						
						break;
					}
				}
				
				
				
			}
			
			
		}
		
		
		public String getGenre(){
			return GenreSend;
			
		}
		

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "Enable GPS-Locus Muzik", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "GPS Enabled-Locus Muzik", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	
	
	

}
