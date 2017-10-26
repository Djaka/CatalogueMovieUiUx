package com.dicoding.assosiate.catalogmovie_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailMovieActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewRelease, textViewOverview;
    public static String EXTRA_ID = "EXTRA_ID";
    public static String EXTRA_TITLE = "EXTRA_TITLE";
    public static String EXTRA_RELEASE = "EXTRA_RELEASE";
    public static String EXTRA_OVERVIEW = "EXTRA_OVERVIEW";
    public static String EXTRA_POSTER = "EXTRA_POSTER";
    private final String TAG = "DetailMovieActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String url_img = "https://image.tmdb.org/t/p/original";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        int id_movie = getIntent().getIntExtra(EXTRA_ID,0);
        String title_movie = getIntent().getStringExtra(EXTRA_TITLE);
        String release_movie = getIntent().getStringExtra(EXTRA_RELEASE);
        String overview_movie = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String poster_path_movie =getIntent().getStringExtra(EXTRA_POSTER);

        ImageView imageView = (ImageView) findViewById(R.id.img_poster_path);

        Glide.with(this)
                .load(url_img+poster_path_movie)
                .into(imageView);

        textViewTitle = (TextView) findViewById(R.id.det_title);
        textViewTitle.setText(title_movie);
        textViewRelease = (TextView) findViewById(R.id.year);
        textViewRelease.setText(release_movie);
        textViewOverview = (TextView) findViewById(R.id.overview);
        textViewOverview.setText(overview_movie);

        actionBar.setTitle(title_movie);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home){
            Intent intent = new Intent(DetailMovieActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}