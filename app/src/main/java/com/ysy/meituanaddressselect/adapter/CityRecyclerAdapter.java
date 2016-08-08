package com.ysy.meituanaddressselect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ysy.meituanaddressselect.BaseApp;
import com.ysy.meituanaddressselect.R;
import com.ysy.meituanaddressselect.bean.City;


/**
 * Created by yang on 2016/7/26.
 */
public abstract class CityRecyclerAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    public CityRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == ViewHolder.SELECT_COUNTY) {
            holder = new ViewHolder.SelectCountyViewHolder(mContext,
                    mLayoutInflater.inflate(R.layout.litem_select_county, parent, false));
        } else if (viewType == ViewHolder.CURRENT_CITY) {
            holder = new ViewHolder.CurrentCityViewHolder(
                    mLayoutInflater.inflate(R.layout.item_current_city, parent, false));//为啥为null才
        } else if (viewType == ViewHolder.LATELY_CITY) {
            holder = new ViewHolder.LatelyCityViewHolder(
                    mLayoutInflater.inflate(R.layout.item_lately_city, parent, false));
        } else if (viewType == ViewHolder.HOT_CITY) {
            holder = new ViewHolder.HotCityViewHolder(mContext,
                    mLayoutInflater.inflate(R.layout.item_hot_city, parent, false));
        } else if (viewType == ViewHolder.CITY_LIST) {
            holder = new ViewHolder.CityListViewHolder(
                    mLayoutInflater.inflate(R.layout.item_city_list, parent, false));
        }
        initHolderData(holder);
        return holder;
    }

    //初始化数据
    public abstract void initHolderData(final RecyclerView.ViewHolder viewHolder);

    /**
     * 设置城市列表数据
     */
    private void setHolderData(final ViewHolder.CityListViewHolder viewHolder, int position) {
        //数据是静态的 存在 BaseApp 里了，只是做个演示
        City city = BaseApp.cityList.getList().get(position);
        viewHolder.setCityName(city.getName());
        //比较当前城市首字母和前一个城市的首字母，若不一致，则显示字母，若一致，则忽略
        String c = city.getPinyin().substring(0, 1);
        if (position != 0) {
            if (!c.equalsIgnoreCase(BaseApp.cityList.getList().get(position - 1)
                    .getPinyin().substring(0, 1))) {
                viewHolder.showLetter(c);
            } else {
                viewHolder.hideLetter();
            }
        } else {
            viewHolder.showLetter(c);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //城市列表专用
        if (position > 3) {
            setHolderData((ViewHolder.CityListViewHolder) holder, position - 4);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if (position == 0) {
            result = ViewHolder.SELECT_COUNTY;
        } else if (position == 1) {
            result = ViewHolder.CURRENT_CITY;
        } else if (position == 2) {
            result = ViewHolder.LATELY_CITY;
        } else if (position == 3) {
            result = ViewHolder.HOT_CITY;
        } else if (position > 3) {
            result = ViewHolder.CITY_LIST;
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return BaseApp.cityList.getList().size() + 4;
    }
}
