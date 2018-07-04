package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
import fitness.sistem.sistemfitness.R;

public class EditTextMaskColor extends EditTextMask {

    protected int canvasW, canvasH;
    private float DENSITY = getResources().getDisplayMetrics().density;
    protected int offsetY = (int) (7f * DENSITY);
    protected int dp1 = (int) (DENSITY);
    protected int dp2 = (int) (2f * DENSITY);
    protected int BG_COLOR, LINE_ACTIVE, LINE_PASSIVE;
    private OnFocusChangeListener onFocusChangeListener;
    protected boolean isFocus;
    protected boolean setMask = false;
    private int colorText = AppColors.textOnPrimary;

    public EditTextMaskColor(Context context) {
        this(context, null);
    }

    public EditTextMaskColor(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public EditTextMaskColor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
    }


    private void setAttributes(Context context, AttributeSet attrs) {
        onFocusChangeListener = null;
        super.setFocusChangeListenerInheritor(focus);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColor);
        try {
            int i = a.getInt(R.styleable.ChangeColor_colorCursor, 0);
            LINE_ACTIVE = AppColors.colors[i];
            i = a.getInt(R.styleable.ChangeColor_colorText, -1);
            if (i > -1) {
                colorText = AppColors.colors[i];
                setTextColor(colorText);
            }
        } finally {
            a.recycle();
        }
        LINE_PASSIVE = AppColors.gray;
        setBackgroundColor(BG_COLOR);
        AppColors.setCursorDrawableColor(this, AppColors.primary);
    }

    public void setFocusChangeListenerInheritor(OnFocusChangeListener listener) {
        onFocusChangeListener = listener;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (isFocus) {
            paint.setColor(LINE_ACTIVE);
            paint.setStrokeWidth(dp2);
        } else {
            paint.setColor(LINE_PASSIVE);
            paint.setStrokeWidth(dp1);
        }
        canvas.drawLine(2, canvasH - offsetY, canvasW - 2, canvasH - offsetY, paint);
    }
}
