package com.example.tableviewlibrary;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 表格的item，一行
 * 
 */
public class TableItem extends LinearLayout {

	private static final int TABLE_WIDTH = 1000; // 总宽度，这是不科学的假设
	private static final int TABLE_CELL_HEIGHT = 48;  //这个必须和TableView.header的高度一致

	private Context context;
	private int column = 0;
	private ArrayList<TextView> texts;
	private int[] weight;
	private int totalWidth;

	public TableItem(Context context, int column) {
		this(context, column, null, 0);
	}

	// private String [] str_left=new String
	// []{"CIF号：","客户名称：","客户类型：","客户状态：","联系电话：","证件类别：","证件号码：","创建日期："};
	public TableItem(Context context, int column, int[] weight, int width) {
		super(context);
		this.context = context;
		this.column = column;
		this.weight = weight;
		this.totalWidth = width;
		if (totalWidth == 0) {
			totalWidth = TABLE_WIDTH;
		}
		init();
	}

	private TextView getCellView() {
		return (TextView) LayoutInflater.from(context).inflate(
				R.layout.layout_table_content, null);
	}

	private View getSeperator() {
		return LayoutInflater.from(context).inflate(
				R.layout.layout_table_seperator, null);
	}

	private void init() {
	    //L.debug("TableItem---init一个新的");
		this.setBackgroundResource(R.drawable.selector_table_item_bg);
		texts = new ArrayList<TextView>();

		this.removeAllViews();
		int widthTaken = 0;
		for (int i = 0; i < column; i++) {

			// 添加左侧竖线
			View sep = this.getSeperator();
			LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(1,
			        TABLE_CELL_HEIGHT);
			sep.setLayoutParams(lp2);
			this.addView(sep);

			// 添加文本框
			TextView tv = this.getCellView();

			// ==计算宽度：
			int width = 0;
			if (weight != null && weight.length > 0) {
				width = (int) (totalWidth * ((float) weight[i] / (float) 100));
			} else {
				width = totalWidth / column; // 平均
			}
			widthTaken += width;

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,
					TABLE_CELL_HEIGHT);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,
//			        LinearLayout.LayoutParams.WRAP_CONTENT);
			tv.setLayoutParams(lp);
			this.addView(tv);
			texts.add(tv);

			// 添加右侧竖线，只有最后一格才需要
			if (i == column - 1) {
				View sep2 = this.getSeperator();
				LinearLayout.LayoutParams lp22 = new LinearLayout.LayoutParams(
						1, TABLE_CELL_HEIGHT);
				sep2.setLayoutParams(lp22);
				this.addView(sep2);
			}
		}
	}

	public TextView getCell(int position) {
		return texts.get(position);
	}

	/**
	 * 更改表格一行的背景色，但不影响分割线
	 */
	public void setBackgroundColor(int color) {
		int len = this.getChildCount();
		for (int i = 0; i < len; i++) {
			View v = this.getChildAt(i);
			if (v instanceof TextView)
				v.setBackgroundColor(color);
		}
	}

	/**
	 * 更改表格一行的背景色，但不影响分割线
	 */
	public void setBackground(int drawableId) {
		int len = this.getChildCount();
		for (int i = 0; i < len; i++) {
			View v = this.getChildAt(i);
			if (v instanceof TextView)
				v.setBackgroundResource(drawableId);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
}
