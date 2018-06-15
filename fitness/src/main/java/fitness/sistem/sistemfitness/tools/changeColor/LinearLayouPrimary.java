package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LinearLayouPrimary extends LinearLayout {
    public LinearLayouPrimary(Context context) {
        super(context);
        init(context, null);
    }

    public LinearLayouPrimary(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LinearLayouPrimary(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setBackgroundColor(AppColors.primary);
    }
}
