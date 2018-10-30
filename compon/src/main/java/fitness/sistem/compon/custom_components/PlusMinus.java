package fitness.sistem.compon.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import fitness.sistem.compon.R;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.components.PlusMinusComponent;
import fitness.sistem.compon.interfaces_classes.IComponent;
import fitness.sistem.compon.interfaces_classes.ICustom;
import fitness.sistem.compon.interfaces_classes.Multiply;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamComponent;

public class PlusMinus extends AppCompatEditText {

    private Context context;
    private int plusId, minusId;
    public ICustom iCustom;
    public ParamComponent paramMV;
    private View parentView;
    private Record record;
    private BaseComponent component;
    private int minValueInt,maxValueInt;
    private String minValue,maxValue;
    private PlusMinusComponent plusMinusComponent;

    public PlusMinus(Context context) {
        this(context, null);
    }

    public PlusMinus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Simple);
            try {
                plusId = a.getResourceId(R.styleable.Simple_plusViewId, 0);
                minusId = a.getResourceId(R.styleable.Simple_minusViewId, 0);
                minValue = a.getString(R.styleable.Simple_minValue);
                maxValue = a.getString(R.styleable.Simple_maxValue);
            } finally {
                a.recycle();
            }
            maxValueInt = Integer.MAX_VALUE;
            minValueInt = Integer.MIN_VALUE;
            if (maxValue != null && maxValue.length() > 0) {
                try {
                    maxValueInt = Integer.parseInt(maxValue);
                } catch (NumberFormatException e) {}
            }
            if (minValue != null && minValue.length() > 0) {
                try {
                    minValueInt = Integer.parseInt(minValue);
                } catch (NumberFormatException e) {}
            }
        }

    }

    public void setParam(View view, final Record record, final BaseComponent component) {
        parentView = view;
        this.record = record;
        iCustom = component.iCustom;
        paramMV = component.paramMV;
        this.component = component;
        BaseComponent bc = component.multiComponent.getComponent(getId());
        if (bc instanceof PlusMinusComponent) {
            plusMinusComponent = (PlusMinusComponent) bc;
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (plusMinusComponent.paramMV.multiplies != null) {
                    int i = 0;
                    String st = getText().toString();
                    if (st != null && st.length() > 0) {
                        i = Integer.valueOf(st);
                    }
                    for (Multiply m : plusMinusComponent.paramMV.multiplies) {
                        Float mult = record.getFloat(m.nameField);
                        if(mult != null) {
                            TextView tv = parentView.findViewById(m.viewId);
                            if (tv != null) {
                                if (tv instanceof IComponent) {
                                    ((IComponent) tv).setData(mult * i);
                                } else {
                                    tv.setText(String.valueOf(mult * i));
                                }
                            }
                        }
                    }
                }
                iCustom = PlusMinus.this.component.iCustom;
                if (iCustom != null) {
                    iCustom.changeValue(getId(), null);
                }
            }
        });

        setText(getText());

        if (plusMinusComponent != null) {
            plusId = plusMinusComponent.paramMV.paramView.layoutTypeId[0];
            minusId = plusMinusComponent.paramMV.paramView.layoutFurtherTypeId[0];
        }
// PLUS
        if (plusId != 0) {
            View vPlus = parentView.findViewById(plusId);
            if (vPlus != null) {
                vPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = 0;
                        String st = getText().toString();
                        if (st != null && st.length() > 0) {
                            i = Integer.valueOf(st);
                        }
                        if (i < maxValueInt) {
                            i++;
                            st = String.valueOf(i);
                            setText(st);
                            setSelection(st.length());
                        }
                    }
                });
            }
        }
// MINUS
        if (minusId != 0) {
            View vMinus = parentView.findViewById(minusId);
            if (vMinus != null) {
                vMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = 0;
                        String st = getText().toString();
                        if (st != null && st.length() > 0) {
                            i = Integer.valueOf(st);
                        }
                        if (i > minValueInt) {
                            i--;
                            st = String.valueOf(i);
                            setText(st);
                            setSelection(st.length());
                        }
                    }
                });
            }
        }
    }
}
