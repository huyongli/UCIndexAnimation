package cn.ittiger.ucpage.view;

import cn.ittiger.ucpage.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by ylhu on 16-5-4.
 */
public class UCIndexView extends FrameLayout implements TouchMoveView.TouchMoveListener {

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 页面头部视图(对应UC首页新闻视图向上滑动完成后页面的头部View，UC中最开始隐藏)
     */
    private PageHeadView mPageHeadView;
    /**
     * 页面内容导航视图(对应UC首页的网址导航视图)
     */
    private PageNavigationView mPageNavigationView;
    /**
     * 内容视图的头部视图(对应UC首页新闻视图完全展示后的新闻类型导航头部部分，初始时隐藏)
     */
    private ContentHeadView mContentHeadView;
    /**
     * 内容视图(对应UC首页中的新闻部分)
     */
    private ContentView mContentView;


    /**
     * 页面头部视图是否固定(默认不固定)，此属性为true的话PageHeadView则会固定在界面顶部不会滑动,此属性为false的话PageHeadView会首先隐藏再慢慢滑出
     */
    private boolean mIsPageHeadViewFixed = false;
    /**
     * 内容头部视图是否启用，默认启用
     */
    private boolean mContentHeadViewEnable = true;
    /**
     * 页面头部的高度
     */
    private int mPageHeadViewHeight = 0;
    /**
     * 页面内容视图的头部视图的高度
     */
    private int mContentHeadViewHeight = 0;
    /**
     * 手指放开后，视图自动滑动时的每次滑动距离间隔，默认每次滑动20px
     */
    private int mAutoSlipStep = 20;
    /**
     * 手指放开后，视图自动滑动时的时间间隔，默认每间隔5毫秒滑动一次
     */
    private int mAutoSlipTimeStep = 5;

    public UCIndexView(Context context) {

        super(context);
        this.init(context, null);
    }

