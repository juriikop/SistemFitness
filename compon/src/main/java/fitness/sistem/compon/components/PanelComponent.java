package fitness.sistem.compon.components;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamComponent;

public class PanelComponent extends BaseComponent {
    @Override
    public void initView() {
    }

    @Override
    public void changeData(Field field) {
        if (field == null) return;
        if (field.value instanceof Record) {
            Record rec = (Record) field.value;
            viewComponent = parentLayout.findViewById(paramMV.paramView.viewId);
            workWithRecordsAndViews.RecordToView(rec, viewComponent, null, null, paramMV.paramView.visibilityArray);
        } else {
            iBase.log("Тип данных не Record в " + paramMV.nameParentComponent);
        }
    }

    public PanelComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }
}
