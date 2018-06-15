package fitness.sistem.compon.components;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.ParentModel;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.param.ParamComponent;

public class ModelComponent extends BaseComponent {

    @Override
    public void initView() {

    }

    @Override
    public void changeData(Field field) {
        ParentModel pm = iBase.getParentModel(paramMV.name);
        pm.field = field;
        for (BaseComponent bc : pm.componentList) {
            bc.setParentData(field);
        }
    }

    public ModelComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }
}
