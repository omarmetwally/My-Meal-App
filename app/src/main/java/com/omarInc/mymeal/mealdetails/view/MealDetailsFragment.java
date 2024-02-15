package com.omarInc.mymeal.mealdetails.view;

import android.app.DatePickerDialog;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.omarInc.mymeal.R;
import com.omarInc.mymeal.db.MealRepository;
import com.omarInc.mymeal.db.MealRepositoryImpl;
import com.omarInc.mymeal.mealdetails.presenter.MealDetailsPresenter;
import com.omarInc.mymeal.mealdetails.presenter.MealDetailsPresenterImpl;
import com.omarInc.mymeal.model.MealDetail;
import com.omarInc.mymeal.network.MealRemoteDataSourceImpl;
import com.omarInc.mymeal.plan.model.ScheduledMeal;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;


public class MealDetailsFragment extends Fragment implements MealDetailView {

    private static final String TAG = "MealDetailsFragment";
    ProgressBar progressBar;
    private ImageView foodCoverImg,btnBackImage,recipeYouTube,detailFav,scroll_Image,btnCalendar;
    private YouTubePlayerView youTubePlayerView ;
    private TextView foodCategoryTxt, foodAreaTxt, foodTitleTxt, foodDescTxt;
    private RecyclerView ingredientsRecyclerView;
    private MealDetailsPresenter presenter;
    private  Ingredients_MeasuesAdapter ingredientsMeasuesAdapter;
    private boolean isOffline,scrollState=false;
    private ScrollView scrollView;


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
        scroll_Image=view.findViewById(R.id.scroll_Image);
        scrollView=view.findViewById(R.id.scrollView);
        btnCalendar=view.findViewById(R.id.btnCalendar);


        scroll_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                       if(!scrollState){
                           scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                           scroll_Image.setImageResource(R.drawable.up_arrow);
                           scrollState=true;
                       }
                       else {
                           scrollView.fullScroll(ScrollView.FOCUS_UP);
                           scroll_Image.setImageResource(R.drawable.down_arrow);
                           scrollState=false;
                       }
                    }
                });

            }
        });


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

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        (views, year, month, dayOfMonth) -> {
                            Calendar selectedDate = Calendar.getInstance();
                            selectedDate.set(year, month, dayOfMonth);
                            // Schedule the meal with selectedDate

                            presenter.scheduleMealForDate(mealDetail, selectedDate);
                        },
                        now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btnBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        detailFav.setOnClickListener(v -> {presenter.onFavoriteClicked(mealDetail);});


        ingredientsMeasuesAdapter.setIngredientsAndMeasures(mealDetail.getNonEmptyIngredientsAndMeasures());

        if(mealDetail != null ) {
            ingredientsRecyclerView.scheduleLayoutAnimation();
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

                if (mealDetail.getStrYoutube() != null && !mealDetail.getStrYoutube().isEmpty())
                    youTubePlayer.cueVideo(extractYouTubeVideoId(mealDetail.getStrYoutube()), 0);
                else
                    youTubePlayerView.setVisibility(View.GONE);
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
            Log.i(TAG, "openYouTubeVideo: "+url);
            String videoId = extractYouTubeVideoId(url);

            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoId));

            try {
                startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                startActivity(webIntent);
            }
        } else {
           Snackbar.make(getView(),R.string.Video_Not_Available , BaseTransientBottomBar.LENGTH_LONG).show();

        }


    }

    private String extractYouTubeVideoId(String url) {
        // Assuming the URL is in the format "http://www.youtube.com/watch?v=VIDEO_ID"
        Uri uri = Uri.parse(url);
        return uri.getQueryParameter("v");
    }
}