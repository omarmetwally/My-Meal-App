package com.omarInc.mymeal.mealdetails.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.db.MealRepositoryImpl;
import com.omarInc.mymeal.mealdetails.presenter.MealDetailsPresenter;
import com.omarInc.mymeal.mealdetails.presenter.MealDetailsPresenterImpl;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.network.MealRemoteDataSourceImpl;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;


public class MealDetailsFragment extends Fragment implements MealDetailView {

    ProgressBar progressBar;
    private ImageView foodCoverImg,btnBackImage,recipeYouTube,detailFav;
    private YouTubePlayerView youTubePlayerView ;
    private TextView foodCategoryTxt, foodAreaTxt, foodTitleTxt, foodDescTxt;
    private RecyclerView ingredientsRecyclerView;
    private MealDetailsPresenter presenter;
    private  Ingredients_MeasuesAdapter ingredientsMeasuesAdapter;
    private boolean isOffline;


    public MealDetailsFragment() {
        // Required empty public constructor
    }


    public static MealDetailsFragment newInstance() {
        MealDetailsFragment fragment = new MealDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar=view.findViewById(R.id.detailLoading);
        foodCoverImg = view.findViewById(R.id.recipeCoverImg);
        foodCategoryTxt = view.findViewById(R.id.recipeCategoryTxt);
        foodAreaTxt = view.findViewById(R.id.recipeAreaTxt);
        foodTitleTxt = view.findViewById(R.id.recipeTitleTxt);
        foodDescTxt = view.findViewById(R.id.recipeDescTxt);
        btnBackImage=view.findViewById(R.id.detailBack);
        recipeYouTube=view.findViewById(R.id.recipeYouTube);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        detailFav=view.findViewById(R.id.detailFav);

        ingredientsRecyclerView=view.findViewById(R.id.ingredientRecyclerView);
        ingredientsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager categoriesLayoutManager = new LinearLayoutManager(getActivity());
        categoriesLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ingredientsRecyclerView.setLayoutManager(categoriesLayoutManager);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_scale_in);
        ingredientsRecyclerView.setLayoutAnimation(animation);

        ingredientsMeasuesAdapter = new Ingredients_MeasuesAdapter(getContext(), new ArrayList<>());
        ingredientsRecyclerView.setAdapter(ingredientsMeasuesAdapter);

        MealRepository repository = MealRepositoryImpl.getInstance(getContext());

        presenter = new MealDetailsPresenterImpl(this, MealRemoteDataSourceImpl.getInstance(),repository);
        if (getArguments() != null) {
            String mealID = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealID();
             isOffline = MealDetailsFragmentArgs.fromBundle(getArguments()).getIsOffline();

            presenter.checkIfFavorite(mealID);
            presenter.fetchMealDetails(mealID,isOffline);

        }
    }

    @Override
    public void showMealDetails(MealDetail mealDetail) {

        progressBar.setVisibility(View.GONE);
        Glide.with(this).load(mealDetail.getStrMealThumb()).into(foodCoverImg);
        foodCategoryTxt.setText(mealDetail.getStrCategory());
        foodAreaTxt.setText(mealDetail.getStrArea());
        foodTitleTxt.setText(mealDetail.getStrMeal());
        foodDescTxt.setText(mealDetail.getStrInstructions());

        btnBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        detailFav.setOnClickListener(v -> {
            // Assuming 'mealDetail' is the instance of MealDetail you want to save.
//            MealRepositoryImpl.getInstance(getContext()).insert(mealDetail);
            presenter.onFavoriteClicked(mealDetail);
//            detailFav.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
        });


        ingredientsMeasuesAdapter.setIngredientsAndMeasures(mealDetail.getNonEmptyIngredientsAndMeasures());

        if(mealDetail != null ) {
            ingredientsRecyclerView.scheduleLayoutAnimation(); // Trigger animation after setting data
        }
        recipeYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openYouTubeVideo(mealDetail.getStrYoutube());
            }
        });

        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.cueVideo(extractYouTubeVideoId(mealDetail.getStrYoutube()), 0);
            }
        });
    }

    @Override
    public void showError(String errorMessage) {


    }

    @Override
    public void setFavoriteStatus(boolean isFavorite) {

        if(isFavorite) {
            detailFav.setColorFilter(ContextCompat.getColor(getContext(), R.color.red));
        } else {
            detailFav.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
        }
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return getViewLifecycleOwner();
    }

    private void openYouTubeVideo(String url) {
        if (url != null && !url.isEmpty()) {
            String videoId = extractYouTubeVideoId(url);

            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoId));

            try {
                startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                startActivity(webIntent);
            }
        } else {
            Toast.makeText(getContext(), "YouTube video link is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private String extractYouTubeVideoId(String url) {
        // Assuming the URL is in the format "http://www.youtube.com/watch?v=VIDEO_ID"
        Uri uri = Uri.parse(url);
        return uri.getQueryParameter("v");
    }
}