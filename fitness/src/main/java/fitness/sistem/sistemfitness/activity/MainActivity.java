package fitness.sistem.sistemfitness.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.tools.changeColor.AppColors;
import fitness.sistem.sistemfitness.tools.changeColor.ButtonAccent;
import fitness.sistem.sistemfitness.tools.changeColor.ImageViewOnPrimary;

public class MainActivity extends AppCompatActivity {

    protected Unbinder unbinder;

    @BindView(R.id.button) ButtonAccent button;
    @BindView(R.id.back) ImageViewOnPrimary back;
    @BindView(R.id.imgSel)
    ImageView imgSel;
    @BindView(R.id.txtSel)
    TextView txtSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppColors.makeSelectors(this);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.changeColor)
    public void changeColor() {
        button.setEnabled( ! button.isEnabled());
    }

    private void changeColorApp() {
        int cc = AppColors.primary;
        AppColors.primary = AppColors.accent;
        AppColors.accent = cc;
        cc = AppColors.primaryDark;
        AppColors.primaryDark = AppColors.accentDark;
        AppColors.accentDark = cc;
        cc = AppColors.primaryLight;
        AppColors.primaryLight = AppColors.accentLight;
        AppColors.accentLight = cc;
        cc = AppColors.textOnPrimary;
        AppColors.textOnPrimary = AppColors.textOnAccent;
        AppColors.textOnAccent = cc;
    }

    @OnClick(R.id.button)
    public void setButton() {
        changeColorApp();
        recreate();
    }

    @OnClick(R.id.sel)
    public void sel() {
        if (AppColors.textOnPrimary == 0xffff0000) {
            AppColors.textOnPrimary = 0xff00ff00;
        } else {
            AppColors.textOnPrimary = 0xffff0000;
        }
        imgSel.setSelected( ! imgSel.isSelected());
        txtSel.setSelected( ! txtSel.isSelected());
    }
}
