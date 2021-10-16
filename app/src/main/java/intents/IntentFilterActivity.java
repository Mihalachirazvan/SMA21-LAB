package intents;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lab3.R;

import org.w3c.dom.Text;

public class IntentFilterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_filter);

        TextView textView = (TextView) findViewById(R.id.textView);
        Uri url = getIntent().getData();
        textView.setText(url.toString());
    }
}
