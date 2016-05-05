package cn.ittiger.ucpage;

import cn.ittiger.ucpage.view.UCIndexView;

import android.app.Activity;
import android.os.Bundle;

public class DemoActivity extends Activity {
	UCIndexView mUCIndexView;
	boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uc_index_view);
		mUCIndexView = (UCIndexView) findViewById(R.id.ucindexview);
	}

	@Override
	public void onBackPressed() {

		if(flag == false && !mUCIndexView.isPullRestoreEnable()) {
			mUCIndexView.onBackRestore();
			flag = true;
		} else {
			super.onBackPressed();
		}
	}
}
