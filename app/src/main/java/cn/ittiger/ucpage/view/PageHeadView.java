package cn.ittiger.ucpage.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 页面头部View(对应UC首页新闻视图向上滑动完成后页面的头部View，UC中最开始隐藏)
 * @author laohu
 * @link http://ittiger.cn
 *
 */
public class PageHeadView extends MoveView {

	public PageHeadView(Context context) {

		super(context);
	}

	public PageHeadView(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public PageHeadView(Context context, AttributeSet attrs, int defStyleAttr) {

		super(context, attrs, defStyleAttr);
	}

	public synchronized void onShowAnimation(float step) {

		if(isShowFinish()) {
			return;
		}
		updateMarginTop(getShowMoveStep(step));
	}

	public synchronized void onHideAnimation(float step) {

		if(isHideFinish()) {
			return;
		}
		updateMarginTop(-getHideMoveStep(step));
	}

	public int getShowHeight() {

		int marginTop = Math.abs(getMarginTop());

		return mNeedMoveHeight - marginTop;
	}
}
