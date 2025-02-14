package com.myapplication.myvehicleinsuranceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.myvehicleinsuranceapp.R;
import com.myapplication.myvehicleinsuranceapp.model.Claim;

import java.util.List;

public class ClaimModelAdapter extends RecyclerView.Adapter<ClaimModelAdapter.ViewHolder>
{
    Context context;
    List<Claim> claimModel;
    public ClaimModelAdapter(Context context,List<Claim> claimModel)
    {
        this.context=context;
        this.claimModel=claimModel;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.recycle_row,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimModelAdapter.ViewHolder holder, int position)
    {
        Claim claim=claimModel.get(position);
        //holder.id1.setText(claim.getClaimId());
        holder.des.setText(claim.getDescription());
        holder.status.setText(claim.getStatus());
        holder.date_submission.setText(claim.getDateSubmitted());
        holder.date_update.setText(claim.getDateUpdated());

    }

    @Override
    public int getItemCount() {
        return (claimModel != null) ? claimModel.size() : 0;
    }

    public void setClaims(List<Claim> newClaims) {
        this.claimModel.clear(); // Assuming `claimList` is your dataset
        this.claimModel.addAll(newClaims);
    }

    public void clearClaims() {
        this.claimModel.clear();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView id1,des,status,date_submission,date_update;
        ViewHolder(View itemView)
        {
            super(itemView);
            //id1=itemView.findViewById(R.id.claim_id);
            des=itemView.findViewById(R.id.des);
            status=itemView.findViewById(R.id.status);
            date_submission=itemView.findViewById(R.id.date_submission);
            date_update=itemView.findViewById(R.id.date_update);

        }
    }


}
