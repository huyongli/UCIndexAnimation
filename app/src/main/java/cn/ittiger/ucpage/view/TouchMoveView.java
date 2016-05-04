package cn.ittiger.ucpage.view;

import android.content.Context;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TouchMoveView extends MoveView {

	private TouchMoveListener mTouchMoveListener;
	
	public TouchMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public TouchMoveView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TouchMoveView(Context context) {
		super(context);
	}

	@Override
	public boolean isTouchEventEnable() {
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if(isTouchEventEnable()) {
			if(mTouchMoveListener != null) {
				mTouchMoveListener.onTouchMoveEvent(event);
			}
		}
		return isTouchEventEnable() || super.onTouchEvent(event);
	}


	public interface TouchMoveListener {

		void onTouchMoveEvent(MotionEvent event);
	}

	public void setTouchMoveListener(TouchMoveListener touchMoveListener) {

		this.mTouchMoveListener = touchMoveListener;
	}
}
