package fitness.sistem.compon.components;

import android.view.View;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.param.ParamComponent;

public class ButtonComponent extends BaseComponent{
    private boolean[] valid;
    private int lenValid;

    public View buttonView;

    public ButtonComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        buttonView = (View) parentLayout.findViewById(paramMV.paramView.viewId);
        if (buttonView == null) {
            iBase.log( "Не найден Button(View) в " + paramMV.nameParentComponent);
            return;
        }
        buttonView.setOnClickListener(clickListener);
        if (paramMV.mustValid != null) {
            lenValid = paramMV.mustValid.length;
            valid = new boolean[lenValid];
            for (boolean bb : valid) {
                bb = false;
            }
        }
    }

    @Override
    public void actualEvent(int sender, Object paramEvent) {
        int status = (Integer) paramEvent;
        if (status == 3 || status == 4) {
            for (int i = 0; i < lenValid; i++) {
                if (paramMV.mustValid[i] == sender) {
                    valid[i] = status == 4;
                    break;
                }
            }
            boolean valided = true;
            for (boolean bb : valid) {
                if (!bb) {
                    valided = false;
                    break;
                }
            }
            buttonView.setEnabled(valided);
        }
    }

    @Override
    public void changeData(Field field) {

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
