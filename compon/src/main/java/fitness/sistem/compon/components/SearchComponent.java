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
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.tools.StaticVM;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchComponent extends BaseComponent {

    public View viewSearch;
    RecyclerView recycler;
    BaseProviderAdapter adapter;
    Handler handler = new Handler();

    public SearchComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        viewSearch = parentLayout.findViewById(paramMV.viewSearchId);
        if (viewSearch instanceof IComponent) {

        } else if (viewSearch instanceof EditText){
            ((EditText) viewSearch).addTextChangedListener(new Watcher());
        } else {
            Log.i(TAG, "View для поиска должно быть IComponent или EditText в " + paramMV.nameParentComponent);
            return;
        }
        if (paramMV.paramView == null || paramMV.paramView.viewId == 0) {
            recycler = (RecyclerView) StaticVM.findViewByName(parentLayout, "recycler");
        } else {
            recycler = (RecyclerView) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (recycler == null) {
            Log.i(TAG, "Не найден RecyclerView в " + paramMV.nameParentComponent);
            return;
        }
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
//        provider.setData(listData);
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
                Log.i(TAG, "Не найден SplashView в " + paramMV.nameParentComponent);
            }
        }
        iBase.sendEvent(paramMV.paramView.viewId);
    }

    public class Watcher implements TextWatcher{

        private String searchString = "";
        private Runnable task;
        private Runnable task1 = new Runnable() {
            @Override
            public void run() {
                ComponGlob.getInstance().addParamValue(nameParam, searchString);
                actual();
//                    if(changeSearchText != null) {
////                        if (validEdit) {
//                        changeSearchText.onChange(st);
////                        } else {
////                            changeSearchText.onChange("");
////                        }
//                    }
            }
        };
        String nameParam;

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
            searchString = s.toString();
            if(task != null) {
                handler.removeCallbacks(task);
            }
            task = task1;
//            task = new Runnable() {
//                @Override
//                public void run() {
//                    ComponGlob.getInstance().addParamValue(nameParam, st);
//                    actual();
////                    if(changeSearchText != null) {
//////                        if (validEdit) {
////                        changeSearchText.onChange(st);
//////                        } else {
//////                            changeSearchText.onChange("");
//////                        }
////                    }
//                }
//            };
            handler.postDelayed(task, 700);
        }
    }

//    private TextWatcher watcher = new TextWatcher() {
//
//        //        private boolean validEdit;
//        private Runnable task;
//
//        public TextWatcher() {
//            String[] stAr = paramMV.paramModel.param.split(",");
//            String nameParam = stAr[0];
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            final String st = s.toString();
//            if(task != null) {
//                handler.removeCallbacks(task);
//            }
//            task = new Runnable() {
//                @Override
//                public void run() {
//                    ComponGlob.getInstance().addParamValue(nameParam, st);
//                    actual();
////                    if(changeSearchText != null) {
//////                        if (validEdit) {
////                        changeSearchText.onChange(st);
//////                        } else {
//////                            changeSearchText.onChange("");
//////                        }
////                    }
//                }
//            };
//            handler.postDelayed(task, 700);
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////            validEdit = after > 0;
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//    };
}
