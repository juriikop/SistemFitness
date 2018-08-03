package fitness.sistem.compon.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import fitness.sistem.compon.interfaces_classes.DescriptTableDB;
import fitness.sistem.compon.interfaces_classes.ParamDB;
import fitness.sistem.compon.json_simple.ListRecords;

public class DataBase extends BaseDB {


    private Context context;
    public ParamDB paramDB;
    public SQLiteDatabase db;
    public DBHelper dbHelper;

    public DataBase(Context context, ParamDB paramDB) {
        this.context = context;
        this.paramDB = paramDB;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void post(String sql) {

    }

    @Override
    public ListRecords get(String sql) {
        return null;
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