    public UCIndexView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.init(context, attrs);
    }

    public UCIndexView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {

        this.mContext = context;
        TypedArray typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.UCIndexView);

        //页面头部的高度(对应UC首页新闻视图向上滑动完成后页面的头部View，UC中最开始隐藏)
        float pageHeadViewHeight = typeArray.getDimension(R.styleable.UCIndexView_pageHeadViewHeight, 0);
        mPageHeadViewHeight = (int)(pageHeadViewHeight + 0.5f);

        //页面头部是否固定，默认不固定(即默认隐藏，随着滑动而展示出来)，如果固定的话则一直在头部展示不会随着滑动和动
        mIsPageHeadViewFixed = typeArray.getBoolean(R.styleable.UCIndexView_isPageHeadViewFixed, false);

        //页面头部的内容布局资源ID
        int pageHeadViewLayoutId = typeArray.getResourceId(R.styleable.UCIndexView_pageHeadViewLayoutId, 0);

        //页面内容导航的高度(对应UC首页的网址导航视图)
        float pageNavigationViewHeight = typeArray.getDimension(R.styleable.UCIndexView_pageNavigationViewHeight, 0);

        //页面内容导航的布局资源ID
        int pageNavigationViewLayoutId = typeArray.getResourceId(R.styleable.UCIndexView_pageNavigationViewLayoutId, 0);

        //内容视图的头部(对应UC首页新闻视图完全展示后的新闻类型导航头部部分，初始时隐藏)
        float contentHeadViewHeight = typeArray.getDimension(R.styleable.UCIndexView_contentHeadViewHeight, 0);
        mContentHeadViewHeight = (int)(contentHeadViewHeight + 0.5f);

        //内容视图的头部是否启用，默认启用
        mContentHeadViewEnable = typeArray.getBoolean(R.styleable.UCIndexView_contentHeadViewEnable, true);

        //内容视图头部的布局资源ID
        int contentHeadViewLayoutId = typeArray.getResourceId(R.styleable.UCIndexView_contentHeadViewLayoutId, 0);

        //内容视图的布局资源ID
        int contentViewLayoutId = typeArray.getResourceId(R.styleable.UCIndexView_contentViewLayoutId, 0);

        mAutoSlipStep = (int)(typeArray.getDimension(R.styleable.UCIndexView_autoSlipDistanceStep, mAutoSlipStep) + 0.5f);
        mAutoSlipTimeStep = typeArray.getInt(R.styleable.UCIndexView_autoSlipTimeStep, mAutoSlipTimeStep);

        typeArray.recycle();

        //下面四个视图的添加顺序不能更改，否则无法达到效果，此处视图的遮盖采用的是FrameLayout布局的特性
        addPageNavigationView(pageNavigationViewLayoutId, pageNavigationViewHeight);
        addPageHeadView(pageHeadViewLayoutId, pageHeadViewHeight);
        addContentHeadView(contentHeadViewLayoutId, contentHeadViewHeight, pageNavigationViewHeight);
        addContentView(contentViewLayoutId, pageNavigationViewHeight);
    }

    /**
     * 添加页面头部视图
     * @param resId                 页面头部视图中的元素布局资源文件ID
     * @param pageHeadViewHeight    页面头部视图的高度
     */
    private void addPageHeadView(int resId, float pageHeadViewHeight) {

        if(resId == 0) {
            throw new IllegalArgumentException("please set attribution pageHeadViewLayoutId int UCIndexView");
        }

        mPageHeadView = new PageHeadView(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int)(pageHeadViewHeight + 0.5f));
        if(!mIsPageHeadViewFixed) {//不固定头部，头部会进行隐藏
            layoutParams.topMargin = -(int)(pageHeadViewHeight + 0.5f);
        }
        mPageHeadView.setLayoutParams(layoutParams);

        mPageHeadView.setShowStopMarginTop(0);//向上滑时PageHeadView的滑动停止的位置，该位置应该是PageHeadView刚好展示在页面顶部的位置，此时marginTop为0
        mPageHeadView.setHideStopMarginTop(layoutParams.topMargin);//向下滑时PageHeadView的滑动停止的位置，此时该位置为PageHeadView的初始化位置
        mPageHeadView.setNeedMoveHeight(mPageHeadViewHeight);//设置ContentHeadView相对ContentView需要滑动的高度，即为其本身的高度

        inflate(mContext, resId, mPageHeadView);
        addView(mPageHeadView);
    }

    /**
     * 添加页面内容导航视图
     * @param resId                         页面内容导航视图的元素布局资源文件ID
     * @param pageNavigationViewHeight      页面内容导航视图的高度
     */
    private void addPageNavigationView(int resId, float pageNavigationViewHeight) {

        if(resId == 0) {
            throw new IllegalArgumentException("please set attribution pageNavigationViewLayoutId int UCIndexView");
        }
        mPageNavigationView = new PageNavigationView(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int)(pageNavigationViewHeight + 0.5f));
        mPageNavigationView.setLayoutParams(layoutParams);

        //向上滑动时PageNavigationView的停止位置设置为PageHeadView高度的一半
        mPageNavigationView.setShowStopMarginTop(-mPageHeadViewHeight / 2);
        //PageNavigationView的滑动距离为PageHeadView高度的一半
        mPageNavigationView.setNeedMoveHeight(mPageHeadViewHeight / 2);
        mPageNavigationView.setHideStopMarginTop(layoutParams.topMargin);

        inflate(mContext, resId, mPageNavigationView);
        addView(mPageNavigationView);
    }

    /**
     * 添加内容视图的头部视图
     * @param resId                     页面内容视图中的头部视图的元素布局资源文件ID
     * @param contentHeadViewHeight     页面内容视图中的头部视图的高度
     * @param marginTop                 页面内容视图中的头部视图的marginTop
     */
    private void addContentHeadView(int resId, float contentHeadViewHeight, float marginTop) {

        if(!mContentHeadViewEnable) {//内容头部视图不启用
            return;
        }
        if(resId == 0) {
            throw new IllegalArgumentException("please set attribution contentHeadViewLayoutId int UCIndexView");
        }
        mContentHeadView = new ContentHeadView(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int)(contentHeadViewHeight + 0.5f));
        layoutParams.topMargin = (int)(marginTop + 0.5f);
        mContentHeadView.setLayoutParams(layoutParams);

        mContentHeadView.setShowStopMarginTop(mPageHeadViewHeight);//向上滑时ContentHeadView的滑动停止的位置，该位置应该是刚好距离一个PageHeadView的高度
        mContentHeadView.setHideStopMarginTop(layoutParams.topMargin);//向下滑时ContentHeadView的滑动停止的位置，此时该位置为ContentHeadView的初始化位置
        mContentHeadView.setNeedMoveHeight((int)(contentHeadViewHeight + 0.5f));//设置ContentHeadView相对ContentView需要滑动的高度，即为其本身的高度

        inflate(mContext, resId, mContentHeadView);
        addView(mContentHeadView);
    }

    /**
     * 添加内容视图
     * @param resId         页面内容视图的元素布局资源文件ID
     * @param marginTop     页面内容视图的marginTop
     */
    private void addContentView(int resId, float marginTop) {

        if(resId == 0) {
            throw new IllegalArgumentException("please set attribution contentViewLayoutId int UCIndexView");
        }

        mContentView = new ContentView(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.topMargin = (int)(marginTop + 0.5f);
        mContentView.setLayoutParams(layoutParams);
        mContentView.setTouchMoveListener(this);

        //向上滑时ContentView的滑动停止的位置，该位置应该是PageHeadView和ContentHeadView刚好展示在页面顶部的位置，此时marginTop为PageHeadView和ContentHeadView两者的高度之和
        mContentView.setShowStopMarginTop(mContentHeadViewHeight + mPageHeadViewHeight);
        mContentView.setHideStopMarginTop(layoutParams.topMargin);//向下滑时ContentView的滑动停止的位置，此时该位置为ContentView的初始化位置
        //ContentView需要移动的距离为其本身到顶部的MarginTop值减去ContentHeadView和PageHeadView两者的高度
        mContentView.setNeedMoveHeight(Math.abs(layoutParams.topMargin - mContentHeadViewHeight - mPageHeadViewHeight));

        inflate(mContext, resId, mContentView);
        addView(mContentView);
    }

    /**
     * 上一次的触摸点Y坐标
     */
    private float mLastTouchY = 0;
    /**
     * 两次触摸点之间的距离
     */
    private float mDelY = 0;

    @Override
    public void onTouchMoveEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mDelY = event.getRawY() - mLastTouchY;
                viewMove(mDelY);
                mLastTouchY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                int showHeight = mPageHeadView.getShowHeight();
                if(mDelY > 0) {//hide
                    if(showHeight > mPageHeadView.getNeedMoveHeight() / 2) {//没有滑过二分之一高度
                        slip(-mDelY);
                    } else {
                        slip(mDelY);
                    }
                } else {//show
                    if(showHeight >= mPageHeadView.getNeedMoveHeight() / 2) {//没有滑过二分之一高度
                        slip(mDelY);
                    } else {
                        slip(-mDelY);
                    }
                }
                break;
        }
    }

    /**
     * 手指松开屏幕后，视图自动滑动
     * 每隔 mAutoSlipTimeStep 长时间滑动 mAutoSlipStep 距离
     * @param delY  当前的滑动距离
     */
    private void slip(float delY) {

        if(delY > 0) {//当前滑动为向下滑动，即处于恢复状态
            if(isHideFinish()) {//已经恢复结束
                return;
            }
            mPageHeadView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewMove(mAutoSlipStep);
                    slip(mAutoSlipStep);//准备下一次滑动
                }
            }, mAutoSlipTimeStep);
        } else {//当前滑动为向上滑动，即处于展示状态
            if(isShowFinish()) {//已经展示结束
                return;
            }
            mPageHeadView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewMove(-mAutoSlipStep);
                    slip(-mAutoSlipStep);//准备下一次滑动
                }
            }, mAutoSlipTimeStep);
        }
    }

    /**
     * 对所有视图进行滑动操作
     * @param delY 当前的滑动步长
     */
    private void viewMove(float delY) {

        float step = Math.abs(delY);
        float pageHeadViewStep = step * mPageHeadView.getNeedMoveHeight() / mContentView.getNeedMoveHeight();
        float contentViewStep = step;
        float contentHeadViewStep = step + step * mContentHeadView.getNeedMoveHeight() / mContentView.getNeedMoveHeight();
        float pageNavigationViewStep = step * mPageNavigationView.getNeedMoveHeight() / mContentView.getNeedMoveHeight();

        if(delY > 0) {//下滑
            if(!isHideFinish()) {
                if(mIsPageHeadViewFixed == false) {
                    mPageHeadView.onHideAnimation(pageHeadViewStep);
                }
                mContentView.onHideAnimation(contentViewStep);
                if(mContentHeadViewEnable) {
                    mContentHeadView.onHideAnimation(contentHeadViewStep);
                }
                mPageNavigationView.onHideAnimation(pageNavigationViewStep);
            }
        } else {//上滑
            if(!isShowFinish()) {
                if(mIsPageHeadViewFixed == false) {//PageHeadView没有被固定时才进行滑动
                    mPageHeadView.onShowAnimation(pageHeadViewStep);
                }
                mContentView.onShowAnimation(contentViewStep);
                if(mContentHeadViewEnable) {//ContentHeadView启用时才进行滑动
                    mContentHeadView.onShowAnimation(contentHeadViewStep);
                }
                mPageNavigationView.onShowAnimation(pageNavigationViewStep);
            }
        }
    }

    /**
     * 判断所有视图是否全部恢复结束
     * @return
     */
    private boolean isHideFinish() {

        //当PageHeadView固定的时候，默认为恢复结束
        boolean pageHeadViewHideFinish = mIsPageHeadViewFixed ? true : mPageHeadView.isHideFinish();
        //当ContentHeadView不启用的时候，默认为恢复结束
        boolean contentHeadViewHideFinish = mContentHeadViewEnable ? mContentHeadView.isHideFinish() : true;

        return pageHeadViewHideFinish && mContentHeadView.isHideFinish() &&
                contentHeadViewHideFinish && mPageNavigationView.isHideFinish();
    }

    /**
     * 判断所有视图是否全部展示结束
     * @return
     */
    private boolean isShowFinish() {

        //当PageHeadView固定的时候，默认为展示结束
        boolean pageHeadViewShowFinish = mIsPageHeadViewFixed ? true : mPageHeadView.isHideFinish();
        //当ContentHeadView不启用的时候，默认为展示结束
        boolean contentHeadViewShowFinish = mContentHeadViewEnable ? mContentHeadView.isHideFinish() : true;

        return pageHeadViewShowFinish && mContentHeadView.isShowFinish() &&
                contentHeadViewShowFinish && mPageNavigationView.isShowFinish();
    }
}
