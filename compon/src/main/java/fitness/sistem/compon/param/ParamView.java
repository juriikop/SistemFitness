package fitness.sistem.compon.param;

import java.util.ArrayList;
import java.util.List;

import fitness.sistem.compon.interfaces_classes.Visibility;

public class ParamView {
    public int viewId;
    public int viewIdWithList;
    public String fieldType;
    public int[] layoutTypeId, layoutFurtherTypeId;
    public int indicatorId;
    public int furtherViewId;
    public int viewId_1, viewId_2;
//    public Visibility[] furtherVisibilityArray;
    public int tabId;
    public int arrayLabelId;
    public int splashScreenViewId;
    public ParamModel paramModel;
    public String[] nameFragment;
    public String[] nameFields;
    public int furtherSkip, furtherNext, furtherStart;
    public Visibility[] visibilityArray;
    public boolean selected = false;
    public int maxItemSelect;
    public List<Expanded> expandedList;

    public ParamView(int viewId) {
        this(viewId, "", null, null);
    }

    public ParamView(int viewId, int layoutItemId) {
        this(viewId, "", new int[] {layoutItemId}, null);
    }

    public ParamView(int viewId, int layoutItemId, int layoutFurtherId) {
        this(viewId, "", new int[] {layoutItemId}, new int[] {layoutFurtherId});
    }

    public ParamView(int viewId, int[] layoutTypeId) {
        this(viewId, "select", layoutTypeId, null);
    }

    public ParamView(int viewId, String fieldType, int[] layoutTypeId) {
        this(viewId, fieldType, layoutTypeId, null);
    }

    public ParamView(int viewId, String fieldType, int style) {
        this.viewId = viewId;
        this.fieldType = fieldType;
        layoutTypeId = null;
        layoutFurtherTypeId = null;
        indicatorId = 0;
        furtherViewId = 0;
        tabId = style;
        paramModel = null;
        arrayLabelId = 0;
        nameFragment = null;
    }

    public ParamView(int viewId, String[] nameFragment) {
        this.viewId = viewId;
        this.fieldType = "";
        this.layoutTypeId = null;
        this.layoutFurtherTypeId = null;
        indicatorId = 0;
        furtherViewId = 0;
        tabId = 0;
        paramModel = null;
        arrayLabelId = 0;
        this.nameFragment = nameFragment;
    }

    public ParamView(int viewId, String[] nameFragment, int[] containerId) {
        this.viewId = viewId;
        this.fieldType = "";
        this.layoutTypeId = containerId;
        this.layoutFurtherTypeId = null;
        indicatorId = 0;
        furtherViewId = 0;
        tabId = 0;
        paramModel = null;
        arrayLabelId = 0;
        this.nameFragment = nameFragment;
    }

    public ParamView(int viewId, String fieldType, int[] layoutTypeId, int[] layoutFurtherTypeId) {
        this.viewId = viewId;
        this.fieldType = fieldType;
        this.layoutTypeId = layoutTypeId;
        this.layoutFurtherTypeId = layoutFurtherTypeId;
        indicatorId = 0;
        furtherViewId = 0;
        tabId = 0;
        paramModel = null;
        arrayLabelId = 0;
        nameFragment = null;
    }

    public ParamView setIndicator(int indicatorId) {
        this.indicatorId = indicatorId;
        return this;
    }

    public ParamView setTab(int tabId, ParamModel mp) {
        this.tabId = tabId;
        paramModel = mp;
        return this;
    }

    public ParamView setTab(int tabId, int arrayLabelId) {
        this.tabId = tabId;
        this.arrayLabelId = arrayLabelId;
        return this;
    }

    public ParamView setFurtherView(int furtherViewId) {
        this.furtherViewId = furtherViewId;
        return this;
    }

    public ParamView setFurtherBtn(int skip, int next, int stert) {
        furtherSkip = skip;
        furtherNext = next;
        furtherStart = stert;
        return this;
    }

//    public ParamView setFurtherView(int furtherViewId, Visibility ... args) {
//        this.furtherViewId = furtherViewId;
//        furtherVisibilityArray = args;
//        return this;
//    }

    public ParamView setSplashScreen(int splashScreenViewId) {
        this.splashScreenViewId = splashScreenViewId;
        return this;
    }

    public ParamView selected() {
        selected = true;
        maxItemSelect = -1;
        return this;
    }

    public ParamView selected(int maxItemSelect) {
        selected = true;
        this.maxItemSelect = maxItemSelect;
        return this;
    }

    public ParamView expanded(int expandedId, int rotateId, ParamModel paramModel) {
        if (expandedList == null) {
            expandedList = new ArrayList<>();
        }
        Expanded exp = new Expanded();
        exp.expandedId = expandedId;
        exp.rotateId = rotateId;
        exp.expandModel = paramModel;
        exp.expandNameField = "";
        expandedList.add(exp);
        return this;
    }

    public ParamView expanded(int expandedId, int rotateId, String nameField) {
        if (expandedList == null) {
            expandedList = new ArrayList<>();
        }
        Expanded exp = new Expanded();
        exp.expandedId = expandedId;
        exp.rotateId = rotateId;
        exp.expandModel = null;
        exp.expandNameField = nameField;
        expandedList.add(exp);
        return this;
    }

    public ParamView visibilityManager(Visibility ... args) {
        visibilityArray = args;
        return this;
    }

    public static Visibility visibility(int viewId, String nameField) {
        return new Visibility(0, viewId, nameField);
    }

    public class Expanded {
        public int expandedId, rotateId;
        public ParamModel expandModel;
        public String expandNameField;
    }
//
//    public class Visibility {
//        int viewId;
//        String nameField;
//
//        public Visibility(int viewId, String nameField) {
//            this.viewId = viewId;
//            this.nameField = nameField;
//        }
//    }

}
