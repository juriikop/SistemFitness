package fitness.sistem.compon.base;

import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.IPresenterListener;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamModel;

public abstract class BaseDB {
    public abstract void insertRecord(String sql, Record record);
    public abstract void insertListRecord(String sql, ListRecords listRecords, String nameAlias);
    public abstract void insertListRecord(String sql, ListRecords listRecords);
    public abstract Field get(String sql, String[] param);
    public abstract void get(BaseComponent baseComponent, String[] param, IPresenterListener listener);
    public abstract void remoteToLocale(IBase iBase, String url, String table, String nameAlias);
}
