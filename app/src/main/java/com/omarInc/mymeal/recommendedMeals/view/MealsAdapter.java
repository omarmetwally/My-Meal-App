package com.omarInc.mymeal.recommendedMeals.view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.model.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    private List<Meal> meals;
    private Context context;

    public MealsAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.txtCategoryName.setText(meal.getStrMeal());
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.shimmerViewContainer.hideShimmer();
                        holder.shimmerViewContainer.stopShimmer();
                        holder.shimmerViewContainer.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.shimmerViewContainer.hideShimmer();
                        holder.shimmerViewContainer.stopShimmer();
                        holder.shimmerViewContainer.setVisibility(View.GONE);
                        return false;
                    }
                })
//                .placeholder(R.layout.shimmer_placeholder)
                .error(R.drawable.image_not_found_icon).into(holder.mealImage);

    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView txtCategoryName;
        ShimmerFrameLayout shimmerViewContainer;


        public ViewHolder(View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.recipe_title);
            mealImage=itemView.findViewById(R.id.recipe_thumbnail);
            shimmerViewContainer=itemView.findViewById(R.id.shimmer_view_container);
        }
    }
}