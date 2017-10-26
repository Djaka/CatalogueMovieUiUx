package com.dicoding.assosiate.catalogmovie_v2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    RecyclerView recyclerView;
    CardViewNowPlayingMovieAdapter adapter;
    String result;
    ProgressBar progressBar;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        setHasOptionsMenu(true);

        getLoaderManager().initLoader(0, null, this);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CardViewNowPlayingMovieAdapter(getActivity());
        progressBar = (ProgressBar)view.findViewById(R.id.pb_Movie);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                result = String.valueOf(query.toString());
                if (query == null || query.isEmpty()){
                    getLoaderManager().restartLoader(0, null, UpcomingFragment.this);
                }else {
                    getLoaderManager().restartLoader(0, null, UpcomingFragment.this);
                }

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //result = String.valueOf(newText.toString());
                //getLoaderManager().restartLoader(0, null, NowPlayingFragment.this);
                return true;
            }
        });
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new UpcomingMovieLoader(getContext(), result);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        adapter.setListMovie(data);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }
}
