package com.propel.bluemix.propel.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.propel.bluemix.propel.Data.Comment;
import com.propel.bluemix.propel.R;

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholder> {

    List<Comment> comments;

    public CommentAdapter(List<Comment> items) {
        this.comments = items;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_items_comments, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder viewholder, int i) {
        Comment current = comments.get(i);
        Log.d("comment adapter size", comments.size() + "");
        viewholder.text.setText(current.getComment());
       // viewholder.likes.setText(current.getLikes());
        viewholder.date.setText(current.getDateofPublish());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView text;
        TextView likes;
        TextView date;

        public Viewholder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            text = (TextView) itemView.findViewById(R.id.comment_title);
            //likes = (TextView) itemView.findViewById(R.id.comm);
            date = (TextView) itemView.findViewById(R.id.comment_date);
        }

    }
}
