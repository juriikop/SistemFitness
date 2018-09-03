package fitness.sistem.sistemfitness.network;

import fitness.sistem.compon.param.AppParams;
import com.example.cronauto.dialogs.ErrorDialog;
import com.example.cronauto.dialogs.ProgressDialog;

public class MyAppParams extends AppParams {
    @Override
    public void setParams() {
        baseUrl =  "http://stage.toplivo.branderstudio.com:8086/api/v1/";
        nameTokenInHeader = "X-Auth-Token";
        nameLanguageInHeader = "Accept-Language";
        paginationPerPage = 30;
        paginationNameParamPerPage = "itemsPerPage";
        paginationNameParamNumberPage = "page";
        classProgress = ProgressDialog.class;
        classErrorDialog = ErrorDialog.class;
//        errorDialogViewId = R.id.error_dialog;
    }
}
