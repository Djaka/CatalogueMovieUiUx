package com.dicoding.assosiate.catalogmovie_v2;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by yaya on 2017-10-23.
 */

public class Movie {
    private int id;
    private String title, poster_path, overview, overview_detail, release_date;

    public Movie(JSONObject jsonObject) {
        try {
            String myOverview;
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            String poster_path = jsonObject.getString("poster_path");
            String overview = jsonObject.getString("overview");
            String release_date = jsonObject.getString("release_date");

            if(overview.length() > 50){
                String overviewSubString = overview.substring(0,1).toUpperCase() + overview.substring(0,50).toLowerCase();
                myOverview = overviewSubString + "...";
            }else if(overview.isEmpty()) {
                myOverview = "This video has not overviewed";
            }else {
                myOverview = overview;
            }

            this.id = id;
            this.title = title;
            this.poster_path = poster_path;
            this.overview = myOverview;
            this.overview_detail = overview;
            this.release_date = release_date;


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview_detail() {
        return overview_detail;
    }

    public void setOverview_detail(String overview_detail) {
        this.overview_detail = overview_detail;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
