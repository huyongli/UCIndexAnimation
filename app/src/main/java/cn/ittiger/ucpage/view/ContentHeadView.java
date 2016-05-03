package cn.ittiger.ucpage.view;

import android.content.Context;
import android.util.AttributeSet;

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

	public void onShowAnimation(float step) {

		updateMarginTop(-step);
	}

	public void onHideAnimation(float step) {

		updateMarginTop(step);
	}
}
