package fitness.sistem.compon.base;

import android.text.Html;
import android.util.Log;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.IPresenterListener;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.JsonSimple;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamModel;
import fitness.sistem.compon.providers.VolleyInternetProvider;
import fitness.sistem.compon.tools.PreferenceTool;

import java.util.HashMap;
import java.util.Map;

import static fitness.sistem.compon.json_simple.Field.TYPE_CLASS;
import static fitness.sistem.compon.json_simple.Field.TYPE_STRING;

public class BasePresenter implements BaseInternetProvider.InternetProviderListener {
    private IBase iBase;
    private ParamModel paramModel;
    private Record data;
    Map<String, String> headers;
    private IPresenterListener listener;
    private boolean isCanceled;
    private BaseInternetProvider internetProvider;
    protected JsonSimple jsonSimple = new JsonSimple();
    protected String nameJson, json, url;
    protected int method;

    public BasePresenter(IBase iBase, ParamModel paramModel,
                         Map<String, String> headersPar, Record data, IPresenterListener listener) {
        this.iBase = iBase;
        this.paramModel = paramModel;
        this.data = data;
        this.listener = listener;
        this.headers = headersPar;
        if (headers == null) {
            headers = new HashMap<>();
        }

        String nameToken = ComponGlob.getInstance().appParams.nameTokenInHeader;
        String token = PreferenceTool.getSessionToken();
        if (nameToken.length() > 0 && token.length() > 0) {
//            headers.put(nameToken, "bceee76d3c7d761c9ec92c286fb8bebcefb4225c311bb87e");
            headers.put(nameToken, token);
        }
        String nameLanguage = ComponGlob.getInstance().appParams.nameLanguageInHeader;
        if (nameLanguage.length() > 0) {
            headers.put(nameLanguage, ComponGlob.getInstance().language);
        }

        this.method = paramModel.method;
        long duration = paramModel.duration;
        if (method == ParamModel.GET) {
            String st = ComponGlob.getInstance().installParam(paramModel.param, paramModel.typeParam, paramModel.url);
            url = paramModel.url + st;
        } else {
            url = paramModel.url;
        }
        Log.d("QWERT","BasePresenter BasePresenter");
        if (duration > 0) {
            nameJson = url;
            json = ComponGlob.getInstance().cacheWork.getJson(nameJson);
            listener.onResponse(jsonSimple.jsonToModel(Html.fromHtml(json).toString()));
            if (json == null) {
                startInternetProvider();
            }
        } else {
            startInternetProvider();
        }

    }

    public void startInternetProvider() {
        Log.d("QWERT","BasePresenter startInternetProvider +++++");
        isCanceled = false;
        if (paramModel.internetProvider == null) {
            internetProvider = new VolleyInternetProvider();
            internetProvider.setParam(paramModel.method,
                    url, headers, jsonSimple.ModelToJson(data), this);
        } else {
            BaseInternetProvider bip = null;
            try {
                bip = (BaseInternetProvider) paramModel.internetProvider.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (bip != null) {
                internetProvider = bip.getThis();
                internetProvider.setParam(paramModel.method,
                        url, headers, jsonSimple.ModelToJson(data), this);
            } else {
                Log.i("SMPL", "Ошибка создания internetProvider");
            }
        }
        Log.d("QWERT","BasePresenter startInternetProvider *********************");
        iBase.addInternetProvider(internetProvider);
        iBase.progressStart();
    }

    public void cancel() {
        isCanceled = true;
        if (internetProvider != null) {
            internetProvider.cancel();
        }
    }

    @Override
    public void response(String response) {
        iBase.progressStop();
        if (response == null) {
            iBase.showDialog("", "no response", null);
        }
        if (paramModel.duration > 0) {
            ComponGlob.getInstance().cacheWork.addCasche(url,
                    paramModel.duration, response);
        }
        if ( ! isCanceled) {
            if (response.length() == 0) {
                listener.onResponse(new Field("", TYPE_STRING, ""));
            } else {
                Field f = jsonSimple.jsonToModel(response);
                Field f1 = ((Record) f.value).getField("data");
                if (f1 != null) {
                    if (f1.type == TYPE_CLASS) {
                        Field f2 = ((Record) f1.value).getField("items");
                        if (f2 != null) {
                            listener.onResponse(f2);
                        } else {
                            listener.onResponse(f1);
                        }
                    } else {
                        listener.onResponse(f1);
                    }
                } else {
                    iBase.showDialog("", "no response 11111111111", null);
                }
            }
        }
    }

    @Override
    public void error(int statusCode, String message) {
        iBase.progressStop();
        iBase.showDialog(statusCode, message, null);
    }

}
