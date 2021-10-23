package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private Context context;


    public DownloadImageTask(Context context) {
        this.context = context;
        Toast.makeText(context, "Please wait, it may take a few seconds.", Toast.LENGTH_SHORT).show();
    }

    protected Bitmap doInBackground(String... urls) {

        Bitmap bmp = null;

        return bmp;


    }
    protected void onPostExecute(Bitmap result) {
        // save bitmap result in application class
        ((MyApplication) context.getApplicationContext()).setBitmap(result);
        // send intent to stop foreground service
        Intent intent = new Intent(context.getApplicationContext(), ForegroundImageService.class);
        intent.setAction(ForegroundImageService.STOPFOREGROUND_ACTION);
        context.startService(intent);

    }
}