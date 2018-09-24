package com.example.cronauto.data.network;

import com.example.cronauto.R;
import com.example.cronauto.params.CronListScreens;

import fitness.sistem.compon.base.BaseActivity;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.components.Menu;
import fitness.sistem.compon.interfaces_classes.DataFieldGet;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;

public class GetData extends DataFieldGet {
    BaseActivity activity;
    @Override
    public Field getField(BaseComponent mComponent) {
        activity = mComponent.activity;
        if (mComponent.multiComponent.nameComponent != null) {
            switch (mComponent.multiComponent.nameComponent) {
                case CronListScreens.DRAWER: return setMenu();
            }
        }
        return null;
    }

    private Field setMenu() {
        Menu menu = new Menu()
                .item(R.drawable.list, activity.getString(R.string.m_catalog), CronListScreens.CATALOG, true)
                .item(R.drawable.user, activity.getString(R.string.m_profile), CronListScreens.NOVELTIES)
                .item(R.drawable.extrabonus, activity.getString(R.string.m_e_bonus), CronListScreens.EXTRA_BONUS)
                .item(R.drawable.newitem, activity.getString(R.string.m_new_items), CronListScreens.NOVELTIES)
                .item(R.drawable.present, activity.getString(R.string.m_present), CronListScreens.CATALOG)
                .item(R.drawable.shopping_cart, activity.getString(R.string.m_my_product), CronListScreens.CATALOG)
                .divider()
                .item(R.drawable.reader, activity.getString(R.string.m_log_orders), CronListScreens.ORDER_LOG)
                .item(R.drawable.pay, activity.getString(R.string.m_mutual), CronListScreens.CATALOG)
                .divider()
                .item(R.drawable.description, activity.getString(R.string.m_news), CronListScreens.CATALOG)
                .item(R.drawable.sale, activity.getString(R.string.m_bonus_system), CronListScreens.CATALOG)
                .item(R.drawable.information, activity.getString(R.string.m_how_to_buy), CronListScreens.CATALOG)
                .item(R.drawable.pin, activity.getString(R.string.m_contacts), CronListScreens.CATALOG);
        return menu;
    }

}
