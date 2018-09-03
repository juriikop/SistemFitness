package fitness.sistem.compon.db;

import java.util.Date;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.IDbListener;
import fitness.sistem.compon.interfaces_classes.IPresenterListener;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.param.ParamModel;

public class GetDbPresenter {

    protected IBase iBase;
    protected IPresenterListener listener;
    protected ParamModel paramModel;
    protected long duration, newDate;
    protected String[] param;

    public GetDbPresenter(BaseComponent baseComponent, String[] param, IPresenterListener listener) {
        iBase = baseComponent.iBase;
        this.listener = listener;
        this.param = param;
        paramModel = baseComponent.paramMV.paramModel;
        duration = paramModel.duration;
        if (duration > 0) {
            newDate = new Date().getTime();
            long oldDate = ComponGlob.getInstance().updateDB.getDate(paramModel.updateTable);
            if ((newDate - oldDate) > duration) {
                new RemoteToLocale(iBase, paramModel.updateUrl, paramModel.updateTable, paramModel.updateAlias, dbListener);
//                ComponGlob.getInstance().baseDB.remoteToLocale(iBase, paramModel.updateUrl, paramModel.updateTable,
//                        "catalog_id,ID;parent_id,IBLOCK_SECTION_ID;catalog_name,NAME");
            } else {

            }
        } else {

        }
    }

    IDbListener dbListener = new IDbListener() {
        @Override
        public void onResponse(ListRecords listRecords, String table, String nameAlias) {
            ComponGlob.getInstance().baseDB.insertListRecord(table, listRecords, nameAlias);
            ComponGlob.getInstance().updateDB.add(paramModel.updateTable, newDate);
            Field f = ComponGlob.getInstance().baseDB.get(paramModel.url, param);
            listener.onResponse(f);
        }
    };
}
