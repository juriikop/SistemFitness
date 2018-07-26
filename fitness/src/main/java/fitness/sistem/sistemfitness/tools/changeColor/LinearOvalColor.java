package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import fitness.sistem.sistemfitness.R;

public class LinearOvalColor extends LinearLayout{

    private int color1 = AppColors.accent, color2 = AppColors.accentLight;
//    private int[] colors = {AppColors.primary, AppColors.accent, AppColors.primaryDark,
//            AppColors.accentDark, AppColors.textOnPrimary, AppColors.textOnAccent,
//            AppColors.primaryLight, AppColors.accentLight, AppColors.gray};

    public LinearOvalColor(Context context) {
        super(context);
        init(context, null);
    }

    public LinearOvalColor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LinearOvalColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColor);
        try {
            int i = a.getInt(R.styleable.ChangeColor_color_1, 0);
            color1 = AppColors.colors[i];
            i = a.getInt(R.styleable.ChangeColor_color_2, 0);
            color2 = AppColors.colors[i];
        } finally {
            a.recycle();
        }
        setClickable(true);
        setBackground(AppColors.selectorOval(context, color1, color2));
    }
}
