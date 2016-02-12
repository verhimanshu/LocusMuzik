package music.player;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;

import android.widget.ListAdapter;
import android.widget.ListView;

public class PreferencesVIew extends ListActivity {

	
	DatabaseHelper dbHelper=new DatabaseHelper(this);
	SQLiteDatabase db;
	Context context=this;
	Cursor c;
	String[] dataHolder; 
	int[] ListHolder;
	ListAdapter simcr;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allsongs);
		
		db=dbHelper.getReadableDatabase();
		c= db.rawQuery("SELECT * FROM songType", null);
		dataHolder= new String[] {DatabaseHelper.KEY_GENRE,DatabaseHelper.KEY_ADDRESS};
		ListHolder=new int[] {R.id.TitleSong,R.id.Artist};
		simcr = new SimpleCursorAdapter(this, R.layout.music_items, c, dataHolder,ListHolder);
		setListAdapter(simcr);
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position,final long id) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDelete=new AlertDialog.Builder(context);
		
		alertDelete.setTitle("Delete Genre!!");
		alertDelete.setMessage("Delete Genre").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dbHelper.delete(id);
				Cursor c= db.rawQuery("SELECT * FROM songType", null);
				
				String[] cl=new String[] {DatabaseHelper.KEY_GENRE,DatabaseHelper.KEY_ADDRESS};
				int[] b=new int[] {R.id.TitleSong,R.id.Artist};
				simcr=new SimpleCursorAdapter(getApplicationContext(), R.layout.music_items, c, cl, b);
				
				setListAdapter(simcr);
				
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		AlertDialog alertDialog= alertDelete.create();
		alertDialog.show();
	}

}
