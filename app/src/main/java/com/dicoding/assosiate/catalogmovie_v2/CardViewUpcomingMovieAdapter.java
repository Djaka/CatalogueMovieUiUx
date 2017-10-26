package com.dicoding.assosiate.catalogmovie_v2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by yaya on 2017-10-23.
 */

public class CardViewUpcomingMovieAdapter extends RecyclerView.Adapter<CardViewUpcomingMovieAdapter.CardViewViewHolder> {
    private ArrayList<Movie> listMovies;
    private Context context;


    private ArrayList<Movie> getListMovies(){
        return listMovies;
    }

    @Override
    public CardViewUpcomingMovieAdapter.CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_upcoming_movies, parent, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewUpcomingMovieAdapter.CardViewViewHolder holder, int position) {
        String url_img = "https://image.tmdb.org/t/p/original";

        Movie m = getListMovies().get(position);
        Glide.with(context)
                .load(url_img + m.getPoster_path())
                .override(350,550)
                .into(holder.imgPhoto);
        holder.tvTitle.setText(m.getTitle());
        holder.tvDescription.setText(m.getOverview());
        holder.tvReleaseDate.setText(m.getRelease_date());

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                Movie m = getListMovies().get(position);

                Intent intent = new Intent(context,DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_ID,m.getId());
                intent.putExtra(DetailMovieActivity.EXTRA_TITLE, m.getTitle());
                intent.putExtra(DetailMovieActivity.EXTRA_RELEASE, m.getRelease_date());
                intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW,m.getOverview_detail());
                intent.putExtra(DetailMovieActivity.EXTRA_POSTER, m.getPoster_path());
                context.startActivity(intent);

            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                try {
                    Movie movie = getListMovies().get(position);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Now " + movie.getTitle() + " Released");
                    sendIntent.setType("text/plan");
                    context.startActivity(sendIntent);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }));

    }

    @Override
    public int getItemCount() {
        return getListMovies().size();
    }
    
    class CardViewViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvTitle, tvDescription, tvReleaseDate;
        Button btnDetail, btnShare;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            imgPhoto = (ImageView)itemView.findViewById(R.id.img_item_photo);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_item_title);
            tvDescription = (TextView)itemView.findViewById(R.id.tv_item_description);
            tvReleaseDate = (TextView)itemView.findViewById(R.id.tv_item_date_release);
            btnDetail = (Button)itemView.findViewById(R.id.btn_item_detail);
            btnShare = (Button)itemView.findViewById(R.id.btn_item_share);
        }
    }
}
