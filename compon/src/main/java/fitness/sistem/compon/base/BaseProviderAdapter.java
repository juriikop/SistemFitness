package fitness.sistem.compon.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.Navigator;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.interfaces_classes.Visibility;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.param.ParamView;
import fitness.sistem.compon.json_simple.WorkWithRecordsAndViews;

public class BaseProviderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private int[] layoutItemId;
    public String fieldType;
    private BaseProvider provider;
    private Context context;
    private WorkWithRecordsAndViews modelToView;
    private String layout;
    private Navigator navigator;
    private BaseComponent baseComponent;
    private boolean isClickItem;
    private Visibility[] visibilityManager;
    private LayoutInflater inflater;
    private IBase iBase;

    public BaseProviderAdapter(BaseComponent baseComponent) {
        context = baseComponent.activity;
        iBase = baseComponent.iBase;
        inflater = LayoutInflater.from(context);
        this.baseComponent = baseComponent;
        this.provider = baseComponent.provider;
        navigator = baseComponent.navigator;
        isClickItem = false;
        if (navigator != null) {
            for (ViewHandler vh : navigator.viewHandlers) {
                if (vh.viewId == 0) {
                    isClickItem = true;
                    break;
                }
            }
        }
        ParamView paramView = baseComponent.paramMV.paramView;
        if (paramView != null) {
            layoutItemId = paramView.layoutTypeId;
            fieldType = paramView.fieldType;
        } else {
            layoutItemId = null;
            fieldType = "";
        }
        visibilityManager = paramView.visibilityArray;
        modelToView = new WorkWithRecordsAndViews();
//        layout = "";
        layout = "item_recycler_" + baseComponent.paramMV.nameParentComponent;
    }

    @Override
    public int getItemViewType(int position) {
        if (fieldType.length() == 0) {
            return 0;
        } else {
            Field f = provider.get(position).getField(fieldType);
            if (f == null) {
                return 0;
            }
            if (f.type == Field.TYPE_STRING) {
                return Integer.valueOf((String) f.value);
            } else {
                if (f.value instanceof Integer) {
                    return (int) f.value;
                } else {
                    long ll = (Long) f.value;
                    return (int) ll;
                }
//                if (f.type == Field.TYPE_INTEGER) {
//                    return (int) provider.get(position).getValue(fieldType);
//                } else {
//                    long ll = (Long) f.value;
//                    return (int) ll;
//                }
            }
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (layoutItemId == null) {
            int resurceId = context.getResources().getIdentifier(layout, "layout", context.getPackageName());
            if (resurceId == 0) {
                iBase.log("Не найден " + layout);
            }
            view = inflater.inflate(resurceId, parent, false);
        } else {
            view = inflater.inflate(layoutItemId[viewType], parent, false);
        }
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Record record = (Record) provider.get(position);
        modelToView.RecordToView(provider.get(position),
                holder.itemView, navigator, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseComponent.clickItem.onClick(holder, view, holder.getAdapterPosition());
            }
        }, visibilityManager);

        if (isClickItem) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseComponent.clickItem.onClick(holder, null, holder.getAdapterPosition());
                }
            });
        }
        if (baseComponent.moreWork != null) {
            baseComponent.moreWork.afterBindViewHolder(record, holder);
        }
    }

    @Override
    public int getItemCount() {
        return provider.getCount();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
