package fitness.sistem.compon.functions_fragment;

import android.os.Bundle;

import fitness.sistem.compon.base.BaseFragment;

public class ComponentsFragment extends BaseFragment {
    @Override
    public void initView(Bundle savedInstanceState) {
        mComponent.initComponents(this);
    }


//    @Override
//    public int getLayoutId() {
//        return 0;
//    }
}
