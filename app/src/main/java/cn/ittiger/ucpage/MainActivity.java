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
import android.util.Log;
import android.view.MotionEvent;
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
		mContentView.setShowStopMarginTop(mContentHeadView.getHeight() + mPageHeadView.getHeight());
		mContentHeadView.setShowStopMarginTop(mPageHeadView.getHeight());
	}

	private float mLastTouchX = 0;
	private float mLastTouchY = 0;
	private float mDelY = 0;

	@Override
	public void onTouchMoveEvent(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastTouchX = event.getRawX();
				mLastTouchY = event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				mDelY = event.getRawY() - mLastTouchY;
				viewMove(mDelY);
				mLastTouchY = event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				slip(mDelY);
				break;
		}
	}

	private void slip(float delY) {
		final int step = 20;
		if(delY > 0) {
			if(mPageHeadView.isHideFinish() && mContentHeadView.isHideFinish() && mContentHeadView.isHideFinish()) {
				Log.d("MoveView", "PageHeadView init marginTop:" + mPageHeadView.getMarginTop());
				Log.d("MoveView", "ContentHeadView init marginTop:" + mContentHeadView.getMarginTop());
				Log.d("MoveView", "ContentView init marginTop:" + mContentView.getMarginTop());
				return;
			}
			mPageHeadView.postDelayed(new Runnable() {
				@Override
				public void run() {
					viewMove(step);
					slip(step);
				}
			}, 5);
		} else {
			if(mPageHeadView.isShowFinish() && mContentHeadView.isShowFinish() && mContentHeadView.isShowFinish()) {
				Log.d("MoveView", "***PageHeadView finish marginTop:" + mPageHeadView.getMarginTop());
				Log.d("MoveView", "***ContentHeadView finish marginTop:" + mContentHeadView.getMarginTop());
				Log.d("MoveView", "***ContentView finish marginTop:" + mContentView.getMarginTop());
				return;
			}
			mPageHeadView.postDelayed(new Runnable() {
				@Override
				public void run() {
					viewMove(-step);
					slip(-step);
				}
			}, 5);
		}
	}

	private void viewMove(float delY) {
		float step = Math.abs(delY);
		float pageHeadViewStep = step * mPageHeadView.getNeedMoveHeight() / mContentView.getNeedMoveHeight();
		float contentViewStep = step;
		float contentHeadViewStep = step + step * mContentHeadView.getNeedMoveHeight() / mContentView.getNeedMoveHeight();

		if(delY > 0) {//往下滑
			if(!(mPageHeadView.isHideFinish() && mContentHeadView.isHideFinish() && mContentHeadView.isHideFinish())) {
				mPageHeadView.onHideAnimation(pageHeadViewStep);
				mContentView.onHideAnimation(contentViewStep);
				mContentHeadView.onHideAnimation(contentHeadViewStep);
			}
		} else {//往上滑
			if(!(mPageHeadView.isShowFinish() && mContentHeadView.isShowFinish() && mContentHeadView.isShowFinish())) {
				mPageHeadView.onShowAnimation(pageHeadViewStep);
				mContentView.onShowAnimation(contentViewStep);
				mContentHeadView.onShowAnimation(contentHeadViewStep);
			}
		}
	}
}
