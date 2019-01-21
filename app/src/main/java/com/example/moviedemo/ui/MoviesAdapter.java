package com.example.moviedemo.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviedemo.R;
import com.example.moviedemo.data.model.Movie;
import com.example.moviedemo.databinding.MovieItemBinding;

import java.util.List;

/**
 * Author: created by MarkYoung on 21/01/2019 11:42
 */
public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_ITEM =1;
    private final int TYPE_FOOT =2;

    public static final int PULL_UP_TO_LOAD = 0;
    public static final int LOADING_MORE = 1;
    public static final int NO_MORE = 2;

    private int loadState = PULL_UP_TO_LOAD;

    private List<Movie> movies;

    public MoviesAdapter(List<Movie> movies) {

        this.movies = movies;
    }

    public void resetData(){
        movies.clear();
    }

    public void addData(List<Movie> results) {
        if(movies.size() == 0){
            movies.addAll(results);
            notifyItemRangeChanged(0, movies.size());
        }else{
            movies.addAll(results);
            notifyItemRangeChanged(movies.size()-results.size(), movies.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == TYPE_ITEM){
            MovieItemBinding movieItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(viewGroup.getContext()), R.layout.movie_item,viewGroup,false);
            return new MovieViewHolder(movieItemBinding);
        }else if(viewType == TYPE_FOOT){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.foot_item,viewGroup,false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            movieViewHolder.movieItemBinding.setMovie(movies.get(i));
        }else if(holder instanceof FootViewHolder){
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            if(i == 0){
                footViewHolder.progressBar.setVisibility(View.GONE);
                footViewHolder.tv_line1.setVisibility(View.GONE);
                footViewHolder.tv_line2.setVisibility(View.GONE);
                footViewHolder.tv_state.setText("");
            }
            switch (loadState){
                case PULL_UP_TO_LOAD:
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    footViewHolder.tv_line1.setVisibility(View.GONE);
                    footViewHolder.tv_line2.setVisibility(View.GONE);
                    footViewHolder.tv_state.setText(R.string.pull_up_to_load_more);
                    break;

                case LOADING_MORE:
                    footViewHolder.progressBar.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line1.setVisibility(View.GONE);
                    footViewHolder.tv_line2.setVisibility(View.GONE);
                    footViewHolder.tv_state.setText(R.string.loading);
                    break;

                case NO_MORE:
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    footViewHolder.tv_line1.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line2.setVisibility(View.VISIBLE);
                    footViewHolder.tv_state.setText(R.string.no_more_data);
                    footViewHolder.tv_state.setTextColor(Color.parseColor("#FF00FF"));
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position+1 == getItemCount())
            return TYPE_FOOT;
        else
            return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return movies.size() == 0? 0 : movies.size()+1;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager)manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {
                    return getItemViewType(i) == TYPE_FOOT ?gridLayoutManager.getSpanCount() :1;
                }
            });
        }
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder{

        MovieItemBinding movieItemBinding;

        private MovieViewHolder(@NonNull MovieItemBinding binding) {
            super(binding.getRoot());
            movieItemBinding = binding;
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar progressBar;
        private TextView tv_state;
        private TextView tv_line1;
        private TextView tv_line2;


        private FootViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            tv_state = itemView.findViewById(R.id.foot_view_item_tv);
            tv_line1 = itemView.findViewById(R.id.tv_line1);
            tv_line2 = itemView.findViewById(R.id.tv_line2);
        }
    }

    public void changeLoadState(int loadState) {
        this.loadState = loadState;
        notifyItemRangeChanged(movies.size(),1);
    }
}
