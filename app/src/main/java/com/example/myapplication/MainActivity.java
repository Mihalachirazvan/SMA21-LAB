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

    }
    public void clicked(View view) {
        switch (view.getId()) {
            case R.id.bClick:
                if (eName.getText().toString().isEmpty())
                    tName.setText("Greetings");
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Message");
                    alert.setMessage("Hello," + eName.getText());
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this,"Good Job",Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this,"Too Bad",Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.create().show();
                }
                break;
            default:
                System.out.println(view.getId());


        }

    }

}