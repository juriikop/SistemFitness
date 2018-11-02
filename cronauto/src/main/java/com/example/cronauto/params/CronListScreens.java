package com.example.cronauto.params;

import android.content.Context;
import android.util.Log;

import com.example.cronauto.R;
import com.example.cronauto.activity.AddProductActivity;
import com.example.cronauto.data.db.SQL;
import com.example.cronauto.data.network.Api;
import com.example.cronauto.data.network.GetData;
import fitness.sistem.compon.base.ListScreens;
import fitness.sistem.compon.interfaces_classes.Multiply;
import fitness.sistem.compon.interfaces_classes.Navigator;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.json_simple.JsonSimple;
import fitness.sistem.compon.param.ParamModel;
import fitness.sistem.compon.param.ParamView;

import static fitness.sistem.compon.interfaces_classes.ViewHandler.TYPE_PARAM_FOR_SCREEN.RECORD;

public class CronListScreens  extends ListScreens {
    public CronListScreens(Context context) {
        super(context);
    }

    public final static String SETTINGS = "settings", DRAWER = "drawer", SPLASH = "splash",
            MAIN = "main", INTRO = "intro", AUTH = "auth", LOGIN_REGISTER = "login_register",
            AUTH_LOGIN = "auth_login", AUTH_REGISTER = "auth_register", AUTH_FORGOT = "forgot",
            ORDER = "order", LIST_ORDER = "list_order", INDEX = "index", PRODUCT_LIST = "product_list",
            CATALOG = "catalog", ORDER_LOG = "order_log", ORDER_LOG_HISTORY = "order_log_history",
            ORDER_PRODUCT = "order_product",
            NOVELTIES = "novelties", EXTRA_BONUS = "extra_bonus", PRODUCT_DESCRIPT = "product_descript",
            ADD_PRODUCT = "add_product", EDIT_ORDER = "edit_order", BARCODE = "barcode",
            DESCRIPT = "descript", CHARACTERISTIC = "characteristic";

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

        fragment(CATALOG, R.layout.fragment_catalog)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.OPEN_DRAWER))
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.CATALOG_0)
                                .updateDB(SQL.CATALOG_TAB, Api.DB_CATALOG, SQL.dayMillisecond,
                                        SQL.CATALOG_ALIAS),
                        new ParamView(R.id.recycler, "expandedLevel", new int[]{R.layout.item_catalog_type_1,
                                R.layout.item_catalog_type_2, R.layout.item_catalog_type_3})
                                .expanded(R.id.expand, R.id.expand, new ParamModel(ParamModel.GET_DB, SQL.CATALOG, "catalog_id")),
                        new Navigator().add(0, PRODUCT_LIST, RECORD));

        fragment(NOVELTIES, R.layout.fragment_novelties);

        fragment(EXTRA_BONUS, R.layout.fragment_extra_bonus)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.OPEN_DRAWER))
                .addRecognizeVoiceComponent(R.id.microphone, R.id.search)
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_E_BONUS)
                                .updateDB(SQL.PRODUCT_TAB, Api.DB_PRODUCT, SQL.dayMillisecond, SQL.PRODUCT_ALIAS),
                        new ParamView(R.id.recycler, R.layout.item_product_list),
                new Navigator().add(0, PRODUCT_DESCRIPT, RECORD)
                        .add(R.id.add, ADD_PRODUCT, RECORD))
                .addSearchComponent(R.id.search, new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_SEARCH, "product_name"),
                new ParamView(R.id.recycler), null, false);

        fragment(NOVELTIES, R.layout.fragment_novelties)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.OPEN_DRAWER))
                .addRecognizeVoiceComponent(R.id.microphone, R.id.search)
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_NOVELTIES)
                                .updateDB(SQL.PRODUCT_TAB, Api.DB_PRODUCT, SQL.dayMillisecond, SQL.PRODUCT_ALIAS),
                        new ParamView(R.id.recycler, R.layout.item_product_list),
                        new Navigator().add(0, PRODUCT_DESCRIPT, RECORD)
                                .add(R.id.add, ADD_PRODUCT, RECORD))
                .addSearchComponent(R.id.search, new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_SEARCH, "product_name"),
                        new ParamView(R.id.recycler), null, false);

        activity(PRODUCT_LIST, R.layout.activity_product_list).animate(AS.RL)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK)
                        .add(R.id.barcode, BARCODE, actionsAfterResponse().updateDataByModel(R.id.recycler,
                                new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_BARCODE, "barcode_scanner"))))
                .addRecognizeVoiceComponent(R.id.microphone, R.id.search)
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_QUERY_ARRAY, "catalog_id")
                        .updateDB(SQL.PRODUCT_TAB, Api.DB_PRODUCT, SQL.dayMillisecond, SQL.PRODUCT_ALIAS),
                        new ParamView(R.id.recycler, R.layout.item_product_list)
                                .visibilityManager(visibility(R.id.bonus, "extra_bonus")),
                        new Navigator().add(0, PRODUCT_DESCRIPT, RECORD)
                                .add(R.id.add, ADD_PRODUCT, RECORD))
                .addSearchComponent(R.id.search, new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_SEARCH, "product_name"),
                        new ParamView(R.id.recycler), null, false);

        activity(BARCODE, R.layout.activity_barcode).animate(AS.RL)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK).add(R.id.apply,
                        ViewHandler.TYPE.RESULT_PARAM, "barcode_scanner"))
                .addBarcodeComponent(R.id.barcode_scanner, R.id.result_scan, R.id.repeat);

        fragment(ORDER_LOG, R.layout.fragment_order_log)
                .addNavigator(new Navigator().add(R.id.history, ORDER_LOG_HISTORY)
                        .add(R.id.back, ViewHandler.TYPE.OPEN_DRAWER))
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.ORDER_LIST),
                        new ParamView(R.id.recycler, R.layout.item_order_log_ord),
                        new Navigator().add(0, ORDER_PRODUCT, RECORD));

        activity(ORDER_PRODUCT, R.layout.activity_order_product, "%1$s", "orderName").animate(AS.RL)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK))
                .addPlusMinus(R.id.count, R.id.plus, R.id.minus, new Multiply(R.id.amount, "price", "amount"))
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_IN_ORDER, "orderId").row("row"),
                        new ParamView(R.id.recycler, R.layout.item_order_log_product))
                .addTotalComponent(R.id.total, R.id.recycler, R.id.count, null, "count");

