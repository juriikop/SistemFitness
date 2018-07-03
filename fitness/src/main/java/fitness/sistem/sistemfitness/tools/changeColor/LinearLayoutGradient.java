package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import fitness.sistem.sistemfitness.R;

public class LinearLayoutGradient extends LinearLayout {

    private int colorGradient1 = AppColors.primaryDark, colorGradient2 = AppColors.primary;
    private int[] colors = {AppColors.primary, AppColors.accent, AppColors.primaryDark,
            AppColors.accentDark, AppColors.textOnPrimary, AppColors.textOnAccent,
            AppColors.primaryLight, AppColors.accentLight};

    public LinearLayoutGradient(Context context) {
        super(context);
        init(context, null);
    }

    public LinearLayoutGradient(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LinearLayoutGradient(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColor);
        try {
            int i = a.getInt(R.styleable.ChangeColor_color_1, 0);
            colorGradient1 = colors[i];
            i = a.getInt(R.styleable.ChangeColor_color_2, 0);
            colorGradient2 = colors[i];
        } finally {
            a.recycle();
        }
        setBackground(AppColors.gradient(colorGradient1, colorGradient2));
    }
}
