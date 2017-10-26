package com.dicoding.assosiate.catalogmovie_v2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Djaka on 25/10/2017.
 */

public class SchedulerService extends GcmTaskService {

    public static final String TAG = "GetMovie";
    private final String APP_ID = "738b79e3ef5d0c5080144c9389006f92";
    public static String TAG_TASK_MOVIE = "MovieTask";

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if(taskParams.getTag().equals(TAG_TASK_MOVIE)){
            getUpcomingMovie();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    public void getUpcomingMovie() {
        Log.d(TAG,"Running");
        SyncHttpClient httpClient = new SyncHttpClient();
        String url =  "https://api.themoviedb.org/3/movie/upcoming?api_key=" + APP_ID;
        httpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray parentArray = parentObject.getJSONArray("results");
                    JSONObject contentObject = parentArray.getJSONObject(0);

                    int id = contentObject.getInt("id");
                    String title = contentObject.getString("title");
                    String poster_path = contentObject.getString("poster_path");
                    String overview = contentObject.getString("overview");
                    String release_date = contentObject.getString("release_date");

                    int notifId = 100;
                    showNotification(getApplicationContext(), id, title, poster_path, overview, release_date, notifId);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "Failed");
            }
        });
    }

    public void showNotification(Context context, int id, String title, String poster_path, String overview, String release_date, int notifId){
        int id_movie = id;

        Intent notifyIntent = new Intent(getApplicationContext(), DetailMovieActivity.class);

        notifyIntent.putExtra(DetailMovieActivity.EXTRA_ID, String.valueOf(id_movie));
        notifyIntent.putExtra(DetailMovieActivity.EXTRA_TITLE,title);
        notifyIntent.putExtra(DetailMovieActivity.EXTRA_POSTER, poster_path);
        notifyIntent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, overview);
        notifyIntent.putExtra(DetailMovieActivity.EXTRA_RELEASE, release_date);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Upcoming Movie")
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setContentText(title)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);
        notificationManager.notify(notifId, builder.build());
    }
}
