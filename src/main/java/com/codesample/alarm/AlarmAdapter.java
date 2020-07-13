package com.codesample.alarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>{
    public interface OnListItemClickListener{
        public void onListItemClick(Alarm a);
        public void onListItemLongClick(Alarm a);
    }

    private List<Alarm> data;
    private OnListItemClickListener listener;

    public AlarmAdapter(OnListItemClickListener listener){
        this.listener = listener;
    }

    public void updateData(List<Alarm> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = data.get(position);

        holder.textViewMission.setText(alarm.mission);
        holder.textViewTime.setText(alarm.time);
        holder.itemView.setOnClickListener(v->{
            listener.onListItemClick(alarm);
        });
        holder.itemView.setOnLongClickListener(v -> {
            listener.onListItemLongClick(alarm);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return (data==null)? 0:data.size();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder{
        TextView textViewMission;
        TextView textViewTime;
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMission = itemView.findViewById(R.id.textViewMission);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }
}
