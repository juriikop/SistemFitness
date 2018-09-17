package com.example.cronauto.params;

import android.content.Context;

import com.example.cronauto.R;
import com.example.cronauto.data.db.SQL;
import com.example.cronauto.data.network.Api;
import com.example.cronauto.data.network.GetData;
import com.example.cronauto.test_internrt.TestInternetProvider;

import fitness.sistem.compon.base.ListScreens;
import fitness.sistem.compon.interfaces_classes.Navigator;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.param.ParamModel;
import fitness.sistem.compon.param.ParamView;

public class CronListScreens  extends ListScreens {
    public CronListScreens(Context context) {
        super(context);
    }

    public final static String SETTINGS = "settings", DRAWER = "drawer", SPLASH = "splash",
            MAIN = "main", INTRO = "intro", AUTH = "auth", LOGIN_REGISTER = "login_register",
            AUTH_LOGIN = "auth_login", AUTH_REGISTER = "auth_register", AUTH_FORGOT = "forgot",
            ORDER = "order", LIST_ORDER = "list_order", INDEX = "index", PRODUCT_LIST = "product_list",
            CATALOG = "catalog", ORDER_LOG = "order_log", ORDER_LOG_FILTER = "order_log_filter",
            NOVELTIES = "novelties", EXTRA_BONUS = "extra_bonus";

    @Override
    public void initScreen() {
        activity(SPLASH, R.layout.activity_splash)
                .addComponentSplash(null, AUTH, MAIN);

        activity(AUTH, R.layout.activity_auth)
                .fragmentsContainer(R.id.content_frame, LOGIN_REGISTER);

        fragment(LOGIN_REGISTER, R.layout.fragment_login_register)
                .addComponent(TC.PAGER_F, new ParamView(R.id.pager,
                        new String[] {AUTH_LOGIN, AUTH_REGISTER})
                        .setTab(R.id.tabs, R.array.auth_tab_name));

//        fragment(AUTH_LOGIN, R.layout.fragment_login)
//                .addNavigator(new Navigator().add(R.id.done, MAIN));

        fragment(AUTH_LOGIN, R.layout.fragment_login)
                .addNavigator(new Navigator().add(R.id.forgot, AUTH_FORGOT))
                .addComponent(TC.PANEL_ENTER, null, new ParamView(R.id.panel),
                        new Navigator()
                                .add(R.id.done, ViewHandler.TYPE.CLICK_SEND,
                                        new ParamModel(ParamModel.POST, Api.LOGIN, "login,password"),
                                        actionsAfterResponse()
                                                .preferenceSetToken("key")
//                                        .preferenceSetName("phone")
                                                .startScreen(MAIN)
                                                .back(),
                                        false, R.id.login, R.id.password));

        fragment(AUTH_FORGOT, R.layout.fragment_forgot);

        fragment(AUTH_REGISTER, R.layout.fragment_register);

        activity(MAIN, R.layout.activity_main)
                .addDrawer(R.id.drawer, new int[] {R.id.content_frame, R.id.left_drawer},
                        new String[] {"", DRAWER});

        fragment(DRAWER, R.layout.fragment_drawer)
                .addMenu(new ParamModel(new GetData()), new ParamView(R.id.recycler,
                        new int[]{R.layout.item_menu, R.layout.item_menu_select,
                                R.layout.item_menu_divider, R.layout.item_menu_enabled}));

        fragment(INDEX, R.layout.fragment_index)
                .addComponent(TC.PAGER_F, new ParamView(R.id.pager,
                        new String[] {NOVELTIES, EXTRA_BONUS, CATALOG})
                        .setTab(R.id.tabs, R.array.index_tab_name));

        fragment(NOVELTIES, R.layout.fragment_novelties);

        fragment(EXTRA_BONUS, R.layout.fragment_extra_bonus);

        fragment(CATALOG, R.layout.fragment_catalog)
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.CATALOG_0)
                                .updateDB(SQL.CATALOG_TAB, Api.DB_CATALOG, SQL.dayMillisecond,
                                        "catalog_id,ID;parent_id,IBLOCK_SECTION_ID;catalog_name,NAME"),
                        new ParamView(R.id.recycler, "type", new int[]{R.layout.item_catalog_type_1,
                                R.layout.item_catalog_type_2, R.layout.item_catalog_type_3})
                                .expanded(R.id.expand, R.id.expand, new ParamModel()), null);

        activity(PRODUCT_LIST, R.layout.activity_product_list)
                .addRecognizeVoiceComponent(R.id.microphone, R.id.search);

        fragment(ORDER_LOG, R.layout.fragment_order_log)
                .addNavigator(new Navigator().add(R.id.filter, ORDER_LOG_FILTER)
                        .add(R.id.back, ViewHandler.TYPE.OPEN_DRAWER))
                .addComponent(TC.RECYCLER, new ParamModel(Api.ORDER_LOG).internetProvider(TestInternetProvider.class),
                new ParamView(R.id.recycler, "type", new int[]{R.layout.item_order_log_order,
                        R.layout.item_order_log_product, R.layout.item_order_log_amount})
                        .expanded(R.id.expand, R.id.expand, "listProduct"), null);

        fragment(ORDER_LOG_FILTER, R.layout.fragment_orderlog_filter).animate(AS.RL);

        super.initScreen();
    }
}
