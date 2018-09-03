package fitness.sistem.compon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.BaseDB;
import fitness.sistem.compon.base.BasePresenter;
import fitness.sistem.compon.interfaces_classes.DescriptTableDB;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.IDbListener;
import fitness.sistem.compon.interfaces_classes.IPresenterListener;
import fitness.sistem.compon.interfaces_classes.ParamDB;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamModel;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_NONE;

public class DatabaseManager extends BaseDB {

    private Context context;
    public ParamDB paramDB;
    public DBHelper dbHelper;
    private int mOpenCounter;
    public SQLiteDatabase mDatabase;
//    String table;

    public DatabaseManager(Context context, ParamDB paramDB) {
        this.context = context;
        this.paramDB = paramDB;
        dbHelper = new DBHelper(context);
    }

    @Override
    public void remoteToLocale(IBase iBase, String url, String table, String nameAlias) {
        new RemoteToLocale(iBase, url, table, nameAlias, dbListener);
    }

    IDbListener dbListener = new IDbListener() {
        @Override
        public void onResponse(ListRecords listRecords, String table, String nameAlias) {
            insertListRecord(table, listRecords, nameAlias);
        }
    };

    @Override
    public void insertListRecord(String table, ListRecords listRecords) {
        insertListRecord(table, listRecords, null);
    }

    @Override
    public void insertListRecord(String table, ListRecords listRecords, String nameAlias) {
        Map<String, String> mapField = new HashMap<>();
        String[] columnNames = null;
        String[] aliasNames = null;
        openDatabase();
        Cursor c = mDatabase.rawQuery("SELECT * FROM " + table + " WHERE 0", null);
        try {
            columnNames = c.getColumnNames();
        } finally {
            c.close();
        }
        int jk = columnNames.length;
        if (columnNames != null) {
            aliasNames = new String[jk];
            for (int i = 0; i < jk; i++) {
                aliasNames[i] = columnNames[i];
            }
            if (nameAlias != null && nameAlias.length() > 0) {
                String[] na = nameAlias.split(";");
                for (int i = 0; i < na.length; i++) {
                    String[] na1 = na[i].split(",");
                    String name = na1[0];
                    for (int j = 0; j < jk; j++) {
                        if (aliasNames[j].equals(name)) {
                            aliasNames[j] = na1[1];
                            break;
                        }
                    }
                }
            }
            for (Record record : listRecords) {
                ContentValues cv = new ContentValues();
                for (int j = 0; j < jk; j++) {
                    Field f = record.getField(aliasNames[j]);
                    if (f != null) {
                        cv.put(columnNames[j], (String) f.value);
                    }
                }
                long rowID = mDatabase.insert(table, null, cv);
                Log.d("QWERT","rowID="+rowID);
            }
        }
        closeDatabase();
    }

    @Override
    public void insertRecord(String sql, Record record) {
        openDatabase();
        ContentValues cv = new ContentValues();
        for (Field f : record) {
            cv.put(f.name, (String) f.value);
        }
        long rowID = mDatabase.insert(sql, null, cv);
        closeDatabase();
    }

    @Override
    public void get(BaseComponent baseComponent, String[] param, IPresenterListener listener) {
        Log.d("QWERT","DatabaseManager SQL="+baseComponent.paramMV.paramModel.url);
        new GetDbPresenter(baseComponent, param, listener);
    }

    @Override
    public Field get(String sql, String[] param) {
        openDatabase();
        Cursor c = mDatabase.rawQuery(sql, param);
//        Cursor c = mDatabase.query(sql, null, null, null, null, null, null);
        ListRecords listRecords = null;
        if (c.moveToFirst()) {
            int countCol = c.getColumnCount();
            String[] nameColumn = c.getColumnNames();
            listRecords = new ListRecords();
            Record record;
            do {
                record = new Record();
                for (int i = 0; i < countCol; i++) {
                    switch (c.getType(i)) {
                        case Cursor.FIELD_TYPE_INTEGER :
                            record.add(new Field(nameColumn[i], Field.TYPE_LONG, c.getLong(i)));
                            break;
                        case Cursor.FIELD_TYPE_FLOAT :
                            record.add(new Field(nameColumn[i], Field.TYPE_DOUBLE, c.getFloat(i)));
                            break;
                        case Cursor.FIELD_TYPE_STRING :
                            record.add(new Field(nameColumn[i], Field.TYPE_STRING, c.getString(i)));
                            break;
                        default:
                            record.add(new Field(nameColumn[i], Field.TYPE_STRING, c.getString(i)));
                            break;
                    }
                }
                listRecords.add(record);
            } while (c.moveToNext());
        } else {
            Log.d("QWERT", "0 rows");
        }
        c.close();
        closeDatabase();
        return new Field("", Field.TYPE_LIST_RECORD, listRecords);
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
            for (DescriptTableDB dt : paramDB.listTables) {
                db.execSQL("create table " + dt.nameTable + " (" + dt.descriptTable + ");");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
