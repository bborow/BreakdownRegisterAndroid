package pl.edu.uj.matinf.parkitna.breakdownregister.database;

import java.util.ArrayList;

import pl.edu.uj.matinf.parkitna.breakdownregister.model.Breakdown;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseAdapter {

	private static final String DEBUG_TAG = "BreakdownRegister@DatabaseAdapter";
	
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "BreakdownRegister.db";
	private static final String DB_BREAKDOWN_REGISTER_TABLE_NAME = "Breakdowns";
		
	private final int MAX_PHOTO_COUNT = 3;
	
	private enum KEYS{
		Id, Identifier, Title, Description, State, AddedDate, SolutionDate,
		Longitude, Latitude, PhotoPath1, PhotoPath2, PhotoPath3, ModifiedAt
	}
	
	public static final String KEY_ID = "_id";
	public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	
	public static final String KEY_IDENTIFIER = "identifier";
	public static final String IDENTIFIER_OPTIONS = "TEXT NOT NULL";
	
	public static final String KEY_TITLE = "title";
	public static final String TITLE_OPTIONS = "TEXT NOT NULL";
	
	public static final String KEY_DESCRIPTION = "description";
	public static final String DESCRIPTION_OPTIONS = "TEXT NOT NULL";
			
	public static final String KEY_STATE = "state";
	public static final String STATE_OPTIONS = "TEXT NOT NULL";
	
	public static final String KEY_ADDED_DATE = "entry_date";
	public static final String ADDED_DATE_OPTIONS = "INTEGER NOT NULL";
	
	public static final String KEY_SOLUTION_DATE = "solution_date";
	public static final String SOLUTION_DATE_OPTIONS = "INTEGER"; 
	
	public static final String KEY_LONGITUDE = "longitude";
	public static final String LONGITUDE_OPTIONS = "REAL";
	
	public static final String KEY_LATITUDE = "latitude";
	public static final String LATITUDE_OPTIONS = "REAL";
	
	public static final String KEY_PHOTO_PATH_1 = "photo_1";
	public static final String PHOTO_PATH_1_OPTIONS = "TEXT DEFAULT NULL";
	
	public static final String KEY_PHOTO_PATH_2 = "photo_2";
	public static final String PHOTO_PATH_2_OPTIONS = "TEXT DEFAULT NULL";
	
	public static final String KEY_PHOTO_PATH_3 = "photo_3";
	public static final String PHOTO_PATH_3_OPTIONS = "TEXT DEFAULT NULL";
	
	public static final String KEY_MODIFIED_AT = "modified_at";
	public static final String MODIFIED_AT_OPTIONS = "REAL";
	
	private static final String DB_CREATE_TABLE = 
			"CREATE TABLE " + DB_BREAKDOWN_REGISTER_TABLE_NAME + "( " +
			KEY_ID + " " + ID_OPTIONS + ", " +
			KEY_IDENTIFIER + " " + IDENTIFIER_OPTIONS + ", " +
			KEY_TITLE + " " + TITLE_OPTIONS + ", " +
			KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS + ", " +
			KEY_STATE + " " + STATE_OPTIONS + ", " +
			KEY_ADDED_DATE + " " + ADDED_DATE_OPTIONS + ", " +
			KEY_SOLUTION_DATE + " " + SOLUTION_DATE_OPTIONS + ", " +
			KEY_LONGITUDE + " " + LONGITUDE_OPTIONS + ", " +
			KEY_LATITUDE + " " + LATITUDE_OPTIONS + ", " +
			KEY_PHOTO_PATH_1 + " " + PHOTO_PATH_1_OPTIONS + ", " +
			KEY_PHOTO_PATH_2 + " " + PHOTO_PATH_2_OPTIONS + ", " +
			KEY_PHOTO_PATH_3 + " " + PHOTO_PATH_3_OPTIONS + ", " +
			KEY_MODIFIED_AT + " " + MODIFIED_AT_OPTIONS +
			");";
	
	private static final String DROP_BREAKDOWN_REGISTER_TABLE = 
			"DROP TABLE IF EXISTS " + DB_BREAKDOWN_REGISTER_TABLE_NAME;
	
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
			Log.d(DEBUG_TAG, "Table " + DB_BREAKDOWN_REGISTER_TABLE_NAME + " ver." + DB_VERSION + " created");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_BREAKDOWN_REGISTER_TABLE);
			
			Log.d(DEBUG_TAG, "Database updating...");
			Log.d(DEBUG_TAG, "Table " + DB_BREAKDOWN_REGISTER_TABLE_NAME + " updated from ver." + oldVersion + " to ver." + newVersion);
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
		Cursor cursor = db.query(DB_BREAKDOWN_REGISTER_TABLE_NAME, columns, null, null, null, null, null);
		if(cursor != null && cursor.moveToFirst()){
			return cursor.getString(0);
		}
		return null;
	}
	
	public Cursor selectAllbyCursor(){
		return db.query(DB_BREAKDOWN_REGISTER_TABLE_NAME, null, null, null, null, null, null);
	}
	
	public ArrayList<Breakdown> selectAll(){
		ArrayList<Breakdown> breakdownList = new ArrayList<Breakdown>();
		Cursor cursor = db.query(DB_BREAKDOWN_REGISTER_TABLE_NAME, null, null, null, null, null, null);
		if(cursor != null && cursor.moveToFirst()){
			do{
				Breakdown.Builder builder = new Breakdown.Builder();
				builder.id = cursor.getLong(KEYS.Id.ordinal());
				builder.identifier = cursor.getString(KEYS.Identifier.ordinal());
				builder.title = cursor.getString(KEYS.Title.ordinal());
				builder.description = cursor.getString(KEYS.Description.ordinal());
				builder.state = cursor.getString(KEYS.State.ordinal());
				builder.addedDateInMilis = cursor.getLong(KEYS.AddedDate.ordinal());
				builder.solutionDateInMilis = cursor.getLong(KEYS.SolutionDate.ordinal());
				builder.longitude = cursor.getDouble(KEYS.Longitude.ordinal());
				builder.latitude = cursor.getDouble(KEYS.Latitude.ordinal());
				builder.modifiedAt = cursor.getLong(KEYS.ModifiedAt.ordinal());
				
				int firstPhotoPathPos = KEYS.PhotoPath1.ordinal();
				KEYS[] keys = KEYS.values();
				
				ArrayList<String> photoPathList = new ArrayList<String>();
				
				for(int i = firstPhotoPathPos; i < firstPhotoPathPos + MAX_PHOTO_COUNT; i++){
					String photoPath = cursor.getString(keys[i].ordinal());
					if(photoPath != null)
						photoPathList.add(photoPath);
				}
				builder.photoPathList = photoPathList;
			} 
			while(cursor.moveToNext());
		}
		return breakdownList;
	}
	
	public int deleteBreakdown(long id){
		String whereClause = KEY_ID + " = " + id;
		return db.delete(DB_BREAKDOWN_REGISTER_TABLE_NAME, whereClause, null);
	}
}
