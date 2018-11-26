package fitness.sistem.compon.db;
// Підтримує роботу зі зберіганням дат оновленння баз даних
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import fitness.sistem.compon.tools.ComponPrefTool;

public class UpdateDB {
    Map<String, Long> mapUpdateTab;

    public UpdateDB() {
        String st = ComponPrefTool.getUpdateDBDate().replace("{","").replace("}","");
        if (st.length() > 0) {
            mapUpdateTab = new HashMap<>();
            String[] r = st.split(",");
            int ik = r.length;
            if (ik > 0) {
                for (int i = 0; i < ik; i++) {
                    String[] L = r[i].trim().split("=");
                    mapUpdateTab.put(L[0], Long.valueOf(L[1]));
                }
            }
        } else {
            mapUpdateTab = new HashMap<>();
        }
    }

    public void add(String table, long date) {
        mapUpdateTab.put(table, date);
        ComponPrefTool.setUpdateDBDate(mapUpdateTab.toString());
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
