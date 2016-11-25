package com.sanmi.fragment;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;

import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.activity.BoyActivity;
import com.sanmi.activity.MainActivity;
import com.sanmi.activity.MianFeiActivity;
import com.sanmi.activity.MsgBchuzu;
import com.sanmi.activity.PayRankActivity;
import com.sanmi.activity.R;
import com.sanmi.adapter.BchuzuAdapter;
import com.sanmi.adapter.FuJinAdapter;
import com.sanmi.adapter.GroupAdapterF;
import com.sanmi.business.FuJinData;
import com.sanmi.fengzhuang.FuJinDatas;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.view.MyApplication;
import com.sanmi.view.MyApplication.myLocation;
import com.zuwo.data.InfoList;
import com.zuwo.userinfo.mUserInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Fragment
 * 
 * @author Administrator 
 */
public class FuJinFragment extends Fragment {
	private com.sanmi.refresh.PullToRefreshListView mPullToRefreshListView;// listview
	private LodingDialog lls;
	private ListView mListView;// 
	private ArrayList<InfoList> mArrayList;// 
	private FuJinAdapter mFuJinAdapter;
	private String s = "0";
	private int page = 0;
	private int pagenum = 30;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (lls != null) {
				lls.dismiss();
			}
			switch (msg.what) {
			case Constants.NEARDATA:
				if (mPullToRefreshListView != null) {
					mPullToRefreshListView.onRefreshComplete(); 
				}
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						mArrayList = JsonUtil.instance().fromJson(jsonData, new TypeToken<ArrayList<InfoList>>(){}.getType());
						if(mArrayList!=null){
							
							mFuJinAdapter.setdata(mArrayList);
							mFuJinAdapter.notifyDataSetChanged();
							mListView();
						}
					}else{
						Toast.makeText(getActivity().getApplicationContext(), jsonObj.getString("message"), 0).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View mView = init(inflater);//
		
		lls = LodingDialog.DialogFactor(getActivity(),"正在加载...",false);
		sendHttp(s,0,(MainActivity) getActivity());
		
		listViewRefresh();//
		return mView;
	}
	
	/**
	 * 
	 */
	private View init(LayoutInflater inflater) {
		View mView = inflater.inflate(R.layout.fujinfragment, null);
		mPullToRefreshListView = (PullToRefreshListView) mView.findViewById(R.id.lv_fujin);
		mListView = mPullToRefreshListView.getRefreshableView();
		mFuJinAdapter = new FuJinAdapter(mArrayList, getActivity());
		mListView.setAdapter(mFuJinAdapter);
		return mView;
	}
	/**
	 * onClick时间监听
	 */

	
	 /****************START PopWindow ******************/
	
		private PopupWindow popupWindow;
		private View view;
		private ListView lv_group;
		private ArrayList<String> groups;
		private GroupAdapterF groupAdapter;
		
		/***************END PopWindow*********************/

	/****************START PopWindow ******************/
	public void showWindow(View parent) {

		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = layoutInflater.inflate(R.layout.group_list, null);
			// ViewUtils.inject(this,view);
			lv_group = (ListView) view.findViewById(R.id.lvGroup);
			// 加载数据
			groups = new ArrayList<String>();
			
			groups.add("查看全部");
			groups.add("仅看男");
			groups.add("仅看女");

			groupAdapter = new GroupAdapterF(getActivity(), groups);
			lv_group.setAdapter(groupAdapter);
			
			// 创建一个PopuWidow对象
			popupWindow = new PopupWindow(view,getActivity().getWindowManager().getDefaultDisplay().getWidth()/4,LayoutParams.WRAP_CONTENT);
		}

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		Log.i("coder", "xPos:" + xPos);

		popupWindow.showAsDropDown(parent, xPos, 0);

		lv_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				FuJinFragment fragment = new FuJinFragment();
				switch (arg2) {
				case 0:
					Log.i("aaa", "1111111");
					sendHttp("0",1,getActivity());
					break;
				case 1:
					Log.i("aaa", "1111111");
					sendHttp("1",1,getActivity());
					break;
				case 2:
					Log.i("aaa", "2222222");
					sendHttp("2",1,getActivity());
					break;
				}
				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
	}
	/**
	 * 
	 */
	public void mListView(){
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent();
				if(!mArrayList.get(arg2-1).getCategory().equals("1")){
					if (mUserInfo.GetUserInfo(getActivity()).getRank_id().equals("1")) {
						intent.setClass(getActivity(), PayRankActivity.class);
						com.bset.tool.Toast.ToastMe(getActivity(), "你还是不是会员");
					}else{
						intent.setClass(getActivity(),MsgBchuzu.class);
						intent.putExtra("id", mArrayList.get(arg2-1).getId());
						intent.putExtra("user_id", mArrayList.get(arg2-1).getUser_id());
						intent.putExtra("category", false);
						
					}
				}else{
					intent.setClass(getActivity(),MsgBchuzu.class);
					intent.putExtra("user_id", mArrayList.get(arg2-1).getUser_id());
					intent.putExtra("id", mArrayList.get(arg2-1).getId());
					intent.putExtra("category", true);
				}
				startActivity(intent);
			}
		});
	}
	
	/**
	 * ˢ
	 */
	private void listViewRefresh() {
		//
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {//
					lls = LodingDialog.DialogFactor(getActivity(), "正在刷新...",false);
					page = 0;
					mArrayList=new ArrayList<InfoList>();
					mFuJinAdapter.notifyDataSetChanged();
					sendHttp(s,0,(MainActivity) getActivity());
				} else {

					if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {// 
						lls = LodingDialog.DialogFactor(getActivity(),"正在加载...",false);
						
						if (mArrayList.size()/30 == page+1) {
							page = mArrayList.size()/30;
							sendHttp(s,0,(MainActivity) getActivity());
						}else {
						lls.dismiss();
						com.bset.tool.Toast.ToastMe(getActivity(), "没有更多");
						mPullToRefreshListView.onRefreshComplete();
						}
					}
				}
			}
		});
	}
	/**
	 * 筛选男女
	 * @param sex
	 * @param isNew
	 * @param a
	 */
	public void sendHttp(String sex,int isNew,final Activity a) {
		final String s = sex;
		this.s = s;
		final int i = isNew;
		((MyApplication)a.getApplication()).getmyLocation(new myLocation() {
			@Override
			public void result(double nlatitude, double nlontitude, boolean success) {
				if (success) {
					Log.i("aaaa", "bbbbbb");
					switch (i) {
					case 1:
						lls = LodingDialog.DialogFactor(getActivity(),"正在刷新",false);
						mArrayList=new ArrayList<InfoList>();
						mFuJinAdapter.setdata(mArrayList);
						mFuJinAdapter.clearnList();
						mFuJinAdapter.notifyDataSetChanged();
						break;
					}
					PublicRequest http = new PublicRequest(mHandler);
					http.NearData(a, nlontitude +"", nlatitude+"", s, page+"", pagenum+"");
				}else {
					if (lls != null) {
						lls.dismiss();
					}
					com.bset.tool.Toast.ToastMe(getActivity(), "获取地理位置失败");
				}
			}
		});
	}
}
