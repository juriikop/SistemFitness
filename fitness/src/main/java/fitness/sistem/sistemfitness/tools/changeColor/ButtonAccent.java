package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class ButtonAccent extends AppCompatTextView {

    public ButtonAccent(Context context) {
        super(context);
        init(context, null);
    }

    public ButtonAccent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ButtonAccent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setClickable(true);
        setBackgroundDrawable(AppColors.selectorButton);
        setTextColor(AppColors.textOnAccent);
    }
}
