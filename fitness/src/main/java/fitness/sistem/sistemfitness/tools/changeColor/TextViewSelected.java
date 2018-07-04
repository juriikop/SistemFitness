package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import fitness.sistem.sistemfitness.R;

public class TextViewSelected extends AppCompatTextView {

    private int color1 = AppColors.accent, color2 = AppColors.accentLight;
    private int[] colors = {AppColors.primary, AppColors.accent, AppColors.primaryDark,
            AppColors.accentDark, AppColors.textOnPrimary, AppColors.textOnAccent,
            AppColors.primaryLight, AppColors.accentLight};

    public TextViewSelected(Context context) {
        super(context);
        init(context, null);
    }

    public TextViewSelected(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewSelected(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        } finally {
            a.recycle();
        }

        setClickable(true);
        setTextColor(AppColors.selectorText(color1, color2));
    }
}