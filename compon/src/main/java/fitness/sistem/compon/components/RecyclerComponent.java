package fitness.sistem.compon.components;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.BaseProvider;
import fitness.sistem.compon.base.BaseProviderAdapter;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.tools.StaticVM;

public class RecyclerComponent extends BaseComponent {
    RecyclerView recycler;
    BaseProviderAdapter adapter;

    public RecyclerComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        if (paramMV.paramView == null || paramMV.paramView.viewId == 0) {
            recycler = (RecyclerView) StaticVM.findViewByName(parentLayout, "recycler");
        } else {
            recycler = (RecyclerView) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (recycler == null) {
            Log.i(TAG, "Не найден RecyclerView в " + paramMV.nameParentComponent);
            return;
        }
        listData = new ListRecords();
        provider = new BaseProvider(listData);
        LinearLayoutManager layoutManager;
        switch (paramMV.type) {
            case RECYCLER_GRID:
                layoutManager = new GridLayoutManager(activity, 2);
                break;
            case RECYCLER_HORIZONTAL:
                layoutManager = new LinearLayoutManager(activity);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                break;
            default:
                layoutManager = new LinearLayoutManager(activity);
        }
        recycler.setLayoutManager(layoutManager);
        adapter = new BaseProviderAdapter(this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void changeData(Field field) {
        Log.d("QWERT","RecyclerComponent changeData");
        listData.clear();
        listData.addAll((ListRecords) field.value);
        provider.setData(listData);
        adapter.notifyDataSetChanged();
        int splash = paramMV.paramView.splashScreenViewId;
        if (splash != 0) {
            View v_splash = parentLayout.findViewById(splash);
            if (v_splash != null) {
                if (listData.size() > 0) {
                    v_splash.setVisibility(View.GONE);
                } else {
                    v_splash.setVisibility(View.VISIBLE);
                }
            } else {
                Log.i(TAG, "Не найден SplashView в " + paramMV.nameParentComponent);
            }
        }
        iBase.sendEvent(paramMV.paramView.viewId);
    }
}
