package fitness.sistem.compon.components;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fitness.sistem.compon.base.BaseComponent;
import fitness.sistem.compon.custom_components.PagerIndicator;
import fitness.sistem.compon.interfaces_classes.IBase;
import fitness.sistem.compon.interfaces_classes.ViewHandler;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.ListRecords;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.json_simple.WorkWithRecordsAndViews;
import fitness.sistem.compon.param.ParamComponent;
import fitness.sistem.compon.tools.PreferenceTool;

public class IntroComponent extends BaseComponent {
    ViewPager pager;
    ListRecords listData;
    PagerIndicator indicator;
    private View furtherSkip, furtherNext, furtherStart;
    int count, count_1;
    private LayoutInflater inflater;
//    private WorkWithRecordsAndViews modelToFurther = new WorkWithRecordsAndViews();

    public IntroComponent(IBase iBase, ParamComponent paramMV, MultiComponents multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        pager = (ViewPager) parentLayout.findViewById(paramMV.paramView.viewId);
        if (pager == null) {
            Log.i("SMPL", "Не найден ViewPager в " + paramMV.nameParentComponent);
        }
        listData = new ListRecords();
    }

    @Override
    public void changeData(Field field) {
        if (field != null) {
            listData.clear();
            listData.addAll((ListRecords) field.value);
            count = listData.size();
            count_1 = count - 1;
            if (count > 0) {
                inflater = (LayoutInflater) iBase.getBaseActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                if (paramMV.paramView.indicatorId != 0) {
                    indicator = (PagerIndicator) parentLayout.findViewById(paramMV.paramView.indicatorId);
                    indicator.setCount(count);
                }
                if (paramMV.paramView.furtherSkip != 0) {
                    furtherSkip = (View) parentLayout.findViewById(paramMV.paramView.furtherSkip);
                    if (furtherSkip != null) {
                        if (count == 1) {
                            furtherSkip.setVisibility(View.GONE);
                        } else {
                            furtherSkip.setVisibility(View.VISIBLE);
                        }
                        furtherSkip.setOnClickListener(listener);
                    }
                }

                if (paramMV.paramView.furtherNext != 0) {
                    furtherNext = (View) parentLayout.findViewById(paramMV.paramView.furtherNext);
                    if (furtherNext != null) {
                        if (count == 1) {
                            furtherNext.setVisibility(View.GONE);
                        } else {
                            furtherNext.setVisibility(View.VISIBLE);
                        }
                        furtherNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int posit = pager.getCurrentItem() + 1;
                                if (posit < count) {
                                    pager.setCurrentItem(posit);
                                }
                            }
                        });
                    }
                }

                if (paramMV.paramView.furtherStart != 0) {
                    furtherStart = (View) parentLayout.findViewById(paramMV.paramView.furtherStart);
                    if (furtherStart != null) {
                        if (count == 1) {
                            furtherStart.setVisibility(View.VISIBLE);
                        } else {
                            furtherStart.setVisibility(View.GONE);
                        }
                        furtherStart.setOnClickListener(listener);
                    }
                }
                
                pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (indicator != null) {
                            indicator.setSelect(position);
                        }
                        if (count_1 == position) {
                            if (furtherSkip != null) furtherSkip.setVisibility(View.GONE);
                            if (furtherNext != null) furtherNext.setVisibility(View.GONE);
                            if (furtherStart != null) furtherStart.setVisibility(View.VISIBLE);
                        } else {
                            if (furtherSkip != null) furtherSkip.setVisibility(View.VISIBLE);
                            if (furtherNext != null) furtherNext.setVisibility(View.VISIBLE);
                            if (furtherStart != null) furtherStart.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                pager.setAdapter(adapter);
            }
        }
    }

    PagerAdapter adapter = new PagerAdapter() {
        WorkWithRecordsAndViews modelToView = new WorkWithRecordsAndViews();
        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            ViewGroup v = (ViewGroup) inflater.inflate(paramMV.paramView.layoutTypeId[0], null);
            Record record = listData.get(position);
            modelToView.RecordToView(record, v, navigator, listener, null);
            viewGroup.addView(v);
            return v;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int position, Object view) {
            viewGroup.removeView((View) view);
        }
    };

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (navigator != null && navigator.viewHandlers.size() > 0) {
                ViewHandler vh = navigator.viewHandlers.get(0);
                iBase.startScreen(vh.nameFragment, false);
                PreferenceTool.setTutorial(true);
                iBase.backPressed();
            }
        }
    };

}