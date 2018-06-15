package fitness.sistem.compon.interfaces_classes;

import fitness.sistem.compon.param.ParamModel;

public class SendAndUpdate extends ParamModel {
    public SendAndUpdate(String url) {
        super(ParamModel.POST, url, "", -1);
    }

    public SendAndUpdate(String url, String param) {
        super(ParamModel.POST, url, param, -1);
    }
}
