package fitness.sistem.sistemfitness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.network.responce.IconItem;
import fitness.sistem.sistemfitness.network.responce.Icons;

public class CreateElementSpinnerIconAdapter extends BaseAdapter {

    private Icons items;

    public CreateElementSpinnerIconAdapter(Icons it) {
        items = it;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public IconItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        if (position > 0) {
            Context context = parent.getContext();
            IconItem ii = items.get(position);
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_element_icon_type, parent, false);
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_type, parent, false);
            TextView tv = (TextView) view.findViewById(R.id.text);
            tv.setText(ii.name);
            ((ImageView) view.findViewById(R.id.img)).setBackgroundResource(
                    context.getResources().getIdentifier(ii.type, "drawable",
                            context.getPackageName()));
            return view;
        } else {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.create_element_icon_type_null, parent, false);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_element_icon_type_header, parent, false);
        TextView tv = (TextView) view.findViewById(R.id.text);
        tv.setText(items.get(position).name);
        return view;
    }
}
