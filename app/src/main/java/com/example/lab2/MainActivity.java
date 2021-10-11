package com.example.lab2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText eName;
    private Button bClick;
    private TextView tName;
    private Spinner spinner;
    private Button bSearch;
    private Button bShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eName = (EditText) findViewById(R.id.eName);
        bClick = (Button) findViewById(R.id.bClick);
        tName = (TextView) findViewById(R.id.tName);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.colours, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        bShare = (Button) findViewById(R.id.bShare);
        bSearch = (Button) findViewById(R.id.bSearch);

        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, tName.getText().toString());
                sendIntent.setType("text/plain");

                Intent chooser = Intent.createChooser(sendIntent, "choose");
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent();
                searchIntent.setAction(Intent.ACTION_VIEW);
                searchIntent.setData(Uri.parse("https://www.google.com/search?q="+eName.getText().toString()));
                if (searchIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(searchIntent);
                }

            }
        });
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
                            Toast.makeText(MainActivity.this,"Too Bad", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.create().show();
                }
                break;
            default:
            System.out.println(view.getId());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String colour = adapterView.getItemAtPosition(i).toString();
        bClick.setTextColor(Color.parseColor(colour));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}