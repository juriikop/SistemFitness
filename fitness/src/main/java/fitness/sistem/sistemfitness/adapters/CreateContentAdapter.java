package fitness.sistem.sistemfitness.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;

import fitness.sistem.compon.interfaces_classes.OnClickItemRecycler;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.helper.ItemTouchHelperAdapter;
import fitness.sistem.sistemfitness.helper.ItemTouchHelperViewHolder;
import fitness.sistem.sistemfitness.helper.OnStartDragListener;
import fitness.sistem.sistemfitness.network.responce.ContentList;
import fitness.sistem.sistemfitness.network.responce.ContentTotal;
import fitness.sistem.sistemfitness.tools.Constants;

public class CreateContentAdapter extends RecyclerView.Adapter<CreateContentAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private ContentList mItems;
    private Context context;
    private String[] type_content;
    private OnClickItemRecycler onClickItemRecycler;
    private final OnStartDragListener mDragStartListener;

    public CreateContentAdapter(Context context, ContentList contentList,
                                   OnClickItemRecycler clickItemRecycler,
                                   OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        mItems = contentList;
        this.context = context;
        this.onClickItemRecycler = onClickItemRecycler;
        type_content = context.getResources().getStringArray(R.array.type_content);
    }

    @Override
    public int getItemViewType(int position) {
        return indexOfArray(type_content, mItems.get(position).type);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(context.getResources().getIdentifier(
                "ccv_" + type_content[viewType], "layout", context.getPackageName()), parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        String st;
        View v = holder.itemView;
        ContentTotal ct = mItems.get(position);
        if (holder.title != null) {
            holder.title.setText(ct.title);
        }
        st = ct.txt1;
        if (holder.txt1 != null && st != null && st.length() > 0) {
            holder.txt1.setText(st);
        }
        if (holder.img != null) {
            if (ct.data.size() > 0) {
                st = ct.data.get(0);
            } else {
                st = ct.url;
            }
            if (st != null && st.length() > 0) {
                switch (ct.type) {
                    case Constants.TYPE_ST_ICON:
                        holder.img.setBackgroundResource(context.getResources()
                                .getIdentifier(st, "drawable", context.getPackageName()));
                        break;
                    default:
                        Glide.with(context)
                                .load(st)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(holder.img);
                        break;
                }
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemRecycler != null) {
                    onClickItemRecycler.onClick(holder, v, holder.getAdapterPosition(), null);
                }
            }
        });

        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final TextView title;
        public final TextView txt1;
        public final ImageView img;

        public final LinearLayout handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            txt1 = (TextView) itemView.findViewById(R.id.txt1);
            img = (ImageView) itemView.findViewById(R.id.img);
            handleView = (LinearLayout) itemView.findViewById(R.id.handle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public static int indexOfArray(String[] arr, String st) {
        int result = -1;
        for (int i = 0; i < arr.length; i++) {
            if (st.equals(arr[i])) {
                return i;
            }
        }
        return result;
    }
}
