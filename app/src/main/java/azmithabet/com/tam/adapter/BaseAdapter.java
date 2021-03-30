package azmithabet.com.tam.adapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.MyViewHolder> {

   protected List<?> dataList = new ArrayList<>();
    Context BASE_CONTEXT;
    public View itemview;
     public BaseAdapter(Context context) {
        this.BASE_CONTEXT = context;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        onBindViewHold(position, dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public abstract void onBindViewHold(int position, Object itemView);

    protected class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
         }

    }


}