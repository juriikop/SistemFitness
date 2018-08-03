package fitness.sistem.compon.base;

import java.util.ArrayList;
import java.util.List;

import fitness.sistem.compon.json_simple.ListRecords;

public class DataBase extends BaseDB{

    public DataBase() {
        listTable = new ArrayList<>();
    }

    @Override
    public void createTable(List<String> listTable) {

    }

    @Override
    public void post(String sql) {

    }

    @Override
    public ListRecords get(String sql) {
        return null;
    }

    public void addTable(String table) {
        if (listTable == null) {
            listTable = new ArrayList<>();
        }
        listTable.add(table);
    }
}
