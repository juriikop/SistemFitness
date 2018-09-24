package fitness.sistem.compon.base;

import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.api.GoogleApiClient;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.R;
import fitness.sistem.compon.components.MapComponent;
import fitness.sistem.compon.dialogs.DialogTools;
//import fitness.sistem.compon.functions_fragment.ComponentsFragment;
import fitness.sistem.compon.interfaces_classes.ActivityResult;
import fitness.sistem.compon.interfaces_classes.AnimatePanel;
import fitness.sistem.compon.interfaces_classes.EventComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.IErrorDialog;
import fitness.sistem.compon.interfaces_classes.ParentModel;
import fitness.sistem.compon.interfaces_classes.PermissionsResult;
import fitness.sistem.compon.interfaces_classes.RequestActivityResult;
import fitness.sistem.compon.interfaces_classes.RequestPermissionsResult;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.components.MultiComponents;
import fitness.sistem.compon.json_simple.JsonSimple;
import fitness.sistem.compon.json_simple.JsonSyntaxException;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.json_simple.SimpleRecordToJson;
import fitness.sistem.compon.json_simple.WorkWithRecordsAndViews;
import fitness.sistem.compon.tools.ComponPrefTool;
import fitness.sistem.compon.tools.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.view.View.inflate;

public abstract class BaseActivity extends FragmentActivity implements IBase {

    public Map<String, MultiComponents> mapFragment;
    private DialogFragment progressDialog;
    private int countProgressStart;
    public List<BaseInternetProvider> listInternetProvider;
    public List<EventComponent> listEvent;
    private View parentLayout;
    public MultiComponents mComponent;
    public int containerFragmentId;
    private boolean isActive;
    public List<ParentModel> parentModelList;
    private Bundle savedInstanceState;
    private GoogleApiClient googleApiClient;
    private List<AnimatePanel> animatePanelList;
    public DrawerLayout drawer;
    public ComponGlob componGlob = ComponGlob.getInstance();
    public String TAG = componGlob.appParams.NAME_LOG_APP;
    public List<RequestActivityResult> activityResultList;
    public List<RequestPermissionsResult> permissionsResultList;
    public Field paramScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        parentModelList = new ArrayList<>();
        mapFragment = componGlob.MapScreen;
        animatePanelList = new ArrayList<>();
        activityResultList = null;
        permissionsResultList = null;
        countProgressStart = 0;
        listInternetProvider = new ArrayList<>();
        listEvent = new ArrayList<>();
        String st = componGlob.appParams.nameLanguageInHeader;
        Intent intent = getIntent();
        String paramJson = intent.getStringExtra(Constants.NAME_PARAM_FOR_SCREEN);
        if (paramJson != null && paramJson.length() >0) {
            JsonSimple jsonSimple = new JsonSimple();
            try {
                paramScreen = jsonSimple.jsonToModel(paramJson);
            } catch (JsonSyntaxException e) {
                log(e.getMessage());
                e.printStackTrace();
            }
        }
        if (st != null && st.length() > 0) {
            setLocale();
        }
        String nameScreen = getNameScreen();
        if (nameScreen == null) {
            nameScreen = intent.getStringExtra(Constants.NAME_MVP);
        }

