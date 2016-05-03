package cn.ittiger.ucpage;

import cn.ittiger.ucpage.inject.InjectHelper;
import cn.ittiger.ucpage.inject.InjectView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class DemoActivity extends Activity {
	@InjectView(id=R.id.framelayout)
	private FrameLayout mFrameLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		InjectHelper.inject(this);
	}
	
	public void onClick(View v) {
		mFrameLayout.scrollTo(50, 0);
	}
}
