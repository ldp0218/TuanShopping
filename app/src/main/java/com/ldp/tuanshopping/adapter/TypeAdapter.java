package com.ldp.tuanshopping.adapter;

import java.util.ArrayList;

import com.ldp.tuanshopping.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class TypeAdapter extends BaseAdapter {
	private ArrayList<Integer> types;
	private LayoutInflater inflater;
	
	public TypeAdapter(ArrayList<Integer> types,Context context){
		this.types=types;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return types.size();
	}

	@Override
	public Object getItem(int position) {
		return types.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Integer resId=(Integer) getItem(position);
		View view=inflater.inflate(R.layout.type_gridview_item, null);
		ImageView iv_type=(ImageView) view.findViewById(R.id.iv_type_icon);
		iv_type.setImageResource(resId);
		return view;
	}

}