        if (nameScreen != null && nameScreen.length() > 0) {
            mComponent = getComponent(nameScreen);
            if (mComponent.typeView == MultiComponents.TYPE_VIEW.CUSTOM_ACTIVITY) {
                parentLayout = inflate(this, getLayoutId(), null);
            } else {
                parentLayout = inflate(this, mComponent.fragmentLayoutId, null);
            }
        } else {
            parentLayout = inflate(this, getLayoutId(), null);
        }
        if (nameScreen != null) {
            setContentView(parentLayout);
            if (mComponent.navigator != null) {
                for (ViewHandler vh : mComponent.navigator.viewHandlers) {
                    View v = findViewById(vh.viewId);
                    if (v != null) {
                        v.setOnClickListener(navigatorClick);
                    }
                }
            }
            mComponent.initComponents(this);
            if (mComponent.moreWork != null) {
                mComponent.moreWork.startScreen();
            }
        }

//        if (nameScreen != null && nameScreen.length() > 0) {
//            mComponent = getComponent(nameScreen);
//            parentLayout = inflate(this, mComponent.fragmentLayoutId, null);
//            setContentView(parentLayout);
//            if (mComponent.navigator != null) {
//                for (ViewHandler vh : mComponent.navigator.viewHandlers) {
//                    View v = findViewById(vh.viewId);
//                    if (v != null) {
//                        v.setOnClickListener(navigatorClick);
//                    }
//                }
//            }
//            mComponent.initComponents(this);
//            if (mComponent.moreWork != null) {
//                mComponent.moreWork.startScreen();
//            }
//        } else {
//            parentLayout = inflate(this, getLayoutId(), null);
//        }
        initView();
    }

    public void setLocale() {
        String loc = ComponPrefTool.getLocale();
        if (loc.length() == 0) {
            loc = "uk";
        }
        if (loc.equals(Locale.getDefault().getLanguage())) return;
        Locale myLocale = new Locale(loc);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    public String getNameScreen() {
        return null;
    }

    public int getLayoutId() {
        return 0;
    }

    public void initView() {

    }

    public void addPermissionsResult(int requestCode, PermissionsResult permissionsResult) {
        if (permissionsResultList == null) {
            permissionsResultList = new ArrayList<>();
        }
        permissionsResultList.add(new RequestPermissionsResult(requestCode, permissionsResult));
    }

    public void addForResult(int requestCode, ActivityResult activityResult) {
        if (activityResultList == null) {
            activityResultList = new ArrayList<>();
        }
        activityResultList.add(new RequestActivityResult(requestCode, activityResult));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissionsResultList != null) {
            int ik = permissionsResultList.size();
            int j = -1;
            for (int i = 0; i < ik; i++) {
                RequestPermissionsResult rpr = permissionsResultList.get(i);
                if (requestCode == rpr.request) {
                    rpr.permissionsResult.onPermissionsResult(requestCode, permissions, grantResults);
                    j = i;
                    break;
                }
                if (j > -1) {
                    permissionsResultList.remove(j);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultList != null) {
            int ik = activityResultList.size();
            int j = -1;
            for (int i = 0; i < ik; i++) {
                RequestActivityResult rar = activityResultList.get(i);
                if (requestCode == rar.request) {
                    rar.activityResult.onActivityResult(requestCode, resultCode, data);
                    j = i;
                    break;
                }
            }
            if (j > -1) {
                activityResultList.remove(j);
            }
        }
    }

    View.OnClickListener navigatorClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            for (ViewHandler vh : mComponent.navigator.viewHandlers) {
                if (vh.viewId == id) {
                    switch (vh.type) {
                        case NAME_FRAGMENT:
                            startScreen(vh.nameFragment, false);
                            break;
                        case BACK:
                            onBackPressed();
                            break;
                        case SHOW:
                            View showView = parentLayout.findViewById(vh.showViewId);
                            if (showView instanceof AnimatePanel) {
                                ((AnimatePanel) showView).show(BaseActivity.this);
                            } else {
                                showView.setVisibility(View.VISIBLE);
                            }
                            break;
                        case RECEIVER:
                            LocalBroadcastManager.getInstance(BaseActivity.this).registerReceiver(broadcastReceiver,
                                    new IntentFilter(vh.nameFieldWithValue));
                            break;
                    }
//                    break;
                }
            }
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mComponent.moreWork != null) {
                mComponent.moreWork.receiverWork(intent);
            }
            LocalBroadcastManager.getInstance(BaseActivity.this).unregisterReceiver(broadcastReceiver);
        }
    };

    public MultiComponents getComponent(String name) {
        return componGlob.MapScreen.get(name);
    }

    @Override
    public void setFragmentsContainerId(int id) {
        containerFragmentId = id;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    protected void onStop() {
        if (listInternetProvider != null) {
            for (BaseInternetProvider provider : listInternetProvider) {
                provider.cancel();
            }
        }
        isActive = false;
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        int statusBarColor = ComponPrefTool.getStatusBarColor();
        if (statusBarColor != 0) {
            setStatusBarColor(statusBarColor);
        }
    }

    @Override
    public void log(String msg) {
        Log.i(TAG, msg);
    }

    public void setStatusColor(int color) {
        ComponPrefTool.setStatusBarColor(color);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public Field getProfile() {
        return componGlob.profile;
    }

    @Override
    public void backPressed() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        int countFragment = fm.getBackStackEntryCount();
        if ( countFragment > 0) {
            List<Fragment> fragmentList = fm.getFragments();
            Fragment fragment = topFragment(fm);
            if (fragment != null && fragment instanceof BaseFragment) {
                if (((BaseFragment) fragment).canBackPressed()) {
                    if (countFragment == 1) {
                        finish();
                    } else {
                        super.onBackPressed();
                    }
                }
            } else {
                if (countFragment == 1) {
                    finish();
                } else {
                    super.onBackPressed();
                }
            }
        } else {
            if (canBackPressed()) {
                finish();
                if (mComponent.animateScreen != null) {
                    switch (mComponent.animateScreen) {
                        case TB :
                            overridePendingTransition(R.anim.bt_in, R.anim.bt_out);
                            break;
                        case BT :
                            overridePendingTransition(R.anim.tb_in, R.anim.tb_out);
                            break;
                        case LR :
                            overridePendingTransition(R.anim.rl_in, R.anim.rl_out);
                            break;
                        case RL :
                            overridePendingTransition(R.anim.lr_in, R.anim.lr_out);
                            break;
                    }
                }
            }
        }
    }

    private Fragment topFragment(FragmentManager fm) {
        List<Fragment> fragmentList = fm.getFragments();
        Fragment fragment = null;
        for (Fragment fragm : fragmentList) {
            if (fragm != null) {
                fragment = fragm;
            }
        }
        return fragment;
    }

    @Override
    public boolean isHideAnimatePanel() {
        int pos = animatePanelList.size();
        if (pos > 0) {
            animatePanelList.get(pos - 1).hide();
            return false;
        } else {
            return true;
        }
    }

    public boolean canBackPressed() {
        return isHideAnimatePanel();
    }

    @Override
    public BaseFragment getBaseFragment() {
        return null;
    }

    @Override
    public boolean isViewActive() {
        return isActive;
    }

//    @Override

    public void startActivitySimple(String nameMVP, Object object) {
        MultiComponents mc = mapFragment.get(nameMVP);
        if (mc != null) {
            startActivitySimple(nameMVP, mc, object);
        } else {
            log("Нет Screens с именем " + nameMVP);
        }
    }

    public void startActivitySimple(String nameMVP, MultiComponents mc, Object object) {
        Intent intent;
        if (mc.customFragment == null) {
            intent = new Intent(this, ComponBaseStartActivity.class);
        } else {
            intent = new Intent(this, mc.customFragment);
        }
        intent.putExtra(Constants.NAME_MVP, nameMVP);
        if (object != null) {
            SimpleRecordToJson recordToJson = new SimpleRecordToJson();
            Field f = new Field();
            f.value = object;
            if (object instanceof Record) {
                f.type = Field.TYPE_RECORD;
                intent.putExtra(Constants.NAME_PARAM_FOR_SCREEN, recordToJson.modelToJson(f));
            } else if (object instanceof ListRecords) {
                f.type = Field.TYPE_LIST_RECORD;
                intent.putExtra(Constants.NAME_PARAM_FOR_SCREEN, recordToJson.modelToJson(f));
            }
        }
        startActivity(intent);
        if (mc.animateScreen != null) {
            switch (mc.animateScreen) {
                case TB :
                    overridePendingTransition(R.anim.tb_in, R.anim.tb_out);
                    break;
                case BT :
                    overridePendingTransition(R.anim.bt_in, R.anim.bt_out);
                    break;
                case LR :
                    overridePendingTransition(R.anim.lr_in, R.anim.lr_out);
                    break;
                case RL :
                    overridePendingTransition(R.anim.rl_in, R.anim.rl_out);
                    break;
            }
        }
    }

    public void closeDrawer() {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void openDrawer() {
        if (drawer != null) {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    public ParentModel getParentModel(String name) {
        if (parentModelList.size() > 0) {
            for (ParentModel pm : parentModelList) {
                if (pm.nameParentModel.equals(name)) {
                    return pm;
                }
            }
        }
        ParentModel pm = new ParentModel(name);
        parentModelList.add(pm);
        return pm;
    }

    public void showDialog(String title, String message, View.OnClickListener click) {
        int id = componGlob.appParams.errorDialogViewId;
        if (id != 0) {
            Record rec = new Record();
            rec.add(new Field("title", Field.TYPE_STRING, title));
            rec.add(new Field("message", Field.TYPE_STRING, message));
            View viewErrorDialog = parentLayout.findViewById(id);
            if (viewErrorDialog instanceof AnimatePanel) {
                ((AnimatePanel) viewErrorDialog).show(this);
                WorkWithRecordsAndViews workWithRecordsAndViews = new WorkWithRecordsAndViews();
                workWithRecordsAndViews.RecordToView(rec, viewErrorDialog);
            }
        } else {
            DialogTools.showDialog(this, title, message, click);
        }
    }

    @Override
    public void showDialog(int statusCode, String message, View.OnClickListener click) {
        showDialog("StatusCode="+statusCode, message, click);
//        DialogTools.showDialog(this, statusCode, message, click);
    }

    @Override
    public void progressStart() {
        if (componGlob.appParams.classProgress != null) {
            if (progressDialog == null) {
                try {
                    progressDialog = (DialogFragment) componGlob.appParams.classProgress.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (countProgressStart == 0) {
                progressDialog.show(getFragmentManager(), "MyProgressDialog");
            }
            countProgressStart++;
        }
    }

    @Override
    public void progressStop() {
        countProgressStart--;
        if (countProgressStart == 0 && progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void startDrawerFragment(String nameMVP, int containerFragmentId) {
        MultiComponents model = mapFragment.get(nameMVP);
        BaseFragment fragment = new BaseFragment();
        fragment.setModel(model);
        Bundle bundle =new Bundle();
        bundle.putString(Constants.NAME_MVP, nameMVP);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerFragmentId, fragment, nameMVP);
        transaction.commit();
    }

    @Override
    public void startScreen(String nameMVP, boolean startFlag) {
        startScreen(nameMVP, startFlag, null);
    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    @Override
    public void startScreen(String nameMVP, boolean startFlag, Object object) {
        MultiComponents mComponent = mapFragment.get(nameMVP);
        if (mComponent == null || mComponent.typeView == null) {
            log("Нет Screens с именем " + nameMVP);
            return;
        }
        switch (mComponent.typeView) {
            case ACTIVITY:
                startActivitySimple(nameMVP, mComponent, object);
                break;
            case CUSTOM_ACTIVITY:
                startActivitySimple(nameMVP, mComponent, object);
                break;
            case FRAGMENT:
                startFragment(nameMVP, mComponent, startFlag, object);
                break;
            case CUSTOM_FRAGMENT:
                startCustomFragment(nameMVP, mComponent, startFlag, object);
                break;
        }
    }

    public void startCustomFragment(String nameMVP, MultiComponents mComponent, boolean startFlag, Object object) {
        BaseFragment fr = (BaseFragment) getSupportFragmentManager().findFragmentByTag(nameMVP);
        int count = (fr == null) ? 0 : 1;
        if (startFlag) {
            clearBackStack(count);
        }
        BaseFragment fragment = null; // (fr != null) ? fr : new ComponentsFragment();
        if (fr != null) {
            fragment = fr;
        } else {
            try {
                fragment = (BaseFragment) mComponent.customFragment.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (fragment != null) {
            Bundle bundle = null;
            if (object != null) {
                if (object instanceof Bundle) {
                    bundle= (Bundle) object;
                }
            }
            if (bundle == null){
                bundle = new Bundle();
            }
            bundle.putString(Constants.NAME_MVP, nameMVP);
            if (object != null) {
                if (object instanceof Record || object instanceof ListRecords) {
                    SimpleRecordToJson recordToJson = new SimpleRecordToJson();
                    Field f = new Field();
                    f.value = object;
                    if (object instanceof Record) {
                        f.type = Field.TYPE_RECORD;
                    } else {
                        f.type = Field.TYPE_LIST_RECORD;
                    }
                    bundle.putString(Constants.NAME_PARAM_FOR_SCREEN, recordToJson.modelToJson(f));
                } else {
                    fragment.setObject(object);
                }
            }
            fragment.setArguments(bundle);
            fragment.setModel(mComponent);
            startNewFragment(fragment, nameMVP, mComponent);
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(containerFragmentId, bf.getThis(), nameMVP)
////                .addToBackStack(nameMVP)
//                    .commit();
        }
    }

    public void startFragment(String nameMVP, MultiComponents mComponent, boolean startFlag, Object object) {
        BaseFragment fr = (BaseFragment) getSupportFragmentManager().findFragmentByTag(nameMVP);
        int count = (fr == null) ? 0 : 1;
        if (startFlag) {
            clearBackStack(count);
        }
        BaseFragment fragment = (fr != null) ? fr : new BaseFragment();
        Bundle bundle =new Bundle();
        bundle.putString(Constants.NAME_MVP, nameMVP);
        if (object != null) {
            SimpleRecordToJson recordToJson = new SimpleRecordToJson();
            Field f = new Field();
            f.value = object;
            if (object instanceof Record) {
                f.type = Field.TYPE_RECORD;
                bundle.putString(Constants.NAME_PARAM_FOR_SCREEN, recordToJson.modelToJson(f));
            } else if (object instanceof ListRecords) {
                f.type = Field.TYPE_LIST_RECORD;
                bundle.putString(Constants.NAME_PARAM_FOR_SCREEN, recordToJson.modelToJson(f));
            }
        }
        fragment.setArguments(bundle);
        fragment.setModel(mComponent);
        startNewFragment(fragment, nameMVP, mComponent);
    }

    private void startNewFragment(BaseFragment fragment, String nameMVP, MultiComponents mComponent) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mComponent.animateScreen != null) {
            switch (mComponent.animateScreen) {
                case RL:
                    transaction.setCustomAnimations(R.anim.rl_in, R.anim.rl_out,
                            R.anim.lr_in, R.anim.lr_out);
                    break;
                case LR :
                    transaction.setCustomAnimations(R.anim.lr_in, R.anim.lr_out,
                            R.anim.rl_in, R.anim.rl_out);
                    break;
                case TB :
                    transaction.setCustomAnimations(R.anim.tb_in, R.anim.tb_out,
                            R.anim.bt_in, R.anim.bt_out);
                    break;
                case BT :
                    transaction.setCustomAnimations(R.anim.bt_in, R.anim.bt_out,
                            R.anim.tb_in, R.anim.tb_out);
                    break;
            }
        }
        transaction.replace(containerFragmentId, fragment, nameMVP)
                .addToBackStack(nameMVP)
                .commit();
    }

    public void clearBackStack(int count) {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > count) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(count);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void addParamValue(String name, String value) {
        componGlob.addParamValue(name, value);
    }


    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public void addInternetProvider(BaseInternetProvider internetProvider) {
        listInternetProvider.add(internetProvider);
    }

    @Override
    public View getParentLayout() {
        return parentLayout;
    }

    @Override
    public void addEvent(int sender, BaseComponent receiver) {
        listEvent.add(new EventComponent(sender, receiver));
    }

    @Override
    public void addEvent(int[] senderList, BaseComponent receiver) {
        for (int sender : senderList) {
            listEvent.add(new EventComponent(sender, receiver));
        }
    }

    @Override
    public void sendEvent(int sender) {
        for (EventComponent ev : listEvent) {
            if (ev.eventSenderId == sender) {
                ev.eventReceiverComponent.actual();
            }
        }
    }

    @Override
    public void sendActualEvent(int sender, Object paramEvent) {
        for (EventComponent ev : listEvent) {
            if (ev.eventSenderId == sender) {
                ev.eventReceiverComponent.actualEvent(sender, paramEvent);
            }
        }
    }

    @Override
    public void addAnimatePanel(AnimatePanel animatePanel) {
        animatePanelList.add(animatePanel);
    }

    @Override
    public void delAnimatePanel(AnimatePanel animatePanel) {
        animatePanelList.remove(animatePanel);
    }

    @Override
    public Field getParamScreen() {
        return paramScreen;
    }
}
