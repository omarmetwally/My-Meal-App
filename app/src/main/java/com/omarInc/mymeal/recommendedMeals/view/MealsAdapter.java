package com.omarInc.mymeal.recommendedMeals.view;


import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
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
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.model.Meal;
import com.omarInc.mymeal.model.OnMealClickListener;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    private List<Meal> meals;
    private Context context;
    private OnMealClickListener listener;

    public MealsAdapter(Context context, List<Meal> meals,OnMealClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (meals == null || meals.isEmpty()) {
            // Handle the case where meals are not available
            return; // Exit the method to prevent further execution and avoid the divide by zero error
        }


        Meal meal = meals.get(position);

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);

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
                .error(R.drawable.image_not_found_icon).into(holder.kenBurnsView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMealClick(meals.get(position).getIdMeal());
            }
        });

    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;

    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView txtCategoryName;
        ShimmerFrameLayout shimmerViewContainer;
        KenBurnsView kenBurnsView;
        // Duration for the transition in milliseconds
        long duration = 2000; // 10 seconds

        // Using an AccelerateDecelerateInterpolator for smooth acceleration and deceleration
        Interpolator interpolator = new AccelerateDecelerateInterpolator();

        // Creating the generator with specified duration and interpolator
        RandomTransitionGenerator generator = new RandomTransitionGenerator(duration, interpolator);

// Applying the generator to the KenBurnsView




        public ViewHolder(View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.recipe_title);
            mealImage=itemView.findViewById(R.id.recipe_thumbnail);
            shimmerViewContainer=itemView.findViewById(R.id.shimmer_view_container);
            kenBurnsView=itemView.findViewById(R.id.kbvLocation);
            kenBurnsView.setTransitionGenerator(generator);
        }
    }
}