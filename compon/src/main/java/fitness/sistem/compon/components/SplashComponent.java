package fitness.sistem.compon.components;

import android.util.Log;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.tools.ComponPrefTool;

public class SplashComponent extends BaseComponent {
    public SplashComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        if (paramMV.intro != null && paramMV.intro.length() > 0
                && ! ComponPrefTool.getTutorial()) {
            iBase.startScreen(paramMV.intro, false);
        } else {
            if (paramMV.auth != null && paramMV.auth.length() > 0
                    && ComponPrefTool.getSessionToken().length() == 0) {
                iBase.startScreen(paramMV.auth, false);
            } else {
                iBase.startScreen(paramMV.main, false);
            }
        }
        iBase.backPressed();
    }

    @Override
    public void changeData(Field field) {

    }
}
