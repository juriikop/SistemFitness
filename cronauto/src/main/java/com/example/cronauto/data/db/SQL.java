package com.example.cronauto.data.db;

public class SQL {
    public static long dayMillisecond = 24*60*60*1000;
    public static String CATALOG_TAB = "catalog";
    public static String CATALOG = "SELECT * FROM catalog WHERE parent_id = ?";
    public static String CATALOG_0 = "SELECT * FROM catalog WHERE parent_id = 0";
}
