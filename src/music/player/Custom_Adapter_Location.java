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

public class Custom_Adapter_Location extends SimpleCursorAdapter {

	
    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
	private final LayoutInflater inflater;
	
	public Custom_Adapter_Location(Context context,int layout, Cursor c,String[] from,int[] to) {
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
			
			titleS.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Antiqua.ttf"));
			artistS.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "Antiqua.ttf"));
			/*titleS.setTextSize(25);
			artistS.setTextSize(20);*/
			
			
			
//------------------------------------------------------------------------------------------------
			
			
			
	
			
			
//-------------------------------------------------------------------------------------------			
		 if(cursor.getPosition()==LocationSong.LocationPosition){
			if(LocationSong.isLocationPaused==1){
				titleS.setBackgroundColor(Color.WHITE);
				artistS.setBackgroundColor(Color.WHITE);
				titleS.setTextColor(Color.parseColor("#3d75ae"));
				artistS.setTextColor(Color.parseColor("#3d75ae"));

			}
			else if(LocationSong.isLocationPaused==0){
				
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

	

	/*@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 View v = inflater.inflate(layout, null, false);
	     
	     v.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundColor(Color.BLUE);
			}
		});
	     bindView(v, mContext, cr);
	     return v;
		
	}*/ 

	/*@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		return inflater.inflate(R.layout.music_items, parent, false);
	}*/
	

}