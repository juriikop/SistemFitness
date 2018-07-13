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

public interface IBase {
    public BaseActivity getBaseActivity();
    public BaseFragment getBaseFragment();
    public View getParentLayout();
    public void addEvent(int sender, BaseComponent receiver);
    void addEvent(int[] senderList, BaseComponent receiver);
    public void sendEvent(int sender);
    public void sendActualEvent(int sender, Object paramEvent);
    public ParentModel getParentModel(String name);
    public Field getProfile();
//    public void startActivitySimple(String nameMVP);
//    public void startFragment(String nameMVP, boolean startFlag);
    public void startScreen(String nameMVP, boolean startFlag, Object object);
    public void startScreen(String nameMVP, boolean startFlag);
    public void startDrawerFragment(String nameMVP, int containerFragmentId);
    public void backPressed();
    public void progressStart();
    public void progressStop();
    public void showDialog(String title, String message, View.OnClickListener click);
    public void showDialog(int statusCode, String message, View.OnClickListener click);
    public boolean isViewActive();
    public void setFragmentsContainerId(int id);
    public Bundle getSavedInstanceState();
//    public void addRequest(Request request);
    public void addInternetProvider(BaseInternetProvider internetProvider);
    public void setGoogleApiClient(GoogleApiClient googleApiClient);
    public void setMapComponent(MapComponent mapComponent);
    public void addAnimatePanel(AnimatePanel animatePanel);
    public void delAnimatePanel(AnimatePanel animatePanel);
    public boolean isHideAnimatePanel();
    public void log(String msg);
}
