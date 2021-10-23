package com.example.lab4;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        MyApplication myApplication = (MyApplication) getApplicationContext();
        if(myApplication.getBitmap()==null)
        {
            Toast.makeText(this,"Error transmitting URL", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(myApplication.getBitmap());
        }
    }

}
