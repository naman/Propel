package com.propel.bluemix.propel.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.propel.bluemix.propel.Adapters.PostsAdapter;
import com.propel.bluemix.propel.BlueListApplication;
import com.propel.bluemix.propel.CommentActivity;
import com.propel.bluemix.propel.Data.Item;
import com.propel.bluemix.propel.Database.DbSingleton;
import com.propel.bluemix.propel.PostActivity;
import com.propel.bluemix.propel.R;
import com.propel.bluemix.propel.Utils.RecyclerItemClickListener;

import java.util.List;

public class ViewFragment extends Fragment {
    RecyclerView recyclerView;
    PostsAdapter postsAdapter;
    DbSingleton dbSingleton = DbSingleton.getInstance();
    BlueListApplication blueListApplication;
    List<Item> posts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //IBMData dataService = IBMData.initializeService();  //Initializing object storage capability

        //  Item.registerSpecialization(Item.class);  //Registering a specialization

//        blueListApplication = (BlueListApplication) getActivity().getApplication();
//        blueListApplication.getItemList();

        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_posts);


        List<Item> posts = dbSingleton.getItemList();

        postsAdapter = new PostsAdapter(posts);
        recyclerView.setAdapter(postsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String title = ((TextView) view.findViewById(R.id.goal_title)).getText().toString();
                        Intent intent = new Intent(getActivity(), CommentActivity.class);
                        intent.putExtra("TRACK", title);
                        startActivity(intent);
                    }
                })
        );
        FloatingActionButton fab_add_post = (FloatingActionButton) view.findViewById(R.id.fab_add_post);
        fab_add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(view.getContext(), "FAB pressed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }


}
