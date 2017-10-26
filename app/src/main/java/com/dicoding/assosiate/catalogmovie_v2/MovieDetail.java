package com.dicoding.assosiate.catalogmovie_v2;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yaya on 2017-10-23.
 */

public class MovieDetail {
    private int id;
    private String title;
    private String release_date;
    private String production_companies;
    private String production_countries;
    private String tagline;
    private String overview;
    private String poster_path;

    public MovieDetail(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            title = jsonObject.getString("title");
            release_date = jsonObject.getString("release_date");
            tagline = jsonObject.getString("tagline");
            overview = jsonObject.getString("overview");
            poster_path = jsonObject.getString("poster_path");

            if (!TextUtils.isEmpty(release_date)){
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date date = fmt.parse(release_date);
                SimpleDateFormat fmtOut = new SimpleDateFormat("EEEE, MM dd, yyyyy");
                release_date = fmtOut.format(date);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }catch (ParseException e){
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(String production_companies) {
        this.production_companies = production_companies;
    }

    public String getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(String production_countries) {
        this.production_countries = production_countries;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
