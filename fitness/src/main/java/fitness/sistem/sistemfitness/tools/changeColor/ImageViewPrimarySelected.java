package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ImageViewPrimarySelected extends AppCompatImageView {

    public ImageViewPrimarySelected(Context context) {
        super(context);
        init(context, null);
    }

    public ImageViewPrimarySelected(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageViewPrimarySelected(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
//        ColorStateList stateList = new ColorStateList(
//                new int[][]{
//                    new int[]{android.R.attr.state_selected},
//                    new int[]{}
//                },
//                new int[] {
//                    AppColors.primary,
//                    AppColors.primaryLight
//                }
//        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setImageTintList(AppColors.selectorImage);
        } else {
            setImageDrawable(tintIcon(getDrawable(), AppColors.selectorImage));
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
