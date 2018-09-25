package com.example.test.recyclerviewexample;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.test.recyclerviewexample.Adapters.RvAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String[] states;
    private RvAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        states=getResources().getStringArray(R.array.keys);

        adapter=new RvAdapter(this,states);
        recyclerView.setAdapter(adapter);

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Activity2.class);
                startActivity(intent);
            }
        });
    }
}
