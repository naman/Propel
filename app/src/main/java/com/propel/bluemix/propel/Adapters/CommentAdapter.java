package com.propel.bluemix.propel.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.propel.bluemix.propel.Data.Comment;
import com.propel.bluemix.propel.Data.Item;
import com.propel.bluemix.propel.R;

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholder> {

    List<Comment> comments;

    public CommentAdapter(List<Comment> items) {
        this.comments = items;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.list_items_comments, viewGroup, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder viewholder, int i) {
        Comment current = comments.get(i);
        viewholder.text.setText(current.getComment());
        viewholder.text.setText(current.getLikes());
        viewholder.text.setText(current.getOfPublish());

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
            text= (TextView) itemView.findViewById(R.id.textView);
            likes = (TextView) itemView.findViewById(R.id.textView2);
            date = (TextView) itemView.findViewById(R.id.textView3);
        }

    }
}
