package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewPrimarySelected extends AppCompatTextView {
    public TextViewPrimarySelected(Context context) {
        super(context);
        init(context, null);
    }

    public TextViewPrimarySelected(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewPrimarySelected(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setClickable(true);
        setTextColor(AppColors.selectorText());
    }
}
