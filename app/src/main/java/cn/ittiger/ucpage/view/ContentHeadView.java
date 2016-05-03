package cn.ittiger.ucpage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 内容视图的头部(对应UC首页新闻视图完全展示后的新闻类型导航头部部分，初始时隐藏)
 * 
 * @author laohu
 * @link http://ittiger.cn
 */
public class ContentHeadView extends MoveView {

	public ContentHeadView(Context context) {
		super(context);
	}

	public ContentHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ContentHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void draw(Canvas canvas) {

		super.draw(canvas);
		if(mIsInit) {
			this.mHideStopMarginTop = getMarginTop();
			this.mNeedMoveHeight = getHeight();
			this.mIsInit = false;
			Log.d("MoveView", "ContentHeadView init marginTop:" + mHideStopMarginTop);
		}
	}

	public void setShowStopMarginTop(int stopMarginTop) {

		this.mShowStopMarginTop = stopMarginTop;
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
