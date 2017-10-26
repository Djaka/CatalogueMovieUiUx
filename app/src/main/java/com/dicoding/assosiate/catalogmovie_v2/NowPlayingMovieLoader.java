package com.dicoding.assosiate.catalogmovie_v2;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by yaya on 2017-10-23.
 */

public class NowPlayingMovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private boolean hasResult = false;
    private ArrayList<Movie> mData;
    private String cari;
    private static String API_KEY = "738b79e3ef5d0c5080144c9389006f92";

    public NowPlayingMovieLoader(Context context, String sCari) {
        super(context);
        this.cari = sCari;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()){
            forceLoad();
        }else if(hasResult){
            deliverResult(mData);
        }
    }

    @Override
    public void deliverResult(ArrayList<Movie> data) {
        mData = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(hasResult){
            onReleaseResources(mData);
            mData = null;
            hasResult = false;
        }
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> movieArrayList =new ArrayList<>();
        String url = null;

        if (cari != null){
            url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+ cari;
        }else {
            url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en-US";
        }
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i = 0; i< list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movie movies = new Movie(movie);
                        movieArrayList.add(movies);

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return movieArrayList;
    }


    private void onReleaseResources(ArrayList<Movie> mData){

    }

    public ArrayList<Movie> getmData(){
        return mData;
    }
}
