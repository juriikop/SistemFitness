package fitness.sistem.sistemfitness.network;

import fitness.sistem.compon.base.BaseActivity;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.Menu;
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
                case MyListScreens.CREATE_CONTENT: return setContentMenu();
            }
        }
        return null;
    }

    private Field setContentMenu() {
        ListRecords records = new ListRecords();
        Field field = new Field("", Field.TYPE_LIST_FIELD, records);
        records.add(newMenu(0, activity.getString(R.string.menu_content_title_header), activity.getString(R.string.menu_content_type_header)));
        records.add(newMenu(1, activity.getString(R.string.menu_content_title_title), activity.getString(R.string.menu_content_type_title)));
        records.add(newMenu(2, activity.getString(R.string.menu_content_title_subtitle), activity.getString(R.string.menu_content_type_subtitle)));
        records.add(newMenu(3, activity.getString(R.string.menu_content_title_text), activity.getString(R.string.menu_content_type_text)));
        records.add(newMenu(4, activity.getString(R.string.menu_content_title_comment), activity.getString(R.string.menu_content_type_comment)));
        records.add(newMenu(5, activity.getString(R.string.menu_content_title_marker), activity.getString(R.string.menu_content_type_marker)));
        records.add(newMenu(6, activity.getString(R.string.menu_content_title_photo), activity.getString(R.string.menu_content_type_photo)));
        records.add(newMenu(7, activity.getString(R.string.menu_content_title_video), activity.getString(R.string.menu_content_type_video)));
        records.add(newMenu(8, activity.getString(R.string.menu_content_title_gallery), activity.getString(R.string.menu_content_type_gallery)));
        records.add(newMenu(9, activity.getString(R.string.menu_content_title_persone), activity.getString(R.string.menu_content_type_persone)));
        records.add(newMenu(10, activity.getString(R.string.menu_content_title_st_icon), activity.getString(R.string.menu_content_type_st_icon)));
        records.add(newMenu(11, activity.getString(R.string.menu_content_title_line), activity.getString(R.string.menu_content_type_line)));

        return field;
    }

    private Field setMenu() {
        Menu menu = new Menu()
                .item(R.drawable.targets, activity.getString(R.string.m_goals), MyListScreens.CREATE_CONTENT)
                .item(R.drawable.targets, "Qwerty Asdfg", MyListScreens.MAP)
                .divider()
                .item(R.drawable.menu_clubs, activity.getString(R.string.m_clubs), MyListScreens.CLUBS, true)
                .divider()
                .item(R.drawable.menu_clubs, activity.getString(R.string.order), MyListScreens.ORDER)
                .item(R.drawable.menu_clubs, activity.getString(R.string.list_order), MyListScreens.LIST_ORDER)
                .divider()
                .item(R.drawable.menu_settings, activity.getString(R.string.m_settings), MyListScreens.SETTINGS);
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

    private Record newMenu(int id, String title, String type) {
        Record rec = new Record();
        rec.add(new Field("typeId", Field.TYPE_INTEGER, id));
        rec.add(new Field("title", Field.TYPE_STRING, title));
        rec.add(new Field("type", Field.TYPE_STRING, type));
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
