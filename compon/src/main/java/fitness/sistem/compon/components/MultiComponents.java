package fitness.sistem.compon.components;

import android.util.Log;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.ICustom;
import fitness.sistem.compon.interfaces_classes.MoreWork;
import fitness.sistem.compon.interfaces_classes.Multiply;
import fitness.sistem.compon.interfaces_classes.Navigator;
import fitness.sistem.compon.interfaces_classes.SetData;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.interfaces_classes.Visibility;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.param.ParamMap;
import fitness.sistem.compon.param.ParamModel;
import fitness.sistem.compon.param.ParamView;
import fitness.sistem.compon.tools.Constants;

import java.util.ArrayList;
import java.util.List;

public class MultiComponents <T>{
    public String nameComponent;
    public List<ParamComponent> listComponents;
    public List<SetData> listSetData;
    public String title;
    public String[] args;
    public int fragmentLayoutId;
    public enum TYPE_VIEW {ACTIVITY, FRAGMENT, CUSTOM_FRAGMENT, CUSTOM_ACTIVITY};
    public TYPE_VIEW typeView;
    public Navigator navigator;
    public ICustom iCustom;
    public Class<T> customFragment;
    public Class<T> additionalWork;
    public MoreWork moreWork;
    public Constants.AnimateScreen animateScreen;

    public void setCustom(ICustom iCustom) {
        this.iCustom = iCustom;
        for (ParamComponent pc : listComponents) {
            pc.baseComponent.iCustom = iCustom;
        }
    }
    public MultiComponents(String name, int layoutId, String title, String... args) {
        this.title = title;
        this.args = args;
        this.nameComponent = name;
        this.fragmentLayoutId = layoutId;
        listComponents = new ArrayList<>();
    }

    public MultiComponents(String name, int layoutId) {
        this.title = "";
        this.args = null;
        this.nameComponent = name;
        this.fragmentLayoutId = layoutId;
        listComponents = new ArrayList<>();
    }

    public MultiComponents(String name) {
        this.title = "";
        this.args = null;
        this.nameComponent = name;
        typeView = TYPE_VIEW.CUSTOM_FRAGMENT;
        listComponents = new ArrayList<>();
    }

    public MultiComponents(String name, Class<T> customFragment) {
        this.nameComponent = name;
        this.customFragment = customFragment;
        listComponents = new ArrayList<>();
    }

