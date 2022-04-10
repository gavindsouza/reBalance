package com.rebalance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import java.io.IOException;
import android.os.StrictMode;
import com.google.gson.Gson;
import java.util.Hashtable;
import android.content.pm.PackageManager;


public class NotificationService extends NotificationListenerService {
    Context context;
    String titleData="";

    @Override
    public void onCreate() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        Hashtable<String, Object> msg = new Hashtable<>();

        MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        try {
            String appName = (String) packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            );
            msg.put("app", appName);
        } catch (PackageManager.NameNotFoundException e) {}
        msg.put("package", packageName);
        msg.put("title", extras.getString("android.title"));
        msg.put("text", extras.getString("android.text"));
        msg.put("id", String.valueOf(sbn.getId()));
        msg.put("post_time", String.valueOf(sbn.getPostTime()));
        msg.put("tag", sbn.getTag());
        msg.put("user", String.valueOf(sbn.getUser()));

        String jsonBody = gson.toJson(msg);
        Request request = new Request.Builder()
            // .url("https://rebalance.frappe.cloud/api/method/rebalance.api.add")
            .url("https://silent-duck-89.loca.lt/api/method/rebalance.api.add")
            .post(RequestBody.create(MEDIA_TYPE_JSON, jsonBody))
            .build();

        try {
            Response response = client.newCall(request).execute();
            // System.out.println(response.body().string());
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {};
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        Hashtable<String, Object> msg = new Hashtable<>();

        MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        try {
            String appName = (String) packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            );
            msg.put("app", appName);
        } catch (PackageManager.NameNotFoundException e) {}
        msg.put("package", packageName);
        msg.put("title", extras.getString("android.title"));
        msg.put("text", extras.getString("android.text"));
        msg.put("id", String.valueOf(sbn.getId()));
        msg.put("post_time", String.valueOf(sbn.getPostTime()));
        msg.put("tag", sbn.getTag());
        msg.put("user", sbn.getUser().toString());

        String jsonBody = gson.toJson(msg);
        Request request = new Request.Builder()
            // .url("https://rebalance.frappe.cloud/api/method/rebalance.api.remove")
            .url("https://silent-duck-89.loca.lt/api/method/rebalance.api.remove")
            .post(RequestBody.create(MEDIA_TYPE_JSON, jsonBody))
            .build();

        try {
            Response response = client.newCall(request).execute();
            // System.out.println(response.body().string());
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {};
    }
}