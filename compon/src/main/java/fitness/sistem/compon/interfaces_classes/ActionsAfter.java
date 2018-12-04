package fitness.sistem.compon.interfaces_classes;

import java.util.ArrayList;
import java.util.List;

import fitness.sistem.compon.param.ParamModel;

import static fitness.sistem.compon.interfaces_classes.ViewHandler.TYPE;

public class ActionsAfter {
    public List<ViewHandler> viewHandlers = new ArrayList<>();

    public ActionsAfter startScreen(String nameScreen) {
        viewHandlers.add(new ViewHandler(0, nameScreen));
        return this;
    }

    public ActionsAfter preferenceSetToken(String nameField) {
        viewHandlers.add(new ViewHandler(0, TYPE.PREFERENCE_SET_TOKEN, nameField));
        return this;
    }

    public ActionsAfter showComponent(int viewId) {
        viewHandlers.add(new ViewHandler(0, TYPE.SHOW, viewId));
        return this;
    }

    public ActionsAfter showComponent(int viewId, String nameFieldWithValue) {
        viewHandlers.add(new ViewHandler(viewId, TYPE.SHOW, nameFieldWithValue));
        return this;
    }

    public ActionsAfter updateDataByModel(int viewId, ParamModel paramModel) {
        viewHandlers.add(new ViewHandler(viewId, TYPE.UPDATE_DATA, paramModel));
        return this;
    }

    public ActionsAfter updateFromResult(int viewId) {
        viewHandlers.add(new ViewHandler(viewId, TYPE.UPDATE_RESULT));
        return this;
    }

    public ActionsAfter updateFromResult() {
        viewHandlers.add(new ViewHandler(0, TYPE.UPDATE_RESULT));
        return this;
    }

    public ActionsAfter updateFromParam(int viewId, String param) {
        viewHandlers.add(new ViewHandler(viewId, TYPE.UPDATE_PARAM, param));
        return this;
    }

    public ActionsAfter updateFromParam(String param) {
        return updateFromParam(0, param);
    }

    public ActionsAfter preferenceSetName(String nameField) {
        viewHandlers.add(new ViewHandler(0, TYPE.PREFERENCE_SET_NAME, nameField));
        return this;
    }

    public ActionsAfter back() {
        viewHandlers.add(new ViewHandler(0, TYPE.BACK));
        return this;
    }

    public ActionsAfter noActions() {
//        viewHandlers.add(new ViewHandler(0, TYPE.BACK));
        return this;
    }
}
