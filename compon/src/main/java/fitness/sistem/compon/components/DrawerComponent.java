package fitness.sistem.compon.components;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.param.ParamComponent;

public class DrawerComponent extends BaseComponent{

    DrawerLayout drawer;

    public DrawerComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        drawer = (DrawerLayout)parentLayout.findViewById(paramMV.paramView.viewId);
        if (drawer == null) {
            Log.i("SMPL", "Не найден DrawerLayout в " + paramMV.nameParentComponent);
            return;
        }
        activity.drawer = drawer;
        iBase.setFragmentsContainerId(paramMV.paramView.layoutTypeId[0]);
        String st = paramMV.paramView.nameFragment[0];
        if (st != null && st.length() > 0) {
            iBase.startScreen(st, true);
        }
        iBase.startDrawerFragment(paramMV.paramView.nameFragment[1], paramMV.paramView.layoutTypeId[1]);
    }

    @Override
    public void changeData(Field field) {

    }

}
