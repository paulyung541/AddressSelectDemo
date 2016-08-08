package com.ysy.meituanaddressselect;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ysy.meituanaddressselect.adapter.CityRecyclerAdapter;
import com.ysy.meituanaddressselect.adapter.ViewHolder;

import java.util.ArrayList;

/**
 * 模仿美团地址选择功能
 */
public class MainActivity extends AppCompatActivity {
    private EdgeLabelView edgeLabelView;
    private TextView tv;
    private RecyclerView recyclerView;

    private ArrayList<String> countys = new ArrayList<>();//县区

    {
        countys.add("海口");
        countys.add("成都");
        countys.add("南宁");
        countys.add("深圳");
        countys.add("上海");
        countys.add("乌鲁木齐");
        countys.add("东莞");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edgeLabelView = (EdgeLabelView) findViewById(R.id.ed);
        tv = (TextView) findViewById(R.id.tv);
        edgeLabelView.setOnSlidingTouchListener(new EdgeLabelView.OnSlidingTouchListener() {
            @Override
            public void onSlidingTouch(String str) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(getPositionByString(str), 0);
            }
        });
        edgeLabelView.setDialog(tv);
        initView();
    }

    /**
     * 根据 右边导航的字母等，获取RecyclerView中的position
     */
    private int getPositionByString(String str) {
        int result;
        switch (str) {
            case "县区":
                result = 0;
                break;
            case "定位":
                result = 1;
                break;
            case "最近":
                result = 2;
                break;
            case "热门":
                result = 3;
                break;
            default:
                result = BaseApp.cityList.getPositionByLetter(str) + 4;
        }
        return result;
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//不动的状态
                    tv.setVisibility(View.GONE);
                } else {
                    tv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int position = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findFirstVisibleItemPosition();
                switch (position) {
                    case 0:
                        if (!tv.getText().toString().equals("县区")) {
                            tv.setText("县区");
                        }
                        break;
                    case 1:
                        if (!tv.getText().toString().equals("定位")) {
                            tv.setText("定位");
                        }
                        break;
                    case 2:
                        if (!tv.getText().toString().equals("最近")) {
                            tv.setText("最近");
                        }
                        break;
                    case 3:
                        if (!tv.getText().toString().equals("热门")) {
                            tv.setText("热门");
                        }
                        break;
                    default:
                        //poisition > 3 的情况
                        String tmp = BaseApp.cityList.getList().get(position - 4).getPinyin().substring(0, 1);
                        if (!tv.getText().toString().equals(tmp)) {
                            tv.setText(tmp);
                        }
                }
            }
        });

        recyclerView.setAdapter(new CityRecyclerAdapter(this) {
            @Override
            public void initHolderData(final RecyclerView.ViewHolder viewHolder) {

                if (viewHolder instanceof ViewHolder.SelectCountyViewHolder) {//选择区县
                    final ViewHolder.SelectCountyViewHolder vh = (ViewHolder.SelectCountyViewHolder) viewHolder;
                    vh.setOnCountyClickListener(new ViewHolder.SelectCountyViewHolder.OnCountyClickListener() {
                        @Override
                        public void onCountyClick(String whichCounty) {
                            Utils.showToast(whichCounty);
                        }

                        @Override
                        public void displayCounty() {
                            //执行加载过程，需要异步
                            new AsyncTask<Void, Void, ArrayList<String>>() {
                                @Override
                                protected ArrayList<String> doInBackground(Void... params) {
                                    try {
                                        Thread.sleep(800);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    return countys;
                                }

                                @Override
                                protected void onPostExecute(ArrayList<String> strings) {
                                    vh.loadDataComplete(countys);
                                    vh.clickAble();//设置可点击
                                    if (countys.size() == 0) {
                                        vh.showNoData();
                                    } else {
                                        vh.hideNoData();
                                    }
                                }
                            }.execute();
                        }
                    });
                } else if (viewHolder instanceof ViewHolder.HotCityViewHolder) {//热门城市
                    final ViewHolder.HotCityViewHolder vh = (ViewHolder.HotCityViewHolder) viewHolder;
                    vh.setOnItemClickListener(new ViewHolder.HotCityViewHolder.OnItemClickListener() {
                        @Override
                        public void onItemListener(String s) {
                            Utils.showToast(s);
                        }
                    });
                } else if (viewHolder instanceof ViewHolder.CityListViewHolder) {//城市列表
                    final ViewHolder.CityListViewHolder vh = (ViewHolder.CityListViewHolder) viewHolder;
                    vh.setOnCityClickListener(new ViewHolder.CityListViewHolder.OnCityClickListener() {
                        @Override
                        public void onCityClick(String s) {
                            Utils.showToast(s);
                        }
                    });
                }
            }
        });
    }
}
