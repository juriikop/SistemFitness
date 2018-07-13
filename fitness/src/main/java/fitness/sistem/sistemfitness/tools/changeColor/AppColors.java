package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v7.widget.AppCompatEditText;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import java.lang.reflect.Field;

import fitness.sistem.compon.json_simple.JsonSimple;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.tools.PreferenceTool;

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
    public static int[] colors = {primary, accent, primaryDark, accentDark,
            textOnPrimary, textOnAccent, primaryLight, accentLight};

    public static StateListDrawable selectorButton(Context context, int color1, int color2) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float r = 24 * dm.density;
        float[] outR = new float[] {r, r, r, r, r, r, r, r };
        ShapeDrawable shapeAccent = new ShapeDrawable (new RoundRectShape(outR, null, null));
        shapeAccent.getPaint().setColor(color1);
        ShapeDrawable shapeAccentLight = new ShapeDrawable (new RoundRectShape(outR, null, null));
        shapeAccentLight.getPaint().setColor(color2);
        ShapeDrawable shapeEnabled = new ShapeDrawable (new RoundRectShape(outR, null, null));
        shapeEnabled.getPaint().setColor(AppColors.gray);
        StateListDrawable selectorButton = new StateListDrawable();
        selectorButton.addState(new int[]{ - android.R.attr.state_enabled}, shapeEnabled);
        selectorButton.addState(new int[]{android.R.attr.state_pressed}, shapeAccentLight);
        selectorButton.addState(new int[]{}, shapeAccent);
        return selectorButton;
    }

    public static StateListDrawable selectorOval(Context context, int color1, int color2) {
        ShapeDrawable shapeAccent = new ShapeDrawable (new OvalShape());
        shapeAccent.getPaint().setColor(color1);
        ShapeDrawable shapeAccentLight = new ShapeDrawable (new OvalShape());
        shapeAccentLight.getPaint().setColor(color2);
        ShapeDrawable shapeEnabled = new ShapeDrawable (new OvalShape());
        shapeEnabled.getPaint().setColor(AppColors.gray);
        StateListDrawable selectorButton = new StateListDrawable();
        selectorButton.addState(new int[]{ - android.R.attr.state_enabled}, shapeEnabled);
        selectorButton.addState(new int[]{android.R.attr.state_pressed}, shapeAccentLight);
        selectorButton.addState(new int[]{}, shapeAccent);
        return selectorButton;
    }

    public static void recordToColor(Record color) {
        accent = Color.parseColor(color.getString("accent"));
        accentDark = Color.parseColor(color.getString("accentDark"));
        accentLight = Color.parseColor(color.getString("accentLight"));
        primary = Color.parseColor(color.getString("primary"));
        primaryDark = Color.parseColor(color.getString("primaryDark"));
        primaryLight = Color.parseColor(color.getString("primaryLight"));
        textOnAccent = Color.parseColor(color.getString("textOnAccent"));
        textOnPrimary = Color.parseColor(color.getString("textOnPrimary"));
        setColors();
    }

    public static void jsonToColor(String json) {
        JsonSimple rj = new JsonSimple();
        recordToColor((Record) rj.jsonToModel(json).value);
    }

    public static void setColors() {
        colors = new int[] {primary, accent, primaryDark, accentDark,
                textOnPrimary, textOnAccent, primaryLight, accentLight};
    }

    public static GradientDrawable gradient() {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[] { primaryDark, primary});
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return drawable;
    }

    public static GradientDrawable gradient(int gradient1, int gradient2) {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[] { gradient1, gradient2});
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return drawable;
    }

    public static ColorStateList selectorText(int color1, int color2) {
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_selected},
                new int[] {}
        };
        int[] colors = new int[] {
                color1,
                color2
        };
        return new ColorStateList(states, colors);
    }

    public static ColorStateList selectorImage(int color1, int color2) {
        ColorStateList selectorImage = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_selected},
                        new int[]{}
                },
                new int[] {
                        color1,
                        color2
                }
        );
        return selectorImage;
    }

    public static void setCursorDrawableColor(AppCompatEditText editText, int color) {
        try {
            Field fCursorDrawableRes =
                    TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);
            Drawable[] drawables = new Drawable[2];
            Resources res = editText.getContext().getResources();
            drawables[0] = res.getDrawable(mCursorDrawableRes);
            drawables[1] = res.getDrawable(mCursorDrawableRes);
            drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (final Throwable ignored) {
            Log.d("QWERT","Throwable="+ignored);
        }
    }

}
