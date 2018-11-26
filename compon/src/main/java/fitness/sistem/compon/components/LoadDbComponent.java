package fitness.sistem.compon.components;

import android.util.Log;

import java.util.Date;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.BaseDB;
import fitness.sistem.compon.db.RemoteToLocale;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.IDbListener;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.param.ParamModel;

public class LoadDbComponent extends BaseComponent{
    ParamModel paramModel;
    protected long duration, newDate;

    public LoadDbComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        paramModel = paramMV.paramModel;
    }

    @Override
    public void actual() {
        duration = paramModel.duration;
        if (duration > 0) {
            newDate = new Date().getTime();
            long oldDate = ComponGlob.getInstance().updateDB.getDate(paramModel.updateTable);
            if ((newDate - oldDate) > duration) {
                new RemoteToLocale(iBase, paramModel.updateUrl, paramModel.updateTable, paramModel.updateAlias, dbListener);
            }
        }
    }

    @Override
    public void changeData(Field field) {

    }

    IDbListener dbListener = new IDbListener() {
        @Override
        public void onResponse(IBase iBase, ListRecords listRecords, String table, String nameAlias) {
            BaseDB baseDB = ComponGlob.getInstance().baseDB;
            baseDB.insertListRecord(iBase, table, listRecords, nameAlias);
            ComponGlob.getInstance().updateDB.add(paramModel.updateTable, newDate);
        }
    };
}
