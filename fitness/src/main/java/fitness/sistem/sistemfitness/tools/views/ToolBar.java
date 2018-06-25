package fitness.sistem.sistemfitness.tools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.tools.changeColor.AppColors;
import fitness.sistem.sistemfitness.tools.changeColor.TextViewOnPrimary;

public class ToolBar extends RelativeLayout {

    private String title;

    public ToolBar(Context context) {
        super(context);
        init(context, null);
    }

    public ToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setBackground(AppColors.gradient());
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ToolBar,
                0, 0);
        try {
            title = a.getString(R.styleable.ToolBar_title);
        } finally {
            a.recycle();
        }
        TextViewOnPrimary text = new TextViewOnPrimary(context, attrs);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        text.setLayoutParams(lp);
        text.setTextColor(AppColors.textOnPrimary);
        addView(text);
    }
}
