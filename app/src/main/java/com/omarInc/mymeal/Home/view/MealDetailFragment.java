//package com.omarInc.mymeal.Home.view;
//
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.DataSource;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
//import com.facebook.shimmer.ShimmerFrameLayout;
//import com.omarInc.mymeal.R;
//
//public class MealDetailFragment extends Fragment {
//    private static final String ARG_MEAL_IMAGE = "meal_image";
//    private static final String ARG_MEAL_TITLE = "meal_title";
//
//    public static MealDetailFragment newInstance(String mealImage, String mealTitle) {
//        MealDetailFragment fragment = new MealDetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_MEAL_IMAGE, mealImage);
//        args.putString(ARG_MEAL_TITLE, mealTitle);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.stack_meal_item, container, false);
//        ImageView imageView = view.findViewById(R.id.recipe_thumbnail);
//        TextView titleView = view.findViewById(R.id.recipe_title);
//        ShimmerFrameLayout shimmerViewContainer=view.findViewById(R.id.shimmer_view_container);
//
//        if (getArguments() != null) {
//            String mealImage = getArguments().getString(ARG_MEAL_IMAGE);
//            String mealTitle = getArguments().getString(ARG_MEAL_TITLE);
//            titleView.setText(mealTitle);
//            Glide.with(this).load(mealImage).into(imageView);
//            Glide.with(this)
//                    .load(mealImage)
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                           shimmerViewContainer.hideShimmer();
//                           shimmerViewContainer.stopShimmer();
//                           shimmerViewContainer.setVisibility(View.GONE);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                          shimmerViewContainer.hideShimmer();
//                        shimmerViewContainer.stopShimmer();
//                        shimmerViewContainer.setVisibility(View.GONE);
//                            return false;
//                        }
//                    })
////                .placeholder(R.layout.shimmer_placeholder)
//                    .error(R.drawable.image_not_found_icon).into(imageView);
//        }
//        return view;
//    }
//}
