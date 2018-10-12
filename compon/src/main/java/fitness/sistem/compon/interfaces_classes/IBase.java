package fitness.sistem.compon.interfaces_classes;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

import fitness.sistem.compon.base.BaseActivity;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.BaseFragment;
import fitness.sistem.compon.base.BaseInternetProvider;
import fitness.sistem.compon.components.MapComponent;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.Record;

public interface IBase {
    BaseActivity getBaseActivity();
    BaseFragment getBaseFragment();
    View getParentLayout();
    void addEvent(int sender, BaseComponent receiver);
    void addEvent(int[] senderList, BaseComponent receiver);
    void sendEvent(int sender);
    void sendActualEvent(int sender, Object paramEvent);
    ParentModel getParentModel(String name);
    Field getProfile();
    void startScreen(String nameMVP, boolean startFlag, Object object, int forResult);
    void startScreen(String nameMVP, boolean startFlag, Object object);
    void startScreen(String nameMVP, boolean startFlag);
    void startDrawerFragment(String nameMVP, int containerFragmentId);
    void backPressed();
    void progressStart();
    void progressStop();
    void showDialog(String title, String message, View.OnClickListener click);
    void showDialog(int statusCode, String message, View.OnClickListener click);
    boolean isViewActive();
    void setFragmentsContainerId(int id);
    Bundle getSavedInstanceState();
    void addInternetProvider(BaseInternetProvider internetProvider);
    void setGoogleApiClient(GoogleApiClient googleApiClient);
    void addAnimatePanel(AnimatePanel animatePanel);
    void delAnimatePanel(AnimatePanel animatePanel);
    Field getParamScreen();
    boolean isHideAnimatePanel();
    void log(String msg);
    void setResumePause(OnResumePause resumePause);
}
