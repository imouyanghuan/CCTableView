package com.example.tableviewlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

public class TableView extends FrameLayout{//HorizontalScrollView {
	private ListView listContent;
	private FrameLayout header;

	public TableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(); 
	}

	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TableView(Context context) {
		super(context);
		init();
	}

	private void init() {
		//setClickable(false);
		View.inflate(getContext(), R.layout.layout_table_view, this);
		listContent = (ListView) findViewById(R.id.list_custome_content);
		header = (FrameLayout) findViewById(R.id.table_header);
		listContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (itemClickedLisenter == null) return;
				// ===执行响应动作
				itemClickedLisenter.onItemClicked(arg2 - 1, (TableItem) arg1,arg0);
			}
		});
	}

	public void setAdapter(TableAdapter adapter) {
		listContent.setAdapter(adapter);
		adapter.setHeaderContainer(header);
		adapter.setOnHeaderCellClickedListener(headerClickedLisenter);
		//DisplayUtil.setListViewHeightBasedOnChildren(adapter, listContent);
//		TableItem head = adapter.getHeader(this.getWidth());
//		header.addView(head);
//		int len = head.getChildCount();
//		for (int i = 0; i < len; i++) {
//			final int j = i;
//			View v = head.getChildAt(i);
//			// ==分割线：忽略
//			if (!(v instanceof TextView))
//				continue;
//			final TextView tv = (TextView) v;
//			tv.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					if (headerClickedLisenter == null) {
//						return;
//					}
//					headerClickedLisenter.onHeaderCellClicked(j, tv.getText()
//							.toString());
//				}
//			});
//		}
	}

	public void setHeaderFixed(boolean fixed) {
		if (fixed) {
			header.setVisibility(View.VISIBLE);
		} else {
			header.setVisibility(View.GONE);
		}
	}

	// =====点击事件: 条目========================//
	public interface OnItemClickedListener {
		void onItemClicked(int position, TableItem item, AdapterView<?> parent);
	}

	private OnItemClickedListener itemClickedLisenter;

	public void setOnItemClickedListener(OnItemClickedListener oicl) {
		this.itemClickedLisenter = oicl;
	}

	// =====点击事件: 表头的单元格被点击=======================//
	public interface OnHeaderCellClickedListener {
		void onHeaderCellClicked(int position, String field);
	}

	private OnHeaderCellClickedListener headerClickedLisenter;

	public void setOnHeaderCellClickedListener(OnHeaderCellClickedListener oicl) {
		this.headerClickedLisenter = oicl;
	}

	// =====上拉加载========================//
	public interface OnLoadListener {
		void onLoad(int pageNow);
	}

	private OnLoadListener loadListener;

	public void setOnLoadListener(OnLoadListener oicl) {
		this.loadListener = oicl;
	}
}
