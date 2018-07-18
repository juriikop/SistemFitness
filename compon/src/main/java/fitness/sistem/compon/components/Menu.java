package fitness.sistem.compon.components;

import android.util.Log;

import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;

public class Menu extends Field{

    public ListRecords menuList;
    public int menuStart;
    public enum TYPE {NORMAL, SELECT, DIVIDER, ENABLED};

    public Menu() {
        name = "menu";
        type = TYPE_LIST_FIELD;
        menuList = new ListRecords();
        value = menuList;
        menuStart = -1;
    }

    public Menu addItem(int icon, String title, String nameFragment, TYPE type) {
        return addItem(icon, title, nameFragment, type, -1);
    }

    public Menu addItem(int icon, String title, String nameFragment) {
        return addItem(icon, title, nameFragment, -1, false);
    }

    public Menu addItem(int icon, String title, String nameFragment, int badge) {
        addItem(icon, title, nameFragment, badge, false);
        return this;
    }

    public Menu addItem(int icon, String title, String nameFragment, boolean start) {
        return addItem(icon, title, nameFragment, -1, start);
    }

    public Menu addDivider(){
        Record item = new Record();
        item.add(new Field("select", Field.TYPE_INTEGER, 2));
        menuList.add(item);
        return this;
    }

    public Menu addItem(int icon, String title, String nameFragment, TYPE type, int badge) {
        Record item = new Record();
        item.add(new Field("icon", Field.TYPE_INTEGER, icon));
        item.add(new Field("name", Field.TYPE_STRING, title));
        item.add(new Field("nameFunc", Field.TYPE_STRING, nameFragment));
        item.add(new Field("badge", Field.TYPE_INTEGER, badge));
        item.add(new Field("select", Field.TYPE_INTEGER, type.ordinal()));
        menuList.add(item);
        return this;
    }

    public Menu addItem(int icon, String title, String nameFragment, int badge, boolean start) {
        Record item = new Record();
        item.add(new Field("icon", Field.TYPE_INTEGER, icon));
        item.add(new Field("name", Field.TYPE_STRING, title));
        item.add(new Field("nameFunc", Field.TYPE_STRING, nameFragment));
        item.add(new Field("badge", Field.TYPE_INTEGER, badge));
        if (start && menuStart < 0) {
            item.add(new Field("select", Field.TYPE_INTEGER, 1));
            menuStart = menuList.size();
        } else {
            item.add(new Field("select", Field.TYPE_INTEGER, 0));
        }
        menuList.add(item);
        return this;
    }
}
