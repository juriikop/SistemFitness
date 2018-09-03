package fitness.sistem.compon.custom_components;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class SwipeLayout extends RelativeLayout {

    private VelocityTracker mVelocityTracker;
    private FlingAnimation mFlingXAnimation;
    private SpringAnimation animX;
    private View mSwipeView;
    private float mDownX;
    private float mOffsetX;
    private float minV, halfMin;
    private float maxV, halfMax;
    private View dragRight, dragLeft;
    private TYPE_SWIPE typeSwipeRight, typeSwipeLeft;
    private boolean startMove;
    private boolean isNoSwipe;
    private RecyclerView recycler;
    private RecyclerView.ViewHolder holder;
    private OnSwipeRemove listener;

    public enum TYPE_SWIPE {DRAG, CURTAIN, HARMONIC, REMOVE};
    public enum DIRECT {LEFT, RIGHT};

    public SwipeLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mVelocityTracker = VelocityTracker.obtain();
        dragRight = null;
        dragLeft = null;
        recycler = null;
        minV = 0;
        maxV = 0;
        startMove = false;
        isNoSwipe = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float tX;
        if (mSwipeView == null) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if ( ! isSwipe() && isSwipeInRecycler()) {
                    isNoSwipe = true;
                    return true;
                } else {
                    isNoSwipe = false;
                }
                mDownX = event.getX();
                if (animX != null) {
                    animX.cancel();
                }
                if (mFlingXAnimation != null) {
                    mFlingXAnimation.cancel();
                }
                if (typeSwipeLeft == TYPE_SWIPE.REMOVE) {
                    maxV = mSwipeView.getWidth();
                    halfMax = maxV / 2;
                } else {
                    if (dragLeft != null && maxV == 0) {
                        maxV = dragLeft.getWidth();
                        halfMax = maxV / 2;
                    }
                }
                if (typeSwipeRight == TYPE_SWIPE.REMOVE) {
                    minV = - mSwipeView.getWidth();
                    halfMin = minV / 2;
                } else {
                    if (dragRight != null && minV == 0) {
                        minV = -dragRight.getWidth();
                        halfMin = minV / 2;
                        setWidthRight();
                    }
                }
                mOffsetX = mSwipeView.getTranslationX();
                mVelocityTracker.addMovement(event);
                startMove = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isNoSwipe) return true;
                tX = (event.getX() - mDownX + mOffsetX);
                if (startMove && Math.abs(tX) < 20) return true;
                startMove = false;
                if (tX > maxV) {
                    tX = maxV;
                }
                if (tX < minV ) {
                    tX = minV;
                }
                mSwipeView.setTranslationX(tX);
                swipeShow(tX);
                mVelocityTracker.addMovement(event);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isNoSwipe) return true;
                mVelocityTracker.computeCurrentVelocity(1000);
                tX = mSwipeView.getTranslationX();
                float minS, maxS;
                if (tX <= maxV && tX >= minV) {
                    if (tX < 0) {
                        minS = minV;
                        maxS = 0f;
                    } else {
                        minS = 0f;
                        maxS = maxV;
                    }
                    mFlingXAnimation = new FlingAnimation(mSwipeView,
                            DynamicAnimation.TRANSLATION_X)
                            .setFriction(0.5f)
                            .setMinValue(minS)
                            .setMaxValue(maxS)
                            .setStartVelocity(mVelocityTracker.getXVelocity());
                    mFlingXAnimation.addUpdateListener(updateListener);
                    mFlingXAnimation.addEndListener(endListener);
                    mFlingXAnimation.start();
                } else {
                    setWidthRight();
                }
                mVelocityTracker.clear();
                return true;
        }
        return false;
    }

    DynamicAnimation.OnAnimationUpdateListener updateListener = new DynamicAnimation.OnAnimationUpdateListener() {
        @Override
        public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
            swipeShow(value);
        }
    };

    DynamicAnimation.OnAnimationEndListener endListener = new DynamicAnimation.OnAnimationEndListener() {
        @Override
        public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
            if (velocity == 0 ) {
                if (value > 0) {
                    if (value < halfMax) {
                        closer(0f);
                    } else {
                        closer(maxV);
                    }
                } else {
                    if (value > halfMin) {
                        closer(0f);
                    } else {
                        closer(minV);
                    }
                }
            } else {
                setWidthRight();
                swipeRemove(value);
            }
        }
    };

    private boolean isSwipeInRecycler() {
        if (recycler != null) {
            int ik = recycler.getChildCount();
            for (int i = 0; i < ik; i++) {
                boolean is = ((SwipeLayout) recycler.getChildAt(i)).isSwipe();
                if (is) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSwipe() {
        return mSwipeView != null && mSwipeView.getTranslationX() != 0f;
    }

    public void setOneSwipe(RecyclerView recycler) {
        this.recycler = recycler;
    }

    public void setSwipeRight(TYPE_SWIPE typeSwipe, View child) {
        dragRight = child;
        typeSwipeRight = typeSwipe;
        child.setVisibility(VISIBLE);
    }

    public void setSwipeLeft(TYPE_SWIPE typeSwipe, View child) {
        dragLeft = child;
        typeSwipeLeft = typeSwipe;
        child.setVisibility(VISIBLE);
    }

    public void setSwipeView(View view) {
        mSwipeView = view;
        if (mSwipeView.getTranslationX() != 0) {
            mSwipeView.setTranslationX(0);
        }
        dragRight = null;
        dragLeft = null;
        minV = 0;
        maxV = 0;
        int ik = getChildCount();
        for (int i = 0; i < ik; i++) {
            View v = getChildAt(i);
            if (v != mSwipeView) {
                v.setVisibility(INVISIBLE);
            }
        }
    }

    public void setOnSwipeRemove(boolean rightRemove, boolean leftRemove, RecyclerView.ViewHolder holder, OnSwipeRemove listener) {
        if (rightRemove) {
            typeSwipeRight = TYPE_SWIPE.REMOVE;
        }
        if (leftRemove) {
            typeSwipeLeft = TYPE_SWIPE.REMOVE;
        }
        this.holder = holder;
        this.listener = listener;
    }

    public interface OnSwipeRemove {
        public void onRemove(DIRECT direct, int position);
    }

    private void swipeShow(float tX) {
        if (tX < 0) {
            if (dragRight != null) {
                switch (typeSwipeRight) {
                    case DRAG:
                        dragRight.setTranslationX(tX - minV);
                        break;
                    case HARMONIC:
                        LayoutParams rl = new LayoutParams(-(int) tX, ViewGroup.LayoutParams.MATCH_PARENT);
                        rl.addRule(CENTER_IN_PARENT);
                        rl.addRule(ALIGN_PARENT_RIGHT);
                        dragRight.setLayoutParams(rl);
                        break;
                }
            }
        } else {
            if (dragLeft != null) {
                switch (typeSwipeLeft) {
                    case DRAG:
                        dragLeft.setTranslationX(tX - maxV);
                        break;
                    case HARMONIC:
                        LayoutParams rl = new LayoutParams((int) tX, ViewGroup.LayoutParams.MATCH_PARENT);
                        dragLeft.setLayoutParams(rl);
                        break;
                }
            }
        }
    }

    private void closer(final float finalPosition) {
        animX = new SpringAnimation(mSwipeView,
                new FloatPropertyCompat<View>("translationX") {
                    @Override
                    public float getValue(View view) {
                        return view.getTranslationX();
                    }

                    @Override
                    public void setValue(View view, float value) {
                        view.setTranslationX(value);
                    }
                }, finalPosition);
        animX.getSpring().setStiffness(1000f);
        animX.getSpring().setDampingRatio(0.7f);
        animX.setStartVelocity(0);
        animX.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                swipeShow(value);
            }
        });
        animX.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                setWidthRight();
                swipeRemove(finalPosition);
            }
        });
        animX.start();
    }

    private void swipeRemove(float finalPosition) {
        if (finalPosition < 0 && typeSwipeRight == TYPE_SWIPE.REMOVE && listener != null) {
            listener.onRemove(DIRECT.RIGHT, holder.getAdapterPosition());
        }
        if (finalPosition > 0 && typeSwipeLeft == TYPE_SWIPE.REMOVE && listener != null) {
            listener.onRemove(DIRECT.LEFT, holder.getAdapterPosition());
        }
    }

    private void setWidthRight() {
        if (dragRight != null) {
            LayoutParams rl = new LayoutParams(-(int) minV, dragRight.getHeight());
            rl.addRule(CENTER_IN_PARENT);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            dragRight.setLayoutParams(rl);
        }
    }
}
