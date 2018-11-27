package com.example.day01.ui.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ImageAdaper extends PagerAdapter {
    //private ShopBean.DataBean mData;
    private Context mContext;
    private List<String> list;
    public ImageAdaper(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
    }
    public void setmData(List<String> datas){
        list.clear();
        if(datas!=null){
            list.addAll(datas);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size()>0?5000:0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);
        ImageLoader.getInstance().displayImage(list.get(position%list.size()),imageView);
        return imageView;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
