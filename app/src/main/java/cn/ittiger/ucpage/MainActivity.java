package cn.ittiger.ucpage;

import cn.ittiger.ucpage.inject.InjectHelper;
import cn.ittiger.ucpage.inject.InjectView;
import cn.ittiger.ucpage.view.ContentHeadView;
import cn.ittiger.ucpage.view.ContentView;
import cn.ittiger.ucpage.view.PageHeadView;
import cn.ittiger.ucpage.view.PageNavigationView;
import cn.ittiger.ucpage.view.TouchMoveView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

/**
 * @author: laohu
 * @link:	http://ittiger.cn
 */
public class MainActivity extends Activity implements TouchMoveView.TouchMoveListener {
	@InjectView(id=R.id.pageHeadView)
	private PageHeadView mPageHeadView;
	@InjectView(id=R.id.pageNavigationView)
	private PageNavigationView mPageNavigationView;
	@InjectView(id=R.id.contentHeadView)
	private ContentHeadView mContentHeadView;
	@InjectView(id=R.id.contentView)
	private ContentView mContentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		InjectHelper.inject(this);
		mContentView.setTouchMoveListener(this);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		super.onWindowFocusChanged(hasFocus);
		//ContentView需要移动的距离为其本身到顶部的MarginTop值减去ContentHeadView和PageHeadView两者的高度
		int contentViewMoveHeight = Math.abs(mContentView.getMarginTop()) - mContentHeadView.getHeight() - mPageHeadView.getHeight();
		mContentView.setNeedMoveHeight(contentViewMoveHeight);
		mContentHeadView.setNeedMoveHeight(mContentHeadView.getHeight());
		mPageHeadView.setNeedMoveHeight(mPageHeadView.getHeight());
	}

	private float mLastTouchX = 0;
	private float mLastTouchY = 0;
	private float mDelY = 0;

	@Override
	public void onTouchMoveEvent(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastTouchX = event.getRawX();
				mLastTouchY = event.getRawX();
				mPageHeadView.startMoved();
				break;
			case MotionEvent.ACTION_MOVE:
				mDelY = event.getRawY() - mLastTouchY;
				if(!(mPageHeadView.isHideFinish() || mPageHeadView.isShowFinish())) {
					float pageHeadViewStep = mDelY * mPageHeadView.getNeedMoveHeight() / mContentView.getNeedMoveHeight();
					float contentViewStep = mDelY;
					float contentHeadViewStep = mDelY + mDelY * mContentHeadView.getNeedMoveHeight() / mContentView.getNeedMoveHeight();
					if(mDelY > 0) {//往下滑
						mPageHeadView.onHideAnimation(pageHeadViewStep);
						mContentView.onHideAnimation(contentViewStep);
						mContentHeadView.onHideAnimation(contentHeadViewStep);
					} else {//往上滑
						mPageHeadView.onShowAnimation(pageHeadViewStep);
						mContentView.onShowAnimation(contentViewStep);
						mContentHeadView.onShowAnimation(contentHeadViewStep);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
	}

	public void onMoveUpClick(View view) {
		
	}

	public void onMoveDownClick(View view) {
		int count = 100;//200次，200*10共2秒
		float step = mPageHeadView.getHeight() / count * 1.0f;
		for(int i = 0; i < count && !mPageHeadView.isHideFinish(); i++) {
			if(mPageHeadView.isHideFinish()) {
				break;
			}
			mPageHeadView.onHideAnimation(step);
		}
	}
}
