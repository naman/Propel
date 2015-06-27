package com.propel.bluemix.propel.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.propel.bluemix.propel.Data.Item;
import com.propel.bluemix.propel.R;

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholder> {

    List<Item> items;

    public CommentAdapter(List<Item> items) {
        this.items = items;
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
        Item current = items.get(i);
        viewholder.text.setText(current.getName());
        viewholder.date.setText( current.getPost_date().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView text;
        TextView date;

        public Viewholder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            text= (TextView) itemView.findViewById(R.id.goal_title);
            date = (TextView) itemView.findViewById(R.id.goal_date);
        }

    }
}
