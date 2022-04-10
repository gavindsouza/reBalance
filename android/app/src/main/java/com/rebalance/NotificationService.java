package com.rebalance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;


public class NotificationService extends NotificationListenerService {
    Context context;
    String titleData="", textData="";
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        Bundle extras = sbn.getNotification().extras;

        if (extras.getString("android.title")!=null){
            titleData = extras.getString("android.title");
        } else {
            titleData = "";
        }

        if (extras.getCharSequence("android.text")!=null){
            textData = extras.getCharSequence("android.text").toString();
        } else{
            textData = "";
        }

        Log.d("Package", packageName);
        Log.d("Title", titleData);
        Log.d("Text", textData);

        Intent msg = new Intent("Msg");
        msg.putExtra("package", packageName);
        msg.putExtra("title", titleData);
        msg.putExtra("text", textData);

        // TODO: Make API request here

    }
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d("Msg", "Notification Removed");
    }
}