package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;

public class AppColors {
    public static int primary = 0xFFff9600;
    public static int primaryDark = 0xffce7901;
    public static int primaryLight = 0xffffbf63;
    public static int accent = 0xff17d3ff;
    public static int accentDark = 0xff0da3c7;
    public static int accentLight = 0xff98e9fc;
    public static int textOnPrimary = 0xffffffff;
    public static int textOnAccent = 0xffffffff;
    public static int gray = 0xffaaaaaa;

    public static StateListDrawable selectorButton(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float r = 24 * dm.density;
        float[] outR = new float[] {r, r, r, r, r, r, r, r };
        ShapeDrawable shapeAccent = new ShapeDrawable (new RoundRectShape(outR, null, null));
        shapeAccent.getPaint().setColor(AppColors.accent);
        ShapeDrawable shapeAccentLight = new ShapeDrawable (new RoundRectShape(outR, null, null));
        shapeAccentLight.getPaint().setColor(AppColors.accentLight);
        ShapeDrawable shapeEnabled = new ShapeDrawable (new RoundRectShape(outR, null, null));
        shapeEnabled.getPaint().setColor(AppColors.gray);
        StateListDrawable selectorButton = new StateListDrawable();
        selectorButton.addState(new int[]{ - android.R.attr.state_enabled}, shapeEnabled);
        selectorButton.addState(new int[]{android.R.attr.state_pressed}, shapeAccentLight);
        selectorButton.addState(new int[]{}, shapeAccent);
        return selectorButton;
    }

    public static GradientDrawable gradient() {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[] { primaryDark, primary});
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return drawable;
    }

    public static ColorStateList selectorText() {
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_selected},
                new int[] {}
        };
        int[] colors = new int[] {
                AppColors.primary,
                AppColors.primaryLight
        };
        return new ColorStateList(states, colors);
    }

    public static ColorStateList selectorImage() {
        ColorStateList selectorImage = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_selected},
                        new int[]{}
                },
                new int[] {
                        AppColors.primary,
                        AppColors.primaryLight
                }
        );
        return selectorImage;
    }

}
