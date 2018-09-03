package com.example.cronauto.test_internrt;

import android.os.Handler;

import com.example.cronauto.data.network.Api;

import java.util.Map;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseInternetProvider;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.json_simple.SimpleRecordToJson;

public class TestInternetProvider extends BaseInternetProvider {
    private Handler handler;

    @Override
    public void setParam(int method, String url, Map<String, String> headers,
                         String data, InternetProviderListener listener) {
        super.setParam(method, url, headers, data, listener);
        handler = new Handler();
        handler.postDelayed(result, 1000);
//        result.run();
    }

    Runnable result = new Runnable() {
        @Override
        public void run() {
            String request = url.replace(ComponGlob.getInstance().appParams.baseUrl, "");
            int i = request.indexOf("?");
            if (i > -1) {
                request = request.substring(0, i);
            }
            listener.response(jsonResult(request));
        }
    };

    private String jsonResult(String request) {
        switch (request) {
            case Api.ORDER_LOG: return setOrderLog();
        }
        return null;
    }

    private String setOrderLog() {
        String st = "[{\"bonus\":234.0,\"listProduct\":[{\"amount\":125.0,\"count\":5,\"number\":0,\"price\":25.0,\"productId\":0,\"productName\":\"Radiator bolshoy ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":375.0,\"count\":3,\"number\":0,\"price\":125.0,\"productId\":0,\"productName\":\"Radiator bolshoy 11111 qqqqqqqqqqqqqqqq \\nwwwwwwwwwwwwwwwww eeeeeeeeeeeeeeee\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":120.0,\"count\":4,\"number\":0,\"price\":30.0,\"productId\":0,\"productName\":\"Swecha bolshoy ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":250.0,\"bonus\":40.0,\"type\":2,\"expanded\":false,\"expandedLevel\":0}],\"orderId\":1234,\"status\":0,\"type\":0,\"expanded\":false,\"expandedLevel\":0},{\"bonus\":12.0,\"listProduct\":[{\"amount\":125.0,\"count\":5,\"number\":0,\"price\":25.0,\"productId\":0,\"productName\":\"Radiator bolshoy ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":375.0,\"count\":3,\"number\":0,\"price\":125.0,\"productId\":0,\"productName\":\"Radiator bolshoy 11111 Radiator bolshoy 11111 Radiator bolshoy 11111\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":120.0,\"count\":4,\"number\":0,\"price\":30.0,\"productId\":0,\"productName\":\"Swecha bolshoy ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":375.0,\"count\":3,\"number\":0,\"price\":125.0,\"productId\":0,\"productName\":\"Radiator bolshoy 2222\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":120.0,\"count\":4,\"number\":0,\"price\":30.0,\"productId\":0,\"productName\":\"Swecha bolshoy 2222 ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":280.0,\"bonus\":54.0,\"type\":2,\"expanded\":false,\"expandedLevel\":0}],\"orderId\":2345,\"status\":0,\"type\":0,\"expanded\":false,\"expandedLevel\":0},{\"bonus\":24.0,\"listProduct\":[{\"amount\":125.0,\"count\":5,\"number\":0,\"price\":25.0,\"productId\":0,\"productName\":\"Radiator bolshoy ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":375.0,\"count\":3,\"number\":0,\"price\":125.0,\"productId\":0,\"productName\":\"Radiator bolshoy 11111\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":120.0,\"count\":4,\"number\":0,\"price\":30.0,\"productId\":0,\"productName\":\"Swecha bolshoy ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":375.0,\"count\":3,\"number\":0,\"price\":125.0,\"productId\":0,\"productName\":\"Radiator bolshoy 2222\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":120.0,\"count\":4,\"number\":0,\"price\":30.0,\"productId\":0,\"productName\":\"Swecha bolshoy 222 ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":150.0,\"bonus\":31.0,\"type\":2,\"expanded\":false,\"expandedLevel\":0}],\"orderId\":34567,\"status\":1,\"type\":0,\"expanded\":false,\"expandedLevel\":0},{\"bonus\":34.0,\"listProduct\":[{\"amount\":125.0,\"count\":5,\"number\":0,\"price\":25.0,\"productId\":0,\"productName\":\"Radiator bolshoy ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":375.0,\"count\":3,\"number\":0,\"price\":125.0,\"productId\":0,\"productName\":\"Radiator bolshoy 11111\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":120.0,\"count\":4,\"number\":0,\"price\":30.0,\"productId\":0,\"productName\":\"Swecha bolshoy ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":125.0,\"count\":5,\"number\":0,\"price\":25.0,\"productId\":0,\"productName\":\"Radiator 222222 bolshoy ochen\",\"type\":1,\"expanded\":false,\"expandedLevel\":0},{\"amount\":250.0,\"bonus\":40.0,\"type\":2,\"expanded\":false,\"expandedLevel\":0}],\"orderId\":45678,\"status\":1,\"type\":0,\"expanded\":false,\"expandedLevel\":0}]";
        return st;
    }
}
