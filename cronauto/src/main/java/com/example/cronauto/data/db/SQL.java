package com.example.cronauto.data.db;

public class SQL {
    public static long dayMillisecond = 24*60*60*1000;
    public static String DB_NAME = "db_cron";

    public static String CATALOG_TAB = "catalog";
    public static String CATALOG_FIELDS = "catalog_id integer primary key, parent_id integer, catalog_name text";
    public static String CATALOG_ALIAS = "catalog_id,ID;parent_id,IBLOCK_SECTION_ID;catalog_name,NAME";
    public static String CATALOG = "SELECT * FROM catalog WHERE parent_id = ?";
    public static String CATALOG_0 = "SELECT * FROM catalog WHERE parent_id = 0;";

    public static String PROPERTY = "property";
    public static String PROPERTY_FIELDS = "property_id INTEGER PRIMARY KEY, product_id INTEGER, name TEXT, value TEXT";
    public static String PROPERTY_ID_PRODUCT = "SELECT * FROM property WHERE product_id = ?";

    public static String PRODUCT_TAB = "product";
    public static String PRODUCT_INDEX_NAME = "prod_ind";
    public static String PRODUCT_INDEX_COLUMN = "catalog_id";
    public static String PRODUCT_FIELDS = "product_id integer primary key, catalog_id integer, product_name TEXT, catalog_code TEXT, picture TEXT, " +
            "bar_code, oem TEXT, price REAL, product_code TEXT, new_product INTEGER, extra_bonus REAL, measure TEXT, quantity TEXT, " +
            "analog TEXT, gift integer";

    public static String PRODUCT_ALIAS = "product_id,ID;catalog_id,SECTION_ID;product_name,NAME;catalog_code,CATALOG_CODE;picture,DETAIL_PICTURE;bar_code,BAR_CODE;" +
            "oem,OEM;price,PRICE;product_code,PRODUCT_CODE_1C;new_product,NEW_PRODUCT;extra_bonus,EXTRA_BONUS;measure,MEASURE;quantity,QUANTITY;" +
            "analog,ANALOG";

    public static String PRODUCT_IN_CATALOG = "SELECT * FROM product WHERE catalog_id = ?";

    public static String PRODUCT_E_BONUS = "SELECT * FROM product WHERE extra_bonus > 0 ";

    public static String CATALOG_L1_L3 = "SELECT * FROM product, (SELECT catalog.catalog_id FROM catalog, " +
            "(SELECT * FROM catalog WHERE parent_id = ?) AS cat1 WHERE catalog.parent_id = cat1.catalog_id) AS cat " +
            "WHERE product.catalog_id = cat.catalog_id";

    public static String CATALOG_L2_L3 = "SELECT * FROM product, (SELECT catalog_id FROM catalog WHERE parent_id = ?) AS cat " +
            "WHERE product.catalog_id = cat.catalog_id";

    public static String PRODUCT_IN_ORDER = "SELECT product.product_name, product.price, " +
            "OrderProduct.productId, OrderProduct.orderId, OrderProduct.count, OrderProduct.productOrderId " +
            "FROM OrderProduct, product WHERE OrderProduct.orderId = ? AND OrderProduct.productId = product.product_id";

    public static String[] PRODUCT_QUERY_ARRAY = {"expandedLevel", CATALOG_L1_L3, CATALOG_L2_L3, PRODUCT_IN_CATALOG};
}
