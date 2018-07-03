package fitness.sistem.sistemfitness.network;

import android.os.Handler;

//import fitness.sistem.ComponGlob;
//import fitness.sistem.base.BaseInternetProvider;
//import fitness.sistem.json_simple.Field;
//import fitness.sistem.json_simple.ListRecords;
//import fitness.sistem.json_simple.Record;
//import fitness.sistem.json_simple.SimpleRecordToJson;
//import fitness.sistem.tools.Constants;
import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseInternetProvider;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.json_simple.SimpleRecordToJson;

import java.util.Map;

public class TestInternetProvider extends BaseInternetProvider {
    private Handler handler;

    @Override
    public void setParam(int method, String url, Map<String, String> headers,
                         String data, InternetProviderListener listener) {
        super.setParam(method, url, headers, data, listener);
        handler = new Handler();
        handler.postDelayed(result, 1000);
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
             case Api.INTRO:
                return setIntro();
        }
        return null;
    }

    private String setIntro() {
        Record rec = new Record();
        ListRecords lr = new ListRecords();
        Field f = new Field("", Field.TYPE_RECORD, rec);
        rec.add(new Field("data", Field.TYPE_LIST_RECORD, lr));

        Record record;
        record = new Record();
        record.add(new Field("message", Field.TYPE_STRING, "Более 120 индивидуальных\nи групповых услуг"));
        record.add(new Field("title", Field.TYPE_STRING, "Заказывай услуги"));
        record.add(new Field("img", Field.TYPE_STRING, "intro_1"));
        lr.add(record);

        record = new Record();
        record.add(new Field("message", Field.TYPE_STRING, "Удобный планировщик\nс возможностью отмены занятий"));
        record.add(new Field("title", Field.TYPE_STRING, "Составляй расписание"));
        record.add(new Field("img", Field.TYPE_STRING, "intro_2"));
        lr.add(record);

        record = new Record();
        record.add(new Field("message", Field.TYPE_STRING, "Поставь перед собой цель\nи иди к ней, а мы поможем"));
        record.add(new Field("title", Field.TYPE_STRING, "Достигай целей"));
        record.add(new Field("img", Field.TYPE_STRING, "intro_3"));
        lr.add(record);

        record = new Record();
        record.add(new Field("message", Field.TYPE_STRING, "Ни одна акция или новость\nне пройдет мимо"));
        record.add(new Field("title", Field.TYPE_STRING, "Будь в курсе событий"));
        record.add(new Field("img", Field.TYPE_STRING, "intro_4"));
        lr.add(record);

        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
        return recordToJson.modelToJson(f);
    }

}
