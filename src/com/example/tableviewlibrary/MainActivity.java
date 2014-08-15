
package com.example.tableviewlibrary;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TableView table; // 表格
    private Context context;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        table= (TableView) findViewById(R.id.tablelist_custome_content);
        table.setHeaderFixed(true);//设置表头固定
        table.setAdapter(new MyAdapter(context));
        table.setOnHeaderCellClickedListener(new TableView.OnHeaderCellClickedListener() {

            @Override
            public void onHeaderCellClicked(int position, String field) {
                Toast.makeText(context, field, 0).show();
            }
        });

        table.setOnItemClickedListener(new TableView.OnItemClickedListener() {
    
            @Override
            public void onItemClicked(int position, TableItem item, AdapterView<?> parent) {
                Toast.makeText(context, "点击了第" + position + "行", 0).show();
            }
        });
    }

    class MyAdapter extends TableAdapter{
        
        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }
        
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 20 + 1;
        }
        
        @Override
        public void notifyDataSetChanged(Object data, Object data2) {
            // TODO Auto-generated method stub
            
        }
        
//        @Override
//        protected int getWidth() {
//        }
        
        @Override
        protected int[] getWeight() {
            // TODO Auto-generated method stub
            return new int[]{
                    10, 11, 12, 13, 14, 25, 15
            };
        }
        
        @Override
        protected String[] getValues(int position) {
            // TODO Auto-generated method stub
            return new String[]{
                    "王二", "男", "29", "烟台", "北京", "安卓APP", "20K"
            };
        }
        
        
        @Override
        protected String[] getHeaders() {
            // TODO Auto-generated method stub
            return new String[]{
                    "Name", "Gender", "Age", "From", "Now", "Job", "Salary"
            };
        }
        
        
        @Override
        protected int getColumn() {
            // TODO Auto-generated method stub
            return 7;
        }
        
        @Override
        protected int getOddBackground() {
            return R.drawable.selector_table_item_bg_odd;
        }

        @Override
        protected int getEvenBackground() {
            return R.drawable.selector_table_item_bg;
        }
    };
}
