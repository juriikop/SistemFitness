package fitness.sistem.compon.json_simple;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.custom_components.SimpleImageView;
import fitness.sistem.compon.custom_components.SimpleTextView;
import fitness.sistem.compon.custom_components.SwipeLayout;
import fitness.sistem.compon.interfaces_classes.IComponent;
import fitness.sistem.compon.interfaces_classes.Navigator;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.interfaces_classes.Visibility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import static fitness.sistem.compon.json_simple.Field.TYPE_STRING;

public class WorkWithRecordsAndViews {
    protected Record model;
    protected View view;
    protected Navigator navigator;
    protected View.OnClickListener clickView;
    protected Context context;
    protected String[] param;
    protected Record recordResult;
    private boolean setParam;
    private Visibility[] visibilityManager;
    private boolean isSwipe;
    private int swipeId, rightId, leftId;
    private SwipeLayout swipeLayout;

    public void RecordToView(Record model, View view) {
        RecordToView(model, view, null, null, null);
    }

    public void RecordToView(Record model, View view, Navigator navigator,
                             View.OnClickListener clickView, Visibility[] visibilityManager) {
        this.model = model;
        this.view = view;
        this.navigator = navigator;
        this.clickView = clickView;
        if (view instanceof SwipeLayout) {
            isSwipe = true;
            swipeLayout = (SwipeLayout) view;
            swipeId = swipeLayout.getSwipeId();
            rightId = swipeLayout.getRightId();
            leftId = swipeLayout.getLeftId();
        } else {
            isSwipe = false;
        }
        context = view.getContext();
        setParam = false;
        this.visibilityManager = visibilityManager;
        enumViewChild(view);
    }

    public Record ViewToRecord(View view, String par) {
        recordResult = new Record();
        param = par.split(",");
        for (String st : param) {
            recordResult.add(new Field(st, TYPE_STRING, null));
        }
        setParam = true;
        enumViewChild(view);
        return recordResult;
    }

    private void enumViewChild(View v) {
        ViewGroup vg;
        int id;
        if (v instanceof ViewGroup) {
            vg = (ViewGroup) v;
            int countChild = vg.getChildCount();
            id = v.getId();
            if (id > -1) {
                setValue(v);
            }
            for (int i = 0; i < countChild; i++) {
                enumViewChild(vg.getChildAt(i));
            }
        } else {
            id = v.getId();
            if (id != -1) {
                setValue(v);
            }
        }
    }

    private void setRecordField(View v, String name) {
        for (Field f : recordResult) {
            if (f.name.equals(name)) {
                if (v instanceof IComponent) {
                    f.value = ((IComponent) v).getString();
                    break;
                }
                if (v instanceof TextView) {
                    f.value = ((TextView) v).getText().toString();
                    break;
                }
            }
        }
    }

