package com.example.fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SensorDataAdapter extends RecyclerView.Adapter<SensorDataAdapter.ViewHolder> {

    private final List<SensorData1> sensorDataList;

    public SensorDataAdapter(List<SensorData1> sensorDataList) {
        this.sensorDataList = sensorDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savedata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SensorData1 data = sensorDataList.get(position);
        holder.lightTextView.setText("Light: " + data.getLightValue());
        holder.proximityTextView.setText("Proximity: " + data.getProximityValue());
    }

    @Override
    public int getItemCount() {
        return sensorDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lightTextView;
        TextView proximityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lightTextView = itemView.findViewById(R.id.light);
            proximityTextView = itemView.findViewById(R.id.proximity);
        }
    }
}
