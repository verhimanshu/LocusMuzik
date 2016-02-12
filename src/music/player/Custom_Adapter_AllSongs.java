package music.player;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class Custom_Adapter_AllSongs extends SimpleCursorAdapter {

	
    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
	private final LayoutInflater inflater;
	
	public Custom_Adapter_AllSongs(Context context,int layout, Cursor c,String[] from,int[] to) {
		super(context, layout, c, from, to);
		this.layout=layout;
        this.mContext = context;
        this.inflater=LayoutInflater.from(context);
        this.cr=c;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		super.bindView(view, context, cursor);
		
		
	//	view=inflater.inflate(layout, null, false);
		TextView titleS=(TextView)view.findViewById(R.id.TitleSong);
		TextView artistS=(TextView)view.findViewById(R.id.Artist);
		int Title_index;
		int Artist_index;
		
		
			
			Title_index=cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
			Artist_index=cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
			titleS.setText(cursor.getString(Title_index));
			artistS.setText(cursor.getString(Artist_index));
			
//			titleS.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Antiqua.ttf"));
//			artistS.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Antiqua.ttf"));
			/*titleS.setTextSize(25);
			artistS.setTextSize(20);*/
			
			
			
//------------------------------------------------------------------------------------------------
			
			
			

			
			
//-------------------------------------------------------------------------------------------			
		 if(cursor.getPosition()==AllSongs.songPosition){
			if(AllSongs.isSongPaused==1){
				titleS.setBackgroundColor(Color.WHITE);
				artistS.setBackgroundColor(Color.WHITE);
				titleS.setTextColor(Color.parseColor("#3d75ae"));
				artistS.setTextColor(Color.parseColor("#3d75ae"));
				
			 
			}
			else if(AllSongs.isSongPaused==0){
				titleS.setBackgroundColor(Color.parseColor("#3d75ae"));
				 artistS.setBackgroundColor(Color.parseColor("#3d75ae"));
				 titleS.setTextColor(Color.WHITE);
				 artistS.setTextColor(Color.WHITE);	
			}
			else{
				titleS.setBackgroundColor(Color.TRANSPARENT);
				 artistS.setBackgroundColor(Color.TRANSPARENT);
				 titleS.setTextColor(Color.WHITE);
				 artistS.setTextColor(Color.WHITE);
			}
			 
		 }
		 else{
			 titleS.setBackgroundColor(Color.TRANSPARENT);
			 artistS.setBackgroundColor(Color.TRANSPARENT);
			 titleS.setTextColor(Color.WHITE);
			 artistS.setTextColor(Color.WHITE);
		 }
		 
		 
		 
	}

	


}
