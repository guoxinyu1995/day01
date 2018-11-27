package com.example.day01.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.day01.ui.adaper.ImageAdaper;
import com.example.day01.Util.NetUtil;
import com.example.day01.R;
import com.example.day01.Bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView original;
    private TextView discounts;
    private String url = "http://www.zhaoapi.cn/product/getProductDetail?pid=%d";
    private ImageAdaper adaper;
    private int mPage;
    private List<String> list = new ArrayList<>();
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取资源id
        viewPager = findViewById(R.id.view_pager);
        title = findViewById(R.id.title);
        original = findViewById(R.id.original);
        discounts = findViewById(R.id.discounts);
        //创建适配器
        adaper = new ImageAdaper(this);
        viewPager.setAdapter(adaper);
        original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        initData();
    }

    private void initData() {
        mPage = 1;
        NetUtil.getIntance().getRequest(String.format(url,mPage), ShopBean.class, new NetUtil.CallBack<ShopBean>() {
            @Override
            public void onSuccess(ShopBean o) {
                sub(o.getData().getImages());
                adaper.setmData(list);
                title.setText(o.getData().getTitle());
                original.setText("原价:"+o.getData().getPrice());
                discounts.setText("优惠价:"+o.getData().getBargainPrice());
                //切换到中间位置
                int center = adaper.getCount() / 2;
                center = center - center % (list.size());
                viewPager.setCurrentItem(center);
                //开启轮播
                startLooper();
            }
        });
    }
    //创建handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            handler.sendEmptyMessageDelayed(0, 2000);
        }
    };
    //开启轮播
    private void startLooper() {
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(0, 2000);
    }
    //字符串截取
    public void sub(String str){
        //第一步找到关键字的角标
        int index = str.indexOf("|");
        //如果角标>=0说明有关键字
        if(index>=0){
            String strLeft = str.substring(0, index);
            list.add(strLeft);
            sub(str.substring(index+1,str.length()));
        }else{
            list.add(str);
        }
    }
}
