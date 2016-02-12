package music.player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	
	

	public static final String KEY_ID="_id"; 
	public static final String KEY_GENRE="genre";
	public static final String KEY_LATITUDE="latitude";
	public static final String KEY_LONGITUDE="longitude";
	public static final String  KEY_ADDRESS="address";
	public static final String TAG="DBadapter";
	public static final String DATABASE_TABLE="songType";
	public static final String DATABASE_NAME="Genres.db";
	public static final int DATABASE_VERSION=1;
	
	public static final String DATABASE_CREATE="CREATE TABLE songType (_id INTEGER PRIMARY KEY AUTOINCREMENT,genre TEXT NOT NULL,latitude DOUBLE NOT NULL,longitude DOUBLE NOT NULL,address TEXT NOT NULL);";
	
	private static SQLiteDatabase db;
	
	
	
	
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}





	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}





	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS songType");
		onCreate(db);
	}
	
	//Insert Table in Database
	public long insertTitle(String genre,Double longitude,double latitude,String address){
		
		ContentValues initialValues=new ContentValues();
		initialValues.put(KEY_GENRE, genre);
		initialValues.put(KEY_LATITUDE, latitude);
		initialValues.put(KEY_LONGITUDE, longitude);
		initialValues.put(KEY_ADDRESS, address);
		
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	//Retrive all titles
	
	public Cursor getAllTitles(){
		return db.query(DATABASE_TABLE, new String[]{
				KEY_ID,
				KEY_GENRE,
				KEY_LATITUDE,
				KEY_LONGITUDE,
				KEY_ADDRESS
				},
				null,
				null,
				null,
				null,
				null,
				null);
	}
		
	//Delete Row
	
	public void delete(long id){
		String gh=Integer.toString((int)id);
		getWritableDatabase().delete("songType", "_ID="+gh, null);
	}
	
	
}
