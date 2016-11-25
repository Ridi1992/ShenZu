package com.sanmi.fragment;

import java.util.ArrayList;
import java.util.Collection;

import com.best.mesage.MessageData;
import com.best.mesage.MessageList;
import com.best.mesage.MessageTool;
import com.dream.framework.utils.dialog.LodingDialog;
import com.sanmi.activity.R;
import com.sanmi.adapter.MsgAdapter;
import com.sanmi.business.MsgData;
import com.sanmi.fengzhuang.MsgDatas;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.view.MyApplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
/**
 * ϢFragment
 * @author Administrator
 *
 */
public class MsgFragment extends Fragment {
	private com.sanmi.refresh.PullToRefreshListView mPullToRefreshListView;//ˢlistview
	private LodingDialog lls;//ˢDialog
    private ListView mListView;//listview
	private ArrayList<MsgDatas> mDatas=new ArrayList<MsgDatas>();//ݼ
	private MsgData mData=new MsgData();//Դ
	private MsgAdapter mMsgAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView=inflater.inflate(R.layout.msgfragment, null);
		init(mView);//ʼ
//		listViewRefresh();//ˢ
		mMsgAdapter=new MsgAdapter(getActivity(), null);
		mListView.setAdapter(mMsgAdapter);
		mMsgAdapter.setmList(getData());
		mMsgAdapter.notifyDataSetChanged();
		return mView;
	}
	//本地獲取消息列表
	private MessageList getData(){
		MessageTool messageTool=new MessageTool();
		MessageList list=messageTool.Getdatalist(MyApplication.share);
		return list;
	}
	//刷新數據
	public void refresh(){
//		mMsgAdapter.clearn();
		mMsgAdapter.setmList(getData());
		mMsgAdapter.notifyDataSetChanged();
	}
	/**
	 *ˢ 
	 */
	private void listViewRefresh() {
		// ˢ·
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// ˢ
				if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {// ˢ
					 lls=LodingDialog.DialogFactor(getActivity(), "...", false);
					
					
					mPullToRefreshListView.onRefreshComplete();
					lls.dismiss();
				} else{
					
					if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {// 
					
						lls=LodingDialog.DialogFactor(getActivity(), "...", false);
						
						mPullToRefreshListView.onRefreshComplete();
						lls.dismiss();
					}
				}
					
					
					
			}
			
		});
	}
	/**
	 * ʼ
	 * @param mView
	 */
	private void init(View mView) {
		mListView=(ListView) mView.findViewById(R.id.lv_msg);
	}

}
