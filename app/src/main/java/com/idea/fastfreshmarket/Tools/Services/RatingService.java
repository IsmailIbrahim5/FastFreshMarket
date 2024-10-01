package com.idea.fastfreshmarket.Tools.Services;

/*
    Created by Idea co. 2020
    Contact Us on : IdeaOfficialCompany@gmail.com for more information
 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idea.fastfreshmarket.R;
import com.idea.fastfreshmarket.UI.activities.Home;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class RatingService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        final String orderId = intent.getStringExtra("orderId");
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("orders").child(orderId).child("Info").child("delivered");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(Boolean.TRUE.equals(snapshot.getValue(Boolean.class))){
                    db.removeEventListener(this);

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("rating" , true);
                    intent.putExtra("order" , orderId);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext() , "Ratings")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getResources().getString(R.string.order_delivered))
                            .setContentText(getResources().getString(R.string.tap_here))
                            .setColor(getApplication().getResources().getColor(R.color.green))
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        builder.setChannelId("Ratings");
                        notificationManager.createNotificationChannel(new NotificationChannel("Ratings", "Ratings", NotificationManager.IMPORTANCE_DEFAULT));
                    }

                    notificationManager.notify(5, builder.build());

                    RatingService.this.stopSelf();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return START_STICKY;
    }
}