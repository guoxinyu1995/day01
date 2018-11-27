package com.example.day01.Application;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.day01.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String path = Environment.getExternalStorageDirectory()+"/images";
        File file = new File(path);
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this)
                //配置内存缓存
                .memoryCacheSizePercentage(10)
                //配置磁盘缓存
                //限制图片缓存路径
                .diskCache(new UnlimitedDiskCache(file))
                //配置缓存数量
                .diskCacheFileCount(50)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50*1024*1024)
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        //设置色彩模式
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .cacheOnDisk(true)
                        .cacheInMemory(true)
                        .showImageOnLoading(R.mipmap.ic_launcher)
                        .showImageOnFail(R.mipmap.ic_launcher)
                        .showImageForEmptyUri(R.mipmap.ic_launcher)
                        .build())
                .build());
    }
}
