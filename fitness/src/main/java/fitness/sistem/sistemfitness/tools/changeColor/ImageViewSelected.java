package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import fitness.sistem.sistemfitness.R;

public class ImageViewSelected extends AppCompatImageView {

    private int color1 = AppColors.accent, color2 = AppColors.accentLight;
    private int[] colors = {AppColors.primary, AppColors.accent, AppColors.primaryDark,
            AppColors.accentDark, AppColors.textOnPrimary, AppColors.textOnAccent,
            AppColors.primaryLight, AppColors.accentLight};

    public ImageViewSelected(Context context) {
        super(context);
        init(context, null);
    }

    public ImageViewSelected(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageViewSelected(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setImageTintList(AppColors.selectorImage(color1, color2));
        } else {
            setImageDrawable(tintIcon(getDrawable(), AppColors.selectorImage(color1, color2)));
        }
    }

    public Drawable tintIcon(Drawable icon, ColorStateList colorStateList) {
        if(icon!=null) {
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon, colorStateList);
            DrawableCompat.setTintMode(icon, PorterDuff.Mode.SRC_IN);
        }
        return icon;
    }
}
