package com.omarInc.mymeal.area.view;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.area.model.Area;

import java.util.List;


public class AreasAdapter extends RecyclerView.Adapter<AreasAdapter.ViewHolder> {
    private List<Area> areas;
    private LayoutInflater inflater;
    private OnAreaClickListener listener;
    private static final String TAG = "AreasAdapter";

    public AreasAdapter(Context context, List<Area> areas ,OnAreaClickListener listener) {
        this.areas = areas;
        this.inflater = LayoutInflater.from(context);
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.area_chip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);

        Area area = areas.get(position);
        holder.chip.setText(area.getStrArea());
        Log.i(TAG, "qqqqqqqqqqqqqqqqqqqq: ana braaa bbdos");

        holder.chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAreaClick(area.getStrArea());
            }
        });

//        holder.itemView.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onAreaClick(area.getStrArea());
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return areas.size();
    }
    public void setAreas(List<Area> areas) {
        this.areas = areas;
        notifyDataSetChanged();
    }
    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.area_chip);
        }
    }
}
