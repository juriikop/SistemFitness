package com.example.cronauto.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cronauto.R;
import com.example.cronauto.data.db.SQL;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseActivity;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.components.PlusMinusComponent;
import fitness.sistem.compon.components.RecyclerComponent;
import fitness.sistem.compon.custom_components.PlusMinus;
import fitness.sistem.compon.interfaces_classes.ICustom;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;

public class AddProductActivity extends BaseActivity implements ICustom{
    @BindView(R.id.count) EditText count;
    @BindView(R.id.bonus_v) TextView bonus_v;
    @BindView(R.id.amount) TextView amount;
    @BindView(R.id.more_residue) TextView more_residue;

    private DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");

    private double price, priceBonus;
    private int quantity;
    RecyclerComponent recyclerComponent;
    PlusMinusComponent plusMinusComponent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_product;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this, parentLayout);
        price = paramScreenRecord.getDouble("price");
        priceBonus = paramScreenRecord.getDouble("extra_bonus");
        quantity = paramScreenRecord.getInt("quantity");
        amount.setText(decimalFormat.format(price));
        bonus_v.setText(decimalFormat.format(priceBonus));
        recyclerComponent = (RecyclerComponent) mComponent.getComponent(R.id.recycler);
        plusMinusComponent = (PlusMinusComponent) mComponent.getComponent(R.id.count);
        if (count instanceof PlusMinus) {
            ((PlusMinus) count).setParam(parentLayout, paramScreenRecord, plusMinusComponent);
        }
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
        switch (viewId) {
            case R.id.count :
                if (count == null) return;
                String st = count.getText().toString();
                long c = Long.valueOf(st);
//                amount.setText(decimalFormat.format(price * c));
//                bonus_v.setText(decimalFormat.format(priceBonus * c));
                if (c > quantity) {
                    more_residue.setVisibility(View.VISIBLE);
                } else {
                    more_residue.setVisibility(View.GONE);
                }
                break;
            case R.id.recycler :
                if (field == null || field.value == null || ((ListRecords) field.value).size() == 0) {
                    newOrder();
                    if (recyclerComponent == null) {
                        recyclerComponent = (RecyclerComponent) mComponent.getComponent(R.id.recycler);
                    }
                    recyclerComponent.actual();
                }
                break;
        }
    }

    @OnClick(R.id.create_new)
    public void create_new() {
        if (recyclerComponent.listData.size() < 5) {
            newOrder();
            recyclerComponent.actual();
        }
    }

    private void newOrder() {
        String id = createOrderId();
        long d = new Date().getTime();
        ContentValues cv = new ContentValues();
        cv.put("orderId", id);
        cv.put("orderName", "Заказ "+ id);
        cv.put("status", 0);
        cv.put("comment", "");
        cv.put("payBonus", 0);
        cv.put("date", d);
        ComponGlob.getInstance().baseDB.insertCV(SQL.ORDER_TAB, cv);
    }

    public String createOrderId() {
        String alf = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        String st = "" + alf.charAt(random.nextInt(25));
        for (int i = 0; i < 4; i++) {
            int j = random.nextInt(35);
            st+= alf.charAt(j);
        }
        return st;
    }
}
