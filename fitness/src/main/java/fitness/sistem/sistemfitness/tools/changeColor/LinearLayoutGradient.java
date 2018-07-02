package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LinearLayoutPrimaryGradient extends LinearLayout {

    private String title;

    public LinearLayoutPrimaryGradient(Context context) {
        super(context);
        init(context, null);
    }

    public LinearLayoutPrimaryGradient(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LinearLayoutPrimaryGradient(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setBackground(AppColors.gradient());
    }
}
