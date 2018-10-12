package fitness.sistem.compon.custom_components;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeScanner extends ZXingScannerView {
    public BarcodeScanner(Context context) {
        super(context);
    }

    public BarcodeScanner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }


}
