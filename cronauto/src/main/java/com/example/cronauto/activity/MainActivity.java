package com.example.cronauto.activity;

import com.example.cronauto.data.network.Api;
import com.example.cronauto.params.CronListScreens;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public String getNameScreen() {
        return CronListScreens.SPLASH;
    }

//    @Override
//    public void initView() {
////        ComponGlob.getInstance().baseDB.remoteToLocale(this, Api.DB_CATALOG,
////                "catalog", "catalog_id,ID;parent_id,IBLOCK_SECTION_ID;catalog_name,NAME");
//    }
}
