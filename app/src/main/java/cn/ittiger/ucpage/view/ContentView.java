package cn.ittiger.ucpage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

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

	@Override
	public void draw(Canvas canvas) {

		super.draw(canvas);
		if(mIsInit) {
			this.mHideStopMarginTop = getMarginTop();
			this.mIsInit = false;
			Log.d("MoveView", "ContentView init marginTop:" + mHideStopMarginTop);
		}
	}

	public void setShowStopMarginTop(int stopMarginTop) {

		this.mShowStopMarginTop = stopMarginTop;
	}

	@Override
	public int getNeedMoveHeight() {

		if(mNeedMoveHeight == -1) {
			this.mNeedMoveHeight = Math.abs(mHideStopMarginTop - mShowStopMarginTop);
		}
		return super.getNeedMoveHeight();
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
}
