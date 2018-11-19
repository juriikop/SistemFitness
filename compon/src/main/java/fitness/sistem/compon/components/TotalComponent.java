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
import static fitness.sistem.compon.json_simple.Field.TYPE_FLOAT;
import static fitness.sistem.compon.json_simple.Field.TYPE_INTEGER;
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
            for (Record recList : listData) {
//                Log.d("QWERT","TotalComponent recList="+recList.toString());
                for (String name : nameFields) {
                    Field fRecList = recList.getField(name);
                    if (fRecList == null) continue;
                    Field fRecord = record.getField(name);
                    if (fRecord == null) {
                        Field ff = new Field(name, fRecList.type, null);
                        switch (fRecList.type) {
                            case TYPE_INTEGER :
                                ff.value = new Integer((Integer) fRecList.value);
                                break;
                            case TYPE_LONG :
                                ff.value = new Long ((Long) fRecList.value);
                                break;
                            case TYPE_DOUBLE :
                                ff.value = new Double ((Double) fRecList.value);
                                break;
                            case TYPE_FLOAT :
                                ff.value = new Float ((Float) fRecList.value);
                                break;
                        }
                        record.add(ff);
                    } else {
                        switch (fRecord.type) {
                            case TYPE_INTEGER :
                                fRecord.value = (Integer) fRecord.value + (Integer) fRecList.value;
                                break;
                            case TYPE_LONG :
                                if (fRecList.value instanceof Long) {
                                    fRecord.value = (Long) fRecord.value + (Long) fRecList.value;
                                } else {
                                    fRecord.value = (Long) fRecord.value + (Integer) fRecList.value;
                                }
                                break;
                            case TYPE_DOUBLE :
                                fRecord.value = (Double) fRecord.value + (Double) fRecList.value;
                                break;
                            case TYPE_FLOAT :
                                fRecord.value = (Float) fRecord.value + (Float) fRecList.value;
                                break;
                        }
                    }
                }
            }
            workWithRecordsAndViews.RecordToView(record, totalView, this, null);
        }
    }
}
