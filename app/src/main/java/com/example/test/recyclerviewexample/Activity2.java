package com.example.test.recyclerviewexample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.LruCache;
import android.view.View;

import com.example.test.recyclerviewexample.Adapters.Rv2Adapter;


public class Activity2 extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private String[] states2,cities2,images;
    private static LruCache<String,Bitmap> memCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        final int maxMemorySize=(int)Runtime.getRuntime().maxMemory()/1024;
        final int cacheSize=maxMemorySize/10;
        memCache=new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };

        recyclerView2=findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        states2=getResources().getStringArray(R.array.keys);
        cities2=getResources().getStringArray(R.array.values);
        images=getResources().getStringArray(R.array.city_images);

        Rv2Adapter rv2Adapter=new Rv2Adapter(this,states2,cities2,images);
        recyclerView2.setAdapter(rv2Adapter);

    }

    public static Bitmap getBitmapFromCache(String key){
        return  memCache.get(key);
    }

    public static void setBitmapToCache(String key,Bitmap bitmap){
        if(getBitmapFromCache(key)==null){
            memCache.put(key,bitmap);
        }
    }
}
