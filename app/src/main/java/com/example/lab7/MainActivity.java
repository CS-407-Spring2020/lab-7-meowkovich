package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.lab7.App.CHANNEL_1_ID;
import static com.example.lab7.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private EditText title;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        title = findViewById( R.id.title );
        message = findViewById( R.id.message );
    }

    public void sendOnChannel1( View v )
    {
        String strTitle = title.getText().toString();
        String strMessage = message.getText().toString();

        Intent activityIntent = new Intent( this, MainActivity.class );
        PendingIntent contentIntent = PendingIntent.getActivity( this, 0,
                                        activityIntent, 0 );

        Intent broadcastIntent = new Intent( this, NotificationReceiver.class );
        broadcastIntent.putExtra( "toastMessage", strMessage );
        PendingIntent actionIntent = PendingIntent.getBroadcast( this, 0,
                                        broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setContentTitle(strTitle)
                .setContentText(strMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor( Color.BLUE )
                .setContentIntent( contentIntent )
                .setAutoCancel( true )
                .setOnlyAlertOnce( true )
                .addAction( R.mipmap.ic_launcher, "Toast", actionIntent )
                .build();

        notificationManager.notify(1, notification );
    }

    public void sendOnChannel2( View v )
    {
        String strTitle = title.getText().toString();
        String strMessage = message.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_send_black_24dp)
                .setContentTitle(strTitle)
                .setContentText(strMessage)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification );
    }
}
