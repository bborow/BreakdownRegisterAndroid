package pl.edu.uj.matinf.parkitna.breakdownregister.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseAdapter {

	private static final String DEBUG_TAG = "FailureNotifier";
	
	private static final int DB_VERSION = 3;
	private static final String DB_NAME = "FailureNotifier.db";
	private static final String DB_FAILURE_NOTIFIER_TABLE_NAME = "Failures";
		
	public static final String KEY_ID = "_id";
	public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	
	public static final String KEY_TITLE = "title";
	public static final String TITLE_OPTIONS = "TEXT NOT NULL";
	
	public static final String KEY_DESCRIPTION = "description";
	public static final String DESCRIPTION_OPTIONS = "TEXT NOT NULL";
			
	public static final String KEY_STATUS = "status";
	public static final String STATUS_OPTIONS = "TEXT NOT NULL DEFAULT 'Not solved'";
	
	public static final String KEY_ENTRY_DATE = "entry_date";
	public static final String ENTRY_DATE_OPTIONS = "INTEGER NOT NULL";
	
	public static final String KEY_SOLUTION_DATE = "solution_date";
	public static final String SOLUTION_DATE_OPTIONS = "INTEGER DEFAULT NULL"; 
	
	public static final String KEY_LONGITUDE = "longitude";
	public static final String LONGITUDE_OPTIONS = "TEXT";
	
	public static final String KEY_LATITUDE = "latitude";
	public static final String LATITUDE_OPTIONS = "TEXT";
	
	public static final String KEY_PHOTO_1 = "photo_1";
	public static final String PHOTO_1_OPTIONS = "TEXT DEFAULT NULL";
	
	public static final String KEY_PHOTO_2 = "photo_2";
	public static final String PHOTO_2_OPTIONS = "TEXT DEFAULT NULL";
	
	public static final String KEY_PHOTO_3 = "photo_3";
	public static final String PHOTO_3_OPTIONS = "TEXT DEFAULT NULL";
	
	private static final String[] KEYS_TO_PHOTOS = {KEY_PHOTO_1, KEY_PHOTO_2, KEY_PHOTO_3};
	private static final int NUMBER_OF_FIELDS = 10;
	
	private static final String DB_CREATE_TABLE = 
			"CREATE TABLE " + DB_FAILURE_NOTIFIER_TABLE_NAME + "( " +
			KEY_ID + " " + ID_OPTIONS + ", " +
			KEY_TITLE + " " + TITLE_OPTIONS + ", " +
			KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS + ", " +
			KEY_STATUS + " " + STATUS_OPTIONS + ", " +
			KEY_ENTRY_DATE + " " + ENTRY_DATE_OPTIONS + ", " +
			KEY_SOLUTION_DATE + " " + SOLUTION_DATE_OPTIONS + ", " +
			KEY_LONGITUDE + " " + LONGITUDE_OPTIONS + ", " +
			KEY_LATITUDE + " " + LATITUDE_OPTIONS + ", " +
			KEY_PHOTO_1 + " " + PHOTO_1_OPTIONS + ", " +
			KEY_PHOTO_2 + " " + PHOTO_2_OPTIONS + ", " +
			KEY_PHOTO_3 + " " + PHOTO_3_OPTIONS +
			");";
	
	private static final String DROP_APPLICATIONS_TABLE = 
			"DROP TABLE IF EXISTS " + DB_FAILURE_NOTIFIER_TABLE_NAME;
	
	private SQLiteDatabase db;
	private Context context;
	private DatabaseHelper dbHelper;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE_TABLE);
			
			Log.d(DEBUG_TAG, "Database creating...");
			Log.d(DEBUG_TAG, "Table " + DB_FAILURE_NOTIFIER_TABLE_NAME + " ver." + DB_VERSION + " created");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_APPLICATIONS_TABLE);
			
			Log.d(DEBUG_TAG, "Database updating...");
			Log.d(DEBUG_TAG, "Table " + DB_FAILURE_NOTIFIER_TABLE_NAME + " updated from ver." + oldVersion + " to ver." + newVersion);
			Log.d(DEBUG_TAG, "All data is lost.");
			
			onCreate(db);
		}
	}
	
	public DatabaseAdapter(Context context) {
		this.context = context;
	}
	
	public DatabaseAdapter open(){
		dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLException e) {
			db = dbHelper.getReadableDatabase();
		}
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
//	public long insert(Failure item){
//		ContentValues valuesToInsert = new ContentValues();
//		
//		valuesToInsert.put(KEY_TITLE, item.title);
//		valuesToInsert.put(KEY_DESCRIPTION, item.description);
//		if(item.status != null)
//			valuesToInsert.put(KEY_STATUS, item.status);
//		valuesToInsert.put(KEY_ENTRY_DATE, item.entryDate);
//		if(item.solutionDate != null)
//			valuesToInsert.put(KEY_SOLUTION_DATE, item.solutionDate);
//		valuesToInsert.put(KEY_LONGITUDE, item.longitude);
//		valuesToInsert.put(KEY_LATITUDE, item.latitude);
//		
//		int i = 0;
//		for(String path : item.pathToPhotos){
//			valuesToInsert.put(KEYS_TO_PHOTOS[i++], path);
//		}
//		
//		return db.insert(DB_FAILURE_NOTIFIER_TABLE_NAME, null, valuesToInsert);
//	}
	