    private void setValue(View v) {
        int id = v.getId();
        String st;
        String name = v.getContext().getResources().getResourceEntryName(id);
        if (v instanceof IComponent) {
            st = ((IComponent) v).getAlias();
            if (st != null && st.length() > 0) {
                name = st;
            }
        }
        if (setParam) {
            setRecordField(v, name);
            return;
        }
        if (navigator != null) {
            for (ViewHandler vh : navigator.viewHandlers) {
                if (id == vh.viewId) {
                    if (isSwipe) {
                        if (id == swipeId) {
                            swipeLayout.setOnClick(clickView);
                        }
//                        else {
//                            switch (typeSwipe(v)) {
//                                case 1:
//                                    v.setOnClickListener(clickView);
//                                    break;
//                                case 2:
//    //                                v.setOnClickListener(clickRight);
//                                    v.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            SwipeLayout rp = getRootParent(v);
//                                            Log.d("QWERT","clickRight TAG="+rp.getTag());
//                                            if (rp.isSwipeRight()) {
//                                                Log.d("QWERT","clickRight clickRight clickRight");
//                                                clickView.onClick(v);
//                                            }
//                                        }
//                                    });
//                                    break;
//                                case 3:
//                                    v.setOnClickListener(clickLeft);
//                                    break;
//                            }
//                        }
                    } else {
                        v.setOnClickListener(clickView);
                    }
                    break;
                }
            }
        }
        if (visibilityManager != null && visibilityManager.length > 0) {
            for (Visibility vis : visibilityManager) {
                if (vis.viewId == id) {
                    if (model.getBooleanVisibility(vis.nameField)) {
                        switch (vis.typeShow) {
                            case 0 :
                                v.setVisibility(View.VISIBLE);
                                break;
                            case 1 :
                                v.setEnabled(true);
                                break;
                        }
                    } else {
                        switch (vis.typeShow) {
                            case 0 :
                                v.setVisibility(View.GONE);
                                break;
                            case 1 :
                                v.setEnabled(false);
                                break;
                        }
                    }
                    break;
                }
            }
        }
        if (model == null) {
            return;
        }
        Field field = model.getField(name);
        if (field != null) {
            if (v instanceof IComponent) {
                ((IComponent) v).setData(field.value);
                return;
            }

            if (v instanceof TextView) {
                if (field.value instanceof String) {
                    ((TextView) v).setText((String )field.value);
                    return;
                }
                if (field.value instanceof Number) {
                    if (v instanceof SimpleTextView) {
                        st = ((SimpleTextView) v).getNumberFormat();
                        if (st != null) {
                            ((SimpleTextView) v).setText(new Formatter().format(st, field.value).toString());
                        } else {
                            ((SimpleTextView) v).setText(field.value.toString());
                        }
                    } else {
                        ((TextView) v).setText(field.value.toString());
                    }
                    return;
                }
                if(field.value instanceof Date) {
                    SimpleDateFormat format;
                    if (v instanceof SimpleTextView) {
                        st = ((SimpleTextView) v).getDateFormat();
                        if (st != null) {
                            ((SimpleTextView) v).setText(new Formatter().format(st, field.value).toString());
                        } else {
                            format = new SimpleDateFormat();
                            ((TextView) v).setText(format.format((Date) field.value));
                        }
                    } else {
                        format = new SimpleDateFormat();
                        ((TextView) v).setText(format.format((Date) field.value));
                    }
                    return;
                }
                return;
            }
        }

        if (v instanceof ImageView) {
            if (field == null) return;
            if (field.type == TYPE_STRING) {
                st = (String) field.value;
                if (st == null) {
                    st = "";
                }
                if (st.length() == 0) return;
                if (st.contains("content")) {
                    Glide.with(view.getContext())
                            .load(st)
                            .into((ImageView) v);
                } else {
                    if (st.contains("/")) {
                        if (!st.contains("http")) {
                            st = ComponGlob.getInstance().appParams.baseUrl + st;
                        }
                        Glide.with(view.getContext())
                                .load(st)
                                .into((ImageView) v);
                    } else {
                        if (v instanceof SimpleImageView) {
                            ((ImageView) v).setImageDrawable(view.getContext()
                                    .getResources().getDrawable(((SimpleImageView) v).getPlaceholder()));
                        } else {
                            ((ImageView) v).setImageResource(view.getContext().getResources()
                                    .getIdentifier(st, "drawable", view.getContext().getPackageName()));
                        }
                    }
                }
            } else {
                ((ImageView) v).setImageResource((Integer)field.value);
            }
        }
    }

//    private SwipeLayout getRootParent(View v) {
//        if (v == null) return null;
//        ViewParent vp = v.getParent();
//        while (vp != null && ! (vp instanceof SwipeLayout)) {
//            vp = vp.getParent();
//        }
//        return (SwipeLayout)vp;
//    }
//
//    private int typeSwipe(View v) {
//        View vv = v;
//        do {
//            int id = vv.getId();
//            if (id == swipeId) {
//                return 1;
//            } else if (id == rightId) {
//                return 2;
//            } else if (id == leftId) {
//                return 3;
//            }
//            vv = (ViewGroup) vv.getParent();
//        } while (vv != null);
//        return 0;
//    }
//
//    View.OnClickListener clickRight = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (swipeLayout.isSwipeRight()) {
//                Log.d("QWERT","clickRight clickRight clickRight");
//                clickView.onClick(v);
//            }
//        }
//    };
//
//    View.OnClickListener clickLeft = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (swipeLayout.isSwipeLeft()) {
//                clickView.onClick(v);
//            }
//        }
//    };

}
