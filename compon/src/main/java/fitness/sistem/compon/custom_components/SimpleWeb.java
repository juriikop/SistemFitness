package fitness.sistem.compon.custom_components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.interfaces_classes.IComponent;
import fitness.sistem.compon.interfaces_classes.OnChangeStatusListener;

public class SimpleWeb extends WebView implements IComponent {
    public SimpleWeb(Context context) {
        this(context, null);
    }

    public SimpleWeb(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleWeb(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

    }

    @Override
    public void setData(Object data) {
        String detail = (String) data;
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        loadDataWithBaseURL(ComponGlob.getInstance().appParams.baseUrl, detail,
                "text/html", "utf-8", null);
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void setOnChangeStatusListener(OnChangeStatusListener statusListener) {

    }

    @Override
    public String getString() {
        return null;
    }
}
