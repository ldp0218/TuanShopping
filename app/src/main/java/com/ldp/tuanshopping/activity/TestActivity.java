package com.ldp.tuanshopping.activity;

import com.ldp.tuanshopping.R;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity {
	//美团开放API接口
	private final String URI="http://www.meituan.com/api/v2/beijing/deals";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
	}
}
