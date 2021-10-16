package intents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3.R;

import lifecycle.ActivityA;
import lifecycle.ActivityB;
import lifecycle.ActivityC;

public class MainIntentActivity extends AppCompatActivity {

    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_intent);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
    }
    public void clicked(View view){
        switch (view.getId()) {
            case R.id.buttonA:
                Intent a = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.google.com"));
                startActivity(a);
                break;
            case R.id.buttonB:
                Intent b = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("tel:00401213456"));
                startActivity(b);
                break;
            case R.id.buttonC:
                Intent c = new Intent("MSA.LAUNCH",
                        Uri.parse("https://www.google.com"));
                startActivity(c);
                break;
            case R.id.buttonD:
                Intent d = new Intent("MSA.LAUNCH",
                        Uri.parse("tel:00401213456"));
                startActivity(d);
                break;
            default:
                System.out.println(view.getId());
        }
    }

}
