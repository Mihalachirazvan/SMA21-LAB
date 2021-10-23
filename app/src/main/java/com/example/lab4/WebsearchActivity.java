package com.example.lab4;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WebsearchActivity extends AppCompatActivity {

    public static final String EXTRA_URL="EXTRA_URL";
    private Button bLoadBackground;
    private Button bLoadForeground;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search);
        webView = (WebView) findViewById(R.id.webView);
        bLoadBackground = (Button) findViewById(R.id.bLoadBackground);
        bLoadBackground = (Button) findViewById(R.id.bLoadForeground);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new MyCustomWebViewClient());
        webView.loadUrl("https://www.google.com/search?q=cat&tbm=isch&source=lnms&sa=X");

    }
    private class MyCustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (Uri.parse(url).toString().startsWith("https://www.google.com/search?q=")
                    && Uri.parse(url).toString().contains("tbm=isch")) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
            startActivity(intent);
            return true;

        }
    }
    public void clicked(View view) {

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData a = clipboard.getPrimaryClip();
        ClipData.Item item = a.getItemAt(0);
        String url = item.getText().toString();
        if (!url.contains("https://images.app.goo.gl"))
            Toast.makeText(this, "URL not valid.Try another.", Toast.LENGTH_SHORT).show();
        else {
            switch (view.getId()) {
                case R.id.bLoadBackground:
                    Intent intent = new Intent(this, ImageIntentService.class);
                    intent.putExtra("EXTRA_URL", url);
                    System.out.println("ai");
                    if (intent.resolveActivity(getPackageManager()) != null)
                        startService(intent);
                    break;
                case R.id.bLoadForeground:
                    Intent intent2 = new Intent(this, ForegroundImageService.class);
                    intent2.putExtra("EXTRA_URL", url);
                    intent2.setAction(ForegroundImageService.STARTFOREGROUND_ACTION);
                    System.out.println("bi");
                    if (intent2.resolveActivity(getPackageManager()) != null)
                        startService(intent2);
                    break;
            }
        }
    }
}
