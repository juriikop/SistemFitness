package fitness.sistem.compon.components;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.param.ParamComponent;

public class PlusMinusComponent extends BaseComponent {

    public EditText editPlusMinus;

    public PlusMinusComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        editPlusMinus = (EditText) parentLayout.findViewById(paramMV.paramView.viewId);
        editPlusMinus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (iCustom != null) {
                    iCustom.changeValue(paramMV.paramView.viewId, null);
                }
            }
        });
        parentLayout.findViewById(paramMV.paramView.layoutTypeId[0]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                String st = editPlusMinus.getText().toString();
                if (st != null && st.length() > 0) {
                    i = Integer.valueOf(st);
                }
                i++;
                st = String.valueOf(i);
                editPlusMinus.setText(st);
                editPlusMinus.setSelection(st.length());
            }
        });

        parentLayout.findViewById(paramMV.paramView.layoutFurtherTypeId[0]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                String st = editPlusMinus.getText().toString();
                if (st != null && st.length() > 0) {
                    i = Integer.valueOf(st);
                }
                i--;
                st = String.valueOf(i);
                editPlusMinus.setText(st);
                editPlusMinus.setSelection(st.length());
            }
        });
    }

    @Override
    public void changeData(Field field) {

    }
}
