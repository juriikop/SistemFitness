package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewAccent extends AppCompatTextView {

    public TextViewAccent(Context context) {
        super(context);
        init(context, null);
    }

    public TextViewAccent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewAccent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setTextColor(AppColors.accent);
    }
}
