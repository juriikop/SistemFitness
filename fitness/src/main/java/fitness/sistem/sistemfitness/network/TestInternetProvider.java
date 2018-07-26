package fitness.sistem.sistemfitness.network;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseInternetProvider;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.json_simple.SimpleRecordToJson;
import fitness.sistem.sistemfitness.FitnessApp;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.tools.PreferenceTool;

import java.util.Map;

public class TestInternetProvider extends BaseInternetProvider {
    private Handler handler;

    @Override
    public void setParam(int method, String url, Map<String, String> headers,
                         String data, InternetProviderListener listener) {
        super.setParam(method, url, headers, data, listener);
        handler = new Handler();
        handler.postDelayed(result, 1000);
//        result.run();
    }

    Runnable result = new Runnable() {
        @Override
        public void run() {
            String request = url.replace(ComponGlob.getInstance().appParams.baseUrl, "");
            int i = request.indexOf("?");
            if (i > -1) {
                request = request.substring(0, i);
            }
            listener.response(jsonResult(request));
        }
    };

    private String jsonResult(String request) {
        switch (request) {
            case Api.CLUBS: return setClubs();
            case Api.SEARCH_CITY: return setSearchCity();
            case Api.SEARCH_CLUB: return setSearchClub();
            case Api.CONTENT: return setContent();
        }
        return null;
    }

    private String setContent() {
        String st = PreferenceTool.getContent();
        if (st.length() == 0) {
            Record rec = new Record();
            ListRecords lr = new ListRecords();
            Field fil = new Field("", Field.TYPE_RECORD, rec);
            rec.add(new Field("data", Field.TYPE_LIST_RECORD, lr));
            SimpleRecordToJson recordToJson = new SimpleRecordToJson();
            st = recordToJson.modelToJson(fil);
        }
        return st;
    }

    private String setSearchClub() {
        Record rec = new Record();
        ListRecords lr = new ListRecords();
        Field fil = new Field("", Field.TYPE_RECORD, rec);
        rec.add(new Field("data", Field.TYPE_LIST_RECORD, lr));
        Record record;
        record = new Record();
        record.add(new Field("club", Field.TYPE_STRING, "Aura"));
        record.add(new Field("id_club", Field.TYPE_STRING, "11"));
        record.add(new Field("id_city", Field.TYPE_STRING, "11"));
        lr.add(record);
        record = new Record();
        record.add(new Field("club", Field.TYPE_STRING, "Вертикаль"));
        record.add(new Field("id_club", Field.TYPE_STRING, "22"));
        record.add(new Field("id_city", Field.TYPE_STRING, "11"));
        lr.add(record);
        record = new Record();
        record.add(new Field("club", Field.TYPE_STRING, "Локомотив"));
        record.add(new Field("id_club", Field.TYPE_STRING, "33"));
        record.add(new Field("id_city", Field.TYPE_STRING, "11"));
        lr.add(record);

        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
        return recordToJson.modelToJson(fil);
    }

    private String setSearchCity() {
        Record rec = new Record();
        ListRecords lr = new ListRecords();
        Field fil = new Field("", Field.TYPE_RECORD, rec);
        rec.add(new Field("data", Field.TYPE_LIST_RECORD, lr));
        Record record;
        record = new Record();
        record.add(new Field("city", Field.TYPE_STRING, "Харьков"));
        record.add(new Field("id_city", Field.TYPE_STRING, "11"));
        lr.add(record);
        record = new Record();
        record.add(new Field("city", Field.TYPE_STRING, "Киев"));
        record.add(new Field("id_city", Field.TYPE_STRING, "22"));
        lr.add(record);
        record = new Record();
        record.add(new Field("city", Field.TYPE_STRING, "Днепр"));
        record.add(new Field("id_city", Field.TYPE_STRING, "33"));
        lr.add(record);

        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
        return recordToJson.modelToJson(fil);
    }

