package music.player;



import java.io.IOException;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.app.ListActivity;

import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Artists extends ListActivity implements AudioManager.OnAudioFocusChangeListener{

	
	

	
     private Cursor musiccursor;
     int music_column_index;
     public static int ArtistPosition=-3;
     int ResumePosition=0;
     public static int isArtistPaused=-1;  //true=0 false=1 notInsideActivity=-1
     MediaPlayer mMediaPlayer=MainActivity.mPlayer;
     String filename;
 //    ListAdapter adapter;
     int RememberArtistPosition;
     int count,index,top;
     ListView lv;
     View v;
     AudioManager am; 
     int result;
     Custom_Adapter_Artists adapter12;
	
     
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allsongs);
		
		
		
//-------------------------------------------------------------------------------------------------
		am =   (AudioManager) getSystemService(AUDIO_SERVICE);
		result=am.requestAudioFocus(this,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
		
		
		
		
		
//----------------------------------------------------------------------------------------------	
		String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC"; //----->Use for artists
		

		lv=getListView();
		
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
		
		 String[] projection = {
		            MediaStore.Audio.Media._ID,
		            MediaStore.Audio.Media.ARTIST,
		            MediaStore.Audio.Media.TITLE,
		            MediaStore.Audio.Media.DATA,
		            MediaStore.Audio.Media.DISPLAY_NAME,
		            MediaStore.Audio.Media.DURATION,
		            
		    };
		    //query 
		  
		    musiccursor = this.managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,sortOrder);
		    music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

		    count = musiccursor.getCount();
		    int a[]= new int[]{R.id.TitleSong,R.id.Artist};
		    

		//    adapter = new SimpleCursorAdapter(this,R.layout.music_items, musiccursor, new String[]{MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST} ,a);
		   adapter12=new Custom_Adapter_Artists(this, R.layout.music_items, musiccursor, new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST},a);
		    
		
		    
		    lv.setAdapter(adapter12); 
		    
		    mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					
					try {
						if(ArtistPosition<=musiccursor.getCount()-2){
						mMediaPlayer.reset();
						ArtistPosition=ArtistPosition+1;
						playSong(ArtistPosition);
						adapter12.notifyDataSetChanged();
						}
						else{
							ArtistPosition=0;
							mMediaPlayer.reset();
							adapter12.notifyDataSetChanged();
							playSong(ArtistPosition);
						}
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				}
			});
		    
		    
	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		index = lv.getFirstVisiblePosition();
		v = lv.getChildAt(0);
		top = (v == null) ? 0 : v.getTop();
		if(isArtistPaused==0){
			ArtistPosition=-2;
		}
		
		
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lv.setSelectionFromTop(index, 0);
		if(isArtistPaused==0){
			ArtistPosition=-2;
			adapter12.notifyDataSetChanged();
		}
		else{
			ArtistPosition=RememberArtistPosition;
			adapter12.notifyDataSetChanged();
		}
		
	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
		
		
	}



	@Override
	 	protected void onListItemClick(ListView l, View v, int position, long id) {
	 		// TODO Auto-generated method stub
				
		if(result==AudioManager.MODE_IN_CALL){
			mMediaPlayer.stop();
		}
		
		if(result!=AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
			Toast.makeText(getApplicationContext(), "Request not Granted", Toast.LENGTH_LONG).show();
			
		}
		else{
			 
	
				  //Resume Song
				  if(ResumePosition>0 && ArtistPosition==position)
				  {
				
						  mMediaPlayer.start();
						  Toast.makeText(getApplicationContext(), "Song resumed", Toast.LENGTH_SHORT).show();
						  ResumePosition=0;
						 isArtistPaused=1; 
					 
				  }
				  else
				  {
			 
			 
					  //To pause song
				  	if(mMediaPlayer.isPlaying())
				  	{
				  		if(ArtistPosition==position)
				  		{
				  			
				  			isArtistPaused=0;
				  			adapter12.notifyDataSetChanged();
				  			pauseSong(position);
				  			ResumePosition=mMediaPlayer.getCurrentPosition();
				  			Toast.makeText(getApplicationContext(), "Song paused", Toast.LENGTH_SHORT).show();
				  			
				  		}
				  		else
				  		{
				  			
				  			//If different song is selected	
				  	
				  			
				  			
					  			mMediaPlayer.reset();
					  			ArtistPosition=position;
					  			ResumePosition=0;
					  			RememberArtistPosition=ArtistPosition;
					  			isArtistPaused=1;
					  			try {
									playSong(position);
								} 
					  			catch (IllegalStateException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
					  			catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					  		
					  	}
				  			
				  	}
				  	else
				  	{
					  		try {
					  			//Playing for first time or after pausing another song
					  			
					  			
					  			mMediaPlayer.reset();
					  			ArtistPosition=position;
					  			ResumePosition=0;
					  			RememberArtistPosition=ArtistPosition;
					  			isArtistPaused=1;
					  			playSong(position);
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			  		}
		  
				  }
				  adapter12.notifyDataSetChanged();
		}
			
		
		  
	}
	
	
	
	

	private void pauseSong(int position) {
		// TODO Auto-generated method stub
		
        musiccursor.moveToPosition(position);
        filename = musiccursor.getString(music_column_index);
        
        mMediaPlayer.pause();
    //    imageView.setImageResource(R.drawable.play);
	}


	private void playSong(int position) throws IllegalStateException, IOException {
		// TODO Auto-generated method stub\
		
		
         musiccursor.moveToPosition(position);
         filename = musiccursor.getString(music_column_index);
         
         mMediaPlayer.setDataSource(filename);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			
		//	imageView.setImageResource(R.drawable.pause);
		//	play.setImageResource(R.drawable.play);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflate=getMenuInflater();
		menuInflate.inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_settings:
			Intent myIntent =new Intent(Artists.this,HomeScreen.class);
			startActivity(myIntent);
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onAudioFocusChange(int focusChange) {
		// TODO Auto-generated method stub
		

	}	
		
		
		
		
	

}