//        fragment(ORDER_LOG, R.layout.fragment_order_log)
//                .addNavigator(new Navigator().add(R.id.history, ORDER_LOG_HISTORY)
//                        .add(R.id.back, ViewHandler.TYPE.OPEN_DRAWER))
//                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.ORDER_LIST),
//                new ParamView(R.id.recycler, "type", new int[]{R.layout.item_order_log_order,
//                        R.layout.item_order_log_product, R.layout.item_order_log_amount})
//                        .expanded(R.id.expand, R.id.expand,
//                                new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_IN_ORDER, "orderId").row("row")),
//                        new Navigator().add(R.id.edit, EDIT_ORDER, RECORD)
//                                .add(R.id.done, ViewHandler.TYPE.CLICK_SEND,
//                                new ParamModel(ParamModel.POST, Api.ORDER_ADD, "orderName,status,comment,payBonus,date")));

        activity(EDIT_ORDER, R.layout.activity_edit_order);

        fragment(ORDER_LOG_HISTORY, R.layout.fragment_orderlog_history).animate(AS.RL)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK));

        activity(PRODUCT_DESCRIPT, R.layout.activity_product_descript, "%1$s", "catalog_name").animate(AS.RL)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK))
                .addComponent(TC.PANEL, new ParamModel(ParamModel.ARGUMENTS),
                        new ParamView(R.id.name_panel))
                .addComponent(TC.PAGER_F, new ParamView(R.id.pager,
                        new String[] {DESCRIPT, CHARACTERISTIC})
                        .setTab(R.id.tabs, R.array.descript_tab_name));

        fragment(DESCRIPT, R.layout.fragment_descript)
                .addComponent(TC.PANEL, new ParamModel(ParamModel.GET_DB, SQL.PRODUCT_ID, "product_id"),
                        new ParamView(R.id.panel).visibilityManager(visibility(R.id.bonus_img, "extra_bonus")),
                        new Navigator().add(R.id.add, ADD_PRODUCT, RECORD))
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.ANALOG_ID_PRODUCT,"product_id")
                                .updateDB(SQL.ANALOG_TAB, Api.ANALOG, SQL.dayMillisecond, SQL.ANALOG_ALIAS),
                        new ParamView(R.id.recycler, R.layout.item_product_list).setSplashScreen(R.id.not_analog),
                        new Navigator().add(0, PRODUCT_DESCRIPT, RECORD)
                                .add(R.id.add, ADD_PRODUCT, RECORD).add(0, ViewHandler.TYPE.BACK));;

        fragment(CHARACTERISTIC, R.layout.fragment_characteristic)
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.PROPERTY_ID_PRODUCT,"product_id")
                                .updateDB(SQL.PROPERTY_TAB, Api.PROPERTY, SQL.dayMillisecond, SQL.PROPERTY_ALIAS),
                        new ParamView(R.id.recycler, "2", new int[] {R.layout.item_property, R.layout.item_property_1}));

        activity(ADD_PRODUCT, AddProductActivity.class).animate(AS.RL)
                .addPlusMinus(R.id.count, R.id.plus, R.id.minus, new Multiply(R.id.amount, "price"))
                .addComponent(TC.PANEL_ENTER, new ParamModel(ParamModel.ARGUMENTS),
                        new ParamView(R.id.panel), new Navigator()
                                .add(R.id.add, ViewHandler.TYPE.CLICK_SEND,
                                new ParamModel(ParamModel.POST_DB, SQL.PRODUCT_ORDER, SQL.PRODUCT_ORDER_PARAM),
                                actionsAfterResponse().showComponent(R.id.inf_add_product, "orderName"), false))
                .addComponent(TC.RECYCLER, new ParamModel(ParamModel.GET_DB, SQL.ORDER_LIST),
                        new ParamView(R.id.recycler, "status",
                                new int[] {R.layout.item_order_log, R.layout.item_order_log_select}).selected(),
                        new Navigator().add(0, ViewHandler.TYPE.SET_PARAM));

        super.initScreen();
    }
}
