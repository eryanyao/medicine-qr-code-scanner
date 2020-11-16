package com.example.intimedicineqrscanner.record;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intimedicineqrscanner.R;
import com.example.intimedicineqrscanner.RecordActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecordCustomAdapter extends RecyclerView.Adapter<RecordViewHolder> {
    RecordActivity listData;
    List<RecordModel> modelList;
    Context context;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public RecordCustomAdapter(RecordActivity listData,
                               List<RecordModel> modelList) {
        this.listData = listData;
        this.modelList = modelList;
    }

    @NonNull @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.record_layout,parent,false);

        RecordViewHolder viewHolder = new RecordViewHolder(itemView);

        return viewHolder;
    }

    @Override public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        String status = modelList.get(position).getStatus();
        String entry = modelList.get(position).getEntry();
        if(status.equals("Danger")){
            holder.txtStatus.setTextColor(Color.RED);
        }
        else if(status.equals("Safe")){
            holder.txtStatus.setTextColor(Color.GREEN);
        }

        if(entry.equals("Yes")){
            holder.txtEntry.setTextColor(Color.GREEN);
        }
        else if(entry.equals("No")){
            holder.txtEntry.setTextColor(Color.RED);
        }
        holder.txtStatus.setText(status);
        holder.txtEntry.setText(entry);
        holder.txtTemparuture.setText( modelList.get(position).getTemperature());
        holder.txtId.setText(modelList.get(position).getStudentId());
        holder.txtDate.setText(format.format(modelList.get(position).getCheckIn().toDate()));
        holder.txtName.setText(modelList.get(position).getName());

        holder.txtNum.setText(Integer.toString(modelList.get(position).getNumOfData()));
    }

    @Override public int getItemCount() {
        return modelList.size();
    }
}
