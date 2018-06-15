package fitness.sistem.sistemfitness.network;

import fitness.sistem.compon.network.NetworkParams;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.dialogs.ErrorDialog;
import fitness.sistem.sistemfitness.dialogs.ProgressDialog;

public class MyNetworkParams extends NetworkParams {
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
        errorDialogViewId = R.id.error_dialog;
    }
}
