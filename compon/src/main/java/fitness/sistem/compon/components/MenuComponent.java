package fitness.sistem.compon.components;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseActivity;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.BaseProvider;
import fitness.sistem.compon.base.BaseProviderAdapter;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.presenter.ListPresenter;
import fitness.sistem.compon.tools.StaticVM;

public class MenuComponent extends BaseComponent {
    RecyclerView recycler;
    ListRecords listData;
    BaseProviderAdapter adapter;

    public MenuComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
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
            Log.i("SMPL", "Не найден RecyclerView для Menu в " + paramMV.nameParentComponent);
        }

        for (ViewHandler vh : navigator.viewHandlers) {
            if (vh.viewId == 0 && vh.type == ViewHandler.TYPE.FIELD_WITH_NAME_FRAGMENT) {
                selectViewHandler = vh;
                break;
            }
        }
        listData = new ListRecords();
        listPresenter = new ListPresenter(this);
        provider = new BaseProvider(listData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recycler.setLayoutManager(layoutManager);
        adapter = new BaseProviderAdapter(this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void changeData(Field field) {
        listData.clear();
        listData.addAll((ListRecords) field.value);
        provider.setData(listData);
        int selectStart = -1;
        int ik = listData.size();
        for (int i = 0; i < ik; i++) {
            Record r = listData.get(i);
            int j = (Integer) r.getValue("select");
            if (j > 1) {
                selectStart = i;
                break;
            }
        }
        listPresenter.changeData(listData, selectStart);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void changeDataPosition(int position, boolean select) {
        adapter.notifyItemChanged(position);
        if (select && selectViewHandler != null) {
            ((BaseActivity) activity).closeDrawer();
            Record record = listData.get(position);
            ComponGlob.getInstance().setParam(record);
            String st = record.getString(selectViewHandler.nameFragment);
            if (st != null && st.length() > 0) {
                iBase.startScreen(st, true);
            }
        }
    }

}
