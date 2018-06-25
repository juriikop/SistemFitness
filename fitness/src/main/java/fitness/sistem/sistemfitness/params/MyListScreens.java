package fitness.sistem.sistemfitness.params;

import android.content.Context;
import fitness.sistem.compon.base.ListScreens;
import fitness.sistem.compon.interfaces_classes.Navigator;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.param.ParamModel;
import fitness.sistem.compon.param.ParamView;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.network.Api;
import fitness.sistem.sistemfitness.network.TestInternetProvider;

public class MyListScreens extends ListScreens {

    public MyListScreens(Context context) {
        super(context);
    }

    @Override
    public void initScreen() {
        activity(context.getString(R.string.splash), R.layout.activity_splash)
                .addComponentSplash(context.getString(R.string.tutorial),
                        context.getString(R.string.auth), context.getString(R.string.main));

        activity(context.getString(R.string.tutorial), R.layout.activity_intro)
                .addComponent(TC.INTRO, new ParamModel(Api.INTRO)
                                .internetProvider(TestInternetProvider.class),
                        new ParamView(R.id.pager, R.layout.item_intro)
                                .setIndicator(R.id.indicator)
                                .setFurtherBtn(R.id.skip, R.id.contin, R.id.start),
                        new Navigator().add(context.getString(R.string.auth)));

        activity(context.getString(R.string.auth), R.layout.activity_auth)
                .fragmentsContainer(R.id.content_frame, context.getString(R.string.auth_phone));

        fragment(context.getString(R.string.auth_phone), R.layout.fragment_auth_phone)
                .addComponent(TC.PANEL_ENTER, null, new ParamView(R.id.panel),
                        new Navigator().add(R.id.done, ViewHandler.TYPE.CLICK_SEND,
                                new ParamModel(ParamModel.POST, Api.LOGIN_PHONE, "phone"),
                                actionsAfterResponse().startScreen(context.getString(R.string.auth_code)),
                                true, R.id.phone));
//
//        fragment(context.getString(R.string.auth_code), R.layout.fragment_auth_code)
//                .addComponent(ParamComponent.TC.PANEL_ENTER, null, new ParamView(R.id.panel),
//                        new Navigator().add(R.id.done, ViewHandler.TYPE.CLICK_SEND,
//                                new ParamModel(ParamModel.POST, Api.LOGIN_CODE, "phone,code"),
//                                actionsAfterResponse()
//                                        .preferenceSetToken("token")
////                                        .preferenceSetName("phone")
//                                        .startScreen(context.getString(R.string.main))
//                                        .back(),
//                                true, R.id.code));
//
//        activity(context.getString(R.string.main), R.layout.activity_fuel)
//                .fragmentsContainer(R.id.content_frame, context.getString(R.string.tickets))
//                .addNavigator(new Navigator().add(R.id.radio1, context.getString(R.string.tickets))
//                        .add(R.id.radio2, context.getString(R.string.map))
////                        .add(R.id.radio3, context.getString(R.string.calculator))
//                        .add(R.id.radio5, context.getString(R.string.profile)));
//
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
//        fragment(context.getString(R.string.map), R.layout.fragment_map)
//                .addComponent(ParamComponent.TC.SPINNER, new ParamModel(Api.NETWORKS_ALL).changeNameField("id", "network"),
//                        new ParamView(R.id.spinner, "type", new int[] {R.layout.spinner_drop_map, R.layout.spinher_drop_begin_map},
//                                new int[] {R.layout.spinher_hider_map}), null, 0, FuelMoreWork.class)
//                .addComponentMap(R.id.map, new ParamModel(Api.MARKER_MAP, "network").changeNameField("network.icon", "icon_1"),
//                        new ParamMap(true)
//                                .coordinateValue(50.0276271, 36.2237879)
//                                .markerImg(R.drawable.tab_map_green, Constants.MARKER_NAME_NUMBER,
//                                        R.drawable.marker_map, R.drawable.marker_map)
//                                .markerClick(R.id.infoWindow), new Navigator().add(R.id.contin, ViewHandler.TYPE.MAP_ROUTE),
//                        R.id.spinner);
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
