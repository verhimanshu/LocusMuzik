package music.player;

import java.io.IOException;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.Toast;

public class LocationSong extends ListActivity implements OnAudioFocusChangeListener {

	Bundle locationSong;
	static String GenreName;
	private Cursor musiccursor,GenreSongCursor,lastCursor;
    int music_column_index,index,Mindex;
    Uri uri;
    Bundle gen;
	
    public static int isLocationPaused=-1;
    
    public static int LocationPosition;
    int ResumePosition=0;
    MediaPlayer mMediaPlayer=MainActivity.mPlayer;
    String filename;
    ImageView imageView;
//    ListAdapter adapter;
    int count,top;
    ListView lv;
    View v;
    AudioManager am; 
    int result;
    Custom_Adapter_Location adapter12;
    
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allsongs);
		
		gen=getIntent().getExtras();
		if(gen!=null){
		GenreName=gen.getString("Gen");
		}
		else{
			GenreName=null;
		}
		
		am =   (AudioManager) getSystemService(AUDIO_SERVICE);
		result=am.requestAudioFocus(this,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);

		
	//	String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
		
		 String[] projGenre= {MediaStore.Audio.Genres.NAME,MediaStore.Audio.Genres._ID};
		    //query 
		  
		    musiccursor = this.managedQuery(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,projGenre,null,null,null);
		 //   music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
		    
		 String[] projGenreSongs = {MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DATA};   
		
		
		if(musiccursor.moveToFirst()){
			
				do{
					Mindex =  musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME);
					
					Log.d("Cursor Name", musiccursor.getString(Mindex));
					
					if(musiccursor.getString(Mindex).equals(GenreName)){
						uri = MediaStore.Audio.Genres.Members.getContentUri("external", Long.parseLong(musiccursor.getString(musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Genres._ID))));
					
						GenreSongCursor	= managedQuery(uri, projGenreSongs, null, null, null);
						if(GenreSongCursor.getCount()==0){
							Toast.makeText(getApplicationContext(), "No songs available!!", Toast.LENGTH_LONG).show();
						}
						for(int i=0;i<GenreSongCursor.getCount();i++){
							GenreSongCursor.moveToNext();
							index=GenreSongCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
						}
						
						/*if(GenreSongCursor.moveToLast()){
							do{
									index=GenreSongCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
									Log.d("Filepath in LocationSong:", GenreSongCursor.getString(index));
								
							}while(GenreSongCursor.moveToNext());
						}*/
						GenreSongCursor.moveToFirst();
					}
					
					else{
						
					}
					
					
				}
				while(musiccursor.moveToNext());
				
		}
		lv=getListView();
		

		int a[] = new int[]{R.id.TitleSong,R.id.Artist};
	//	ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.music_items, GenreSongCursor, new String[]{MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST},a);
		
		adapter12=new Custom_Adapter_Location(this, R.layout.music_items, GenreSongCursor, new String[]{MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST},a);
		lv.setAdapter(adapter12);
		
		//if genre song cursor is null
		
		if(GenreSongCursor==null){
			Toast.makeText(getApplicationContext(), "No songs!!", Toast.LENGTH_LONG).show();
			this.finish();
		}
		
		
		
		
		//on song completeion
		 mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					
					try {
						if(LocationPosition<=musiccursor.getCount()-2){
						mMediaPlayer.reset();
						LocationPosition=LocationPosition+1;
						playSong(LocationPosition);
						adapter12.notifyDataSetChanged();
						}
						else{
							LocationPosition=0;
							mMediaPlayer.reset();
							adapter12.notifyDataSetChanged();
							
							playSong(LocationPosition);
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
		if(isLocationPaused==0){
			LocationPosition=-2;
		}
	}






	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lv.setSelectionFromTop(index, 0);
		if(isLocationPaused==0){
			LocationPosition=-2;
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
		if(result!=AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
			Toast.makeText(getApplicationContext(), "Request not Granted", Toast.LENGTH_LONG).show();
		}
		else{
				  
		  //Resume Song
		  if(ResumePosition>0 && LocationPosition==position){
			  mMediaPlayer.start();
			  Toast.makeText(getApplicationContext(), "Song resumed", Toast.LENGTH_SHORT).show();
			  ResumePosition=0;
			  isLocationPaused=1;
			 
		  }
		  else{
			  
			  //To pause song
		  	if(mMediaPlayer.isPlaying()){
		  		if(LocationPosition==position){
		  			pauseSong(position);
		  			
		  			ResumePosition=mMediaPlayer.getCurrentPosition();
		  	//		play.setVisibility(0);
		  			Toast.makeText(getApplicationContext(), "Song paused", Toast.LENGTH_SHORT).show();
		  			isLocationPaused=0;
		  		}
		  		else{
		  			
		  			//If different song is selected
			  			mMediaPlayer.reset();
			  			LocationPosition=position;
			  			ResumePosition=0;
			  			isLocationPaused=1;
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
		  	else{
		  		try {
		  			//Playing for first time or after pausing another song
		  			mMediaPlayer.reset();
		  			LocationPosition=position;
		  			ResumePosition=0;
		  			isLocationPaused=1;
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
		  }
		adapter12.notifyDataSetChanged();
	 	}
	
	
	
	

	private void pauseSong(int position) {
		// TODO Auto-generated method stub
		
		GenreSongCursor.moveToPosition(position);
        filename = GenreSongCursor.getString(index);
        
        mMediaPlayer.pause();
    //    imageView.setImageResource(R.drawable.play);
	}


	private void playSong(int position) throws IllegalStateException, IOException {
		// TODO Auto-generated method stub\
		
		
		GenreSongCursor.moveToPosition(position);
         filename = GenreSongCursor.getString(index);
         Log.d("Filename selected:", filename);
         mMediaPlayer.setDataSource(filename);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			
		//	imageView.setImageResource(R.drawable.pause);
		//	play.setImageResource(R.drawable.play);
	}






	@Override
	public void onAudioFocusChange(int focusChange) {
		// TODO Auto-generated method stub
		
	}

	





	


	
	
	
	

}
