package fitness.sistem.sistemfitness.params;

import android.content.Context;
import fitness.sistem.compon.base.ListScreens;
import fitness.sistem.compon.components.Menu;
import fitness.sistem.compon.interfaces_classes.Navigator;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.param.ParamMap;
import fitness.sistem.compon.param.ParamModel;
import fitness.sistem.compon.param.ParamView;
import fitness.sistem.compon.tools.Constants;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.more_work.FitnessProcessing;
import fitness.sistem.sistemfitness.network.Api;
import fitness.sistem.sistemfitness.network.GetData;
import fitness.sistem.sistemfitness.network.TestInternetProvider;

public class MyListScreens extends ListScreens {

    public MyListScreens(Context context) {
        super(context);
    }

    public final static String LANGUAGE = "language", SETTINGS = "settings", DRAWER = "drawer",
            SPLASH = "splash", MAIN = "main", INTRO = "intro", AUTH = "auth",
            AUTH_PHONE = "auth_phone", AUTH_CODE = "auth_code", AUTH_REGISTER = "auth_register",
            CLUBS = "clubs", ADD_CLUB = "addClub", MAP = "map";

    public static String ACTUAL_CLUB = "actual_club";

    @Override
    public void initScreen() {
        activity(SPLASH, R.layout.activity_splash)
                .addComponentSplash(INTRO,
//                        AUTH,
                        null, MAIN);

        activity(INTRO, R.layout.activity_intro)
                .addComponent(TC.INTRO, new ParamModel(new GetData()),
//                .addComponent(TC.INTRO, new ParamModel(Api.INTRO)
//                                .internetProvider(TestInternetProvider.class),
                        new ParamView(R.id.pager, R.layout.item_intro)
                                .setIndicator(R.id.indicator)
                                .setFurtherBtn(R.id.skip, R.id.contin, R.id.start),
                        new Navigator().add(AUTH));

        activity(AUTH, R.layout.activity_auth).animate(AS.RL)
                .fragmentsContainer(R.id.content_frame, AUTH_PHONE);

        fragment(AUTH_PHONE, R.layout.fragment_auth_phone)
                .addNavigator(new Navigator().add(R.id.register, AUTH_REGISTER)
                        .add(R.id.back, ViewHandler.TYPE.BACK))
                .addComponent(TC.PANEL_ENTER, null, new ParamView(R.id.panel),
                        new Navigator()
                                .add(R.id.done, ViewHandler.TYPE.CLICK_SEND,
                                        new ParamModel(ParamModel.POST, Api.LOGIN_PHONE, "phone"),
                                        actionsAfterResponse().startScreen(AUTH_CODE),
                                        true, R.id.phone));

        fragment(AUTH_REGISTER, R.layout.fragment_register).animate(AS.RL)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK))
                .addComponent(TC.PANEL_ENTER, null, new ParamView(R.id.panel),
                        new Navigator()
                                .add(R.id.done_register, ViewHandler.TYPE.CLICK_SEND,
                                        new ParamModel(ParamModel.POST, Api.REGISTER,
                                                "phone,surname,name,patronymic,email"),
                                        actionsAfterResponse().startScreen(AUTH_CODE),false,
                                        R.id.phone, R.id.surname, R.id.name, R.id.patronymic, R.id.email));



        fragment(AUTH_CODE, R.layout.fragment_auth_code)
                .addComponent(TC.PANEL_ENTER, null, new ParamView(R.id.panel),
                        new Navigator().add(R.id.done, ViewHandler.TYPE.CLICK_SEND,
                                new ParamModel(ParamModel.POST, Api.LOGIN_CODE, "phone,code"),
                                actionsAfterResponse()
                                        .preferenceSetToken("token")
//                                        .preferenceSetName("phone")
                                        .startScreen(MAIN)
                                        .back(),
                                true, R.id.code));

        activity(MAIN, R.layout.activity_main)
                .addDrawer(R.id.drawer, new int[] {R.id.content_frame, R.id.left_drawer},
                        new String[] {"", DRAWER});

        fragment(DRAWER, R.layout.fragment_drawer)
                .addComponent(TC.PANEL_MULTI, new ParamModel(getProfile()),
                        new ParamView(R.id.panel, R.layout.drawer_header_main, R.layout.drawer_header_not),
                        new Navigator().add(R.id.enter, AUTH)
                                .add(R.id.enter, ViewHandler.TYPE.CLOSE_DRAWER))
                .addMenu(new ParamModel(new GetData()), new ParamView(R.id.recycler,
                        new int[]{R.layout.item_menu, R.layout.item_menu_select, R.layout.item_menu_divider, R.layout.item_menu_enabled}));

        fragment(CLUBS, R.layout.fragment_clubs)
                .addNavigator(new Navigator().add(R.id.addclub, ADD_CLUB)
                        .add(R.id.back, ViewHandler.TYPE.OPEN_DRAWER))
                .addComponent(TC.RECYCLER, new ParamModel(Api.CLUBS)
                        .internetProvider(TestInternetProvider.class), new ParamView(R.id.recycler, R.layout.item_clubs),
                        new Navigator().add(0, ViewHandler.TYPE.CLICK_VIEW),
                        0, FitnessProcessing.class).actualReceiver(ACTUAL_CLUB);

        fragment(SETTINGS, R.layout.fragment_settings, FitnessProcessing.class)
                .addNavigator(new Navigator()
                        .add(R.id.back, ViewHandler.TYPE.OPEN_DRAWER)
                        .add(R.id.language, LANGUAGE)
                        .add(R.id.language, ViewHandler.TYPE.RECEIVER, Constants.CHANGE_LOCALE));

        fragment(LANGUAGE, R.layout.fragment_language).animate(AS.RL)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK))
                .addComponent(TC.RECYCLER, new ParamModel(new GetData()), new ParamView(R.id.recycler, R.layout.item_language),
                        new Navigator().add(0, ViewHandler.TYPE.BROADCAST, Constants.CHANGE_LOCALE)
                                .add(0, ViewHandler.TYPE.BACK));

        activity(ADD_CLUB, R.layout.activity_add_club).animate(AS.RL)
                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK))
                .addSearchComponent(R.id.city,
                        new ParamModel(Api.SEARCH_CITY).internetProvider(TestInternetProvider.class),
                        new ParamView(R.id.recycler, R.layout.item_search_city), null, true)
                .addSearchComponent(R.id.club,
                        new ParamModel(Api.SEARCH_CLUB).internetProvider(TestInternetProvider.class),
                        new ParamView(R.id.recyclerClub, R.layout.item_search_club),
                        new Navigator().add(0, ViewHandler.TYPE.BROADCAST, ACTUAL_CLUB)
                                .add(0, ViewHandler.TYPE.BACK), false);

