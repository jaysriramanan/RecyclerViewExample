package com.example.test.recyclerviewexample.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.recyclerviewexample.Activity2;
import com.example.test.recyclerviewexample.DisplayView;
import com.example.test.recyclerviewexample.R;
import java.io.InputStream;

public class Rv2Adapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    String[] states2,cities2,images;

    public Rv2Adapter(Context context,String[] states2,String[] cities2,String[] images) {
        this.context = context;
        this.states2=states2;
        this.cities2=cities2;
        this.images=images;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.recycler_item_2,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Holder holder=(Holder)viewHolder;
        Bitmap bitmap= Activity2.getBitmapFromCache(images[i]);
        if(bitmap!=null){
            holder.image.setImageBitmap(resizeImage(bitmap));
        }
        else {
            new GetImageFromUrl(holder.image).execute(images[i]);
        }

        holder.state.setText("abcduyfgwjihgehjoun'oikfvbxhsiuefrgt");
        holder.city.setText("asdfghjklou");

        ConstraintSet constraintSet=new ConstraintSet();
        constraintSet.clone(holder.constraintLayout);
        if(i%2==0){
            constraintSet.connect(holder.image.getId(),ConstraintSet.START,holder.constraintLayout.getId(),ConstraintSet.START);
            constraintSet.connect(holder.image.getId(),ConstraintSet.TOP,holder.constraintLayout.getId(),ConstraintSet.TOP);
            constraintSet.connect(holder.state.getId(),ConstraintSet.START,holder.image.getId(),ConstraintSet.END);
            constraintSet.connect(holder.state.getId(),ConstraintSet.TOP,holder.constraintLayout.getId(),ConstraintSet.TOP);
            constraintSet.connect(holder.state.getId(),ConstraintSet.END,holder.constraintLayout.getId(),ConstraintSet.END);
            constraintSet.connect(holder.city.getId(),ConstraintSet.START,holder.image.getId(),ConstraintSet.END);
            constraintSet.connect(holder.city.getId(),ConstraintSet.TOP,holder.state.getId(),ConstraintSet.BOTTOM);
            constraintSet.connect(holder.city.getId(),ConstraintSet.END,holder.constraintLayout.getId(),ConstraintSet.END);
        }
        else{
            constraintSet.connect(holder.image.getId(),ConstraintSet.TOP,holder.constraintLayout.getId(),ConstraintSet.TOP);
            constraintSet.connect(holder.image.getId(),ConstraintSet.END,holder.constraintLayout.getId(),ConstraintSet.END);
            constraintSet.connect(holder.state.getId(),ConstraintSet.START,holder.constraintLayout.getId(),ConstraintSet.START);
            constraintSet.connect(holder.state.getId(),ConstraintSet.TOP,holder.constraintLayout.getId(),ConstraintSet.TOP);
            constraintSet.connect(holder.state.getId(),ConstraintSet.END,holder.image.getId(),ConstraintSet.START);
            constraintSet.connect(holder.city.getId(),ConstraintSet.START,holder.constraintLayout.getId(),ConstraintSet.START);
            constraintSet.connect(holder.city.getId(),ConstraintSet.TOP,holder.state.getId(),ConstraintSet.BOTTOM);
            constraintSet.connect(holder.city.getId(),ConstraintSet.END,holder.image.getId(),ConstraintSet.START);
        }
        constraintSet.applyTo(holder.constraintLayout);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),DisplayView.class);
                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context,holder.image, ViewCompat.getTransitionName(holder.image));
                intent.putExtra("position",holder.getAdapterPosition());
                view.getContext().startActivity(intent,optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return states2.length;
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView state,city;
        ImageView image;
        ConstraintLayout constraintLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.recyclerItem2);
            state=new TextView(context);
            city=new TextView(context);
            image=new ImageView(context);
            image.setTransitionName("trial_transition");
            ConstraintLayout.LayoutParams params=new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            state.setLayoutParams(params);
            state.setId(View.generateViewId());
            constraintLayout.addView(state);

            city.setLayoutParams(params);
            city.setId(View.generateViewId());
            constraintLayout.addView(city);

            params=new ConstraintLayout.LayoutParams(200,200);
            image.setLayoutParams(params);
            image.setId(View.generateViewId());
            constraintLayout.addView(image);
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
            Activity2.setBitmapToCache(urlDisplay,bmp);
            return resizeImage(bmp);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img.setImageBitmap(bitmap);
        }
    }

    public Bitmap resizeImage(Bitmap bitmap){
        /*int originalWidth=bitmap.getWidth();
        int originalHeight=bitmap.getHeight();

        int desiredWidth=200;*/
        Bitmap bmp=Bitmap.createScaledBitmap(bitmap,200,200,false);
        return bmp;
    }
}
