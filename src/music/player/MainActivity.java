package music.player;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	Bundle bun;
	TabHost tabHost;
	String genre;
	public static MediaPlayer mPlayer=new MediaPlayer();
	public static int songPosition;
	
	
    @SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bun=getIntent().getExtras();
        tabHost =getTabHost();
        
        
        
        
        //All Songs Tab
        Intent inte=new Intent().setClass(this, AllSongs.class);
        TabSpec tabAllSongs=tabHost.newTabSpec("All Songs").setIndicator("All Songs").setContent(inte);
        
        //Artist Tab
        Intent intArtist=new Intent().setClass(this, Artists.class);
        TabSpec tabArtist=tabHost.newTabSpec("Artists").setIndicator("Artists").setContent(intArtist);
        
        //Albums Tab
        Intent inteAlbums=new Intent().setClass(this, Albums.class);
        TabSpec tabAlbums=tabHost.newTabSpec("Albums").setIndicator("Albums").setContent(inteAlbums);
        
        //Location Tab
        Intent inteLoc=new Intent().setClass(this, LocationSong.class);

        
       if(bun!=null){
        genre=bun.getString("GenreFromService");
        Log.d("GenreName Tab Act", "Genre name" + genre);
        inteLoc.putExtra("Gen", genre);
      }
        
        
        TabSpec tabLoc=tabHost.newTabSpec("Location Wise").setIndicator("Location Wise").setContent(inteLoc);
       
        
        //#E3E4FA -Lavender color for selecting list
        
        //Add all tabs
        tabHost.addTab(tabAllSongs);
        tabHost.addTab(tabAlbums);
        tabHost.addTab(tabArtist);
        tabHost.addTab(tabLoc);
        
        //Sending data from tab activity to LocationActivity
        bun=getIntent().getExtras();
//        String genreName=bun.getString("GenreFromService");
        
        /*Intent x=new Intent(this,LocationService.class);
        x.putExtra("FromMain", "Rap");*/
        tabHost.setCurrentTab(0);
            
        
 //       //For unselected tab on starting
        
        for(int i=1;i<tabHost.getTabWidget().getChildCount();i++)
        {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff")); //unselected
            TextView tv1 = (TextView) tabHost.getTabWidget().findViewById(android.R.id.title); //Unselected Tabs
            tv1.setTextColor(Color.parseColor("#000000"));
            tv1.setTextSize(20);
            tv1.setTypeface(Typeface.createFromAsset(getAssets(), "Kingthings.ttf"));
        }
        
        
        
        
        
        //For current tab on starting
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#3d75ae"));
        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title);    
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTextSize(25);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "Kingthings.ttf"));
        
        
        
        
        
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				
				for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
		        {
		            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff")); //unselected
		            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
		        //    tv.setTextColor(Color.parseColor("#BBD9EE"));
		            tv.setTextColor(Color.parseColor("#000000"));
		            tv.setTypeface(Typeface.createFromAsset(getAssets(), "Kingthings.ttf"));
		            tv.setTextSize(20);
		        }
		        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#3d75ae")); // selected
		        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
		        tv.setTextColor(Color.parseColor("#ffffff"));
		        tv.setTextSize(25);
		        tv.setTypeface(Typeface.createFromAsset(getAssets(), "Kingthings.ttf"));
				
			}
		});
        
        
        
    }


    


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
