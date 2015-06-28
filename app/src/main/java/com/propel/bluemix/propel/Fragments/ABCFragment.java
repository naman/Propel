package com.propel.bluemix.propel.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.propel.bluemix.propel.Database.DbSingleton;
import com.propel.bluemix.propel.R;

/**
 * Created by MananWason on 28-06-2015.
 */
public class ABCFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abc, container, false);
        final DbSingleton dbSingleton = DbSingleton.getInstance();
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("QWERTY", dbSingleton.getItemList().size() + "");
            }
        });
        return view;
    }
}
