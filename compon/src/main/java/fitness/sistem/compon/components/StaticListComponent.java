package fitness.sistem.compon.components;

import fitness.sistem.compon.adapters.StaticListAdapter;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.BaseProvider;
import fitness.sistem.compon.custom_components.StaticList;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.tools.StaticVM;

public class StaticListComponent extends BaseComponent {
    StaticList staticList;
    StaticListAdapter adapter;

    public StaticListComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        if (paramMV.paramView == null || paramMV.paramView.viewId == 0) {
            staticList = (StaticList) StaticVM.findViewByName(parentLayout, "baseStaticList");
        } else {
            staticList = (StaticList) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (staticList == null) {
            iBase.log("Не найден StaticList в " + paramMV.nameParentComponent);
            return;
        }
        listData = new ListRecords();
        provider = new BaseProvider(listData);
        adapter = new StaticListAdapter(this);
        staticList.setAdapter(adapter, false);
    }

    @Override
    public void changeData(Field field) {
        listData.clear();
        listData.addAll((ListRecords) field.value);
        provider.setData(listData);
        adapter.notifyDataSetChanged();
    }
}
