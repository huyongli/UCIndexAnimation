package cn.ittiger.ucpage.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author laohu
 * @link http://ittiger.cn
 * 
 * 页面头部View(对应UC首页新闻视图向上滑动完成后页面的头部View，UC中最开始隐藏)
 */
public class PageHeadView extends MoveView {
	private boolean mStartMove = false;

	public PageHeadView(Context context) {

		super(context);
	}

	public PageHeadView(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public PageHeadView(Context context, AttributeSet attrs, int defStyleAttr) {

		super(context, attrs, defStyleAttr);
	}

	public void startMoved() {

		mStartMove = true;
	}

	public void onShowAnimation(float step) {

		updateMarginTop(step);
		if(getMarginTop() > 0) {
			mStartMove = false;
		}
	}
	
	public void onHideAnimation(float step) {

		updateMarginTop(-step);
		if(getMarginTop() <= 0) {
			mStartMove = false;
		}
	}

	public boolean isShowFinish() {

		return !mStartMove && getMarginTop() <= 0 ? false : true;
	}
	
	public boolean isHideFinish() {

		return !mStartMove && getMarginTop() > 0 ? false : true;
	}


}
