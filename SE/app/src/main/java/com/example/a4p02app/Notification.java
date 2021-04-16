package com.example.a4p02app;

import android.app.PendingIntent;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Class sends a notification with the corresponding title and text.
 */
public class Notification {

    /**
     * Displays notification to the device in the specified context with the given title and text.
     * @param nContext The context of the notification.
     * @param nPriority The priority level of the notification
     * @param nTitle The title of the notification
     * @param nText The text body of the notification
     * @param nIntent The intent of the activity that on-click interacts will lead to
     */
    public static void display(Context nContext, int nPriority, String nTitle, String nText, PendingIntent nIntent){
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(nContext, "donation_application") // channelid changed from MainActivity.NOTIF_CHANNEL_ID
                //.setSmallIcon(R.drawable.ic_heart) //filler icon for testing
                .setContentTitle(nTitle)
                .setContentText(nText)
                .setPriority(nPriority) //ex. NotificationCompat.PRIORITY_DEFAULT
                .setContentIntent(nIntent)  // where interaction navigation goes
                .setAutoCancel(true); // removes notification on click interaction
        // sends notification
        NotificationManagerCompat nManager = NotificationManagerCompat.from(nContext);
        nManager.notify(1,notifBuilder.build()); //sends notification, tempId used for later updating or deleting notification
    };
}
