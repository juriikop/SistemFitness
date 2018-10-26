package fitness.sistem.compon.components;

import android.util.Log;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamComponent;

public class PanelComponent extends BaseComponent {
    @Override
    public void initView() {
        viewComponent = parentLayout.findViewById(paramMV.paramView.viewId);
        if (viewComponent == null) {
            iBase.log("Не найдена панель в " + paramMV.nameParentComponent);
        }
    }

    @Override
    public void changeData(Field field) {
        if (field == null) return;
        if (field.value instanceof Record) {
            Record rec = (Record) field.value;
            workWithRecordsAndViews.RecordToView(rec, viewComponent, paramMV.navigator, null, paramMV.paramView.visibilityArray);
        } else {
            if (field.value instanceof ListRecords) {
                ListRecords lr = (ListRecords) field.value;
                if (lr. size() > 0) {
                    Record rec = lr.get(0);
                    workWithRecordsAndViews.RecordToView(rec, viewComponent, paramMV.navigator, null, paramMV.paramView.visibilityArray);
                } else {
                    iBase.log("Тип данных не Record в " + paramMV.nameParentComponent);
                }
            } else {
                iBase.log("Тип данных не Record в " + paramMV.nameParentComponent);
            }
        }
    }

    public PanelComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }
}
