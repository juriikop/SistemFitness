package fitness.sistem.compon.providers;

import android.util.Log;

import com.android.volley.VolleyError;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseInternetProvider;
import fitness.sistem.compon.interfaces_classes.IVolleyListener;
import fitness.sistem.compon.volley.VolleyProvider;
import fitness.sistem.compon.volley.VolleyRequest;

import java.util.Map;

public class VolleyInternetProvider extends BaseInternetProvider {
    VolleyRequest request;

    @Override
    public void setParam(int method, String url, Map<String, String> headers,
                         String data, InternetProviderListener listener) {
        super.setParam(method, url, headers, data, listener);
        byte [] dataBytes = null;
        if (data != null) {
            dataBytes = data.getBytes();
        }
//        String st = ComponPrefTool.getUserKey();
//        if (st.length() > 0) {
//            if (headers == null) {
//                headers = new HashMap<>();
//            }
//            headers.put("X-Auth-Token", "bceee76d3c7d761c9ec92c286fb8bebcefb4225c311bb87e");
//
//        }


//        String nameToken = ComponGlob.getInstance().appParams.nameTokenInHeader;
//        if (nameToken.length() > 0) {
//            if (headers == null) {
//                headers = new HashMap<>();
//            }
//            headers.put("X-Auth-Token", "bceee76d3c7d761c9ec92c286fb8bebcefb4225c311bb87e");
//        }
        request = new VolleyRequest(method, url, listenerVolley,
                headers, dataBytes);
        VolleyProvider.getInstance().addToRequestQueue(request);
    }

    @Override
    public void cancel() {
        super.cancel();
        request.cancel();
    }

    IVolleyListener listenerVolley = new IVolleyListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            int statusCode = ERRORINMESSAGE;
            String st = error.toString();
            int status = 0;
            if (error.networkResponse != null) {
                status = error.networkResponse.statusCode;
            }
            Log.d(ComponGlob.getInstance().appParams.NAME_LOG_NET,"VolleyInternetProvider error.toString()="+error.toString()+"< status="
                    + status
                    +"< mes="+error.getMessage()+"< URL="+url
//                    +"< DDD="+error.networkResponse.data
            );
            if (st != null) {
                st = st.toUpperCase();
                if (st.contains("TIMEOUT")) {
                    statusCode = TIMEOUT;
                } else {
                    if (st.contains("NOCONNECTIONERROR")) {
                        statusCode = NOCONNECTIONERROR;
                    } else {
                        if (st.contains("SERVERERROR")) {
                            statusCode = SERVERERROR;
                        } else {
                            if (st.contains("AUTHFAILURE")) {
                                statusCode = AUTHFAILURE;
                            }
                        }
                    }
                }
            }
            listener.error(statusCode, error.getMessage());
        }

        @Override
        public void onResponse(String response) {
            Log.d("QWERT","VolleyInternetProvider +++++ SIZE="+response.length());
            listener.response(response);
        }
    };

}
