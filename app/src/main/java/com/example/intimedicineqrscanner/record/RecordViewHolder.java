package com.example.intimedicineqrscanner.record;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.example.intimedicineqrscanner.R;

public class RecordViewHolder extends RecyclerView.ViewHolder {
    View mView;
    TextView txtId,txtStatus,txtName,txtDate,txtTemparuture,txtEntry,txtNum;

    public RecordViewHolder(@NonNull View itemView){
        super(itemView);
        mView = itemView;

        txtId = itemView.findViewById(R.id.txtRecordID);
        txtDate = itemView.findViewById(R.id.txtRecordDateC);
        txtName = itemView.findViewById(R.id.txtRecordFName);
        txtStatus = itemView.findViewById(R.id.txtRecordStatus);
        txtTemparuture = itemView.findViewById(R.id.txtRecordTemp);
        txtEntry = itemView.findViewById(R.id.txtRecordEntry);
        txtNum = itemView.findViewById(R.id.txtNumOfRecord);

    }
    private RecordViewHolder.ClickListener myClickListener;

    public void setOnClickListener(RecordViewHolder.ClickListener clickListener){
        myClickListener = clickListener;
    }

    public interface ClickListener{

    }
}