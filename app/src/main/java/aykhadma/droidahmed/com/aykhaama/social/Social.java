package aykhadma.droidahmed.com.aykhaama.social;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import aykhadma.droidahmed.com.aykhaama.R;

/**
 * Created by ahmed on 8/8/2016.
 */
public class Social extends Fragment {
    private WebView webView;
    String link;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_media,container,false);
        Bundle bundle = getArguments();
        link = bundle.getString("link");
        webView = (WebView) view.findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(link);
        webView.getSettings().setJavaScriptEnabled(true);

        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        webView.onPause();

    }
}
