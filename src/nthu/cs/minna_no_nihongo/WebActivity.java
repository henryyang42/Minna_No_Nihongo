package nthu.cs.minna_no_nihongo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
 
public class WebActivity extends Activity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String name = this.getIntent().getStringExtra("name");
        
        webView = (WebView) this.findViewById(R.id.webView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.setInitialScale(1);
        webView.loadUrl("file:///android_asset/lessons_html/" + name + ".html");
    }
}