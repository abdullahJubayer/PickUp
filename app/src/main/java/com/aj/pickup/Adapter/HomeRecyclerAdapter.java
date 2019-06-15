package com.aj.pickup.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aj.pickup.Model.PhotoList.PhotoListModel;
import com.aj.pickup.Model.SearchPhotoList.Result;
import com.aj.pickup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private static final int Model1=1;
    private static final int Model2=2;
    private ArrayList<Object> list;
    private MyClickListner myClickListner;

    public HomeRecyclerAdapter(Context context, ArrayList<Object> list, MyClickListner myClickListner) {
        this.context = context;
        this.list=list;
        this.myClickListner = myClickListner;
    }


    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof PhotoListModel){
            return Model1;
        }
        if (list.get(position) instanceof Result){
            return Model2;
        }

        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        switch (i){
            case Model1:
                 v= LayoutInflater.from(context).inflate(R.layout.home_image,viewGroup,false);
                return new MyViewHolder(v, myClickListner);
            case Model2:
                 v= LayoutInflater.from(context).inflate(R.layout.search_image_design,viewGroup,false);
                return new MyViewHolder(v, myClickListner);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder= (MyViewHolder) viewHolder;
        switch (viewHolder.getItemViewType()){
            case Model1:
                    PhotoListModel listModel= (PhotoListModel) list.get(i);
                    Picasso.get().load(listModel.getUrls().getRegular()).into(myViewHolder.imageView);

                break;

            case Model2:
                    Result resultList= (Result) list.get(i);
                    Picasso.get().load(resultList.getUrls().getRegular()).into(myViewHolder.imageView);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         ImageView imageView;
         TextView love_txt;
         LinearLayout download_id,shre_id;
        private MyClickListner myClickListner;
        public MyViewHolder(@NonNull View itemView,MyClickListner myClickListner) {
            super(itemView);

            love_txt=itemView.findViewById(R.id.love_count_id);
            download_id=itemView.findViewById(R.id.layout_download);
            shre_id=itemView.findViewById(R.id.layout_share);
            imageView=itemView.findViewById(R.id.photo_id);
            this.myClickListner=myClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListner.onClick(getAdapterPosition());
        }
    }

    public interface MyClickListner{
        void onClick(int position);
    }
}
