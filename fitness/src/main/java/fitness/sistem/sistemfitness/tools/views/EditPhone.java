package fitness.sistem.sistemfitness.tools.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

import fitness.sistem.compon.custom_components.EditTextMask;
import fitness.sistem.sistemfitness.tools.changeColor.AppColors;

public class EditPhone extends EditTextMask{

    protected int canvasW, canvasH;
    private float DENSITY = getResources().getDisplayMetrics().density;
    protected int offsetY = (int) (7f * DENSITY);
    protected int dp1 = (int) (DENSITY);
    protected int dp2 = (int) (2f * DENSITY);
    protected int BG_COLOR, LINE_ACTIVE, LINE_PASSIVE;
    private OnFocusChangeListener onFocusChangeListener;
    protected boolean isFocus;
    protected boolean setMask = false;

    public EditPhone(Context context) {
        super(context);
        setAttrs(context, null);
    }

    public EditPhone(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(context, attrs);
    }

    public EditPhone(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttrs(context, attrs);
    }

    private void setAttrs(Context context, AttributeSet attrs) {
        onFocusChangeListener = null;
        super.setOnFocusChangeListener(focus);
        LINE_ACTIVE = AppColors.primary;
        LINE_PASSIVE = AppColors.gray;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        onFocusChangeListener = l;
    }

    private View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus && ! setMask) {
                setMask = true;
                setMask();
            }
            isFocus = hasFocus;
            if (onFocusChangeListener != null) {
                onFocusChangeListener.onFocusChange(v, hasFocus);
            }
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        canvasW = w;
        canvasH = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
