package com.example.mytest.wheel;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import com.example.mytest.wheel.WheelView.WheelItem;
import com.example.mytest_wheel.R;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {
	private WheelView view;
	private TextView btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view = (WheelView)findViewById(R.id.marquee);
		ArrayList<WheelItem> items = new ArrayList<WheelItem>();
		for(int i = 0; i<5; i++){
			WheelView.WheelItem item = new WheelView.WheelItem("list "+i);
			items.add(item);
		}
		view.setData(items);
		
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		view.startScroll();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
