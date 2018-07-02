package fitness.sistem.compon.custom_components;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;

import fitness.sistem.compon.R;
import fitness.sistem.compon.interfaces_classes.IComponent;
import fitness.sistem.compon.interfaces_classes.OnChangeStatusListener;

import java.util.ArrayList;
import java.util.List;

public class EditTextMask extends android.support.v7.widget.AppCompatEditText implements IComponent {
    private String mask;
    private int lenOriginText;
    private int textColor, hintColor;
    private String significant = "_";
    private List<MaskElem> maskElemList;
    private int lenPref;
    private String textPref;
    private String oldStr;
    private OnChangeStatusListener statusListener;
    private Integer status = new Integer(-1);
    private boolean isNoBlank, isValid, isTimeOut;
    private boolean noFocus = true;

    public EditTextMask(Context context) {
        super(context);
        init(context, null);
    }

    public EditTextMask(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EditTextMask(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
//        setOnFocusChangeListener(focus);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Simple,
                0, 0);
        try {
            mask = a.getString(R.styleable.Simple_mask);
        } finally {
            a.recycle();
        }
        lenPref = 0;
        hintColor = getCurrentHintTextColor();
        textColor = getCurrentTextColor();
        isNoBlank = isValid = isTimeOut = false;
        oldStr = "";
        if (mask == null) {
            mask = "";
        } else {
            maskProcessing();
        }
    }

//    private View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
//            Log.d("QWERT","focus hasFocus="+hasFocus+" LLL="+getText());
//            if (hasFocus && getText().length() == 0) {
//                noFocus = false;
//                maskProcessing();
//                oldStr = formText("");
//                Log.d("QWERT","focus oldStr="+oldStr);
//                setSpan(oldStr);
//                setSelection(oldStr.length());
//                addTextChangedListener(new MaskTextWatcher());
//            }
//        }
//    };

    public void setMask(String mask) {
        this.mask = mask;
        setMask();
//        maskProcessing();
//        oldStr = formText(stripText(getText().toString()));
//        setSpan(oldStr);
//        setSelection(oldStr.length());
//        addTextChangedListener(new MaskTextWatcher());
    }

    public void setMask() {
        maskProcessing();
        oldStr = formText(stripText(getText().toString()));
        setSpan(oldStr);
        setSelection(oldStr.length());
        addTextChangedListener(new MaskTextWatcher());
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (noFocus) return;
        if (lenPref > 0 && selStart < lenPref && getText().length() > 0) {
            setSelection(lenPref);
            return;
        }
    }

    private void maskProcessing() {
        int ik = mask.length();
        char charSignificant = significant.charAt(0);
        maskElemList = new ArrayList<>();
        boolean isMask = true;
        MaskElem me = null;
        boolean first = true;
        for (int i = 0; i <ik; i++) {
            char c = mask.charAt(i);
            if (c == charSignificant) {
                if ( ! isMask) {
                    isMask = true;
                    if (me != null) {
                        me.end = i;
                        me.beginMask = i;
                        me.count = me.end - me.begin;
                        me.value = mask.substring(me.begin, me.end);
                    }
                } else {
                    if (first) {
                        me = new MaskElem();
                        me.begin = -1;
                        me.end = 0;
                        me.count = 0;
                        me.value = "";
                        me.beginMask = 0;
                        maskElemList.add(me);
                    }
                }
            } else {
                if (isMask) {
                    isMask = false;
                    me = new MaskElem();
                    me.begin = i;
                    if (maskElemList.size() > 0) {
                        maskElemList.get(maskElemList.size() - 1).endMask = i;
                    }
                    maskElemList.add(me);
                }
            }
            first = false;
        }
        if (isMask) {
            me.endMask = ik;
        } else {
            maskElemList.get(maskElemList.size() - 1).endMask = ik;
        }
        if (maskElemList.size() > 0) {
            me = maskElemList.get(0);
            if (me.begin > -1) {
                lenPref = me.count;
                textPref = me.value;
            } else {
                lenPref = -1;
                textPref = "";
            }
            int iBeg = 0;
            ik = maskElemList.size();
            me = maskElemList.get(0);
            MaskElem me1;
            for (int i = 1; i < ik; i++) {
                me1 = maskElemList.get(i);
                me.beginMask = iBeg;
                int len = me1.begin - me.end;
                me.endMask = iBeg + len;
                iBeg = me.endMask;
                me = me1;
            }
            me.beginMask = iBeg;
            me.endMask = iBeg + mask.length() - me.end;
            lenOriginText = me.endMask;
        }
    }

