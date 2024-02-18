package com.omarInc.mymeal.model;


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

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    private List<Meal> meals;
    private Context context;
    private OnMealClickListener listener;
    private int layoutResId;

    public MealsAdapter(Context context, List<Meal> meals,OnMealClickListener listener,int layoutResId) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
        this.layoutResId = layoutResId;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (meals == null || meals.isEmpty()) {
            return;
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

    public Meal getMealAt(int position) {
        return meals.get(position);
    }
    public void removeAt(int position) {
        if (position >= 0 && position < meals.size()) {
            meals.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void insertAt(int position, Meal meal) {
        meals.add(position, meal);
        notifyItemInserted(position);
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
        long duration = 2000;

        Interpolator interpolator = new AccelerateDecelerateInterpolator();

        RandomTransitionGenerator generator = new RandomTransitionGenerator(duration, interpolator);





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