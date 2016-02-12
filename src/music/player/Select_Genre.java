package music.player;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Select_Genre extends Activity {

	Spinner Genrespinner;
	Bundle GenreBundle;
	double storeLatitude,storeLongitude;
	String storeGenre,storeAddress;
	String genre;
	DatabaseHelper dbHelper=new DatabaseHelper(this);
	private ArrayList<String> columnArray3;
	Cursor GenreCursor,GenreDelete;
	Uri uri;
	int Mindex,index;
	ArrayList<String> deleteGenre=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_genre);
		Genrespinner=(Spinner)findViewById(R.id.GenreSpinner);
		Button submit=(Button)findViewById(R.id.SubmitGenre);
		GenreBundle=getIntent().getExtras();
			
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
		String sortOrder = MediaStore.Audio.Genres.NAME + " ASC";
		Cursor genrecursor;
		String[] proj1 = {MediaStore.Audio.Genres.NAME, MediaStore.Audio.Genres._ID};
		genrecursor=managedQuery(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,proj1,null, null, sortOrder);
		
		
		
	//	Removes duplicate Genres
		columnArray3 = new ArrayList<String>();
		   
		   
		   for(genrecursor.moveToFirst(); genrecursor.moveToNext(); genrecursor.isAfterLast()) {
		       columnArray3.add(genrecursor.getString(genrecursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME)));
		   }
		   
		   
		 //
		   for(int i=1;i<columnArray3.size();i++){
			   
			   if(columnArray3.get(i-1).equals(columnArray3.get(i))){
				   columnArray3.remove(i-1);
			   }
			
		   }
		
		
		
		
		
		
//----------------------------------------------------------------------------------------------------
		

		 String[] projGenreSongs = {MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DATA};   
			
			
			if(genrecursor.moveToFirst()){
				
					do{
						Mindex =  genrecursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME);
						
						
							
						
							uri = MediaStore.Audio.Genres.Members.getContentUri("external", Long.parseLong(genrecursor.getString(genrecursor.getColumnIndexOrThrow(MediaStore.Audio.Genres._ID))));
						
							GenreDelete	= managedQuery(uri, projGenreSongs, null, null, null);
							if(GenreDelete.getCount()==0){
								Log.d("Removed Genre", genrecursor.getString(index));
								deleteGenre.add(genrecursor.getString(index));
								
							}
							
							GenreDelete.moveToFirst();	
						

						
						
						
						
						
					}
					while(genrecursor.moveToNext());
					
			}
		
		
		
		
//----------------------------------------------------------------------------------------------------		
		
		   
		   //Remove empty genres
		   for(int t=0;t<deleteGenre.size();t++){
			   for(int y=0;y<columnArray3.size();y++){
				   if(deleteGenre.get(t).equals(columnArray3.get(y))){
					   columnArray3.remove(y);
				   }
			   }
		   }
			
			
		
		   
		   ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,columnArray3);
	       adp.setDropDownViewResource(R.layout.dropdown_item);
		   	Genrespinner.setAdapter(adp);
	        Genrespinner.setVisibility(View.VISIBLE);
		   
	        Genrespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					 genre = String.valueOf(Genrespinner.getSelectedItem());
					
				//	 genre="metal";
					 Toast.makeText(getApplicationContext(), genre, Toast.LENGTH_LONG).show();
					 
					 GenreBundle.putString("genreSelect", genre);
					 
					 
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			
	        
	        });
	        
	        
	        submit.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				//	Intent myIntent=new Intent(Select_Genre.this,)
					
					Intent myIntent=new Intent(Select_Genre.this, MainActivity.class);
					Intent myService=new Intent(Select_Genre.this,LocationService.class);
					
					myService.putStringArrayListExtra("GenreList", columnArray3);
					
					storeLatitude=GenreBundle.getDouble("latitude");
					storeLongitude=GenreBundle.getDouble("longitude");
					storeAddress=GenreBundle.getString("Address");
					
					
					
					
					
					
					SQLiteDatabase db=dbHelper.getWritableDatabase();
					ContentValues cv=new ContentValues();
					cv.put(DatabaseHelper.KEY_GENRE, genre);
					cv.put(DatabaseHelper.KEY_LATITUDE, storeLatitude);
					cv.put(DatabaseHelper.KEY_LONGITUDE, storeLongitude);
					cv.put(DatabaseHelper.KEY_ADDRESS, storeAddress);
					Log.d("Address stored", storeAddress);
					db.insert("songType", null, cv);
					if(db!=null){
						Toast.makeText(getApplicationContext(), "Database created", Toast.LENGTH_SHORT).show();
					}
					
					if(cv.getAsString(DatabaseHelper.KEY_GENRE)==null){
						Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
					}
					db.close();
					
				//	Toast.makeText(getApplicationContext(), "Preferences saved", Toast.LENGTH_LONG).show();
					
					startService(myService);
					startActivity(myIntent);
					
					
				}
			});
		   	   
	}
	

}
