package com.example.cronauto.params;


import com.example.cronauto.dialogs.ErrorDialog;
import com.example.cronauto.dialogs.ProgressDialog;

import fitness.sistem.compon.param.AppParams;

public class CronAppParams extends AppParams {
    @Override
    public void setParams() {
        baseUrl =  "https://kronavto.ua/api/";
        nameTokenInHeader = "Authorization";
        paginationPerPage = 30;
        paginationNameParamPerPage = "itemsPerPage";
        paginationNameParamNumberPage = "page";
        classProgress = ProgressDialog.class;
        classErrorDialog = ErrorDialog.class;
//        errorDialogViewId = R.id.error_dialog;
    }
}
