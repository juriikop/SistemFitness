package fitness.sistem.compon.db;

import java.util.HashMap;
import java.util.Map;

public class UpdateDB {
    Map<String, Long> mapUpdateTab;

    public UpdateDB() {
        mapUpdateTab = new HashMap<>();
    }

    public void add(String table, long date) {
        Long d = mapUpdateTab.get(table);
        if (d != null) {
            d = date;
        } else {
            mapUpdateTab.put(table, date);
        }
    }

    public long getDate(String table) {
        Long d = mapUpdateTab.get(table);
        if (d != null) {
            return d;
        } else {
            return 0;
        }
    }
}
