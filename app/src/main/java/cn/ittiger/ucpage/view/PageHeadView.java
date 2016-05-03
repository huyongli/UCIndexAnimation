package cn.ittiger.ucpage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author laohu
 * @link http://ittiger.cn
 * 
 * 页面头部View(对应UC首页新闻视图向上滑动完成后页面的头部View，UC中最开始隐藏)
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

	@Override
	public void draw(Canvas canvas) {

		super.draw(canvas);

		if(mIsInit) {
			this.mShowStopMarginTop = 0;
			this.mHideStopMarginTop = getMarginTop();
			this.mNeedMoveHeight = getHeight();
			this.mIsInit = false;
			Log.d("MoveView", "PageHeadView init marginTop:" + mHideStopMarginTop);
		}
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
}
