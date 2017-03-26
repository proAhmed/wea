package com.wearable.mivors.myapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wearable.mivors.myapplication.R;
import com.wearable.mivors.myapplication.connector.DownloadTask;
import com.wearable.mivors.myapplication.connector.GetImage;
import com.wearable.mivors.myapplication.connector.GetImageLink;
import com.wearable.mivors.myapplication.controller.OnLoadingComplete;
import com.wearable.mivors.myapplication.controller.OnUserClick;
import com.wearable.mivors.myapplication.controller.Utility;
import com.wearable.mivors.myapplication.model.Row;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by ahmed on 7/19/2016.
 */
public class UserAdapter extends RecyclerView
        .Adapter<UserAdapter
        .DataObjectHolder> {
    private ArrayList<Row> mDataset;
     Activity context;
    OnUserClick onUserClick;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView userName,tvDepartment,tvVaiolate,tvDepa;
        Button btnAccept,btnReject;
        LinearLayout linVolatile,linDep,linUserName,linBtn;
        public DataObjectHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgUser);
            userName = (TextView) itemView.findViewById(R.id.userName);
            tvDepartment = (TextView) itemView.findViewById(R.id.tvDepartment);
            tvDepa = (TextView) itemView.findViewById(R.id.tvDepa);
            tvVaiolate = (TextView) itemView.findViewById(R.id.tvVaiolate);
            btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
            btnReject = (Button) itemView.findViewById(R.id.btnReject);
            linVolatile = (LinearLayout) itemView.findViewById(R.id.linVolatile);
            linDep = (LinearLayout) itemView.findViewById(R.id.linDep);
            linUserName = (LinearLayout) itemView.findViewById(R.id.linUserName);
            linBtn = (LinearLayout) itemView.findViewById(R.id.linBtn);
        }
    }

    public UserAdapter(Activity context, ArrayList<Row> myDataset, OnUserClick onUserClick) {
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
        final String[] image = {""};

        Row userData = mDataset.get(position);
        final String[] obj = new String[1];

        //holder.img.setImageResource(userData.getImageLoc());

        //holder.img.setImageResource(userData.getImageLoc());
        try{
            OnLoadingComplete onLoadingCompletess = new OnLoadingComplete() {
                @Override
                public void onSuccess(Object object) {

                    image[0] = (String) object;
                    Log.d("qqq333", image[0] +"");
                    OnLoadingComplete onLoadingCompletes = new OnLoadingComplete() {
                        @Override
                        public void onSuccess(Object object) {

                            String objs = (String) object;

                            if(objs!=null) {
                                Log.d("qqqq",objs);

                                if(!objs.equals(""))
                                    try {
                                        obj[0] = objs;
                                        Log.d("qqqq", obj[0]);
                                    }catch (Exception e){

                                    }

                            }
                            OnLoadingComplete onLoadingComplete = new OnLoadingComplete() {
                                @Override
                                public void onSuccess(Object object) {
                                    String img = (String) object;
                                    if(!img.equals("")) {

                                        File file = new File("/sdcard/" + img + ".png");
                                        Picasso.with(context).load(file).into(holder.img);
                                    }
                                }

                                @Override
                                public void onFailure() {
                                    Log.d("pppnnnooo55","mmm");
                                }
                            };
                            if(obj[0] !=null) {
                                if(!obj[0].equals("")) {
                                    DownloadTask downloadTask = new DownloadTask(context, onLoadingComplete, mDataset.get(position).getLookupName());
                                    downloadTask.execute(obj[0]);
                                }
                            }
                        }

                        @Override
                        public void onFailure() {
                            Log.d("pppnnnooo66","mmm");

                        }

                    };
                    try{

                        if(!image[0].equals("")) {
                            final GetImageLink downloadTask = new GetImageLink(context, onLoadingCompletes);
                            downloadTask.execute(image[0]);
                            Log.d("pppnnnbbbooo1", image[0]);
                        }
                    }catch (Exception e){

                    }

                }

                @Override
                public void onFailure() {

                }
            };
            new GetImage(context, onLoadingCompletess, userData.getPrimaryId()).execute();

        }catch (Exception e){
            Log.d("pppnnntttee",e.toString());
            Log.d("qqqq2",e.toString());
        }

        holder.userName.setText(userData.getLookupName());
        holder.tvDepartment.setText(Utility.forma(userData.getCreated()));
        holder.tvVaiolate.setText(userData.getSubject());
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
        try {
            mDataset.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mDataset.size());
        }catch (Exception e){
            Log.d("eeeeee",e.toString());
            try {
                mDataset.remove(position-1);

                notifyItemRemoved(position-1);
                notifyItemRangeChanged(position-1, mDataset.size()-1);
            }catch (Exception ee){
                Log.d("eeeeee",ee.toString());
                mDataset.removeAll(mDataset);

            }
        }
    }

}
