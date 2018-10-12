package fitness.sistem.compon.custom_components;

import android.content.Context;
import android.util.AttributeSet;

import fitness.sistem.compon.interfaces_classes.IComponent;
import fitness.sistem.compon.interfaces_classes.OnChangeStatusListener;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeScanner extends ZXingScannerView implements IComponent {

    public String result;

    public BarcodeScanner(Context context) {
        super(context);
    }

    public BarcodeScanner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public Object getData() {
        return result;
    }

    @Override
    public void setOnChangeStatusListener(OnChangeStatusListener statusListener) {

    }

    @Override
    public String getString() {
        return null;
    }
}
