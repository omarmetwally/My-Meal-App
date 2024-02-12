package com.omarInc.mymeal.categories.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.omarInc.mymeal.model.Category;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.recommendedMeals.view.MealsAdapter;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<Category> categories;
    private LayoutInflater inflater;
    private  final Context context;



    public CategoriesAdapter(Context context, List<Category> categories) {
        this.context=context;
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);

        holder.txtCategoryName.setText(categories.get(position).getStrCategory());
        holder.shimmerViewContainer.startShimmer();
        holder.shimmerViewContainer.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(categories.get(position).getStrCategoryThumb())
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
                .error(R.drawable.image_not_found_icon).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName;
        ImageView imageView;
        ShimmerFrameLayout shimmerViewContainer;


        // Other views...

        public ViewHolder(View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtMealName);
            imageView=itemView.findViewById(R.id.itemImage);
            shimmerViewContainer=itemView.findViewById(R.id.shimmer_view_container);

            // Initialize other views...
        }
    }
}
