package com.example.administrator.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ImageView mArrow;
    private EditText mEdit;
    private PopupWindow mPopupWindow;

    private List<String> mDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        setLinsetener();


    }


    private void initView() {
        mArrow = (ImageView) findViewById(R.id.arrow);
        mEdit = (EditText) findViewById(R.id.editText);

    }

    private void initData() {
        mDataList = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            mDataList.add(String.valueOf(i));
        }
    }

    private void setLinsetener() {
        mArrow.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupWindow();
        }
    };

    private void showPopupWindow() {
        if (mPopupWindow == null) {
            int width = mEdit.getWidth();
            int height = 500;
            mPopupWindow = new PopupWindow(width, height);


            ListView listView = new ListView(this);
        //设置listview边框背景
            listView.setBackgroundResource(R.mipmap.listview_background);
            listView.setAdapter(mBaseAdapter);
            listView.setOnItemClickListener(mOnItemClickListener);
      mPopupWindow.setContentView(listView);
        mPopupWindow.setOutsideTouchable(true);//让popupwindow可以消失
        //让popup可以获得焦点
        mPopupWindow.setFocusable(true);

        }
        //弹出popupwindow
        mPopupWindow.showAsDropDown(mEdit);
    }
private AdapterView.OnItemClickListener mOnItemClickListener=new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //点击列表选项，可以显示结果到编辑框
        mEdit.setText(mDataList.get(position));
        //将光标移动到最后面
        mEdit.setSelection(mDataList.get(position).length());
        mPopupWindow.dismiss();//选择完让其消失
    }
};

    private BaseAdapter mBaseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.view_list_item, null);
                holder = new ViewHolder();
                holder.mUserId = (TextView) convertView.findViewById(R.id.user_id);
                holder.mDelete = (ImageView) convertView.findViewById(R.id.delete);
           convertView.setTag(holder);

            }else {
                holder = (ViewHolder) convertView.getTag();
            }
             //刷新用户
            final String data = mDataList.get(position);
            holder.mUserId.setText(data);
            //设置删除的监听事件
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //将点击位置的数据进行删除并且刷新列表
               mDataList.remove(data);
                 notifyDataSetChanged();
                }
            });
            return convertView;
        }
    };






    static class ViewHolder {
        TextView mUserId;
        ImageView mDelete;
    }
}
