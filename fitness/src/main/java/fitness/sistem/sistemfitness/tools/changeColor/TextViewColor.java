package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import fitness.sistem.sistemfitness.R;

public class TextViewColor extends AppCompatTextView {

    private int colorText = AppColors.primary;
    private int[] colors = {AppColors.primary, AppColors.accent, AppColors.primaryDark,
            AppColors.accentDark, AppColors.textOnPrimary, AppColors.textOnAccent,
            AppColors.primaryLight, AppColors.accentLight, AppColors.gray};

    public TextViewColor(Context context) {
        super(context);
        init(context, null);
    }

    public TextViewColor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewColor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColor);
        try {
            int i = a.getInt(R.styleable.ChangeColor_colorText, 0);
            colorText = colors[i];
        } finally {
            a.recycle();
        }
        setTextColor(colorText);
    }
}
