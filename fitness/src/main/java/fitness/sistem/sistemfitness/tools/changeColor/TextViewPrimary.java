package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewPrimary extends AppCompatTextView {
    public TextViewPrimary(Context context) {
        super(context);
        init(context, null);
    }

    public TextViewPrimary(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewPrimary(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setTextColor(AppColors.primary);
    }
}
