package fitness.sistem.compon.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

import fitness.sistem.compon.R;
import fitness.sistem.compon.interfaces_classes.IComponent;
import fitness.sistem.compon.interfaces_classes.IValidate;
import fitness.sistem.compon.interfaces_classes.OnChangeStatusListener;
import fitness.sistem.compon.param.AppParams;

public class ComponEditText extends AppCompatEditText implements IComponent, IValidate {

    protected int typeValidate;
    protected final int FILLED = 0, EMAIL = 1, LENGTH = 2, DIAPASON = 3, MIN_LENGTH = 4;
    private int fieldLength;
    private int maxLength;
    private String alias;
    private OnChangeStatusListener statusListener;
    protected String textError = "";
    protected String nameSpace = "http://schemas.android.com/apk/res/android";
    protected TextInputLayout textInputLayout;
    private String minValueText, maxValueText;
    private double minValue, maxValue;
    private OnFocusChangeListener focusChangeListenerInheritor = null;
    private int minLength = -1;

    public ComponEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ComponEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ComponEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Simple,
                0, 0);
        try {
            typeValidate = a.getInt(R.styleable.Simple_typeValidate, -1);
            textError = a.getString(R.styleable.Simple_textError);
            if (textError == null) {
                textError = "";
            }
            minValueText = a.getString(R.styleable.Simple_minValue);
            maxValueText = a.getString(R.styleable.Simple_maxValue);
            minLength = a.getInt(R.styleable.Simple_minLength, -1);
            fieldLength = a.getInt(R.styleable.Simple_fieldLength, -1);
        } finally {
            a.recycle();
        }

        if (minLength > -1) {
            typeValidate = MIN_LENGTH;
        } else {
            if (fieldLength > -1) {
                typeValidate = LENGTH;
            }
        }
        maxValue = Double.MAX_VALUE;
        minValue = Double.MIN_VALUE;
        if (minValueText != null) {
            try {
                minValue = Double.valueOf(minValueText);
            } catch (NumberFormatException e) {
                minValue = Double.MIN_VALUE;
                errorParam("minValue");
            }
        }
        if (maxValueText != null) {
            try {
                maxValue = Double.valueOf(maxValueText);
            } catch (NumberFormatException e) {
                maxValue = Double.MAX_VALUE;
                errorParam("maxValue");
            }
        }
        if (minValue != Double.MIN_VALUE || maxValue != Double.MAX_VALUE) {
            typeValidate = DIAPASON;
        }
//        setFocusable(true);
//        setFocusableInTouchMode(true);
        getTextInputLayout();
        setOnFocusChangeListener(noFocus);
    }

    public void setFocusChangeListenerInheritor(OnFocusChangeListener listener) {
        focusChangeListenerInheritor = listener;
    }

    private void errorParam(String st) {
        int i = getId();
        String name = getResources().getResourceEntryName(i);
        Log.i(AppParams.NAME_LOG_APP, "error in attribute "+st+" for elemet "+name);
    }

    private View.OnFocusChangeListener noFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                isValid();
            } else {
                setErrorValid("");
            }
            if (focusChangeListenerInheritor != null) {
                focusChangeListenerInheritor.onFocusChange(v, hasFocus);
            }
        }
    };

    @Override
    public void setData(Object data) {
        setText((String) data);
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public Object getData() {
        String st = getText().toString();
        return st;
    }

    @Override
    public void setOnChangeStatusListener(OnChangeStatusListener statusListener) {
        this.statusListener = statusListener;
    }

    @Override
    public String getString() {
        return getText().toString();
    }

    @Override
    public boolean isValid() {
        String st = getText().toString().trim();
        boolean result = false;
        switch (typeValidate) {
            case -1: return true;
            case FILLED :
                result = st != null && st.length() > 0;
                break;
            case LENGTH :
                result = fieldLength == st.length();
                break;
            case EMAIL :
                result = android.util.Patterns.EMAIL_ADDRESS.matcher(st).matches();
                break;
            case MIN_LENGTH :
                result = st.length() >= minLength;
                break;
            case DIAPASON :
                double val = 0d;
                if (st != null) {
                    try {
                        val = Double.valueOf(st);
                    } catch (NumberFormatException e) {
                        result = false;
                        errorParam("value");
                    }
                }
                result = maxValue > val && val > minValue;
                break;
        }
        if (result) {
            setErrorValid("");
        } else {
            setErrorValid(textError);
        }
        return result;
    }

    private void getTextInputLayout() {
        ViewParent viewParent = getParent();
        textInputLayout = null;
        if (viewParent instanceof TextInputLayout) {
            textInputLayout = (TextInputLayout) viewParent;
        } else if (viewParent != null) {
            ViewParent vp = viewParent.getParent();
            if (vp instanceof TextInputLayout) {
                textInputLayout = (TextInputLayout) vp;
            }
        }
    }

    public void setErrorValid(String textError) {
        if (textInputLayout == null) {
            getTextInputLayout();
        }
        if (textInputLayout != null) {
            textInputLayout.setError(textError);
        }
    }
}
