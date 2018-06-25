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
//    private int LINE_ACTIVE_DEFAULT = 0xffff9600;
//    private int LINE_PASSIVE_DEFAULT = 0xffaaaaaa;
    protected int BG_COLOR, LINE_ACTIVE, LINE_PASSIVE;
    private OnFocusChangeListener onFocusChangeListener;
    protected boolean isFocus;

    public EditPhone(Context context) {
        super(context);
        init(context, null);
    }

    public EditPhone(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EditPhone(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        onFocusChangeListener = null;
        super.setOnFocusChangeListener(focus);
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
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        onFocusChangeListener = l;
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
