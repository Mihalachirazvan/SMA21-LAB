package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText eName;
    private Button bClick;
    private TextView tName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eName = (EditText) findViewById(R.id.eName);
        bClick = (Button) findViewById(R.id.bClick);
        tName = (TextView) findViewById(R.id.tName);

        bClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (eName.getText().toString().isEmpty())
                    tName.setText("Greetings");
                else {
                    tName.setText("Hello," + eName.getText());
                    tName.setTextColor(Color.BLUE);
                }

            }

        });
    }

}