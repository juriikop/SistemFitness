package fitness.sistem.compon.components;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.BaseProvider;
import fitness.sistem.compon.base.BaseProviderAdapter;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.presenter.ListPresenter;
import fitness.sistem.compon.tools.ComponPrefTool;
import fitness.sistem.compon.tools.StaticVM;

public class RecyclerComponent extends BaseComponent {
    RecyclerView recycler;
    public BaseProviderAdapter adapter;
    private String componentTag = "RECYCLER_";

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
            iBase.log("Не найден RecyclerView в " + paramMV.nameParentComponent);
            return;
        }
        listData = new ListRecords();
        if (paramMV.paramView.selected) {
            listPresenter = new ListPresenter(this);
        }
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
        if (listData == null) return;
        if (field == null || field.value == null) return;
        listData.clear();
        listData.addAll((ListRecords) field.value);
        provider.setData(listData);
        if (listPresenter != null) {
            int selectStart = ComponPrefTool.getNameInt(componentTag + multiComponent.nameComponent, -1);
            int ik = listData.size();
            Log.d("QWERT","changeData selectStart="+selectStart+" paramMV.paramView.fieldType="+paramMV.paramView.fieldType+"<< TTTT="+(componentTag + multiComponent.nameComponent));
            if (selectStart == -1) {
                for (int i = 0; i < ik; i++) {
                    Record r = listData.get(i);
                    int j = r.getInt(paramMV.paramView.fieldType);
                    if (j == 1) {
                        selectStart = i;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < ik; i++) {
                    Record r = listData.get(i);
                    Field f = r.getField(paramMV.paramView.fieldType);
                    if (i == selectStart) {
                        f.value = 1;
                    } else {
                        if (((Integer) f.value) == 1) {
                            f.value = 0;
                        }
                    }
                }
            }
            listPresenter.changeData(listData, selectStart);
        }
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
                iBase.log("Не найден SplashView в " + paramMV.nameParentComponent);
            }
        }




//        if (listPresenter != null) {
//            int selectStart = -1;
//            int ik = listData.size();
//            for (int i = 0; i < ik; i++) {
//                Record r = listData.get(i);
//                int j = r.getInt(paramMV.paramView.fieldType);
//                if (j == 1) {
//                    selectStart = i;
//                    break;
//                }
//            }
//            listPresenter.changeData(listData, selectStart);
//        }
        iBase.sendEvent(paramMV.paramView.viewId);
    }

    @Override
    public void changeDataPosition(int position, boolean select) {
        Log.d("QWERT","changeDataPosition position="+position+" select="+select);
        if (paramMV.paramView.selected) {
            adapter.notifyItemChanged(position);
            ComponPrefTool.setNameInt(componentTag + multiComponent.nameComponent, position);
            if (select && selectViewHandler != null) {
                Record record = listData.get(position);
                ComponGlob.getInstance().setParam(record);
                String st = record.getString(selectViewHandler.nameFragment);
                if (st != null && st.length() > 0) {
                    iBase.startScreen(st, true);
                }
            }
        }
    }
}
