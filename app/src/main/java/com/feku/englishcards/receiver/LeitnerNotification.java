package com.feku.englishcards.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.feku.englishcards.R;
import com.feku.englishcards.activity.SelectDictionaryActivity;
import com.feku.englishcards.dao.CardDao;
import com.feku.englishcards.dao.util.DaoMaster;
import com.feku.englishcards.dao.util.DaoSession;

import org.joda.time.LocalDate;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by feku on 9/4/2015.
 */
public class LeitnerNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "db-test", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        CardDao cardDao = daoSession.getCardDao();

        SharedPreferences prefs = context.getSharedPreferences("english_cards", MODE_PRIVATE);
        long count = prefs.getLong(LocalDate.now().toString(), 0);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, "Test", System.currentTimeMillis());
        Intent intentTL = new Intent(context, SelectDictionaryActivity.class);

        notification.setLatestEventInfo(context, count + " english cards", "in Leitner mode today! You can do better!",
                PendingIntent.getActivity(context, 0, intentTL,
                        PendingIntent.FLAG_CANCEL_CURRENT));
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        nm.notify(1, notification);
    }
}
