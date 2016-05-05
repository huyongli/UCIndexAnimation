package cn.ittiger.ucpage.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author laohu
 * @link http://ittiger.cn
 * 
 * 内容视图(对应UC首页中展示新闻内容的视图)，主要滑动操作在此视图上
 */
public class ContentView extends TouchMoveView {

	public ContentView(Context context) {
		super(context);
	}

	public ContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ContentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public synchronized void onShowAnimation(float step) {

		if(isShowFinish()) {
			return;
		}
		updateMarginTop(-getShowMoveStep(step));
	}

	public synchronized void onHideAnimation(float step) {

		if(isHideFinish()) {
			return;
		}
		updateMarginTop(getHideMoveStep(step));
	}

	/**
	 * 获取当前视图在展示过程中已经滑离初始化位置的距离
	 * @return
	 */
	public int getShowOffset() {

		return mHideStopMarginTop - getMarginTop();
	}

	/**
	 * 获取当前视图在恢复过程中已经滑离展示停止位置的距离
	 * @return
	 */
	public int getHideOffset() {

		return getMarginTop() - mShowStopMarginTop;
	}
}
