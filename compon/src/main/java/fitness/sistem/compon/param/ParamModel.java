package fitness.sistem.compon.param;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.interfaces_classes.DataFieldGet;
import fitness.sistem.compon.interfaces_classes.Filters;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.Record;

import java.util.ArrayList;
import java.util.List;

public class ParamModel <T> {
    public int method;
    public String url;
    public String param;
    public long duration;
    public static String PARENT_MODEL = "PARENT_MODEL";
    public static int GET = 0;
    public static int POST = 1;
    public static final int GET_DB = 10;
    public static int POST_DB = 11;
    public static final int PARENT = 100;
    public static final int FIELD = 101;
    public static final int ARGUMENTS = 102;
    public static final int STRINGARRAY = 103;
    public static final int DATAFIELD = 104;
    public static int defaultMethod = GET;
    public String nameField, nameFieldTo;
    public DataFieldGet dataFieldGet;
    public String nameTakeField;
    public List<Record> addRecordBegining;
    public Field field;
    public Class<T>  internetProvider;
    public enum TypeParam {MAP, NAME, SLASH};
    public TypeParam typeParam = TypeParam.NAME;
    public boolean isPagination;
    public int paginationPerPage;
    public int stringArray;
    public String paginationNameParamPerPage;
    public String paginationNameParamNumberPage;
    public String updateTable, updateUrl, updateAlias;

    public Filters filters;
//    public FilterParam[] filterParams;
//    public int progressId;

    public static void setDefaultMethod(int method) {
        defaultMethod = method;
    }

    public ParamModel() {
        this(PARENT, PARENT_MODEL, "", -1);
    }

    public ParamModel(int method) {
        this.method = method;
        param = "";
    }
    public ParamModel(String url) {
        this(url, "", -1);
    }

    public ParamModel(int method, String urlOrNameParent) {
        this(method, urlOrNameParent, "", -1);
    }

    public ParamModel(Field field) {
        this(FIELD, "", "", -1);
        this.field = field;
    }

    public ParamModel(DataFieldGet dataFieldGet) {
        this(DATAFIELD, "", "", -1);
        this.dataFieldGet = dataFieldGet;
    }

    public ParamModel(String url, String param) {
        this(url, param, -1);
    }
    public ParamModel(int method, String urlOrNameParent, String paramOrField) {
        this(method, urlOrNameParent, paramOrField, -1);
    }

    public ParamModel(String url, String param, long duration) {
        this(defaultMethod, url, param, duration);
    }

    public ParamModel(int method, String url, String param, long duration) {
        this.method = method;
        if (method == PARENT) {
                if ((url == null || url.length() == 0)) {
                    this.url = PARENT_MODEL;
                }
        } else {
            if (url.startsWith("http") || method == POST_DB
                    || method == GET_DB) {
                this.url = url;
            } else {
                this.url = ComponGlob.getInstance().appParams.baseUrl + url;
            }
//            if (method == POST) {
//                this.url = url;
//            } else {
//                if (url.startsWith("http")) {
//                    this.url = url;
//                } else {
//                    this.url = ComponGlob.getInstance().appParams.baseUrl + url;
//                }
//            }
        }
        this.param = param;
        this.duration = duration;
        nameField = null;
        nameTakeField = null;
        internetProvider = null;
    }

    public ParamModel updateDB(String table, String url, long duration) {
        return updateDB(table, url, duration, null);
    }

    public ParamModel updateDB(String table, String url, long duration, String nameAlias) {
        updateTable = table;
        updateUrl = url;
        this.duration = duration;
        updateAlias = nameAlias;
        return this;
    }

    public ParamModel internetProvider(Class<T> internetProvider) {
        this.internetProvider = internetProvider;
        return this;
    }

    public ParamModel changeNameField(String nameField, String nameFieldTo) {
        this.nameField = nameField;
        this.nameFieldTo = nameFieldTo;
        return this;
    }

    public ParamModel addToBeginning(Record record) {
        if (addRecordBegining == null) {
            addRecordBegining = new ArrayList<>();
        }
        addRecordBegining.add(record);
        return this;
    }

    public ParamModel addToBeginning(String name, int value) {
        Record record = new Record();
        Field field = new Field(name, Field.TYPE_INTEGER, value);
        record.add(field);
        return addToBeginning(record);
    }

    // Реализация ParamModel обеспечивает получение данных из заданных источников в форме Field.
    // Если данные являются Record и задано nameTakeField, то результатом реализации ParamModel
    // будет Field с именем nameTakeField из этого Record
    public ParamModel takeField(String name) {
        nameTakeField = name;
        return this;
    }

    public ParamModel typeParam(TypeParam typeParam) {
        this.typeParam = typeParam;
        return this;
    }

    public ParamModel pagination() {
        isPagination = true;
        paginationPerPage = ComponGlob.getInstance().appParams.paginationPerPage;
        paginationNameParamPerPage = ComponGlob.getInstance().appParams.paginationNameParamPerPage;
        paginationNameParamNumberPage = ComponGlob.getInstance().appParams.paginationNameParamNumberPage;
        return this;
    }

    public ParamModel pagination(int paginationPerPage, String paginationNameParamPerPage,
                                 String paginationNameParamNumberPage) {
        isPagination = true;
        this.paginationPerPage = paginationPerPage;
        this.paginationNameParamPerPage = paginationNameParamPerPage;
        this.paginationNameParamNumberPage = paginationNameParamNumberPage;
        return this;
    }

    public ParamModel filter(Filters filters) {
        this.filters = filters;
        return this;
    }

}
