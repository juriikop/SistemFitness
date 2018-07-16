package fitness.sistem.compon.components;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.BaseProvider;
import fitness.sistem.compon.base.BaseProviderAdapter;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.IComponent;
import fitness.sistem.compon.interfaces_classes.Navigator;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.tools.StaticVM;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchComponent extends BaseComponent {

    public View viewSearch;
    RecyclerView recycler;
    BaseProviderAdapter adapter;
    Handler handler = new Handler();
    public String nameSearch;
    private boolean isChangeText;

    public SearchComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        viewSearch = parentLayout.findViewById(paramMV.viewSearchId);
        nameSearch = activity.getResources().getResourceEntryName(paramMV.viewSearchId);
        isChangeText = true;
        if (viewSearch instanceof EditText){
            ((EditText) viewSearch).addTextChangedListener(new Watcher());
        } else {
            iBase.log("View для поиска должно быть IComponent или EditText в " + paramMV.nameParentComponent);
            return;
        }
        if (paramMV.paramView == null || paramMV.paramView.viewId == 0) {
            recycler = (RecyclerView) StaticVM.findViewByName(parentLayout, "recycler");
        } else {
            recycler = (RecyclerView) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (recycler == null) {
            iBase.log("Не найден RecyclerView в " + paramMV.nameParentComponent);
            return;
        }
        if (navigator == null) {
            navigator = new Navigator();
        }
        navigator.viewHandlers.add(0, new ViewHandler(0, ViewHandler.TYPE.SELECT));
        listData = new ListRecords();
        provider = new BaseProvider(listData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recycler.setLayoutManager(layoutManager);
        adapter = new BaseProviderAdapter(this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void changeData(Field field) {
        listData.clear();
        listData.addAll((ListRecords) field.value);
        adapter.notifyDataSetChanged();
        int splash = paramMV.paramView.splashScreenViewId;
        if (splash != 0) {
            View v_splash = parentLayout.findViewById(splash);
            if (v_splash != null) {
                if (listData.size() > 0) {
                    v_splash.setVisibility(GONE);
                } else {
                    v_splash.setVisibility(VISIBLE);
                }
            } else {
                iBase.log("Не найден SplashView в " + paramMV.nameParentComponent);
            }
        }
        iBase.sendEvent(paramMV.paramView.viewId);
    }

    @Override
    public void actual() {
        listData.clear();
        adapter.notifyDataSetChanged();
        super.actual();
    }

    public void clickAdapter(RecyclerView.ViewHolder holder, View view, int position) {
        Record record = provider.get(position);
        String st = record.getString(nameSearch);
        isChangeText = false;
        ((EditText) viewSearch).setText(st);
        isChangeText = true;
        ComponGlob.getInstance().setParam(record);
        if (paramMV.hide) {
            recycler.setVisibility(GONE);
        }
        if (navigator.viewHandlers.size() > 1) {
            super.clickAdapter(holder, view, position);
        }
    }

    public class Watcher implements TextWatcher{

        private String searchString = "";
        String nameParam;

        private Runnable task = new Runnable() {
            @Override
            public void run() {
                ComponGlob.getInstance().addParamValue(nameParam, searchString);
                if (recycler.getVisibility() == GONE) {
                    recycler.setVisibility(VISIBLE);
                }
                actual();
            }
        };

        public Watcher() {
            String[] stAr = paramMV.paramModel.param.split(",");
            nameParam = stAr[0];
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isChangeText) {
                searchString = s.toString();
                handler.removeCallbacks(task);
                handler.postDelayed(task, 700);
            }
        }
    }

}
