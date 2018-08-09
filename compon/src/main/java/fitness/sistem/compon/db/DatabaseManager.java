package fitness.sistem.compon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import fitness.sistem.compon.base.BaseDB;
import fitness.sistem.compon.interfaces_classes.DescriptTableDB;
import fitness.sistem.compon.interfaces_classes.ParamDB;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;

public class DatabaseManager extends BaseDB {


    private Context context;
    public ParamDB paramDB;
    public DBHelper dbHelper;
    private int mOpenCounter;
    public SQLiteDatabase mDatabase;

    public DatabaseManager(Context context, ParamDB paramDB) {
        this.context = context;
        this.paramDB = paramDB;
        dbHelper = new DBHelper(context);
    }

    @Override
    public void post(String sql, Record record) {
        openDatabase();
        ContentValues cv = new ContentValues();
        for (Field f : record) {
            cv.put(f.name, (String) f.value);
        }
        long rowID = mDatabase.insert(sql, null, cv);
        Log.d("QWERT", "row inserted, ID = " + rowID);
        closeDatabase();
    }

    @Override
    public ListRecords get(String sql) {
        return null;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if(mOpenCounter == 1) {
            mDatabase = dbHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if(mOpenCounter == 0) {
            if (mDatabase != null && mDatabase.isOpen()) {
                mDatabase.close();
            }
        }
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, paramDB.nameDB, null, paramDB.versionDB);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("QWERT", "--- onCreate database ---");
            for (DescriptTableDB dt : paramDB.listTables) {
                db.execSQL("create table " + dt.nameTable + " (" + dt.descriptTable + ");");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }



}
