package fitness.sistem.sistemfitness.more_work;

import android.util.Log;
import android.view.View;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.interfaces_classes.MoreWork;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.json_simple.SimpleRecordToJson;
import fitness.sistem.sistemfitness.tools.PreferenceTool;
import fitness.sistem.sistemfitness.tools.changeColor.AppColors;

public class FitnessProcessing extends MoreWork {

    @Override
    public void clickView(View viewClick, View parentView,
                          BaseComponent baseComponent, Record rec, int position) {
        Record color = (Record)rec.getValue("colors");
        AppColors.recordToColor(color);
        SimpleRecordToJson rj = new SimpleRecordToJson();
        String st = rj.recordToJson(color);
        PreferenceTool.setAppColors(st);
        baseComponent.activity.setStatusColor(AppColors.primaryLight);
        baseComponent.activity.recreate();
    }
}
