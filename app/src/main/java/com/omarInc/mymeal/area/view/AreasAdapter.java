package com.omarInc.mymeal.area.view;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.chip.Chip;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.area.model.Area;

import java.util.List;


public class AreasAdapter extends RecyclerView.Adapter<AreasAdapter.ViewHolder> {
    private List<Area> areas;
    private LayoutInflater inflater;

    public AreasAdapter(Context context, List<Area> areas) {
        this.areas = areas;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.area_chip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Area area = areas.get(position);
        holder.chip.setText(area.getStrArea());
        // Set any listeners or additional settings for the chip here
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }
    public void setAreas(List<Area> areas) {
        this.areas = areas;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.area_chip);
        }
    }
}
