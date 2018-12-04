package fitness.sistem.compon.base;

import android.content.Context;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.components.MultiComponents;
import fitness.sistem.compon.interfaces_classes.ActionsAfter;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.interfaces_classes.Visibility;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.param.ParamModel;
import fitness.sistem.compon.tools.Constants;

import java.util.Map;

public class ListScreens <T>{
    protected ParamComponent.TC TC;
    protected Constants.AnimateScreen AS;
    protected ViewHandler.TYPE VH;
    protected int GET = ParamModel.GET, POST = ParamModel.POST,
            GET_DB = ParamModel.GET_DB, POST_DB = ParamModel.POST_DB, UPDATE_DB = ParamModel.UPDATE_DB,
            INSERT_DB = ParamModel.INSERT_DB, DEL_DB = ParamModel.DEL_DB, PARENT = ParamModel.PARENT,
            FIELD = ParamModel.FIELD, ARGUMENTS = ParamModel.ARGUMENTS,
            STRINGARRAY = ParamModel.STRINGARRAY, DATAFIELD = ParamModel.DATAFIELD;

    private Map<String, MultiComponents> MapScreen;
    protected Context context;
    protected ComponGlob componGlob;

    public void initScreen() {
        for (MultiComponents value : MapScreen.values()) {
            String par = value.getParamModel();
            if (par != null && par.length() > 0) {
                String[] param = par.split(Constants.SEPARATOR_LIST);
                int ik = param.length;
                for (int i = 0; i < ik; i++) {
                    componGlob.addParam(param[i]);
//                    ComponGlob.getInstance().addParam(param[i]);
                }
            }
        }
    }

    public Field getProfile() {
        return componGlob.profile;
    }

    public ListScreens(Context context) {
        componGlob = ComponGlob.getInstance();
        this.context = context;
    }

//    public String getString(int id) {
//        return context.getString(id);
//    }

    public void setMapScreen(Map<String, MultiComponents> MapScreen) {
        this.MapScreen = MapScreen;
    }

    protected MultiComponents fragment(String name, int layoutId, String title, String... args) {
        MultiComponents mc = new MultiComponents(name, layoutId, title, args);
        mc.typeView = MultiComponents.TYPE_VIEW.FRAGMENT;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents fragment(String name, int layoutId, Class<T> additionalWork) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.FRAGMENT;
        mc.additionalWork = additionalWork;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents fragment(String name, int layoutId) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.FRAGMENT;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents fragment(String name, Class customFragment) {
        MultiComponents mc = new MultiComponents(name, customFragment);
        mc.typeView = MultiComponents.TYPE_VIEW.CUSTOM_FRAGMENT;
        MapScreen.put(name, mc);
        return mc;
    }

//    protected MultiComponents customFragment(String name) {
//        MultiComponents mc = new MultiComponents(name);
//        MapScreen.put(name, mc);
//        return mc;
//    }

    protected MultiComponents activity(String name, Class customActivity) {
        MultiComponents mc = new MultiComponents(name, customActivity);
        mc.typeView = MultiComponents.TYPE_VIEW.CUSTOM_ACTIVITY;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents activity(String name, int layoutId, String title, String... args) {
        MultiComponents mc = new MultiComponents(name, layoutId, title, args);
        mc.typeView = MultiComponents.TYPE_VIEW.ACTIVITY;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents activity(String name, int layoutId) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.ACTIVITY;
        MapScreen.put(name, mc);
        return mc;
    }

    protected MultiComponents activity(String name, int layoutId, Class<T> additionalWork) {
        MultiComponents mc = new MultiComponents(name, layoutId);
        mc.typeView = MultiComponents.TYPE_VIEW.ACTIVITY;
        mc.additionalWork = additionalWork;
        MapScreen.put(name, mc);
        return mc;
    }

    public static ActionsAfter actionsAfter() {
        return new ActionsAfter();
    }

    public static Visibility[] showManager(Visibility ... args) {
        return args;
    }

    public static Visibility visibility(int viewId, String nameField) {
        return new Visibility(0, viewId, nameField);
    }

    public static Visibility enabled(int viewId, String nameField) {
        return new Visibility(1, viewId, nameField);
    }
}
