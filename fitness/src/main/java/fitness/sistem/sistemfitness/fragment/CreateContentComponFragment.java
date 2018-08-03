package fitness.sistem.sistemfitness.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.base.BaseFragment;
import fitness.sistem.compon.components.PhotoComponent;
import fitness.sistem.compon.components.PopUpComponent;
import fitness.sistem.compon.components.RecyclerComponent;
import fitness.sistem.compon.interfaces_classes.ICustom;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.json_simple.WorkWithRecordsAndViews;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.tools.Constants;

public class CreateContentComponFragment extends BaseFragment implements ICustom {

    @BindView(R.id.add_content) ImageView add_content;
    @BindView(R.id.content_edit) LinearLayout content_edit;
    @BindView(R.id.show_menu) LinearLayout show_menu;
    @BindView(R.id.title) TextView titleView;

    protected Unbinder unbinder;
    private String title, type;
    private int typeId;
    private String filePath, typeIcon;
    private LayoutInflater inflater;
    private PhotoComponent photoComponent;
    private PopUpComponent popUpComponent;
    private RecyclerComponent recyclerComponent;
    private Record recordContent;
    private WorkWithRecordsAndViews workWithRecordsAndViews = new WorkWithRecordsAndViews();
    private String stParam = "typeInt,type,title,img,txt1,txt2,txt3";
    private int positionSelect;
    private View viewContent;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_create_content_compon;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mComponent.setCustom(this);
        unbinder = ButterKnife.bind(this, parentLayout);
        title = titleView.getText().toString();
        inflater = LayoutInflater.from(activity);
        photoComponent = (PhotoComponent) mComponent.getComponent(R.id.camera);
        popUpComponent = (PopUpComponent) mComponent.getComponent(R.id.show_menu);
        recyclerComponent = (RecyclerComponent) mComponent.getComponent(R.id.recycler);
    }

    @Override
    public void customClick(int viewId, int position, Record record) {
        add_content.setVisibility(View.VISIBLE);
        content_edit.setVisibility(View.VISIBLE);
        show_menu.setVisibility(View.GONE);
        type = record.getString("type");
        typeId = (Integer) record.getValue("typeId");
        switch (viewId) {
            case R.id.show_menu :
                recordContent = null;
                positionSelect = -1;
                titleView.setText(record.getString("title"));
                break;
            case R.id.recycler :
                recordContent = record;
                positionSelect = position;
                String st = popUpComponent.listData.get(typeId).getString("title");
                titleView.setText(st);
                break;
        }
        setView();
    }

    @Override
    public void afterBindViewHolder(int viewId, int position, Record record, RecyclerView.ViewHolder holder) {

    }

    @Override
    public void beforeProcessingResponse(Field response, BaseComponent baseComponent) {

    }

    @Override
    public void clickView(View viewClick, View parentView, BaseComponent baseComponent, Record rec, int position) {

    }

    @Override
    public void receiverWork(Intent intent) {

    }

    private void setView() {
        content_edit.removeAllViews();
        viewContent = inflater.inflate(getResources().getIdentifier("cc_" + type,
                "layout", activity.getPackageName()), null);
        if (viewContent == null) return;
        filePath = typeIcon = "";
        content_edit.addView(viewContent);
        if (type.equals(Constants.TYPE_PHOTO)) {
            if (photoComponent != null) {
                photoComponent.initView();
            }
        }

        if (recordContent != null) {
            workWithRecordsAndViews.RecordToView(recordContent, viewContent);
        }
    }

    @Override
    public boolean canBackPressed() {
        boolean result = isHideAnimatePanel();
        if (content_edit.getVisibility() == View.VISIBLE) {
            content_edit.setVisibility(View.GONE);
            result = false;
        }
        return result;
    }

    @OnClick(R.id.add_content)
    public void add_content() {
        add_content.setVisibility(View.GONE);
        content_edit.setVisibility(View.GONE);
        show_menu.setVisibility(View.VISIBLE);
        titleView.setText(title);
        if (type.equals(Constants.TYPE_PHOTO)) {
            if (photoComponent != null) {
                filePath = photoComponent.getFilePath();
            }
        }
        Record param = workWithRecordsAndViews.ViewToRecord(viewContent, stParam);
        Record rec = setRecord(param);
        rec.setString("type", type);
        rec.setInteger("typeId", typeId);
        rec.setString("img", filePath);
        if (positionSelect == -1) {
            recyclerComponent.listData.add(rec);
            recyclerComponent.adapter.notifyItemInserted(recyclerComponent.listData.size() - 1);
        } else {
            recyclerComponent.listData.set(positionSelect, rec);
            recyclerComponent.adapter.notifyItemChanged(positionSelect);
        }
    }

    public Record setRecord(Record paramRecord) {
        Record rec = new Record();
        for (Field f : paramRecord) {
            if (f.value == null) {
                String st = ComponGlob.getInstance().getParamValue(f.name);
                if (st.length() > 0) {
                    rec.add(new Field(f.name, Field.TYPE_STRING, st));
                }
            } else {
                rec.add(new Field(f.name, Field.TYPE_STRING, f.value));
            }
        }
        return rec;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
