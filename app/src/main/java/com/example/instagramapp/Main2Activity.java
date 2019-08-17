package com.example.instagramapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private final String TAG = "Main2Activity";
   PostAdapter adapter;
    private RecyclerView recyclerView;
    private List<Post> post;
    FloatingActionButton btnCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.rvPosts);
        post = new ArrayList<>();
        adapter = new PostAdapter(this,post);
        recyclerView.setAdapter(adapter);
        getSupportActionBar().setTitle("Instagram");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnCompose = (FloatingActionButton) findViewById(R.id.btn_Compose);
        btnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        queryPost();
    }
    private void queryPost() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e!= null){
                    Log.e(TAG,"Error with query");
                    e.printStackTrace();
                    return;
                }
                post.addAll(objects);
                adapter.notifyDataSetChanged();
                for (int i = 0; i < objects.size(); i++){
                    Post post = objects.get(i);
                    Log.d(TAG,"Post: " + post.getDescription() + "username: " +post.getUser().getUsername());
                }

            }
        });
    }
}
