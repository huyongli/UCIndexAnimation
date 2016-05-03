package cn.ittiger.ucpage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MoveView extends LinearLayout {
	/**
	 * 需要滑动的高度
	 */
	protected int mNeedMoveHeight;
	
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
	 * 设置此视图需要滑动的高度
	 * @param needMoveHeight
	 */
	public void setNeedMoveHeight(int needMoveHeight) {

		mNeedMoveHeight = needMoveHeight;
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
}
