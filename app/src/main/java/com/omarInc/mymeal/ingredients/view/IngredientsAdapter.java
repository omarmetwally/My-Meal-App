package com.omarInc.mymeal.ingredients.view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.omarInc.mymeal.categories.view.CategoriesAdapter;
import com.omarInc.mymeal.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private final LayoutInflater inflater;
    private final Context context;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ingredients_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);

        Ingredient ingredient = ingredients.get(position);
        holder.txtIngredientName.setText(ingredient.getStrIngredient());
        // Assuming Ingredient model has a method getIngredientImageUrl() to get the image URL
        holder.shimmerViewContainer.startShimmer();
        holder.shimmerViewContainer.setVisibility(View.VISIBLE);
        holder.imgIngredient.setVisibility(View.INVISIBLE);
        Glide.with(context)
                .load(ingredient.getIngredientImageUrl()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.shimmerViewContainer.hideShimmer();
                        holder.shimmerViewContainer.stopShimmer();
                        holder.shimmerViewContainer.setVisibility(View.GONE);
                        holder.imgIngredient.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.shimmerViewContainer.hideShimmer();
                        holder.shimmerViewContainer.stopShimmer();
                        holder.shimmerViewContainer.setVisibility(View.GONE);
                        holder.imgIngredient.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
//                .placeholder(R.layout.shimmer_placeholder)
                .error(R.drawable.image_not_found_icon)
                .into(holder.imgIngredient);
    }

    @Override
    public int getItemCount() {

//        return ingredients != null ? ingredients.size() : 0;
        return Math.min(ingredients.size(), 20);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIngredientName;
        ImageView imgIngredient;
        ShimmerFrameLayout shimmerViewContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            txtIngredientName = itemView.findViewById(R.id.txtMealName);
            imgIngredient = itemView.findViewById(R.id.itemImage);
            shimmerViewContainer=itemView.findViewById(R.id.shimmer_view_container);
        }
    }
}
