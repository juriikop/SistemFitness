package com.example.cronauto.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.cronauto.R;

import butterknife.ButterKnife;
import fitness.sistem.compon.base.BaseActivity;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.components.PlusMinusComponent;
import fitness.sistem.compon.components.RecyclerComponent;
import fitness.sistem.compon.custom_components.PlusMinus;
import fitness.sistem.compon.interfaces_classes.ICustom;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.Record;

public class FilterActivity extends BaseActivity implements ICustom {

    @Override
    public int getLayoutId() {
        return R.layout.activity_filter;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this, parentLayout);

    }


    @Override
    public void customClick(int viewId, int position, Record record) {

    }

    @Override
    public void afterBindViewHolder(int viewId, int position, Record record, RecyclerView.ViewHolder holder) {

    }

    @Override
    public void beforeProcessingResponse(Field response, BaseComponent baseComponent) {

    }

    @Override
    public void clickView(View viewClick, View parentView, BaseComponent baseComponent, Record rec, int position) {

    }

    @Override
    public void receiverWork(Intent intent) {

    }

    @Override
    public void changeValue(int viewId, Field field) {

    }
}
