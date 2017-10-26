package com.dicoding.assosiate.catalogmovie_v2;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by yaya on 2017-10-23.
 */

public class MovieDetailLoader extends AsyncTaskLoader<MovieDetail> {
    private static String API_KEY = "738b79e3ef5d0c5080144c9389006f92";
    private boolean hasResult = false;
    private MovieDetail mData;
    private String idMovie;

    public MovieDetailLoader(Context context, String sIdMovie) {
        super(context);
        this.idMovie = sIdMovie;
        onContentChanged();
    }

    @Override
    public void deliverResult(MovieDetail data) {
        mData = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged()){
            forceLoad();
        }else if(hasResult){
            deliverResult(mData);
        }
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
    public MovieDetail loadInBackground() {
        final MovieDetail[] details = {null};

        Log.d("Load BG","2");
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/"+idMovie+"?api_key="+API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    details[0] = new MovieDetail(jsonObject);
                    Log.d("Request Success","1");
                } catch (JSONException e) {
                    Log.d("Request Failed", "Exception");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("DB Movies","Failed");
            }
        });
        return details[0];
    }
    private void onReleaseResources(MovieDetail mData){

    }
}
