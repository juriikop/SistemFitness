package fitness.sistem.sistemfitness.more_work;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.MoreWork;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.tools.changeColor.AppColors;

public class FitnessProcessing extends MoreWork {

    @Override
    public void clickView(View viewClick, View parentView,
                          BaseComponent baseComponent, Record rec, int position) {

        Record color = (Record)rec.getValue("colors");
        AppColors.accent = Color.parseColor(color.getString("accent"));
        AppColors.accentDark = Color.parseColor(color.getString("accentDark"));
        AppColors.accentLight = Color.parseColor(color.getString("accentLight"));
        AppColors.primary = Color.parseColor(color.getString("primary"));
        AppColors.primaryDark = Color.parseColor(color.getString("primaryDark"));
        AppColors.primaryLight = Color.parseColor(color.getString("primaryLight"));
        AppColors.textOnAccent = Color.parseColor(color.getString("textOnAccent"));
        AppColors.textOnPrimary = Color.parseColor(color.getString("textOnPrimary"));
        baseComponent.activity.setStatusColor(AppColors.primaryLight);
        baseComponent.activity.recreate();


//        int id = viewClick.getId();
//        Field ff = rec.getField("count");
//        Long count = (Long) ff.value;
//        if (id == R.id.plus) {
//            count++;
//        } else {
//            if (count > 0) {
//                count--;
//            }
//        }
//        ff.value = count;
//        TextView tv = (TextView) parentView.findViewById(R.id.count);
//        ImageView min = (ImageView) parentView.findViewById(R.id.minus);
//        tv.setText(String.valueOf(count));
//        if (count == 0) {
//            tv.setAlpha(0.35f);
//            min.setAlpha(0.35f);
//        } else {
//            tv.setAlpha(1f);
//            min.setAlpha(1f);
//        }
//        Field fAmount = rec.getField("amount");
//        if (fAmount == null) {
//            fAmount = new Field("amount", Field.TYPE_LONG, new Long(0));
//            rec.add(fAmount);
//        }
//        Field fCost = rec.getField("cost");
//        if (fCost == null) {
//            fCost = new Field("cost", Field.TYPE_DOUBLE, new Double(0));
//            rec.add(fCost);
//        }
//        Record arg = (Record) baseComponent.multiComponent.getComponent(R.id.panel).argument.value;
//        Double price = arg.getDouble("price");
//        Long am = count * (Long)rec.getValue("nominal");
//        Double cost = price * am;
//        fCost.value = cost;
//        fAmount.value = am;
//        iBase.sendEvent(baseComponent.paramMV.paramView.viewId);
    }
}
