package fitness.sistem.compon.components;

import android.util.Log;
import android.view.View;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.custom_components.EditPhone;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.OnChangeStatusListener;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.param.ParamComponent;

public class EditPhoneComponent extends BaseComponent {

    EditPhone editPhone;

    public EditPhoneComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        editPhone = (EditPhone) parentLayout.findViewById(paramMV.paramView.viewId);
        if (editPhone == null) {
            Log.i("SMPL", "Не найден EditPhone в " + paramMV.nameParentComponent);
            return;
        }
        editPhone.setOnChangeStatusListener(statusListener);
    }

    OnChangeStatusListener statusListener = new OnChangeStatusListener() {
        @Override
        public void changeStatus(View v, Object status) {
            iBase.sendActualEvent(paramMV.paramView.viewId, status);
//            switch ((Integer) status) {
//                case 3 :    // стало не валидным
//                    iBase.sendActualEvent(paramMV.paramView.viewId, new Boolean(false));
//                    break;
//                case 4 :    // стало валидным
//                    iBase.sendActualEvent(paramMV.paramView.viewId, new Boolean(true));
//                    break;
//            }
        }
    };

    @Override
    public void changeData(Field field) {

    }
}
