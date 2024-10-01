package com.idea.fastfreshmarket.Tools.Services;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.idea.fastfreshmarket.Models.CloudMsg;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.activities.Home;

import java.net.URL;

import androidx.core.app.NotificationManagerCompat;

public class FirebaseMessagesService extends FirebaseMessagingService {
    private final static String CHANNEL_ID = "New Products";

    @Override
    public void handleIntent(Intent intent) {

        try {
            Intent home = new Intent(getApplicationContext(), Home.class);
            home.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            home.putExtra("news" , true);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Bundle bundle = intent.getExtras();


            if (bundle == null)
                return;

            CloudMsg cloudMsg = parseCloudMsg(bundle);


            String title = cloudMsg.getTitle().replace("userName", Home.user.getName());
            String desc = cloudMsg.getValue().replace("userName", Home.user.getName());

            Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(desc)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(getApplication().getResources().getColor(R.color.green))
                    .setAutoCancel(true)
                    .setStyle(new Notification.BigTextStyle())
                    .setContentIntent(pendingIntent);


            try {
                if (cloudMsg.getImage()!=null) {
                    URL url = new URL(cloudMsg.getImage());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    notificationBuilder.setStyle(new Notification.BigPictureStyle().bigLargeIcon((Bitmap) null).bigPicture(bmp)).setLargeIcon(bmp);
                }
            } catch (Exception ignored) {
            }

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationBuilder.setChannelId(CHANNEL_ID);
                notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT));
            }

            Notification notification = notificationBuilder.build();

            notificationManager.notify(16, notification);

        }catch(Exception e){}
    }


    private CloudMsg parseCloudMsg(Bundle bundle) {
        String title = null, msg;

        msg = (String) bundle.get("gcm.notification.body");

        if(bundle.containsKey("gcm.notification.title"))
            title = (String) bundle.get("gcm.notification.title");


        String create =  (String) bundle.get("image");

        CloudMsg cloudMsg = new CloudMsg(title, msg, create);
        return cloudMsg;
    }
}
