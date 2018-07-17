package fitness.sistem.sistemfitness.more_work;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.SetSettings;
import fitness.sistem.compon.interfaces_classes.MoreWork;
import fitness.sistem.compon.json_simple.JsonSimple;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.json_simple.SimpleRecordToJson;
import fitness.sistem.compon.tools.Constants;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.tools.PreferenceTool;
import fitness.sistem.sistemfitness.tools.changeColor.AppColors;

public class FitnessProcessing extends MoreWork {

    private JsonSimple jsonSimple;

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

    @Override
    public void receiverWork(Intent intent) {
        if (jsonSimple == null) {
            jsonSimple = new JsonSimple();
        }
        Record record = (Record) jsonSimple.jsonToModel(intent.getStringExtra(Constants.RECORD)).value;
        SetSettings.setLocale(record.getString("id_language"));
        activity.recreate();
    }
}
