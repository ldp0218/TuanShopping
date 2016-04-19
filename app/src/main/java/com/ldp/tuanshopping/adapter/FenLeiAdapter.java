package com.ldp.tuanshopping.adapter;

import com.ldp.tuanshopping.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class FenLeiAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private String [] content ={"全部","火锅","西餐","海鲜","粤菜","云贵菜","素食","东北菜","新疆菜","地方菜","蛋糕","烧烤","日韩料理","烤鱼","麻辣香锅","东南亚菜","咖啡","下午茶","快餐","其他美食"};
	public FenLeiAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return content.length;
	}

	@Override
	public Object getItem(int position) {
		return content[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View layout = inflater.inflate(R.layout.classify_item, parent, false);
		Button btn_fenlei = (Button) layout.findViewById(R.id.btn_fenlei);
		btn_fenlei.setText(content[position]);
		return layout;
	}

}
