package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import fitness.sistem.sistemfitness.R;

public class ImageViewColor extends AppCompatImageView {

    private int colorText = AppColors.primary;
    private int[] colors = {AppColors.primary, AppColors.accent, AppColors.primaryDark,
            AppColors.accentDark, AppColors.textOnPrimary, AppColors.textOnAccent,
            AppColors.primaryLight, AppColors.accentLight, AppColors.gray};

    public ImageViewColor(Context context) {
        super(context);
        init(context, null);
    }

    public ImageViewColor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageViewColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        setColors();
    }

    public void setColors() {
        ColorStateList stateList = new ColorStateList(
                new int[][]{
                    new int[]{}
                },
                new int[] {
                    colorText
                }
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setImageTintList(stateList);
        } else {
            setImageDrawable(tintIcon(getDrawable(), stateList));
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
