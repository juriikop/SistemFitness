package fitness.sistem.sistemfitness.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fitness.sistem.compon.base.BaseActivity;
import fitness.sistem.compon.components.MultiComponents;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.tools.changeColor.AppColors;
import fitness.sistem.sistemfitness.tools.changeColor.ButtonAccent;
import fitness.sistem.sistemfitness.tools.changeColor.ImageViewOnPrimary;

public class MainActivity extends BaseActivity {
    @Override
    public MultiComponents getScreen() {
        return getComponent(getString(R.string.splash));
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
