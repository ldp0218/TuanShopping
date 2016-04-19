package com.ldp.tuanshopping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.ldp.tuanshopping.R;

public class MapActivity extends Activity {
	final static String TAG = "MapActivity";
	/**
	 *  MapView 是地图主控件
	 */
	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;
	private LocationClient mLocClient = null;
	private boolean isFirstLoc = true; // 是否首次定位

	GeoCoder mSearch = null; // 搜索模块

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		/**
		 * 使用地图sdk前需先初始化BMapManager.
		 * BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		//----------------注册定位监听
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				// map view 销毁后不在处理新接收的位置
				if (location == null || mMapView == null) {
					return;
				}
				MyLocationData locData = new MyLocationData.Builder()
						.accuracy(location.getRadius())
						// 此处设置开发者获取到的方向信息，顺时针0-360
						.direction(100).latitude(location.getLatitude())
						.longitude(location.getLongitude()).build();
				mBaiduMap.setMyLocationData(locData);
				if (isFirstLoc) {
					isFirstLoc = false;
					LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
					MapStatus.Builder builder = new MapStatus.Builder();
					builder.target(ll).zoom(18.0f);
					mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
					// 初始化搜索模块，注册事件监听
					mSearch = GeoCoder.newInstance();
					mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
						@Override
						public void onGetGeoCodeResult(GeoCodeResult result) {
							if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
								Toast.makeText(MapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
								return;
							}
							mBaiduMap.clear();
							mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
									.icon(BitmapDescriptorFactory
											.fromResource(R.drawable.icon_marka)));
							mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
									.getLocation()));
							String strInfo = String.format("纬度：%f 经度：%f",
									result.getLocation().latitude, result.getLocation().longitude);
							Toast.makeText(MapActivity.this, strInfo, Toast.LENGTH_LONG).show();
						}

						@Override
						public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
							if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
								Toast.makeText(MapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
								return;
							}
							mBaiduMap.clear();
							mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
									.icon(BitmapDescriptorFactory
											.fromResource(R.drawable.icon_marka)));
							mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
									.getLocation()));
							Toast.makeText(MapActivity.this, result.getAddress(),
									Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		});
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	@Override
	protected void onPause() {
		/**
		 *  MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		 */
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		/**
		 *  MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		 */
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mMapView.onSaveInstanceState(outState);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    }
    
}
