package fitness.sistem.compon.interfaces_classes;

import fitness.sistem.compon.json_simple.ListRecords;

public interface IDbListener {
    public void onResponse(IBase iBase, ListRecords listRecords, String table, String nameAlias);
}