    public T getCustomFragment() {
        try {
            return (T) customFragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    public MultiComponents animate(Constants.AnimateScreen animate) {
        animateScreen = animate;
        return this;
    }

    public void setName(String nameComponent) {
        this.nameComponent = nameComponent;
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView) {
        return addComponent(type, paramModel, paramView, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel) {
        return addComponent(type, paramModel, null, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView,
                                        Navigator navigator) {
        return addComponent(type, paramModel, paramView, navigator, 0, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type,
                                        ParamView paramView) {
        return addComponent(type, null, paramView, null, 0, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView,
                                        int eventComponent) {
        return addComponent(type, paramModel, paramView, null, eventComponent, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        int eventComponent) {
        return addComponent(type, paramModel, null, null, eventComponent, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView,
                                        Navigator navigator,
                                        int eventComponent) {
        return addComponent(type, paramModel, paramView, navigator, eventComponent, null);
    }

    public MultiComponents addComponent(ParamComponent.TC type, ParamModel paramModel,
                                        ParamView paramView,
                                        Navigator navigator,
                                        int eventComponent,
                                        Class<T> additionalWork) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = type;
        paramComponent.paramModel = paramModel;
        paramComponent.paramView = paramView;
        paramComponent.navigator = navigator;
        paramComponent.eventComponent = eventComponent;
        listComponents.add(paramComponent);
        paramComponent.nameParentComponent = nameComponent;
        paramComponent.additionalWork = additionalWork;
        return this;
    }

    public MultiComponents addMenu(ParamModel paramModel, ParamView paramView) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.MENU;
        paramComponent.paramModel = paramModel;
        paramComponent.paramView = paramView;
        paramComponent.paramView.maxItemSelect = -1;
        paramComponent.navigator = new Navigator().add("nameFunc");
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addComponentMap(int viewId, ParamModel paramModel, ParamMap paramMap,
                                           Navigator navigator, int eventComponent) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.MAP;
        paramComponent.paramView = new ParamView(viewId);
        paramComponent.paramModel = paramModel;
        paramComponent.navigator = navigator;
        paramComponent.eventComponent = eventComponent;
        paramComponent.paramMap = paramMap;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addComponentMap(int viewId, ParamMap paramMap) {
        return addComponentMap(viewId, null, paramMap, null, 0);
    }

    public MultiComponents addDrawer(int viewId, int[] containerId, String[] nameFragments) {
        return addComponent(ParamComponent.TC.DRAWER, new ParamView(viewId, nameFragments, containerId));
    }

    public MultiComponents addPlusMinus(int editId, int plusId, int minusId) {
        return addComponent(ParamComponent.TC.PLUS_MINUS, new ParamView(editId, plusId, minusId));
    }

    public MultiComponents addPlusMinus(int editId, int plusId, int minusId, Navigator navigator, Multiply... args) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.PLUS_MINUS;
        paramComponent.paramModel = null;
        paramComponent.paramView = new ParamView(editId, plusId, minusId);
        paramComponent.navigator = navigator;
        paramComponent.multiplies = args;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addDateDiapasonComponent(int viewId, int from, int before) {
        return addComponent(ParamComponent.TC.DATE_DIAPASON, new ParamView(viewId, from, before));
    }

    public MultiComponents addBarcodeComponent(int viewId, int viewCode, int repeat) {
        return addComponent(ParamComponent.TC.BARCODE, new ParamView(viewId, viewCode, repeat));
    }

    public MultiComponents addSearchComponent(int viewIdEdit, ParamModel paramModel, ParamView paramView,
                                              Navigator navigator, boolean hideRecycler) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.SEARCH;
        paramComponent.paramModel = paramModel;
        paramComponent.viewSearchId = viewIdEdit;
        paramComponent.paramView = paramView;
        paramComponent.eventComponent = viewIdEdit;
        paramComponent.navigator = navigator;
        paramComponent.hide = hideRecycler;
        listComponents.add(paramComponent);
        return this;
    }

//    public MultiComponents photoComponent(int viewClick, int imageView) {
//        ParamComponent paramComponent = new ParamComponent();
//        paramComponent.type = ParamComponent.TC.PHOTO;
//        paramComponent.paramView = new ParamView(viewClick, imageView);
//        listComponents.add(paramComponent);
//        return this;
//    }

    public MultiComponents addModel(String nameModel, ParamModel paramModel) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.MODEL;
        paramComponent.name = nameModel;
        paramComponent.paramModel = paramModel;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addModel(ParamModel paramModel) {
        return addModel(ParamModel.PARENT_MODEL, paramModel);
    }

    public MultiComponents fragmentsContainer(int fragmentsContainerId) {
        return fragmentsContainer(fragmentsContainerId, "");
    }

    public MultiComponents fragmentsContainer(int fragmentsContainerId, String nameStartFragment) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.CONTAINER;
        paramComponent.fragmentsContainerId = fragmentsContainerId;
        paramComponent.nameStartFragment = nameStartFragment;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addComponentSplash(String intro, String auth, String main) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.SPLASH;
        paramComponent.intro = intro;
        paramComponent.auth = auth;
        paramComponent.main = main;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addTotalComponent(int viewId, int viewIdWithList, Visibility[] visbil, String ... nameFields) {
        return addTotalComponent(viewId, viewIdWithList, 0, visbil, nameFields);
//        ParamComponent paramComponent = new ParamComponent();
//        paramComponent.type = ParamComponent.TC.TOTAL;
//        ParamView pv = new ParamView(viewId);
//        paramComponent.paramView = pv;
//        pv.viewIdWithList = viewIdWithList;
//        pv.visibilityArray = visbil;
//        pv.nameFields = nameFields;
////        paramComponent.eventComponent = viewIdWithList;
//        listComponents.add(paramComponent);
//        return this;
    }

    public MultiComponents addTotalComponent(int viewId, int viewIdWithList, int viewEvent, Visibility[] visbil, String ... nameFields) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.TOTAL;
        ParamView pv = new ParamView(viewId);
        paramComponent.paramView = pv;
        pv.viewIdWithList = viewIdWithList;
        pv.visibilityArray = visbil;
        pv.nameFields = nameFields;
        paramComponent.eventComponent = viewEvent;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addLoadDb(String table, String url, long duration, String alias) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.LOAD_DB;
        ParamModel pm = new ParamModel();
        pm.updateTable = table;
        pm.updateUrl = url;
        pm.duration = duration;
        pm.updateAlias = alias;
        paramComponent.paramModel = pm;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addEditPhoneComponent(int viewId) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.PHONE;
        paramComponent.paramView = new ParamView(viewId);
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addButtonComponent(int viewId,
                                              Navigator navigator,
                                              int... mustValid) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.BUTTON;
        paramComponent.mustValid = mustValid;
        paramComponent.paramView = new ParamView(viewId);
        paramComponent.navigator = navigator;
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addRecognizeVoiceComponent(int clickViewId, int textViewResultId) {
        ParamComponent paramComponent = new ParamComponent();
        paramComponent.type = ParamComponent.TC.RECOGNIZE_VOICE;
        paramComponent.paramView = new ParamView(clickViewId, textViewResultId);
        listComponents.add(paramComponent);
        return this;
    }

    public MultiComponents addNavigator(Navigator navigator) {
        this.navigator = navigator;
        return this;
    }

    public MultiComponents add(Navigator navigator) {
        this.navigator = navigator;
        return this;
    }

    public MultiComponents setDataParam(int viewId, String nameParam, int source) {
        if (listSetData == null) {
            listSetData = new ArrayList<>();
        }
        listSetData.add(new SetData(viewId, nameParam, source));
        return this;
    }

    public MultiComponents actualReceiver(String name) {
        int i = listComponents.size();
        if (i > 0) {
            ParamComponent pc = listComponents.get(i - 1);
            if (pc != null) {
                pc.nameReceiver = name;
            }
        }
        return this;
    }

    public BaseComponent getComponent(int viewId) {
        for (ParamComponent cMV : listComponents) {
            if (cMV.paramView.viewId == viewId) {
                return cMV.baseComponent;
            }
        }
        return null;
    }

    public void initComponents(IBase iBase) {
//        Log.d("QWERT","__initComponents COUNT="+listComponents.size());
        if (additionalWork != null) {
            try {
                moreWork = (MoreWork) additionalWork.newInstance();
                moreWork.setParam(iBase, this);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (ParamComponent cMV : listComponents) {
//            Log.d("QWERT","___initComponents TYPE="+cMV.type+" PARENT="+cMV.nameParentComponent);
            switch (cMV.type) {
                case PANEL :
                    new PanelComponent(iBase, cMV, this);
                    break;
                case PANEL_MULTI :
                    new MultiPanelComponent(iBase, cMV, this);
                    break;
                case PANEL_ENTER:
                    new PanelEnterComponent(iBase, cMV, this);
                    break;
                case SPINNER :
                    new SpinnerComponent(iBase, cMV, this);
                    break;
                case RECYCLER_EXPANDED:
                case RECYCLER_STICKY:
                case RECYCLER :
                case RECYCLER_HORIZONTAL :
                case RECYCLER_GRID :
                    new RecyclerComponent(iBase, cMV, this);
                    break;
                case SPLASH :
                    new SplashComponent(iBase, cMV, this);
                    break;
                case MENU :
                    new MenuComponent(iBase, cMV, this);
                    break;
                case STATIC_LIST :
                    new StaticListComponent(iBase, cMV, this);
                    break;
                case PAGER_V:
                    new PagerVComponent(iBase, cMV, this);
                    break;
                case PAGER_F:
                    new PagerFComponent(iBase, cMV, this);
                    break;
                case MODEL:
                    new ModelComponent(iBase, cMV, this);
                    break;
                case CONTAINER:
                    new ContainerComponent(iBase, cMV, this);
                    break;
                case MAP:
                    new MapComponent(iBase, cMV, this);
                    break;
                case TOTAL:
                    new TotalComponent(iBase, cMV, this);
                    break;
                case SEARCH:
                    new SearchComponent(iBase, cMV, this);
                    break;
                case INTRO:
                    new IntroComponent(iBase, cMV, this);
                    break;
                case DRAWER:
                    new DrawerComponent(iBase, cMV, this);
                    break;
                case PHOTO:
                    new PhotoComponent(iBase, cMV, this);
                    break;
                case POP_UP:
                    new PopUpComponent(iBase, cMV, this);
                    break;
                case RECOGNIZE_VOICE:
                    new RecognizeVoiceComponent(iBase, cMV, this);
                    break;
                case PLUS_MINUS:
                    new PlusMinusComponent(iBase, cMV, this);
                    break;
                case BARCODE:
                    new BarcodeComponent(iBase, cMV, this);
                    break;
                case DATE_DIAPASON:
                    new DateDiapasonComponent(iBase, cMV, this);
                    break;
                case LOAD_DB:
                    new LoadDbComponent(iBase, cMV, this);
                    break;
//                case PHONE:
//                    new EditPhoneComponent(iBase, cMV);
//                    break;
            }
//            cMV.setMultiComponent(this);
            cMV.baseComponent.init();
        }
    }

    public String getParamModel () {
        String st = getParamTitle();
        String sep = "";
        if (st.length() > 0) {
            sep = ",";
        }
        for (ParamComponent vp : listComponents) {
            String paramComponent = getParamApi(vp);
            if (paramComponent.length() > 0) {
                st += sep + paramComponent;
                sep = ",";
            }
        }
        return st;
    }

    public String getParamApi(ParamComponent mvp) {
        if (mvp != null) {
            String paramResult = "";
            if (mvp.paramModel != null) {
                paramResult = mvp.paramModel.param;
            }
            if (mvp.navigator != null && mvp.navigator.viewHandlers != null) {
                String sep = "";
                if (paramResult.length() > 0) {
                    sep = ",";
                }
                for (ViewHandler vh : mvp.navigator.viewHandlers) {
                    if (vh.paramModel != null) {
                        paramResult += sep + vh.paramModel.param;
                        sep = ",";
                    }
                }
            }
            return paramResult;
        } else {
            return "";
        }
    }

    public String getParamTitle() {
        String st = "";
        if (args != null) {
            int ik = args.length;
            String sep = "";
            if (ik > 0) {
                for (int i = 0; i < ik; i++) {
                    st += sep + args[i];
                    sep = ",";
                }
            }
        }
        return st;
    }
}
