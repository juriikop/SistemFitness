package fitness.sistem.sistemfitness.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fitness.sistem.compon.base.BaseFragment;
import fitness.sistem.compon.volley.VolleyRequest;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.adapters.CreateContentAdapter;
import fitness.sistem.sistemfitness.helper.OnStartDragListener;
import fitness.sistem.sistemfitness.helper.SimpleItemTouchHelperCallback;
import fitness.sistem.sistemfitness.network.responce.ContentList;
import fitness.sistem.sistemfitness.network.responce.ContentTotal;
import fitness.sistem.sistemfitness.network.responce.IconItem;
import fitness.sistem.sistemfitness.network.responce.Icons;
import fitness.sistem.sistemfitness.tools.Constants;
import fitness.sistem.sistemfitness.tools.changeColor.FloatingButtonColor;

public class CreateContentFragment extends BaseFragment {

    private ItemTouchHelper mItemTouchHelper;
    private String contentStringJson;
    private CreateContentAdapter adapter;
    private final Gson gson = new Gson();
    private ContentList contentList;
    private PopupMenu popupMenu;
    private int offsetY;
    private int offset_x = 0;
    private int offset_y = 0;
    private int click_x, click_y;
    private Boolean touchFlag = false;
    private int wh_add_content;
    private int menuId;
    private String[] menu_create, type_content;
    private String[] icon_type, icon_name;
    private Icons icons;
    private int positionEdit;
    private ContentTotal valueEdit;

    @BindView(R.id.recycler) RecyclerView recycler;
    @BindView(R.id.add_content) FloatingButtonColor add_content;
    @BindView(R.id.root) RelativeLayout root;
    @BindView(R.id.tool) LinearLayout tool;

    protected Unbinder unbinder;

    @Override
    public void initView(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, parentLayout);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(llm);
        offsetY = 0;
        add_content.setOnTouchListener(touchAddContent);
        wh_add_content = (int) getResources().getDimension(R.dimen.wh_add_content);
        menu_create = getResources().getStringArray(R.array.menu_create_content);
        icon_type = getResources().getStringArray(R.array.icon_type);
        icon_name = getResources().getStringArray(R.array.icon_name);
        type_content = getResources().getStringArray(R.array.type_content);
        icons = new Icons();
        for (int i = 0; i < icon_name.length; i++) {
            icons.add(new IconItem(icon_type[i], icon_name[i]));
        }
        popupMenu = new PopupMenu(getActivity(), (View) add_content);
        Menu m = popupMenu.getMenu();
        for (int i = 0; i < menu_create.length; i++) {
            m.add(0, i, i, menu_create[i]);
        }
        popupMenu.setOnMenuItemClickListener(menuItemClick);
        vlContent.onResponse(null);
        root.setOnTouchListener(touchRoot);
    }

    VolleyRequest.VolleyListener vlContent = new VolleyRequest.VolleyListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("QWERT","ContentFragment onErrorResponse="+error);
//            back();
        }

        @Override
        public void onResponse(Object response) {
            contentStringJson = PreferenceTools.getContent(getActivity());
            Log.d("QWERT","JSON="+contentStringJson);
            if (contentStringJson == null) {
                contentList = new ContentList();
            } else {
                try {
                    contentList = gson.fromJson(contentStringJson, ContentList.class);
                } catch (JsonSyntaxException e) {
                    contentList = new ContentList();
                    e.printStackTrace();
                }
            }
            AuraApp.getInstance().contentList = contentList;
            adapter = new FormationContentAdapter(getActivity(), contentList,
                    onClickItemRecycler, FormationContentFragment.this);

            recycler.setHasFixedSize(true);
            recycler.setAdapter(adapter);
            recycler.setOnScrollListener(scroll);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(recycler);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_create_content;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    OnStartDragListener dragListener = new OnStartDragListener() {
        @Override
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            mItemTouchHelper.startDrag(viewHolder);
        }
    };

    View.OnTouchListener touchAddContent = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    touchFlag = true;
                    offset_x = (int) event.getX();
                    offset_y = (int) event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    touchFlag = false;
                    if (Math.abs(offset_x - (int) event.getX()) < 15
                            && Math.abs(offset_y - (int) event.getY()) < 15) {
                        add_content();
                    }
                    break;
            }
            return false;
        }
    };

    View.OnTouchListener touchRoot = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (touchFlag) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        touchFlag = true;
                        click_x = (int) event.getX();
                        click_y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) event.getX() - offset_x;
                        int y = (int) event.getY() - offset_y;
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                new ViewGroup.MarginLayoutParams(wh_add_content, wh_add_content));
                        lp.setMargins(x, y, 0, 0);
                        add_content.setLayoutParams(lp);
                        break;
                    case MotionEvent.ACTION_UP:
                        touchFlag = false;
                        if (Math.abs(click_x - (int) event.getX()) < 5
                                && Math.abs(click_y - (int) event.getY()) < 5) {
                            add_content();
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
            return false;
        }
    };

    public void add_content() {
        popupMenu.show();
    }

    PopupMenu.OnMenuItemClickListener menuItemClick = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            menuId = item.getItemId();
            ContentTotal ct;
            String type = type_content[menuId];
            switch (type) {
                case Constants.TYPE_LINE:
                    ct = new ContentTotal(menuId, type, null, null, null, null, null);
                    contentList.add(ct);
                    adapter.notifyItemChanged(contentList.size() - 1);
                    break;
                case Constants.TYPE_GALLERY:
                    ct = new ContentTotal(menuId, type, "Тест галереи", null, null, null, null);
                    List<String> ls = new ArrayList<>();
                    ls.add("http://aura.alpha.branderstudio.com/uploads/plavanie/21-11-plavanie/IMG_5224.jpg");
                    ls.add("http://aura.alpha.branderstudio.com/uploads/plavanie/21-11-plavanie/IMG_5261.jpg");
                    ls.add("http://aura.alpha.branderstudio.com/uploads/plavanie/21-11-plavanie/IMG_5334.jpg");
                    ls.add("http://aura.alpha.branderstudio.com/uploads/plavanie/21-11-plavanie/IMG_5572.jpg");
                    ls.add("http://aura.alpha.branderstudio.com/uploads/plavanie/21-11-plavanie/IMG_5646.jpg");
                    ls.add("http://aura.alpha.branderstudio.com/uploads/plavanie/21-11-plavanie/IMG_5650.jpg");
                    ct.data = ls;
                    contentList.add(ct);
                    adapter.notifyItemChanged(contentList.size() - 1);
                    break;
                default:
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.TYPE, type);
                    bundle.putInt(Constants.POSITION, -1);
//                    ((MainActivity) getActivity()).handleFragmentSwitching(FactoryFragment.ID.CREATE_ELEMENT, bundle, null, true, adapter);
            }
//            if (Constants.TYPE_LINE.equals(type)) {
//                ContentTotal ct = new ContentTotal(menuId, type, null, null, null, null, null);
//                contentList.add(ct);
//                adapter.notifyItemChanged(contentList.size() - 1);
//            } else {
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.TYPE, type);
//                bundle.putInt(Constants.POSITION, -1);
//                ((MainActivity) getActivity()).handleFragmentSwitching(FactoryFragment.ID.CREATE_ELEMENT, bundle, null, true, adapter);
//            }
            return false;
        }
    };
}
