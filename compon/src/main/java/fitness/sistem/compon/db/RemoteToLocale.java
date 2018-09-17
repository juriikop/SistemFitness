package fitness.sistem.compon.db;

import fitness.sistem.compon.base.BasePresenter;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.IDbListener;
import fitness.sistem.compon.interfaces_classes.IPresenterListener;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.param.ParamModel;

public class RemoteToLocale {

    private String table, nameAlias;
    private IDbListener dbListener;

    public RemoteToLocale(IBase iBase, String url, String table, String nameAlias, IDbListener dbListener) {
        this.nameAlias = nameAlias;
        this.table = table;
        this.dbListener = dbListener;
        ParamModel paramModel = new ParamModel(url);
        new BasePresenter(iBase, paramModel, null, null, listener);
    }

    IPresenterListener listener = new IPresenterListener() {
        @Override
        public void onResponse(Field response) {
            ListRecords listRecords = (ListRecords) response.value;
            dbListener.onResponse(listRecords, table, nameAlias);
        }
    };
}
