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

	public void onShowAnimation(float step) {

		updateMarginTop(-step);
	}

	public void onHideAnimation(float step) {

		updateMarginTop(step);
	}
}
