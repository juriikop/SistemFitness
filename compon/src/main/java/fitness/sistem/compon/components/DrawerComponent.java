package fitness.sistem.compon.components;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.param.ParamComponent;

public class DrawerComponent extends BaseComponent{

    public DrawerComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        iBase.setFragmentsContainerId(paramMV.fragmentsContainerId);
        if (paramMV.nameStartFragment != null && paramMV.nameStartFragment.length() > 0) {
            iBase.startScreen(paramMV.nameStartFragment, false);
        }
    }

    @Override
    public void changeData(Field field) {

    }
}
