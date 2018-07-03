package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import fitness.sistem.sistemfitness.R;

public class ButtonColor extends AppCompatTextView {

    private int color1 = AppColors.accent, color2 = AppColors.accentLight;
    private int colorText = AppColors.textOnAccent;
    private int[] colors = {AppColors.primary, AppColors.accent, AppColors.primaryDark,
            AppColors.accentDark, AppColors.textOnPrimary, AppColors.textOnAccent,
            AppColors.primaryLight, AppColors.accentLight};

    public ButtonColor(Context context) {
        super(context);
        init(context, null);
    }

    public ButtonColor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ButtonColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColor);
        try {
            int i = a.getInt(R.styleable.ChangeColor_color_1, 0);
            color1 = colors[i];
            i = a.getInt(R.styleable.ChangeColor_color_2, 0);
            color2 = colors[i];
            i = a.getInt(R.styleable.ChangeColor_colorText, 0);
            colorText = colors[i];
        } finally {
            a.recycle();
        }
        setClickable(true);
        setBackgroundDrawable(AppColors.selectorButton(context, color1, color2));
        setTextColor(colorText);
    }
}
