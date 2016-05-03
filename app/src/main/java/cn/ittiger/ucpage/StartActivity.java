package cn.ittiger.ucpage;

import cn.ittiger.ucpage.inject.InjectHelper;
import cn.ittiger.ucpage.inject.InjectView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends Activity {
	@InjectView(id= R.id.lv_model, onItemClick="listViewItemClick")
	private ListView mListView;
	private ArrayAdapter<Model> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		InjectHelper.inject(this);
		init();
	}
	
	private void init() {
		List<Model>  list = new ArrayList<>();
		list.add(new Model("ucDemo", "UCDemo"));
		list.add(new Model("move", "ScroolTo-Test"));
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
		mListView.setAdapter(adapter);
	}
	
	public void listViewItemClick(AdapterView<?> parent, View view, int position, long id) {
		String name = adapter.getItem(position).getName();
		if("ucDemo".equals(name)) {
			startActivity(new Intent(this, MainActivity.class));
		} else if("move".equals(name)) {
			startActivity(new Intent(this, DemoActivity.class));
		}
	}
	
	class Model {
		private String name;
		private String dispName;

		public Model(String name, String dispName) {
			super();
			this.dispName = dispName;
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		public String getDispName() {
			return dispName;
		}
		
		@Override
		public String toString() {
			return dispName;
		}
	}
}
