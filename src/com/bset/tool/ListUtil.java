package com.bset.tool;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListUtil {

	/**
	 * 嵌套listview中固定高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) { 
        ListAdapter listAdapter = listView.getAdapter();     
        if (listAdapter == null) {    
            return;    
        }    
    
        int totalHeight = 0;    
        for (int i = 0; i < listAdapter.getCount(); i++) {    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0);    
            totalHeight += listItem.getMeasuredHeight();    
        }    
    
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        params.height += 5;//if without this statement,the listview will be a little short    
        listView.setLayoutParams(params);   
    }  
	
	/**
	 * 嵌套GridView中固定高度
	 * @param GridView
	 * @param h
	 */
	public static void setGridViewHeightBasedOnChildren(GridView gridView,int h) {
        ListAdapter listAdapter = gridView.getAdapter();     
        if (listAdapter == null) {
            return;    
        }    
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, gridView);    
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();    
        }    
    
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        if (listAdapter.getCount()/3 == 0) {
        	if (listAdapter.getCount()%3 != 0) {
        		totalHeight = totalHeight/(listAdapter.getCount());
			}
		}else if (listAdapter.getCount()%3 == 0) {
			totalHeight = totalHeight/3;
		}else if (listAdapter.getCount()%3 != 0) {
			totalHeight = totalHeight/listAdapter.getCount()*(listAdapter.getCount()/3+1);
		}
        params.height = totalHeight + (gridView.getHeight() * (listAdapter.getCount() - 1));    
        params.height += h;//if without this statement,the listview will be a little short    
        gridView.setLayoutParams(params);   
    }  
	
	
	/**
	 * 嵌套GridView中固定高度
	 * @param GridView
	 * @param h
	 */
	public static void setGridViewHeightBasedOnChildren2(GridView gridView,int rowCount,int h) {
        ListAdapter listAdapter = gridView.getAdapter();     
        if (listAdapter == null) {
            return;    
        }    
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, gridView);    
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();    
        }    
    
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        if (listAdapter.getCount()/rowCount == 0) {
        	if (listAdapter.getCount()%rowCount != 0) {
        		totalHeight = totalHeight/(listAdapter.getCount());
			}
		}else if (listAdapter.getCount()%rowCount == 0) {
			totalHeight = totalHeight/rowCount;
		}else if (listAdapter.getCount()%rowCount != 0) {
			totalHeight = totalHeight/listAdapter.getCount()*(listAdapter.getCount()/rowCount+1);
		}
        params.height = totalHeight + (gridView.getHeight() * (listAdapter.getCount() - 1));    
        params.height += h;//if without this statement,the listview will be a little short    
        gridView.setLayoutParams(params);   
    }  

}
