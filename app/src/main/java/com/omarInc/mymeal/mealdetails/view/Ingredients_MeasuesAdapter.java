package com.omarInc.mymeal.mealdetails.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
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
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.model.Category;

import java.util.List;

public class Ingredients_MeasuesAdapter extends RecyclerView.Adapter<Ingredients_MeasuesAdapter.ViewHolder> {

    private  List<Pair<String, String>> ingredientsAndMeasures;
    private final Context context;

    public Ingredients_MeasuesAdapter(Context context, List<Pair<String, String>> ingredientsAndMeasures) {
        this.context = context;
        this.ingredientsAndMeasures = ingredientsAndMeasures;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_ingradients_measure, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String, String> pair = ingredientsAndMeasures.get(position);

        holder.ingredientTextView.setText(pair.first);
        holder.measureTextView.setText(pair.second);


        Glide.with(context)
                .load(constructImageUrl(pair.first))
//                .placeholder(R.drawable.placeholder)
                .error(R.drawable.image_not_found_icon)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ingredientsAndMeasures.size();
    }

    public void setIngredientsAndMeasures(List<Pair<String, String>> ingredientsAndMeasures) {
        this.ingredientsAndMeasures = ingredientsAndMeasures;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView ingredientTextView;
        TextView measureTextView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ingredientImage);
            ingredientTextView = itemView.findViewById(R.id.ingredientName);
            measureTextView = itemView.findViewById(R.id.ingredientMeasure);
        }
    }

    private String constructImageUrl(String ingredient) {
        String formattedIngredient = ingredient.trim().replace(" ", "%20");
        return "https://www.themealdb.com/images/ingredients/" + formattedIngredient + "-Small.png";
    }
}
