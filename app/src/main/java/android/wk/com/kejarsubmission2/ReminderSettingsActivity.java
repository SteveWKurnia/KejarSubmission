package android.wk.com.kejarsubmission2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.wk.com.kejarsubmission2.utilities.ChannelSupport;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReminderSettingsActivity extends AppCompatActivity {
    private ChannelSupport channelSupport = new ChannelSupport();
    private Switch dailySwitch, newMovieSwitch;
    private ImageView backButton;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_settings);

        SharedPreferences sharedPreferences = context.getSharedPreferences("reminderKey",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        dailySwitch = findViewById(R.id.daily_reminder_switch);
        newMovieSwitch = findViewById(R.id.new_reminder_switch);
        backButton = findViewById(R.id.back_button_reminder);

        dailySwitch.setChecked(false);
        newMovieSwitch.setChecked(false);

        if (sharedPreferences.getBoolean("dailySwitch",true)){
            dailySwitch.setChecked(sharedPreferences.getBoolean("dailySwitch",true));
        }else if (sharedPreferences.getBoolean("dailySwitch",false)){
            dailySwitch.setChecked(sharedPreferences.getBoolean("dailySwitch",false));
        }
        if (sharedPreferences.getBoolean("newSwitch",true)){
            newMovieSwitch.setChecked(sharedPreferences.getBoolean("newSwitch",true));
        }else if (sharedPreferences.getBoolean("newSwitch",false)){
            newMovieSwitch.setChecked(sharedPreferences.getBoolean("newSwitch",false));
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = channelSupport.getChannelID();
            String channelName = channelSupport.getChannelName();
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_LOW));
        }

        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    FirebaseMessaging.getInstance().subscribeToTopic("daily");
                    Toast.makeText(context,"You have subscribed to daily notifications",Toast.LENGTH_SHORT).show();
                    editor.putBoolean("dailySwitch",true);
                    editor.apply();
                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("daily");
                    Toast.makeText(context,"You have unsubscribed from daily notifications",Toast.LENGTH_SHORT).show();
                    editor.putBoolean("dailySwitch",false);
                    editor.apply();
                }
            }
        });

        newMovieSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    FirebaseMessaging.getInstance().subscribeToTopic("new_movie");
                    Toast.makeText(context,"You have subscribed to new movie notifications",Toast.LENGTH_SHORT).show();
                    editor.putBoolean("newSwitch",true);
                    editor.apply();
                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("new_movie");
                    Toast.makeText(context,"You have unsubscribed from new movie notifications",Toast.LENGTH_SHORT).show();
                    editor.putBoolean("newSwitch",false);
                    editor.apply();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
