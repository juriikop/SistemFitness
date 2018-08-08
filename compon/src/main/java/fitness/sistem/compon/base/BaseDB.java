package fitness.sistem.compon.base;

import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;

public abstract class BaseDB {
    public abstract void post(String sql, Record record);
    public abstract ListRecords get(String sql);
}
