package com.ysy.meituanaddressselect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysy.meituanaddressselect.R;
import com.ysy.meituanaddressselect.Utils;

import java.util.ArrayList;

/**
 * Created by yang on 2016/7/26.
 */
public class ViewHolder {
    public static final int SELECT_COUNTY = 0;

    public static final int CURRENT_CITY = 1;

    public static final int LATELY_CITY = 2;

    public static final int HOT_CITY = 3;

    public static final int CITY_LIST = 4;

    //选择县区
    public static class SelectCountyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCurrentCity;
        public TextView tvSelectCounty;
        public ImageView imgLoading;
        public TextView tvNoCounty;
        public LinearLayout container;
        private Context mContext;
        private Animation anim;

        private boolean isOpen;

        private OnCountyClickListener mListener;

        public SelectCountyViewHolder(Context context, View root) {
            super(root);
            tvCurrentCity = (TextView) root.findViewById(R.id.tv_current_city);
            tvSelectCounty = (TextView) root.findViewById(R.id.tv_select_county);
            imgLoading = (ImageView) root.findViewById(R.id.loading);
            container = (LinearLayout) root.findViewById(R.id.county);
            tvNoCounty = (TextView) root.findViewById(R.id.tv_no_county);

            mContext = context;

            anim = AnimationUtils.loadAnimation(mContext, R.anim.loading);
            LinearInterpolator lir = new LinearInterpolator();
            anim.setInterpolator(lir);

            //点击展开和关闭县区
            tvSelectCounty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOpen) {
                        container.setVisibility(View.GONE);
                        tvNoCounty.setVisibility(View.GONE);
                        tvSelectCounty.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                mContext.getResources().getDrawable(R.mipmap.down), null);
                    } else {
                        //点击展开
                        tvSelectCounty.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                mContext.getResources().getDrawable(R.mipmap.up), null);
                        tvSelectCounty.setEnabled(false);
                        imgLoading.setVisibility(View.VISIBLE);
                        imgLoading.startAnimation(anim);
                        mListener.displayCounty();
                    }
                    isOpen = !isOpen;
                }
            });
        }

        /**
         * 显示：本城市无区县
         */
        public void showNoData() {
            tvNoCounty.setVisibility(View.VISIBLE);
        }

        /**
         * 隐藏：本城市无区县
         */
        public void hideNoData() {
            tvNoCounty.setVisibility(View.GONE);
        }

        /**
         * 允许点击
         */
        public void clickAble() {
            tvSelectCounty.setEnabled(true);
        }

        /**
         * 外层调用此函数，表示数据加载完毕
         * 数据空间由上层分配
         *
         * @param data 县区信息
         */
        public void loadDataComplete(ArrayList<String> data) {
            imgLoading.setVisibility(View.GONE);
            imgLoading.clearAnimation();
            initContainer(data);
        }

        /**
         * 根据数据，生成相应的行和列，并设置点击事件
         *
         * @param currentSelectCityCountys 县区信息
         */
        private void initContainer(ArrayList<String> currentSelectCityCountys) {
            if (currentSelectCityCountys.size() == 0) {
                return;
            }
            container.setVisibility(View.VISIBLE);
            container.removeAllViews();

            int row = currentSelectCityCountys.size() / 3;
            int res = currentSelectCityCountys.size() % 3;

            if (res != 0) {//如果有余数，则行数加一
                row++;
            }
            ArrayList<TextView> textViews = new ArrayList<>(currentSelectCityCountys.size());

            //生成每一行，设置对应位置参数，并添加进 textViews
            for (int i = 0; i < row; i++) {
                LinearLayout linearLayout = (LinearLayout) View.inflate(mContext,
                        R.layout.s_hot_city, null);
                container.addView(linearLayout);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                params.height = Utils.dip2px(mContext, 36);
                params.setMargins(0, 0, 0, Utils.dip2px(mContext, 10));
                //最后一行,如果有剩余的，则添加进 textViews
                if (row - i == 1 && res != 0) {
                    for (int j = 0; j < res; j++) {
                        textViews.add((TextView) linearLayout.getChildAt(j));
                    }
                    break;
                }
                textViews.add((TextView) linearLayout.getChildAt(0));
                textViews.add((TextView) linearLayout.getChildAt(1));
                textViews.add((TextView) linearLayout.getChildAt(2));
            }

            for (int i = 0; i < currentSelectCityCountys.size(); i++) {
                textViews.get(i).setVisibility(View.VISIBLE);
                textViews.get(i).setText(currentSelectCityCountys.get(i));
                textViews.get(i).setTag(currentSelectCityCountys.get(i));
                textViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onCountyClick((String) v.getTag());
                    }
                });
            }
        }

        public void setOnCountyClickListener(OnCountyClickListener listener) {
            mListener = listener;
        }

        public interface OnCountyClickListener {
            /**
             * 点击选择某个县区，并返回县区信息，这里用县区名字代替县区信息
             */
            void onCountyClick(String whichCounty);

            /**
             * 点击选择县区，展开
             */
            void displayCounty();
        }
    }

    //当前定位城市
    public static class CurrentCityViewHolder extends RecyclerView.ViewHolder {

        public CurrentCityViewHolder(View itemView) {
            super(itemView);
        }
    }

    //最近访问城市
    public static class LatelyCityViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout container;

        public LatelyCityViewHolder(View root) {
            super(root);
        }

        private void initData(ArrayList<String> latelyCitys) {
            //只少是有一个的，因为定位的就算一个
        }
    }

    //热门城市
    public static class HotCityViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout container;

        private OnItemClickListener mListener;

        private String[] mHotCitys;

        private Context mContext;

        public HotCityViewHolder(Context context, View root) {
            super(root);
            mContext = context;
            container = (LinearLayout) root.findViewById(R.id.hot_city_container);
            initView();
        }

        private void initView() {
            mHotCitys = mContext.getResources().getStringArray(R.array.hot_city);

            container.setVisibility(View.VISIBLE);
            container.removeAllViews();

            int row = mHotCitys.length / 3;
            int res = mHotCitys.length % 3;

            if (res != 0) {//如果有余数，则行数加一
                row++;
            }
            ArrayList<TextView> textViews = new ArrayList<>(mHotCitys.length);

            for (int i = 0; i < row; i++) {
                LinearLayout linearLayout = (LinearLayout) View.inflate(mContext,
                        R.layout.s_hot_city, null);
                container.addView(linearLayout);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                params.height = Utils.dip2px(mContext, 36);
                params.setMargins(0, 0, 0, Utils.dip2px(mContext, 10));
                if (row - i == 1 && res != 0) {
                    for (int j = 0; j < res; j++) {
                        textViews.add((TextView) linearLayout.getChildAt(j));
                    }
                    break;
                }
                textViews.add((TextView) linearLayout.getChildAt(0));
                textViews.add((TextView) linearLayout.getChildAt(1));
                textViews.add((TextView) linearLayout.getChildAt(2));
            }

            for (int i = 0; i < mHotCitys.length; i++) {
                textViews.get(i).setVisibility(View.VISIBLE);
                textViews.get(i).setText(mHotCitys[i]);
                textViews.get(i).setTag(mHotCitys[i]);
                textViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onItemListener((String) v.getTag());
                        }
                    }
                });
            }
        }

        public void setOnItemClickListener(OnItemClickListener l) {
            mListener = l;
        }

        public interface OnItemClickListener {
            void onItemListener(String s);
        }
    }

    //城市列表

    /**
     * 实现思路：
     * 每一个item 由 两个TextView组成，一个用于展示字母，一个用于展示城市，显示字母的TextView通常处于隐藏状态
     * 比较前后两个item，如果首字母不一致，则将当前的字母TextView显示出来
     */
    public static class CityListViewHolder extends RecyclerView.ViewHolder {
        private TextView letter;
        private TextView city;

        private OnCityClickListener mListener;

        public CityListViewHolder(View root) {
            super(root);
            letter = (TextView) root.findViewById(R.id.letter);
            city = (TextView) root.findViewById(R.id.city);

            city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onCityClick(city.getText().toString());
                    }
                }
            });
        }

        //显示字母
        public void showLetter(String s) {
            letter.setVisibility(View.VISIBLE);
            letter.setText(s);
        }

        public void hideLetter() {
            letter.setVisibility(View.GONE);
        }

        //设置城市名
        public void setCityName(String s) {
            city.setText(s);
        }

        public void setOnCityClickListener(OnCityClickListener l) {
            mListener = l;
        }

        public interface OnCityClickListener {
            void onCityClick(String s);
        }
    }
}