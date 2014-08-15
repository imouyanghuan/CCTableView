package com.example.tableviewlibrary;

import com.example.tableviewlibrary.TableView.OnHeaderCellClickedListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class TableAdapter extends BaseAdapter{
    
    private Context context;
//    private final int ScreenWidth;
    public TableAdapter(Context context) {
        this.context = context;
    }
    
    protected abstract int getColumn();
    /**
     * 表头
     * @return
     */
    protected abstract String[] getHeaders();
    /**
     * 当前行的值们，position从0开始，表示第一列
     * @param position
     * @return
     */
    protected abstract String[] getValues(int position);
    
    /**
     * 每列的宽度，这是个比例，不论几列，加起来肯定是100
     * ——后期可以扩展，加起来如果大于100，就给滚动条
     * @return
     */
    protected abstract int[] getWeight();
    
    /**
     * 表格的宽度
     * @return
     */
    protected int getWidth(){
        return width;
    }
    protected void setWidth(int w){
        if(width <= 0){
            width = w;
        }
    }
    
    /**
     * 奇数行背景
     * @param resId
     * @return
     */
    protected abstract int getOddBackground();
    /**
     * 偶数行背景
     * @param resId
     * @return
     */
    protected abstract int getEvenBackground();
    

    
    /**
     * 数据更新，刷新全部item
     * @param data  就是个List<T>，因为这里没用泛型，所以需要强转一下
     */
    public abstract void notifyDataSetChanged(Object data, Object data2);
    
    
    public TableItem getHeader(int width){
    	System.out.println("header的width = " + width);
        TableItem item = new TableItem(context, getColumn(), getWeight(), width);
        
        //==表头的背景颜色和其它的不一样
        item.setBackgroundResource(R.drawable.marketing_activities_list_top_bg);
        
        String[] values = null;
        values = this.getHeaders();
        
        for(int i = 0; i < this.getColumn(); i++){
            TextView tv = item.getCell(i);
            
            tv.setText(values[i]);
        }
        return item;
    }
    private int width;
    private ViewGroup headerContainer;
    void setHeaderContainer(ViewGroup container){
        this.headerContainer = container;
    }
    private OnHeaderCellClickedListener headerClickedLisenter;

    void setOnHeaderCellClickedListener(OnHeaderCellClickedListener oicl) {
        this.headerClickedLisenter = oicl;
    }
    
    public View getView(int arg0, View convertView, ViewGroup arg2){
        setWidth(arg2.getWidth());
        System.out.println("TableAdapter.getView("+ arg0 +")---item.width = " + width);
        TableItem item = null; //
        
        TableItem headerr = null;
        
        if(convertView == null){
            item = new TableItem(context, getColumn(), getWeight(), getWidth());
        }else{
            item = (TableItem)convertView;
        }
        
        if(!(item.getLayoutParams() instanceof AbsListView.LayoutParams)){
            //这里是这么个情况，正常的getView
        }
        
        String[] values = null;
        if(arg0 == 0){//表头
            values = this.getHeaders();
            headerr = new TableItem(context, getColumn(), getWeight(), getWidth());
            
        }else{//非表头
            values = this.getValues(arg0 - 1);
            if((arg0 - 1) % 2 == 0){
            	//偶数行，even
            	if(this.getEvenBackground() >= 0) item.setBackgroundResource(this.getEvenBackground());
            }else{
            	//奇数行，odd
            	if(this.getOddBackground() >= 0) item.setBackgroundResource(this.getOddBackground());
            }
        }
        
        for(int i = 0; i < this.getColumn(); i++){
            TextView tv = item.getCell(i);
            tv.setText(values[i]);
            if(arg0 == 0){
                //表头
                TextView tv2 = headerr.getCell(i);
                tv2.setText(values[i]);
            }
        }
        
        if(arg0 == 0 && this.headerContainer != null){
            //表头
            System.out.println("+++++++++++++++++++");
            //==表头的背景颜色和其它的不一样
            headerr.setBackgroundResource(R.drawable.marketing_activities_list_top_bg);
            headerContainer.addView(headerr);
            
            //被headerContainer添加完之后，item.layoutparam变成了Framelayout.LayoutParam
            //而带着这个param直接返回，在ListView里会报错，因为LisView要的是AbsListView.LayoutParam
            //这里需要两个header的item其实，但是一个view又不能添加到两个父控件中
            //所以如果是header，就初始化两个
            
            int len = headerr.getChildCount();
            for (int i = 0; i < len; i++) {
                final int j = i;
                View v = headerr.getChildAt(i);
                // ==分割线：忽略
                if (!(v instanceof TextView))
                    continue;
                final TextView tv = (TextView) v;
                tv.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (headerClickedLisenter == null) {
                            return;
                        }
                        headerClickedLisenter.onHeaderCellClicked(j, tv.getText()
                                .toString());
                    }
                });
            }
        }
        return item;
    }
    
    
    
}
