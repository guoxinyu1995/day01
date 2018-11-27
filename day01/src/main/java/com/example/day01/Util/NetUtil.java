package com.example.day01.Util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetUtil {
    private static NetUtil intance;
    private Gson gson;
    public NetUtil() {
        gson = new Gson();
    }

    public static NetUtil getIntance() {
        if(intance == null){
            intance = new NetUtil();
        }
        return intance;
    }
    public String getRequest(String urlStr){
        String result = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestMethod("GET");
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200){
                result = stream2String(urlConnection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String stream2String(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        for(String tmp = br.readLine();tmp!=null;tmp = br.readLine()){
            builder.append(tmp);
        }
        return builder.toString();
    }
    public <E> E getRequest(String urlStr,Class clazz){
        return (E) gson.fromJson(getRequest(urlStr),clazz);
    }
    public interface CallBack<E>{
        void onSuccess(E e);
    }
    @SuppressLint("StaticFieldLeak")
    public void getRequest(String urlStr, final Class clazz, final CallBack callBack){
        new AsyncTask<String,Void,Object>(){
            @Override
            protected Object doInBackground(String... strings) {
                return getRequest(strings[0],clazz);
            }

            @Override
            protected void onPostExecute(Object o) {
                callBack.onSuccess(o);
            }
        }.execute(urlStr);
    }
}
