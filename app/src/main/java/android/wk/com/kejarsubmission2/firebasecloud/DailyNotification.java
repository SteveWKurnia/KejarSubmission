package android.wk.com.kejarsubmission2.firebasecloud;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.wk.com.kejarsubmission2.BuildConfig;
import android.wk.com.kejarsubmission2.MovieDetailActivity;
import android.wk.com.kejarsubmission2.R;
import android.wk.com.kejarsubmission2.ReminderSettingsActivity;
import android.wk.com.kejarsubmission2.interfaces.moviedatas.NewMoviesAPI;
import android.wk.com.kejarsubmission2.models.MovieModel;
import android.wk.com.kejarsubmission2.models.MovieSuperclass;
import android.wk.com.kejarsubmission2.utilities.ChannelSupport;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DailyNotification extends FirebaseMessagingService {

    private ChannelSupport channelSupport = new ChannelSupport();
    private Date currentDate = Calendar.getInstance().getTime();


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("dailyNotif",s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        if (remoteMessage.getNotification()!=null){
//            if (remoteMessage.getData().containsKey("title")){
//                Log.d("dailyNotif",remoteMessage.getData().get("title"));
//            }
//            if (remoteMessage.getNotification().getBody().equals("Don't forget to open the app!"))
//                sendDailyNotification(remoteMessage.getNotification().getBody());
//            else
//                sendNewMovieNotification(remoteMessage.getNotification().getBody());
//        }
        if (remoteMessage.getData()!=null){
            if (remoteMessage.getData().get("content").equals("Don't forget to open the app!")){
                sendDailyNotification(remoteMessage);
            }else{
                sendNewMovieNotification(remoteMessage);
            }
        }
    }

    public void sendDailyNotification(RemoteMessage remoteMessage){
        Map<String,String> data = remoteMessage.getData();
        String msg = data.get("content");
        String title = data.get("title");

        String channelId = channelSupport.getChannelID();
        String channelName = channelSupport.getChannelName();

        Intent intent = new Intent(this, ReminderSettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setContentTitle(title)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(msg)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationBuilder.setChannelId(channelId);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = notificationBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(0, notification);
        }
    }

    public void sendNewMovieNotification(RemoteMessage remoteMessage){
        Map<String,String> data = remoteMessage.getData();
        String msg = data.get("content");
        String notifTitle = data.get("title");

        String channelId = channelSupport.getChannelID();
        String channelName = channelSupport.getChannelName();
        MovieModel movieModel = retrieveData();

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("parcelledAPIData",movieModel);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setContentTitle(notifTitle)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(movieModel.getTitle()+msg)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationBuilder.setChannelId(channelId);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = notificationBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(0, notification);
        }
    }

    public MovieModel retrieveData(){
        String URL = "https://api.themoviedb.org/3/discover/";
        MovieModel movieModel = new MovieModel(null,null,null,0,null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewMoviesAPI newMoviesAPI = retrofit.create(NewMoviesAPI.class);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(currentDate);
        Log.d("newNotif", formattedDate);
        Call<MovieSuperclass> call = newMoviesAPI.getNewMovie(BuildConfig.API_KEY,formattedDate,formattedDate);
        try{
            Response<MovieSuperclass> response = call.execute();
            MovieSuperclass movieSuperclass = response.body();
            movieModel = movieSuperclass.getResults().get(0);
            Log.d("dailyNotif",movieModel.getTitle());
            Log.d("dailyNotif",movieModel.getRelease_date());
            Log.d("dailyNotif",movieModel.getPoster_path());
        }catch (Exception e){
            Log.d("dailyNotif",e.getMessage());
        }
        return movieModel;
    }

}
