package fitness.sistem.compon.base;

import java.util.ArrayList;
import java.util.List;

import fitness.sistem.compon.json_simple.ListRecords;

public abstract class BaseDB {

    public List<String> listTable;

    public abstract void createTable(List<String> listTable);
    public abstract void post(String sql);
    public abstract ListRecords get(String sql);
}
