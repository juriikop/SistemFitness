package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import fitness.sistem.compon.custom_components.ComponEditText;
import fitness.sistem.sistemfitness.R;

public class EditTextColor extends ComponEditText {

    protected int canvasW, canvasH;
    private float DENSITY = getResources().getDisplayMetrics().density;
    protected int offsetY = (int) (7f * DENSITY);
    protected int dp1 = (int) (DENSITY);
    protected int dp2 = (int) (2f * DENSITY);
    protected int BG_COLOR, LINE_ACTIVE, LINE_PASSIVE;
    private OnFocusChangeListener onFocusChangeListener;
    protected boolean isFocus;

    private int colorCursor = AppColors.primary;
    private int colorText = AppColors.textOnPrimary;

    public EditTextColor(Context context) {
        this(context, null);
    }

    public EditTextColor(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, attrs);
    }

    public EditTextColor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
    }


    private void setAttributes(Context context, AttributeSet attrs) {
        onFocusChangeListener = null;
        super.setOnFocusChangeListener(focus);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColor);
        try {
            int i = a.getInt(R.styleable.ChangeColor_colorCursor, 0);
            colorCursor = AppColors.colors[i];
            i = a.getInt(R.styleable.ChangeColor_colorText, -1);
            if (i > -1) {
                colorText = AppColors.colors[i];
                setTextColor(colorText);
            }
        } finally {
            a.recycle();
        }
        LINE_ACTIVE = colorCursor;
        LINE_PASSIVE = AppColors.gray;
        setBackgroundColor(BG_COLOR);
        AppColors.setCursorDrawableColor(this, colorCursor);
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