//	public boolean update(Failure failuresListItem){
//		ContentValues valuesToUpdate = new ContentValues();
//		valuesToUpdate.put(KEY_TITLE, failuresListItem.title);
//		valuesToUpdate.put(KEY_DESCRIPTION, failuresListItem.description);
//		valuesToUpdate.put(KEY_STATUS, failuresListItem.status);
//		valuesToUpdate.put(KEY_SOLUTION_DATE, failuresListItem.solutionDate);
//		
//		int i = 0;
//		for(String photoPath : failuresListItem.pathToPhotos){
//			valuesToUpdate.put(KEYS_TO_PHOTOS[i++], photoPath);
//		}
//		while(i < KEYS_TO_PHOTOS.length){
//			valuesToUpdate.putNull(KEYS_TO_PHOTOS[i++]);
//		}
//		
//		String where = KEY_ID + " = " + failuresListItem.id;
//		return db.update(DB_FAILURE_NOTIFIER_TABLE_NAME, valuesToUpdate, where, null) > 0;
//	}
	
	
	
	public String select(){
		String[] columns = {KEY_TITLE};
		Cursor cursor = db.query(DB_FAILURE_NOTIFIER_TABLE_NAME, columns, null, null, null, null, null);
		if(cursor != null && cursor.moveToFirst()){
			return cursor.getString(0);
		}
		return null;
	}
	
	public Cursor selectAllbyCursor(){
		return db.query(DB_FAILURE_NOTIFIER_TABLE_NAME, null, null, null, null, null, null);
	}
	
//	public ArrayList<Failure> selectAll(){
//		ArrayList<Failure> failureItemsArrayList = new ArrayList<Failure>();
//		Cursor cursor = db.query(DB_FAILURE_NOTIFIER_TABLE_NAME, null, null, null, null, null, null);
//		if(cursor != null && cursor.moveToFirst()){
//			String[] fields = new String[NUMBER_OF_FIELDS - 3];
//			do{
//				int id = cursor.getInt(0);
//				for(int pos = 0; pos < NUMBER_OF_FIELDS - 3; pos++){
//					try{
//						fields[pos] = cursor.getString(pos + 1);
//					}catch(Exception e){
//						fields[pos] = "";
//					}
//				}
//				
//				ArrayList<String> pathToPhotos = new ArrayList<String>();
//				
//				for(int pos = NUMBER_OF_FIELDS - 2; pos < NUMBER_OF_FIELDS + 1; pos++){
//					String pathToPhoto = cursor.getString(pos);
//					if(pathToPhoto != null)
//						pathToPhotos.add(pathToPhoto);
//				}
//				
//				Log.d("pathToPhotos.size()@DatabaseAdapter", Integer.toString(pathToPhotos.size()));
//				
//				failureItemsArrayList.add(new Failure(Integer.toString(id), 
//						fields[0], fields[1], fields[2], 
//						fields[3], fields[4], fields[5], 
//						fields[6], pathToPhotos));
//			} 
//			while(cursor.moveToNext());
//		}
//		return failureItemsArrayList;
//	}
	
	public int deleteFailureItem(String id){
		String whereClause = KEY_ID + " = " + id;
		return db.delete(DB_FAILURE_NOTIFIER_TABLE_NAME, whereClause, null);
	}
}
