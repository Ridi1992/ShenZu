package com.alipay.sdk.pay.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanmi.activity.R;

public class ExternalFragment extends Fragment {
	
	private TextView mName;
	private TextView mContent;
	private TextView mPrice;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pay_external, container, false);
		
		mName = (TextView) view.findViewById(R.id.aaaa_subject);
		mName.setText(getActivity().getIntent().getStringExtra("order_sn"));
		mContent = (TextView) view.findViewById(R.id.aaaa_content);
		mContent.setText(getActivity().getIntent().getStringExtra("name"));
		mPrice = (TextView) view.findViewById(R.id.aaaa_price);
		mPrice.setText(getActivity().getIntent().getStringExtra("amount"));
		
		
		return view ;
	}
}
