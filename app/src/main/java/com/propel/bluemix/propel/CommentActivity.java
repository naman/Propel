package com.propel.bluemix.propel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ibm.mobile.services.data.IBMData;
import com.propel.bluemix.propel.Adapters.CommentAdapter;
import com.propel.bluemix.propel.Data.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    List<Comment> comments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IBMData dataService = IBMData.initializeService();  //Initializing object storage capability
        setContentView(R.layout.activity_comment);
        recyclerView = (RecyclerView) findViewById(R.id.list_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get item reference
        comments = new ArrayList<>();
        Comment comment = new Comment("Good start", 1, "You should go on like this. You'll succeed on day ");
        Comment comment1 = new Comment("Hard work", 2, "Hard work is the way to go");
        comments.add(comment);
        comments.add(comment1);
        commentAdapter = new CommentAdapter(comments);
        recyclerView.setAdapter(commentAdapter);
        FloatingActionButton fab_add_post = (FloatingActionButton) findViewById(R.id.fab_add_comment);
        fab_add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(view.getContext(), "FAB pressed", Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
//                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