    private String formText(String txt) {
        String result = "";
        int lenTxt = txt.length();
        if (maskElemList != null) {
            for (MaskElem m : maskElemList) {
                if (m.begin > -1) {
                    result += m.value;
                }
                if (lenTxt > m.beginMask) {
                    int i = m.endMask;
                    if (lenTxt <= m.endMask) {
                        i = lenTxt;
                        result += txt.substring(m.beginMask, i);
                        return result;
                    } else {
                        result += txt.substring(m.beginMask, i);
                    }
                } else {
                    break;
                }
            }
        }
        return result;
    }

    private String stripText(String text) {
        String result = "";
        String st;
        if (lenPref > -1 && text.length() > lenPref) {
            st = text.substring(lenPref);
        } else {
            st = "";
        }
        for (int i = 0; i < st.length(); i++) {
            char c = st.charAt(i);
            if (Character.isDigit(c)) {
                result += c;
            }
        }
        return  result;
    }

    private String stripNumber(String st) {
        String result = "";
        for (int i = 0; i < st.length(); i++) {
            char c = st.charAt(i);
            if (Character.isDigit(c) || c == '+') {
                result += c;
            }
        }
        return  result;
    }

    @Override
    public void setData(Object data) {
        setText((String) data);
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public Object getData() {
        String st = stripNumber(getText().toString());
        return st;
    }

    private class MaskTextWatcher implements TextWatcher {
        private boolean updating = false;

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String st = s.toString();
            if (oldStr.equals(st)) return;
            int sel = getSelectionStart();
            boolean isEnd = false;
            if (sel > oldStr.length()) {
                isEnd = true;
            }
            int lenSt = st.length();
            if (lenPref > 0 && sel < lenPref && lenSt > 0) {
                setSpan(oldStr);
                int selN = lenPref;
                if (selN > lenSt) {
                    selN = lenSt;
                }
                Log.d("QWERT","onTextChanged selN="+selN);
                setSelection(selN);
                return;
            }
            String origin = stripText(st);
            Log.d("QWERT","onTextChanged origin="+origin);
            if (origin.length() == 0) {
                if (isNoBlank) {
                    isNoBlank = false;
                    setEvent(0);
                }
            } else {
                if ( ! isNoBlank) {
                    isNoBlank = true;
                    setEvent(1);
                }
            }
            if (origin.length() >= lenOriginText) {
                if ( ! isValid) {
                    isValid = true;
                    setEvent(3);
                }
            } else {
                if (isValid) {
                    isValid = false;
                    setEvent(2);
                }
            }
            if (origin.length() > lenOriginText) {
                setSpan(oldStr);
                if (sel > oldStr.length()) {
                    setSelection(oldStr.length());
                } else {
                    if (sel > 0) {
                        setSelection(sel - 1);
                    } else {
                        setSelection(0);
                    }
                }
                return;
            }
            oldStr = formText(origin);
            setSpan(oldStr);
            int len = oldStr.length();
            if (isEnd) {
                setSelection(len);
            } else {
                if (sel > len) {
                    setSelection(len);
                } else {
                    setSelection(sel);
                }
            }
        }
    }

    private void setEvent(int stat) {
        if (statusListener != null) {
            status = stat;
            statusListener.changeStatus(this, status);
        }
    }

    private void setSpan(String text) {
        if (lenPref > 0 && text.length() >= lenPref) {
            SpannableString ss;
            ss = new SpannableString(text);
            ss.setSpan(new ForegroundColorSpan(hintColor), 0, lenPref, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            setText(ss);
        } else {
            setText(text);
        }
    }

    @Override
    public void setOnChangeStatusListener(OnChangeStatusListener statusListener) {
        this.statusListener = statusListener;
    }

    @Override
    public String getString() {
        String st = stripNumber(getText().toString());
        return st;
    }

    private class MaskElem {
        int begin, end, count, beginMask, endMask;
        String value;
    }
}
