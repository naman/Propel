package com.propel.bluemix.propel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ibm.mobile.services.data.IBMData;
import com.propel.bluemix.propel.Adapters.CommentAdapter;
import com.propel.bluemix.propel.Data.Comment;

import java.util.List;

public class CommentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    List<Comment> comments;

    @Nullable
    protected View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IBMData dataService = IBMData.initializeService();  //Initializing object storage capability
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        final View view = inflater.inflate(R.layout.activity_comment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_comments);

        //get item reference

        Comment comment = new Comment("Hello", 1, "aa");
        comments.add(comment);
        commentAdapter = new CommentAdapter(comments);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        return view;
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
