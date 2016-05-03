package cn.ittiger.ucpage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MoveView extends LinearLayout {

	/**
	 * 需要滑动的高度
	 */
	protected int mNeedMoveHeight = -1;
	/**
	 * Show展示时的停止值
	 */
	protected int mShowStopMarginTop;
	/**
	 * Hide时的停止值
	 */
	protected int mHideStopMarginTop;
	/**
	 * 是否为初始化
	 */
	protected boolean mIsInit = true;
	
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
	 * 手触摸在上面是否可以滑动
	 * @author: huylee
	 * @time:	2016-4-28下午10:06:29
	 * @return
	 */
	public boolean isTouchMoveEnable() {

		return false;
	}

	/**
	 * 获取此视图需要滑动的高度
	 * @return
	 */
	public int getNeedMoveHeight() {

		return mNeedMoveHeight;
	}

	/**
	 * 或取此视图的marginTop值
	 * @return
	 */
	public int getMarginTop() {

		return ((MarginLayoutParams)getLayoutParams()).topMargin;
	}

	/**
	 * 根据指定的step更新此视图的MarginTop值
	 * @param step  此处的step值分正负，如果向marginTop减小，则step为负数
	 */
	public void updateMarginTop(float step) {

		//将step转换为整数
		int intStep = step > 0 ? ((int)(step + 0.5f)) : -((int)(Math.abs(step) + 0.5f));

		MarginLayoutParams layoutParams = (MarginLayoutParams)getLayoutParams();
		layoutParams.topMargin = layoutParams.topMargin + intStep;
		setLayoutParams(layoutParams);
		invalidate();
	}

	public synchronized boolean isHideFinish() {

		if(mHideStopMarginTop < 0) {
			return getMarginTop() <= mHideStopMarginTop ? true : false;
		} else {
			return getMarginTop() >= mHideStopMarginTop ? true : false;
		}
	}

	public synchronized boolean isShowFinish() {

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
	public float getShowMoveStep(float step) {
		int maxStep = Math.abs(getMarginTop()) - Math.abs(mShowStopMarginTop);//此次滑动所允许滑动的最大step
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
	public float getHideMoveStep(float step) {
		int maxStep = Math.abs(mHideStopMarginTop) - Math.abs(getMarginTop());//此次滑动所允许滑动的最大step
		if(step > maxStep) {//可用滑动的marginTop小于step值,则用最大可滑动值替代
			step = maxStep;
		}
		return step;
	}
}
