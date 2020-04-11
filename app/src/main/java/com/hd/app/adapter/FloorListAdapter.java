package com.hd.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hd.app.R;

import java.util.List;

import module.Floor;

import static com.hd.app.navigation.MainActivity.floorImg;

public class FloorListAdapter extends RecyclerView.Adapter<FloorListAdapter.ViewHolder> {
    private List<Floor> mFloorList;
    View oldView = null;
    RecyclerView re;
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView floorIdText;
        public ViewHolder(View view)
        {
            super(view);
            floorIdText = (TextView)view.findViewById(R.id.floor_item_id);
        }
    }

    public FloorListAdapter(List<Floor>floorIDList)
    {
        mFloorList = floorIDList;
    }

    public List<Floor> getmFloorList() {
        return mFloorList;
    }

    public void setmFloorList(List<Floor> mFloorList) {
        this.mFloorList = mFloorList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.floor_item,parent,false);
         final ViewHolder holder = new ViewHolder(view);
         holder.floorIdText.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(oldView!=null){

                     oldView.setBackgroundResource(0);

                 }
                 oldView = v;
                 v.setBackgroundResource(R.drawable.floor_item_bg1);
                 int position = holder.getAdapterPosition();
                 Floor floor = mFloorList.get(position);
                 Log.d("testFloor", floor.getFloor());
                 //处理点击事件
//                 LayoutInflater factory = LayoutInflater.from(parent.getContext());
//                 View layout = factory.inflate(R.layout.activity_main,parent, false);
//                 PhotoView floorImg = (PhotoView)layout.findViewById(R.id.floor_img);
//                 floorImg.enable();
                 floorImg.setImageResource(floor.getFloorImgId());//把楼层图片放上去
             }
         });
         return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        Floor floor = mFloorList.get(position);
        holder.floorIdText.setText(floor.getFloor());
    }
    @Override
    public int getItemCount()
    {
        return mFloorList.size();
    }



}
