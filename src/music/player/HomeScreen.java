package music.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends Activity {

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		Button mediaplayer=(Button)findViewById(R.id.MusicPlayerScreen);
		Button loc=(Button)findViewById(R.id.LocMusic);
		Button seeGenre=(Button)findViewById(R.id.Preferences);
		
		
		seeGenre.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent=new Intent(HomeScreen.this,PreferencesVIew.class);
				startActivity(myIntent);
			}
		});
		
		
		mediaplayer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent=new Intent(HomeScreen.this,MainActivity.class);
				startActivity(myIntent);
			}
		});
		
		loc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent myIntent=new Intent(HomeScreen.this,LocationWise.class);
				startActivity(myIntent);
			}
		});
	}

}
