package cn.ittiger.ucpage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author laohu
 * @link http://ittiger.cn
 *
 */
public class MoveView extends FrameLayout {

	/**
	 * 需要滑动的高度
	 */
	protected int mNeedMoveHeight = -1;
	/**
	 * Show展示时的停止值，此处我设置向上滑动为Show的状态，也可称为展示状态
	 */
	protected int mShowStopMarginTop;
	/**
	 * Hide时的停止值，此处我设置向下滑动为Hide状态，也可称为恢复初始化状态
	 */
	protected int mHideStopMarginTop;

	public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {

		super(context, attrs, defStyleAttr);
	}

	public MoveView(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public MoveView(Context context) {

		super(context);
	}

	/**
	 * 当前View是否可以出发TouchEvent事件
	 * @return 默认不允许
	 */
	public boolean isTouchEventEnable() {

		return false;
	}

	/**
	 * 获取此视图需要滑动的高度
	 * @return
	 */
	protected int getNeedMoveHeight() {

		return mNeedMoveHeight;
	}

	/**
	 * 设置此视图需要滑动的高度
	 * @param needMoveHeight
	 */
	protected void setNeedMoveHeight(int needMoveHeight) {

		mNeedMoveHeight = needMoveHeight;
	}

	/**
	 * 设置此视图向上滑动时，滑动停止时的marginTop值
	 * @param showStopMarginTop
	 */
	protected void setShowStopMarginTop(int showStopMarginTop) {

		mShowStopMarginTop = showStopMarginTop;
	}

	/**
	 * 设置此视图向下滑动时，滑动停止时的marginTop值
	 * @param hideStopMarginTop
	 */
	protected void setHideStopMarginTop(int hideStopMarginTop) {

		mHideStopMarginTop = hideStopMarginTop;
	}

	/**
	 * 或取此视图当前的marginTop值
	 * @return
	 */
	protected int getMarginTop() {

		return ((MarginLayoutParams)getLayoutParams()).topMargin;
	}

	/**
	 * 根据指定的step更新此视图的MarginTop值
	 * @param step  此处的step值分正负，如果向marginTop减小，则step为负数
	 */
	protected void updateMarginTop(float step) {

		//将step转换为整数
		int intStep = step > 0 ? ((int)(step + 0.5f)) : -((int)(Math.abs(step) + 0.5f));

		MarginLayoutParams layoutParams = (MarginLayoutParams)getLayoutParams();
		layoutParams.topMargin = layoutParams.topMargin + intStep;
		setLayoutParams(layoutParams);
		invalidate();
	}

	/**
	 * 是否恢复完成
	 * @return
	 */
	protected synchronized boolean isHideFinish() {

		if(mHideStopMarginTop < 0) {
			return getMarginTop() <= mHideStopMarginTop ? true : false;
		} else {
			return getMarginTop() >= mHideStopMarginTop ? true : false;
		}
	}

	/**
	 * 是否展示完成
	 * @return
	 */
	protected synchronized boolean isShowFinish() {

		if(mShowStopMarginTop > 0) {
			return getMarginTop() <= mShowStopMarginTop ? true : false;
		} else {
			return getMarginTop() >= mShowStopMarginTop ? true : false;
		}
	}


	/**
	 * 获取当前实际可滑动的距离
	 * @param step  可滑动距离，此距离值会与最大可滑动距离进行比较，取最小值
	 * @return
	 */
	protected float getShowMoveStep(float step) {

		int maxStep = Math.abs(Math.abs(getMarginTop()) - Math.abs(mShowStopMarginTop));//此次滑动所允许滑动的最大step
		if(step > maxStep) {//可用滑动的marginTop小于step值,则用最大可滑动值替代
			step = maxStep;
		}
		return step;
	}

	/**
	 * 获取当前实际可滑动的距离
	 * @param step  可滑动距离，此距离值会与最大可滑动距离进行比较，取最小值
	 * @return
	 */
	protected float getHideMoveStep(float step) {

		int maxStep = Math.abs(Math.abs(mHideStopMarginTop) - Math.abs(getMarginTop()));//此次滑动所允许滑动的最大step
		if(step > maxStep) {//可用滑动的marginTop小于step值,则用最大可滑动值替代
			step = maxStep;
		}
		return step;
	}
}