    private String setClubs() {
        Record rec = new Record();
        ListRecords lr = new ListRecords();
        Field fil = new Field("", Field.TYPE_RECORD, rec);
        rec.add(new Field("data", Field.TYPE_LIST_RECORD, lr));
        int iSel = PreferenceTool.getSelectClub();
        if (iSel < 0) iSel = 0;
        Record colors;

        Record record;
        record = new Record();
        if (iSel == 0) {
            record.add(new Field("select", Field.TYPE_LONG, 1));
        } else {
            record.add(new Field("select", Field.TYPE_LONG, 0));
        }
        record.add(new Field("city", Field.TYPE_STRING, "Харьков"));
        record.add(new Field("title", Field.TYPE_STRING, "клуб Аура"));
        record.add(new Field("imgClub", Field.TYPE_STRING, "https://static8.depositphotos.com/1053932/851/i/950/depositphotos_8511050-stock-photo-group-with-weight-training-equipment.jpg"));
            colors = new Record();
            colors.add(new Field("primary", Field.TYPE_STRING, "#ff9600"));
            colors.add(new Field("primaryDark", Field.TYPE_STRING, "#ef9000"));
            colors.add(new Field("primaryLight", Field.TYPE_STRING, "#ffbf63"));
            colors.add(new Field("accent", Field.TYPE_STRING, "#17d3ff"));
            colors.add(new Field("accentDark", Field.TYPE_STRING, "#0da3c7"));
            colors.add(new Field("accentLight", Field.TYPE_STRING, "#bef0fc"));
            colors.add(new Field("textOnPrimary", Field.TYPE_STRING, "#ffffff"));
            colors.add(new Field("textOnAccent", Field.TYPE_STRING, "#ffffff"));
        record.add(new Field("colors", Field.TYPE_RECORD, colors));
        lr.add(record);

        record = new Record();
        if (iSel == 1) {
            record.add(new Field("select", Field.TYPE_LONG, 1));
        } else {
            record.add(new Field("select", Field.TYPE_LONG, 0));
        }
        record.add(new Field("city", Field.TYPE_STRING, "Одеса"));
        record.add(new Field("title", Field.TYPE_STRING, "клуб Вертикаль"));
        record.add(new Field("imgClub", Field.TYPE_STRING, "https://st.depositphotos.com/1158045/2361/i/950/depositphotos_23610209-stock-photo-running-on-treadmills.jpg"));
            colors = new Record();
            colors.add(new Field("primary", Field.TYPE_STRING, "#1fe76e"));
            colors.add(new Field("primaryDark", Field.TYPE_STRING, "#1bbf81"));
            colors.add(new Field("primaryLight", Field.TYPE_STRING, "#83fdb4"));
            colors.add(new Field("accent", Field.TYPE_STRING, "#fad74c"));
            colors.add(new Field("accentDark", Field.TYPE_STRING, "#edc62c"));
            colors.add(new Field("accentLight", Field.TYPE_STRING, "#fce380"));
            colors.add(new Field("textOnPrimary", Field.TYPE_STRING, "#777777"));
            colors.add(new Field("textOnAccent", Field.TYPE_STRING, "#777777"));
        record.add(new Field("colors", Field.TYPE_RECORD, colors));
        lr.add(record);

        record = new Record();
        if (iSel == 2) {
            record.add(new Field("select", Field.TYPE_LONG, 1));
        } else {
            record.add(new Field("select", Field.TYPE_LONG, 0));
        }
        record.add(new Field("city", Field.TYPE_STRING, "Київ"));
        record.add(new Field("title", Field.TYPE_STRING, "клуб FitSila"));
        record.add(new Field("imgClub", Field.TYPE_STRING, "https://st2.depositphotos.com/1003697/6756/i/950/depositphotos_67563907-stock-photo-group-of-people-on-treadmills.jpg"));
            colors = new Record();
            colors.add(new Field("primary", Field.TYPE_STRING, "#51b7ff"));
            colors.add(new Field("primaryDark", Field.TYPE_STRING, "#3095dc"));
            colors.add(new Field("primaryLight", Field.TYPE_STRING, "#84ccff"));
            colors.add(new Field("accent", Field.TYPE_STRING, "#fad74c"));
            colors.add(new Field("accentDark", Field.TYPE_STRING, "#edc62c"));
            colors.add(new Field("accentLight", Field.TYPE_STRING, "#fce380"));
            colors.add(new Field("textOnPrimary", Field.TYPE_STRING, "#ffffff"));
            colors.add(new Field("textOnAccent", Field.TYPE_STRING, "#777777"));
        record.add(new Field("colors", Field.TYPE_RECORD, colors));
        lr.add(record);

        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
        String st = recordToJson.modelToJson(fil);
//        Log.d("QWERT","setClubs st="+st);
        return st;
    }

//    private String setIntro() {
//        Record rec = new Record();
//        ListRecords lr = new ListRecords();
//        Field f = new Field("", Field.TYPE_RECORD, rec);
//        rec.add(new Field("data", Field.TYPE_LIST_RECORD, lr));
//
//        Record record;
//        record = new Record();
//        record.add(new Field("message", Field.TYPE_STRING, "Более 120 индивидуальных\nи групповых услуг"));
//        record.add(new Field("title", Field.TYPE_STRING, "Заказывай услуги"));
//        record.add(new Field("img", Field.TYPE_STRING, "intro_1"));
//        lr.add(record);
//
//        record = new Record();
//        record.add(new Field("message", Field.TYPE_STRING, "Удобный планировщик\nс возможностью отмены занятий"));
//        record.add(new Field("title", Field.TYPE_STRING, "Составляй расписание"));
//        record.add(new Field("img", Field.TYPE_STRING, "intro_2"));
//        lr.add(record);
//
//        record = new Record();
//        record.add(new Field("message", Field.TYPE_STRING, "Поставь перед собой цель\nи иди к ней, а мы поможем"));
//        record.add(new Field("title", Field.TYPE_STRING, "Достигай целей"));
//        record.add(new Field("img", Field.TYPE_STRING, "intro_3"));
//        lr.add(record);
//
//        record = new Record();
//        record.add(new Field("message", Field.TYPE_STRING, "Ни одна акция или новость\nне пройдет мимо"));
//        record.add(new Field("title", Field.TYPE_STRING, "Будь в курсе событий"));
//        record.add(new Field("img", Field.TYPE_STRING, "intro_4"));
//        lr.add(record);
//
//        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
//        return recordToJson.modelToJson(f);
//    }

}
