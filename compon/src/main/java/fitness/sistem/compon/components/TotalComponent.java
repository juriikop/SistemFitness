package fitness.sistem.compon.components;

import android.util.Log;
import android.view.View;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.Visibility;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamComponent;

import static fitness.sistem.compon.json_simple.Field.TYPE_DOUBLE;
import static fitness.sistem.compon.json_simple.Field.TYPE_LONG;

public class TotalComponent extends BaseComponent {
    public View totalView;
    Record record;
    String[] nameFields;

    public TotalComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        if (paramMV.paramView != null && paramMV.paramView.viewId != 0) {
            totalView = parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (totalView == null) {
            iBase.log("Не найден TotalView в " + paramMV.nameParentComponent);
            return;
        }
        record = new Record();
        nameFields = paramMV.paramView.nameFields;
        listData = multiComponent.getComponent(paramMV.paramView.viewIdWithList).listData;
        if (listData != null) {
           total();
        } else {
            iBase.log("Нет данных для TotalView в " + paramMV.nameParentComponent);
        }
        if (paramMV.paramView.visibilityArray != null) {
            for (Visibility vis : paramMV.paramView.visibilityArray) {
                iBase.log("Visibility id=" + vis.viewId + " tt=" + vis.typeShow + " NN=" + vis.nameField);
            }
        }
    }

    @Override
    public void actual() {
        Log.d("QWERT","TotalComponent actual");
        total();
    }

    @Override
    public void changeData(Field field) {
        total();
    }

    private void total() {
        if (nameFields == null || nameFields.length == 0) return;
        if (listData.size() > 0) {
            Field field;
            record.clear();
            for (Record rec : listData) {
                for (String name : nameFields) {
                    Field fRec = rec.getField(name);
                    if (fRec == null) break;
                    Field fRecord = record.getField(name);
                    if (fRecord == null) {
                        Object vv = null;
                        switch (fRec.type) {
                            case TYPE_LONG :
                                vv = new Long((Long) fRec.value);
                                break;
                            case TYPE_DOUBLE :
                                vv = new Double((Double) fRec.value);
                                break;
                        }
                        fRecord = new Field(name, fRec.type, vv);
                        record.add(fRecord);
                    } else {
                        switch (fRecord.type) {
                            case TYPE_LONG :
                                fRecord.value = (Long) fRecord.value + (Long) fRec.value;
                                break;
                            case TYPE_DOUBLE :
                                fRecord.value = (Double) fRecord.value + (Double) fRec.value;
                                break;
                        }
                    }
                }
            }
            iBase.log("total record="+record.toString());
            workWithRecordsAndViews.RecordToView(record, totalView, this, null);
//            workWithRecordsAndViews.RecordToView(record, totalView, null, null, paramMV.paramView.visibilityArray);
        }
    }
}
