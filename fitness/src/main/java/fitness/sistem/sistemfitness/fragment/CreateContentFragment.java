package fitness.sistem.sistemfitness.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fitness.sistem.compon.base.BaseFragment;
import fitness.sistem.compon.components.PhotoComponent;
import fitness.sistem.compon.interfaces_classes.OnClickItemRecycler;
import fitness.sistem.compon.interfaces_classes.VolleyListener;
import fitness.sistem.compon.tools.ComponPrefTool;
import fitness.sistem.sistemfitness.R;
import fitness.sistem.sistemfitness.adapters.CreateContentAdapter;
import fitness.sistem.sistemfitness.adapters.CreateElementSpinnerIconAdapter;
import fitness.sistem.sistemfitness.helper.OnStartDragListener;
import fitness.sistem.sistemfitness.helper.SimpleItemTouchHelperCallback;
import fitness.sistem.sistemfitness.network.responce.ContentList;
import fitness.sistem.sistemfitness.network.responce.ContentTotal;
import fitness.sistem.sistemfitness.network.responce.IconItem;
import fitness.sistem.sistemfitness.network.responce.Icons;
import fitness.sistem.sistemfitness.tools.Constants;
import fitness.sistem.sistemfitness.tools.PreferenceTool;
import fitness.sistem.sistemfitness.tools.changeColor.AppColors;

public class CreateContentFragment extends BaseFragment implements OnStartDragListener,
        View.OnTouchListener{

    @BindView(R.id.recycler) RecyclerView recycler;
    @BindView(R.id.show_menu) LinearLayout show_menu;
    @BindView(R.id.root) RelativeLayout root;
    @BindView(R.id.tool) RelativeLayout tool;
    @BindView(R.id.add_content) ImageView add_content;
    @BindView(R.id.content_edit) LinearLayout content_edit;
    @BindView(R.id.title) TextView title;

    private ItemTouchHelper mItemTouchHelper;
    private String contentStringJson;
    private CreateContentAdapter adapter;
    private final Gson gson = new Gson();
    private ContentList contentList;
    private PopupMenu popupMenu;
    private int offsetY;
//    private int colorTool, colorR, colorG, colorB;
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
    private ViewContent viewContent;
    private LayoutInflater inflater;
    private String filePath, typeIcon;
    private String titleScreen;
    private String type;
    private PhotoComponent photoComponent;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_create_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this, parentLayout);
        photoComponent = (PhotoComponent) mComponent.getComponent(R.id.camera);
        titleScreen = title.getText().toString();
        show_menu.setBackground(AppColors.selectorOval(activity, AppColors.accentDark, AppColors.accent));
        LinearLayoutManager llm = new LinearLayoutManager(activity);
        recycler.setLayoutManager(llm);
        contentList = new ContentList();
        adapter = new CreateContentAdapter(activity, contentList,
                onClickItemRecycler, CreateContentFragment.this);

        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        recycler.setOnScrollListener(scroll);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recycler);
        inflater = LayoutInflater.from(activity);
        offsetY = 0;