//        fragment(context.getString(R.string.tickets), R.layout.fragment_tickets, context.getString(R.string.my_tickets))
//                .addNavigator(new Navigator().add(R.id.question, context.getString(R.string.help)))
//                .addComponent(ParamComponent.TC.PAGER_F, new ParamView(R.id.pager,
//                        new String[] {context.getString(R.string.active_tickets), context.getString(R.string.archive_tickets)})
//                        .setTab(R.id.tabs, R.array.tab_tickets));
//
//        fragment(context.getString(R.string.active_tickets), R.layout.fragment_active)
//                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.TICKETS_ACTIVE),
//                        new ParamView(R.id.recycler, "type",
//                                new int[] {R.layout.item_active_tickets, R.layout.item_active_tickets_begining,
//                                        R.layout.item_active_tickets_splash})
//                                .visibilityManager(visibility(R.id.expect_receive, "pending"),
//                                        visibility(R.id.confirm_payment, "awaits_payment"))
//                                .setSplashScreen(R.id.splash),
//                        new Navigator().add(R.id.confirm_payment, context.getString(R.string.awaits_payment))
//                                .add(R.id.expect_receive, context.getString(R.string.new_wait))
//                                .add(R.id.pay_tickets, context.getString(R.string.choice_fuel))
//                                .add(0, context.getString(R.string.infoTicket), ViewHandler.TYPE_PARAM_FOR_SCREEN.RECORD),
//                        0, FuelMoreWork.class);
//
//        fragment(context.getString(R.string.archive_tickets), R.layout.fragment_archive)
//                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.TICKETS_ARCHIVE),
//                        new ParamView(R.id.recycler, "type",
//                                new int[] {R.layout.item_active_tickets, R.layout.item_active_tickets_begining})
//                                .setSplashScreen(R.id.splash));
//
////        fragment(context.getString(R.string.mapF), MapFragment.class);
//        fragment(context.getString(R.string.profile), R.layout.fragment_profile)
//                .setDataParam(R.id.phone, "phone", 1);
//
        fragment(MAP, R.layout.fragment_map)
                .addComponentMap(R.id.map, new ParamModel(),
                        new ParamMap(true)
                                .coordinateValue(50.0276271, 36.2237879), null, 0);
