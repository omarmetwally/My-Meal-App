//package com.omarInc.mymeal.Home.view;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.omarInc.mymeal.R;
//import com.omarInc.mymeal.model.Meal;
//import java.util.List;
//
//public class LatestMealsAdapter extends RecyclerView.Adapter<LatestMealsAdapter.ViewHolder> {
//
//    private List<Meal> mealList;
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//    private  final Context context;
//
//    // Data is passed into the constructor
//    LatestMealsAdapter(Context context, List<Meal> data) {
//
//        this.context = context;
//        this.mInflater = LayoutInflater.from(context);
//        this.mealList = data;
//    }
//
//    // Inflates the row layout from xml when needed
//    @Override
//    @NonNull
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.meal_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // Binds the data to the `TextView` in each row
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Meal meal = mealList.get(position);
//        holder.txtMealName.setText(meal.getStrMeal());
//        Glide.with(context)
//                .load(meal.getStrMealThumb())
//                .into(holder.imageView);
//    }
//
//    // Total number of rows
//    @Override
//    public int getItemCount() {
//        return mealList.size();
//    }
//
//    // Stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        ImageView imageView;
//        TextView txtMealName;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.itemImage);
//            txtMealName = itemView.findViewById(R.id.txtMealName);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
//    public void setMeals(List<Meal> meals) {
//        this.mealList = meals;
//    }
//
//    // Convenience method for getting data at click position
//    Meal getItem(int id) {
//        return mealList.get(id);
//    }
//
//    // Allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // Parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//}
