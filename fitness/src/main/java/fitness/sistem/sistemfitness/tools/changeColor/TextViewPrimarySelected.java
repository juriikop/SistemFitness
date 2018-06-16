package fitness.sistem.sistemfitness.tools.changeColor;

import android.content.Context;
import android.content.res.ColorStateList;
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
        setTextColor(AppColors.selectorText);
//        int[][] states = new int[][] {
//                new int[] { android.R.attr.state_selected},
//                new int[] {}
//        };
//        int[] colors = new int[] {
//                AppColors.primary,
//                AppColors.primaryLight
//        };
//        setTextColor(new ColorStateList(states, colors));
    }
}