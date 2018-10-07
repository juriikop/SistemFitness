package com.example.cronauto.data.db;

import android.content.ContentValues;

import java.util.Date;
import java.util.Random;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseComponent;

public class SQL {
    public static long dayMillisecond = 24*60*60*1000;
    public static String DB_NAME = "db_cron";

    public static String CATALOG_TAB = "catalog";
    public static String CATALOG_FIELDS = "catalog_id integer primary key, parent_id integer, catalog_name text";
    public static String CATALOG_ALIAS = "catalog_id,ID;parent_id,IBLOCK_SECTION_ID;catalog_name,NAME";
    public static String CATALOG = "SELECT * FROM catalog WHERE parent_id = ?";
    public static String CATALOG_0 = "SELECT * FROM catalog WHERE parent_id = 0;";

    public static String PROPERTY_TAB = "property";
    public static String PROPERTY_INDEX_NAME = "property_ind";
    public static String PROPERTY_INDEX_COLUMN = "product_id";
    public static String PROPERTY_FIELDS = "property_id INTEGER PRIMARY KEY, product_id INTEGER, name TEXT, value TEXT";
    public static String PROPERTY_ID_PRODUCT = "SELECT * FROM property WHERE product_id = ?";

    public static String ORDER_TAB = "order_tab";
    public static String ORDER_INDEX_NAME = "order_ind";
    public static String ORDER_INDEX_COLUMN = "orderId";
    public static String ORDER_FIELDS = "ord_ind INTEGER PRIMARY KEY, orderId TEXT, orderName TEXT, status INTEGER, comment TEXT, payBonus INTEGER, date INTEGER";
    public static String ORDER_LIST = "SELECT * FROM order_tab";
//    public static String ORDER_NEW = "INSERT OR REPLACE INTO order_tab (orderId, orderName, status, comment, payBonus, date) VALUES (?, ?, 0, '', 0, ?)";

    public static void ORDER_NEW(BaseComponent component, int recyclerId) {
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
        component.getComponent(recyclerId).actual();
    }

    public static String createOrderId() {
        String alf = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        String st = "" + alf.charAt(random.nextInt(25));
        for (int i = 0; i < 4; i++) {
            int j = random.nextInt(35);
            st+= alf.charAt(j);
        }
        return st;
    }

    public static String PRODUCT_TAB = "product";
    public static String PRODUCT_INDEX_NAME = "prod_ind";
    public static String PRODUCT_INDEX_COLUMN = "catalog_id";
    public static String PRODUCT_FIELDS = "product_id INTEGER primary key, catalog_id INTEGER, product_name TEXT, catalog_code TEXT, picture TEXT, " +
            "bar_code, oem TEXT, price REAL, product_code TEXT, new_product INTEGER, extra_bonus REAL, measure TEXT, quantity INTEGER, " +
            "analog TEXT, gift integer";
    public static String PRODUCT_ALIAS = "product_id,ID;catalog_id,SECTION_ID;product_name,NAME;catalog_code,CATALOG_CODE;picture,DETAIL_PICTURE;bar_code,BAR_CODE;" +
            "oem,OEM;price,PRICE;product_code,PRODUCT_CODE_1C;new_product,NEW_PRODUCT;extra_bonus,EXTRA_BONUS;measure,MEASURE;quantity,QUANTITY;" +
            "analog,ANALOG";

    public static String PRODUCT_ORDER = "product_order";
    public static String PRODUCT_ORDER_INDEX_NAME = "prod_ord_ind";
    public static String PRODUCT_ORDER_INDEX_COLUMN = "orderId";
    public static String PRODUCT_ORDER_FIELDS = "prod_ord INTEGER PRIMARY KEY, orderId INTEGER, product_id INTEGER, count INTEGER";
    public static String PRODUCT_ORDER_PARAM = "orderId,product_id,count";

    public static String PRODUCT_IN_ORDER = "SELECT product.product_name, product.price, " +
            "product_order.productId, product_order.orderId, product_order.count, product_order.productOrderId " +
            "FROM product_order, product WHERE product_order.orderId = ? AND product_order.productId = product.product_id";

    public static String PRODUCT_IN_CATALOG = "SELECT * FROM product WHERE catalog_id = ?";

    public static String PRODUCT_E_BONUS = "SELECT * FROM product WHERE extra_bonus > 0 ";

    public static String CATALOG_L1_L3 = "SELECT * FROM product, (SELECT catalog.catalog_id FROM catalog, " +
            "(SELECT * FROM catalog WHERE parent_id = ?) AS cat1 WHERE catalog.parent_id = cat1.catalog_id) AS cat " +
            "WHERE product.catalog_id = cat.catalog_id";

    public static String CATALOG_L2_L3 = "SELECT * FROM product, (SELECT catalog_id FROM catalog WHERE parent_id = ?) AS cat " +
            "WHERE product.catalog_id = cat.catalog_id";


    public static String[] PRODUCT_QUERY_ARRAY = {"expandedLevel", CATALOG_L1_L3, CATALOG_L2_L3, PRODUCT_IN_CATALOG};

}