//        colorTool = getResources().getColor(R.color.primary);
//        colorB = colorTool & 0xff;
//        colorG = (colorTool >> 8) & 0xff;
//        colorR = (colorTool >> 16) & 0xff;
        show_menu.setOnTouchListener(this);
        wh_add_content = (int) getResources().getDimension(R.dimen.floating_d);
        menu_create = getResources().getStringArray(R.array.menu_create_content);
        icon_type = getResources().getStringArray(R.array.icon_type);
        icon_name = getResources().getStringArray(R.array.icon_name);
        type_content = getResources().getStringArray(R.array.type_content);
        icons = new Icons();
        for (int i = 0; i < icon_name.length; i++) {
            icons.add(new IconItem(icon_type[i], icon_name[i]));
        }
        Context wrapper = new ContextThemeWrapper(activity, R.style.popupMenuStyle);
        popupMenu = new PopupMenu(wrapper, (View) show_menu);
        Menu m = popupMenu.getMenu();
        for (int i = 0; i < menu_create.length; i++) {
            m.add(0, i, i, menu_create[i]);
        }
        popupMenu.setOnMenuItemClickListener(menuItemClick);
        vlContent.onResponse(null);
        root.setOnTouchListener(touchRoot);
    }

    VolleyListener vlContent = new VolleyListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("QWERT","ContentFragment onErrorResponse="+error);
        }

        @Override
        public void onResponse(Object response) {
            contentStringJson = PreferenceTool.getContent();
            if (contentStringJson != null && contentStringJson.length() > 0) {
                ContentList cl = null;
                try {
                    cl = gson.fromJson(contentStringJson, ContentList.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (cl != null) {
                    contentList.addAll(cl);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };

//    @OnClick(R.id.done)
//    public void done() {
//        PreferenceTool.setContent( gson.toJson(contentList));
//    }

    @OnClick(R.id.add_content)
    public void add_content() {
        Log.d("QWERT","add_content add_content add_content filePath="+filePath);
        if (type.equals(Constants.TYPE_PHOTO)) {
            if (photoComponent != null) {
                filePath = photoComponent.getFilePath();
                Log.d("QWERT","add_content filePath="+filePath);
            }
        }
        if (typeIcon.length() > 0
                && filePath.length() == 0) {
            filePath = typeIcon;
        }
        if (positionEdit > -1) {
            valueEdit.setValue(0, type,
                    viewToStr(viewContent.title),
                    new String(filePath.getBytes()),
                    viewToStr(viewContent.txt1),
                    viewToStr(viewContent.txt2),
                    viewToStr(viewContent.txt3));
            adapter.notifyItemChanged(positionEdit);
        } else {
            ContentTotal ct = new ContentTotal(0, type,
                    viewToStr(viewContent.title),
                    new String(filePath.getBytes()),
                    viewToStr(viewContent.txt1),
                    viewToStr(viewContent.txt2),
                    viewToStr(viewContent.txt3));
            if (Constants.TYPE_HEADER.equals(type)) {
                contentList.add(0, ct);
                adapter.notifyItemChanged(0);
            } else {
                contentList.add(ct);
                adapter.notifyItemInserted(contentList.size() - 1);
            }
        }
        title.setText(titleScreen);
        content_edit.setVisibility(View.GONE);
        add_content.setVisibility(View.GONE);
        show_menu.setVisibility(View.VISIBLE);
    }

    PopupMenu.OnMenuItemClickListener menuItemClick = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            menuId = item.getItemId();
            ContentTotal ct;
            type = type_content[menuId];
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
                    content_edit.setVisibility(View.VISIBLE);
                    add_content.setVisibility(View.VISIBLE);
                    show_menu.setVisibility(View.GONE);
                    positionEdit = -1;
                    setView(type);
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

    private void setView(String type) {
        title.setText(menu_create[indexOfArray(type_content, type)]);
        content_edit.removeAllViews();
        View v = inflater.inflate(getResources().getIdentifier("cc_" + type,
                "layout", activity.getPackageName()), null);
        if (v == null) return;
        viewContent = new ViewContent(v);
        filePath = typeIcon = "";
//        if (viewContent.camera != null) {
//            viewContent.camera.setOnClickListener(clickPhoto);
//        }
        if (viewContent.spinner != null) {
            setSpinner();
        }
        content_edit.addView(v);

        if (type.equals(Constants.TYPE_PHOTO)) {
            if (photoComponent != null) {
                photoComponent.initView();
            }
        }
//        viewContent.title.setText("QWWEEEERRER");
        if (positionEdit > -1) {
            valueEdit = contentList.get(positionEdit);
            viewContent.title.setText(valueEdit.title);
            if (viewContent.txt1 != null) {
                viewContent.txt1.setText(valueEdit.txt1);
            }
            if (viewContent.txt2 != null) {
                viewContent.txt2.setText(valueEdit.txt2);
            }
            if (viewContent.txt3 != null) {
                viewContent.txt3.setText(valueEdit.txt3);
            }
            if (viewContent.img != null) {
                filePath = typeIcon = valueEdit.url;
                if (viewContent.spinner != null) {
                    viewContent.spinner.setSelection(indexOfArray(icon_type, typeIcon));
                } else {
                    Glide.with(getActivity())
                            .load(filePath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(viewContent.img);
                }
            }
            if (viewContent.pager != null) {

            }
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

    private void setSpinner() {
        viewContent.spinner.setAdapter(new CreateElementSpinnerIconAdapter(icons));
        viewContent.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < icons.size()) {
                    typeIcon = icon_type[position];
                    viewContent.img.setBackgroundResource(
                            getResources().getIdentifier(typeIcon, "drawable",
                                    getActivity().getPackageName()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private String viewToStr(TextView et) {
        if (et != null) {
            return et.getText().toString();
        } else {
            return "";
        }
    }

    RecyclerView.OnScrollListener scroll = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            offsetY +=dy;
            if (offsetY < 0) {
                offsetY = 0;
            }
            int alfa = offsetY / 5 * 2;
            if (alfa < 0) alfa = 0;
            if (alfa > 255) {
                alfa = 255;
            }
            tool.setAlpha(alfa);
        }
    };

    OnClickItemRecycler onClickItemRecycler = new OnClickItemRecycler() {
        @Override
        public void onClick(RecyclerView.ViewHolder holder, View view, int position) {
            positionEdit = holder.getAdapterPosition();
            valueEdit = contentList.get(positionEdit);
            String type = valueEdit.type;
            if (type.equals(Constants.TYPE_LINE)) return;
            Bundle bundle = new Bundle();
            bundle.putString(Constants.TYPE, type);
            bundle.putInt(Constants.POSITION, positionEdit);
//            ((MainActivity) getActivity()).handleFragmentSwitching(FactoryFragment.ID.CREATE_ELEMENT, bundle, null, true, adapter);
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
                        show_menu.setLayoutParams(lp);
                        break;
                    case MotionEvent.ACTION_UP:
                        touchFlag = false;
                        if (Math.abs(click_x - (int) event.getX()) < 5
                                && Math.abs(click_y - (int) event.getY()) < 5) {
                            show_menu();
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
                    show_menu();
                }
                break;
        }
        return false;
    }

    @OnClick(R.id.back)
    public void back() {
        activity.openDrawer();
    }

    public void show_menu() {
        popupMenu.show();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

//    View.OnClickListener clickPhoto = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
////            photoCaptureTool.getUserPhoto(CreateElementContentFragment.this,
////                    new PhotoCaptureTool.PhotoReceivingCompleteListener() {
////                        @Override
////                        public void onPhotoReceived(String photoPath) {
////                            filePath = photoPath;
////                            setPhotoMem(filePath);
////                        }
////                    });
//        }
//    };

    private void setPhotoMem(String filePath) {
        if (filePath != null
                && filePath.length() > 0) {
            Glide.with(getActivity())
                    .load(filePath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(viewContent.img);
        }
    }

    public class ViewContent {
        public TextView title;
        public TextView txt1;
        public TextView txt2;
        public TextView txt3;
        public ImageView img;
        public ImageView camera;
        public Spinner spinner;
        public ViewPager pager;

        public ViewContent(View itemView) {
            title = (TextView) itemView.findViewById(R.id.title);
            txt1 = (TextView) itemView.findViewById(R.id.txt1);
            txt2 = (TextView) itemView.findViewById(R.id.txt2);
            txt3 = (TextView) itemView.findViewById(R.id.txt3);
            img = (ImageView) itemView.findViewById(R.id.img);
            camera = (ImageView) itemView.findViewById(R.id.camera);
            spinner = (Spinner) itemView.findViewById(R.id.spinner_icon);
            pager = (ViewPager) itemView.findViewById(R.id.pager);
        }
    }
}
