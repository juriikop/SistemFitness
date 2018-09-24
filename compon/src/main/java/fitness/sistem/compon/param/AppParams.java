package fitness.sistem.compon.param;

public abstract class AppParams<T> {
    public String baseUrl;
    public int paginationPerPage = 20;
    public String paginationNameParamPerPage = "";
    public String paginationNameParamNumberPage = "";
    public int NETWORK_TIMEOUT_LIMIT = 60000; // milliseconds
    public int RETRY_COUNT = 1;
    public int LOG_LEVEL = 3;    // 0 - not, 1 - ERROR, 2 - URL, 3 - URL + jsonResponse
    public static String NAME_LOG_NET = "SMPL_NET";
    public static String NAME_LOG_APP = "SMPL_APP";
    public Class<T>  classProgress;
    public Class<T>  classErrorDialog;
    public AppParams() {
        setParams();
    }
    public String nameTokenInHeader = "";
    public String nameLanguageInHeader = "";
    public abstract void setParams();
    public int errorDialogViewId;
}
