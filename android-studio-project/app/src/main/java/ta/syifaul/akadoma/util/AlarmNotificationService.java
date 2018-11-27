package ta.syifaul.akadoma.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import ta.syifaul.akadoma.MainActivity;
import ta.syifaul.akadoma.R;
import ta.syifaul.akadoma.SplashScreenActivity;

/**
 * Created by user on 31/08/18.
 */

public class AlarmNotificationService extends Service {
    private MediaPlayer mediaPlayer;

    private NotificationManager alarmNotificationManager;

    public static final int NOTIFICATION_ID = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        mediaPlayer.start();
        //mediaPlayer.setLooping(true);
        mediaPlayer.setLooping(false);

        SharedPreferences sharedPreferences = getSharedPreferences("key_alarm_agenda", MODE_PRIVATE);
        String judul = sharedPreferences.getString("judul", "");
        String ket = sharedPreferences.getString("ket", "");

        //Notifikasi
        alarmNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashScreenActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(judul)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(ket))
                .setContentText(ket).setAutoCancel(true)
                .setSound(alarmSound);

        alarmNotificationBuilder.setContentIntent(contentIntent);

        alarmNotificationManager.notify(NOTIFICATION_ID, alarmNotificationBuilder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }
}
