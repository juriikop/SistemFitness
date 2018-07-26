package fitness.sistem.compon.components;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamComponent;

public class PopUpComponent extends BaseComponent {

    View view;
    public PopupMenu popupMenu;

    public PopUpComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        view = parentLayout.findViewById(paramMV.paramView.viewId);
        if (view == null) {
            iBase.log( "Не найден View в PopUpComponent " + paramMV.nameParentComponent);
            return;
        }
        listData = new ListRecords();
        view.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupMenu.show();
        }
    };

    @Override
    public void changeData(Field field) {
        if (listData == null) return;
        listData.clear();
        listData.addAll((ListRecords) field.value);
        Context wrapper = new ContextThemeWrapper(activity, paramMV.paramView.tabId);
        popupMenu = new PopupMenu(wrapper, view);
        Menu m = popupMenu.getMenu();
        int ik = listData.size();
        for (int i = 0; i < ik; i++) {
            m.add(0, i, i, listData.get(i).getString(paramMV.paramView.fieldType));
        }
        popupMenu.setOnMenuItemClickListener(menuItemClick);
    }

    PopupMenu.OnMenuItemClickListener menuItemClick = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = item.getItemId();
            Record rec = listData.get(position);
            clickItem.onClick(null, null, position, rec);
//            iBase.customClickListenet(paramMV.paramView.viewId, position, rec);
            return false;
        }
    };
}