//
//        activity(context.getString(R.string.help), R.layout.activity_help, Constants.AnimateScreen.RL)
//                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK)
//                                            .add(R.id.call_operator, getString(R.string.choice_fuel)));
//
//        activity(context.getString(R.string.infoTicket), R.layout.activity_info_ticket)
//                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK))
//                .addComponent(ParamComponent.TC.PANEL, new ParamModel(ParamModel.ARGUMENTS),
//                        new ParamView(R.id.panel));
//
//        activity(context.getString(R.string.new_wait), R.layout.activity_new_wait)
//                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK)
//                        .add(R.id.question, context.getString(R.string.help)))
//                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.TICKETS_ACTIVE)
//                                .filter(new Filters(new FilterParam("status", equally, "pending"))),
//                        new ParamView(R.id.recycler, R.layout.item_active_tickets));
//
//        activity(context.getString(R.string.awaits_payment), R.layout.activity_awaits_payment)
//                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK)
//                        .add(R.id.question, context.getString(R.string.help)))
//                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.TICKETS_ACTIVE),
//                        new ParamView(R.id.recycler, R.layout.item_awaits_payment),
//                        null, 0, FuelMoreWork.class);
//
//        activity(getString(R.string.choice_fuel), R.layout.activity_choice_fuel)
//                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.NETWORKS),
//                        new ParamView(R.id.recycler, "type",
//                                new int[] {R.layout.item_choice_fuel, R.layout.item_choice_fuel_net}),
//                        new Navigator().add(0, context.getString(R.string.tickets_buy), ViewHandler.TYPE_PARAM_FOR_SCREEN.RECORD),
//                        0, FuelMoreWork.class);
//
//
//        activity(context.getString(R.string.tickets_buy), R.layout.activity_tickets_buy, FitnessProcessing.class)
//                .addNavigator(new Navigator().add(R.id.back, ViewHandler.TYPE.BACK).add(R.id.panel, ViewHandler.TYPE.BACK))
//                .addComponent(ParamComponent.TC.PANEL, new ParamModel(ParamModel.ARGUMENTS),
//                        new ParamView(R.id.panel))
//                .addComponent(ParamComponent.TC.RECYCLER, new ParamModel(Api.NETWORK_ID, "idNetwork")
//                                .typeParam(ParamModel.TypeParam.SLASH),
//                        new ParamView(R.id.recycler, R.layout.item_tickets_buy),
//                        new Navigator().add(R.id.plus, ViewHandler.TYPE.CLICK_VIEW).add(R.id.minus, ViewHandler.TYPE.CLICK_VIEW),
//                        0, FuelMoreWork.class)
//                .addTotalComponent(R.id.total, R.id.recycler, showManager(visibility(R.id.total_panel, "amount"),
//                        enabled(R.id.contin, "amount")), "cost", "amount");
//
////        fragment(context.getString(R.string.calculator), R.layout.activity_calculator1)
////                .addNavigator(new Navigator().add(R.id.first_point, context.getString(R.string.search_departure)));
//
//        activity(context.getString(R.string.search_departure), R.layout.activity_search_departure, Constants.AnimateScreen.BT)
//                .addSearchComponent(R.id.search, new ParamModel(),
//                        new ParamView(R.id.recycler, R.layout.item_search_city), null);

        super.initScreen();
    }
}
