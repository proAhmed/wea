package com.wearable.abobakr.myapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wearable.abobakr.myapplication.R;
import com.wearable.abobakr.myapplication.controller.OnUserClick;
import com.wearable.abobakr.myapplication.model.UserData;

import java.util.ArrayList;


/**
 * Created by ahmed on 7/19/2016.
 */
public class UserAdapter extends RecyclerView
        .Adapter<UserAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<UserData> mDataset;
     Activity context;
    OnUserClick onUserClick;
     public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView userName,tvDepartment,tvVaiolate,tvLate;
        Button btnAccept,btnReject;
        public DataObjectHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgUser);
            userName = (TextView) itemView.findViewById(R.id.userName);
            tvDepartment = (TextView) itemView.findViewById(R.id.tvDepartment);
            tvVaiolate = (TextView) itemView.findViewById(R.id.tvVaiolate);
            tvLate = (TextView) itemView.findViewById(R.id.tvLate);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            btnReject = (Button) itemView.findViewById(R.id.btnReject);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public UserAdapter(Activity context,ArrayList<UserData> myDataset,OnUserClick onUserClick) {
        this.mDataset = myDataset;
        this.context = context;
        this.onUserClick = onUserClick;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
       //     holder.img
        UserData userData = mDataset.get(position);

        holder.userName.setText(userData.getUserName());
        holder.tvDepartment.setText(userData.getDepartment());
        holder.tvVaiolate.setText(userData.getViolation());
        holder.tvLate.setText(userData.getLate());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUserClick.accept(position);
                removeAt(position);

            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUserClick.reject(position);
                removeAt(position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public void removeAt(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataset.size());
    }

}
