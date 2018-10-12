package fitness.sistem.compon.components;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
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

    public BarcodeComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        if (paramMV.paramView == null || paramMV.paramView.viewId != 0) {
            scanner = (BarcodeScanner) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (scanner == null) {
            iBase.log("Не найден BarcodeScanner в " + paramMV.nameParentComponent);
            return;
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
            scanner = null;
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
            if (requestCode == Constants.REQUEST_CODE_MAP_PERMISSION && grantResults.length > 0) {
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
