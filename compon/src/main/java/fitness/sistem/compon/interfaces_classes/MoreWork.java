package fitness.sistem.compon.interfaces_classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.components.MultiComponents;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.Record;

public class MoreWork {
    public IBase iBase;
    public MultiComponents multiComponents;
    public Context context;

    public void setParam(IBase iBase, MultiComponents multiComponents) {
        this.iBase = iBase;
        this.multiComponents = multiComponents;
        context = iBase.getBaseActivity();
    }

    public void afterBindViewHolder(Record record, RecyclerView.ViewHolder holder) {

    }

    public void beforeProcessingResponse(Field response, BaseComponent baseComponent) {

    }

    public void clickView(View viewClick, View parentView,
                          BaseComponent baseComponent, Record rec, int position) {

    }
}
