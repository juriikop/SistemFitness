package fitness.sistem.sistemfitness.network;

import android.util.Log;

import fitness.sistem.compon.base.BaseActivity;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.components.Menu;
import fitness.sistem.compon.interfaces_classes.DataFieldGet;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.params.MyListScreens;

public class GetData extends DataFieldGet {
    BaseActivity activity;
    @Override
    public Field getField(BaseComponent mComponent) {
        activity = mComponent.activity;
        if (mComponent.multiComponent.nameComponent != null) {
            switch (mComponent.multiComponent.nameComponent) {
                case MyListScreens.DRAWER: return setMenu();
                case MyListScreens.LANGUAGE: return setLanguage();
                case MyListScreens.INTRO: return setIntro();
            }
        }
        return null;
    }

    private Field setMenu() {
        Menu menu = new Menu()
                .addItem(R.drawable.targets, activity.getString(R.string.m_goals), "")
                .addItem(R.drawable.targets, "Qwerty Asdfg", "", Menu.TYPE.ENABLED)
                .addDivider()
                .addItem(R.drawable.menu_clubs, activity.getString(R.string.m_clubs), MyListScreens.CLUBS, true)
                .addDivider()
                .addItem(R.drawable.menu_settings, activity.getString(R.string.m_settings), MyListScreens.SETTINGS);
        return menu;
    }

    private Field setIntro() {
        ListRecords records = new ListRecords();
        Field field = new Field("", Field.TYPE_LIST_FIELD, records);
        records.add(newIntro(activity.getString(R.string.intro_img_0), activity.getString(R.string.intro_t_0), activity.getString(R.string.intro_0)));
        records.add(newIntro(activity.getString(R.string.intro_img_1), activity.getString(R.string.intro_t_1), activity.getString(R.string.intro_1)));
        records.add(newIntro(activity.getString(R.string.intro_img_2), activity.getString(R.string.intro_t_2), activity.getString(R.string.intro_2)));
        records.add(newIntro(activity.getString(R.string.intro_img_3), activity.getString(R.string.intro_t_3), activity.getString(R.string.intro_3)));
        return field;
    }

    private Record newIntro(String img, String title, String message) {
        Record rec = new Record();
        rec.add(new Field("message", Field.TYPE_STRING, message));
        rec.add(new Field("title", Field.TYPE_STRING, title));
        rec.add(new Field("img", Field.TYPE_STRING, img));
        return rec;
    }

    private Field setLanguage() {
        ListRecords records = new ListRecords();
        Field field = new Field("", Field.TYPE_LIST_FIELD, records);
        records.add(addLanguage("uk", activity.getString(R.string.uk)));
        records.add(addLanguage("ru", activity.getString(R.string.ru)));
        return field;
    }

    private Record addLanguage(String id, String name) {
        Record rec = new Record();
        rec.add(new Field("name_language", Field.TYPE_STRING, name));
        rec.add(new Field("id_language", Field.TYPE_STRING, id));
        return rec;
    }
}
