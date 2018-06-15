package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RelativeLayoutPrimary extends RelativeLayout{

    public RelativeLayoutPrimary(Context context) {
        super(context);
        init(context, null);
    }

    public RelativeLayoutPrimary(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RelativeLayoutPrimary(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setBackgroundColor(AppColors.primary);
    }
}
