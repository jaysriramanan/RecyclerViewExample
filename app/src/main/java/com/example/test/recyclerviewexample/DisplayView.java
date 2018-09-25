package com.example.test.recyclerviewexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.InputStream;

public class DisplayView extends AppCompatActivity {
    private TextView stateName,capitalCity;
    private ImageView cityImage;
    private String[] states,capitals,images;
    private static LruCache<String,Bitmap> memoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_view);

        final int maxMemorySize=(int)Runtime.getRuntime().maxMemory()/1024;
        final int cacheSize=maxMemorySize/10;
        memoryCache=new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };

        stateName=findViewById(R.id.stateName);
        capitalCity=findViewById(R.id.capitalCity);
        cityImage=findViewById(R.id.cityImage);

        states=getResources().getStringArray(R.array.keys);
        capitals=getResources().getStringArray(R.array.values);
        images=getResources().getStringArray(R.array.city_images);

        int pos=getIntent().getIntExtra("position",0);

        stateName.setText(states[pos]);
        capitalCity.setText("The capital city of "+states[pos]+" is "+capitals[pos]);
        Bitmap bitmap=getBitmapFromCache(images[pos]);
        if(bitmap!=null){
            cityImage.setImageBitmap(bitmap);
        }
        else {
            new GetImageFromUrl(cityImage).execute(images[pos]);
        }

    }

    public class GetImageFromUrl extends AsyncTask<String,Void,Bitmap> {
        ImageView img;

        public GetImageFromUrl(ImageView img) {
            this.img = img;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            setBitmapToCache(urlDisplay,bmp);
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img.setImageBitmap(bitmap);
        }
    }

    public static Bitmap getBitmapFromCache(String key){
        return  memoryCache.get(key);
    }

    public static void setBitmapToCache(String key,Bitmap bitmap){
        if(getBitmapFromCache(key)==null){
            memoryCache.put(key,bitmap);
        }
    }

}
