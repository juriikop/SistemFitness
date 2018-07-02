package fitness.sistem.sistemfitness.tools.changeColor;

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

public class EditTextMaskPrimary extends EditTextMask {

    protected int canvasW, canvasH;
    private float DENSITY = getResources().getDisplayMetrics().density;
    protected int offsetY = (int) (7f * DENSITY);
    protected int dp1 = (int) (DENSITY);
    protected int dp2 = (int) (2f * DENSITY);
    protected int BG_COLOR, LINE_ACTIVE, LINE_PASSIVE;
    private OnFocusChangeListener onFocusChangeListener;
    protected boolean isFocus;

    public EditTextMaskPrimary(Context context) {
        this(context, null);
    }

    public EditTextMaskPrimary(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public EditTextMaskPrimary(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
    }


    private void setAttributes(Context context, AttributeSet attrs) {
        onFocusChangeListener = null;
//        setOnFocusChangeListener(focus);
        LINE_ACTIVE = AppColors.primary;
        LINE_PASSIVE = AppColors.gray;
        setBackgroundColor(BG_COLOR);
        setCursorDrawableColor(this, AppColors.primary);
    }

    private void setCursorDrawableColor(AppCompatEditText editText, int color) {
        try {
            Field fCursorDrawableRes =
                    TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);
            Drawable[] drawables = new Drawable[2];
            Resources res = editText.getContext().getResources();
            drawables[0] = res.getDrawable(mCursorDrawableRes);
            drawables[1] = res.getDrawable(mCursorDrawableRes);
            drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (final Throwable ignored) {
        }
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        onFocusChangeListener = listener;
    }

    private View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
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
