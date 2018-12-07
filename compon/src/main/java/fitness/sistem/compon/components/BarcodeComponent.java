package fitness.sistem.compon.components;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.Result;

import fitness.sistem.compon.base.BaseActivity;
import fitness.sistem.compon.custom_components.BarcodeScanner;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.OnResumePause;
import fitness.sistem.compon.interfaces_classes.PermissionsResult;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.tools.Constants;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeComponent extends ButtonComponent {

    public BarcodeScanner scanner;
    private String rawResult;
    private TextView viewResult;
    private View repeat;

    public BarcodeComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        if (paramMV.paramView == null || paramMV.paramView.viewId != 0) {
            scanner = (BarcodeScanner) parentLayout.findViewById(paramMV.paramView.viewId);
        }
//        Log.d("QWERT","BarcodeComponent scanner="+scanner);
        if (scanner == null) {
            iBase.log("Не найден BarcodeScanner в " + paramMV.nameParentComponent);
            return;
        }
        if (paramMV.paramView.layoutTypeId != null) {
            viewResult = (TextView) parentLayout.findViewById(paramMV.paramView.layoutTypeId[0]);
        }
        if (paramMV.paramView.layoutFurtherTypeId != null) {
            repeat = (TextView) parentLayout.findViewById(paramMV.paramView.layoutFurtherTypeId[0]);
            if (repeat != null) {
                repeat.setOnClickListener(repeatListener);
            }
        }

        iBase.setResumePause(resumePause);

        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initiateScannerView();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions();
            }
        }
    }

    View.OnClickListener repeatListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(scanner != null) {
                scanner.setResultHandler(null);
                scanner.stopCamera();
                scanner.setAutoFocus(true);
                scanner.setResultHandler(resultHandler);
                scanner.startCamera();
            }
        }
    };

    OnResumePause resumePause = new OnResumePause() {
        @Override
        public void onResume() {
            if(scanner != null) {
                scanner.setAutoFocus(true);
                scanner.setResultHandler(resultHandler);
                scanner.startCamera();
            }
        }

        @Override
        public void onPause() {
            if(scanner != null) {
                scanner.setResultHandler(null);
                scanner.stopCamera();
            }
        }
    };

    ZXingScannerView.ResultHandler resultHandler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult(Result result) {
            Ringtone r = null;
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                r = RingtoneManager.getRingtone(activity.getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {}
            rawResult = result.getText();
            if (viewResult != buttonView) {
                viewResult.setText(rawResult);
            }
            scanner.result = rawResult;
        }
    };

    public void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.addPermissionsResult(Constants.REQUEST_CODE_CAMERA, permissionsResult);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    Constants.REQUEST_CODE_CAMERA);
        }
    }

    public PermissionsResult permissionsResult = new PermissionsResult() {
        @Override
        public void onPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == Constants.REQUEST_CODE_CAMERA && grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initiateScannerView();
                }
            }
        }
    };

    private void initiateScannerView() {
        scanner.setAutoFocus(true);
    }



}
