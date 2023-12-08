package com.example.chatme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link calllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class calllFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    WebView webView;

    public calllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment calllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static calllFragment newInstance(String param1, String param2) {
        calllFragment fragment = new calllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_calll, container, false);
                webView = view.findViewById(R.id.webview);
        // Enable JavaScript (optional, depending on your requirements)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Handle links inside WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    view.loadUrl(url);
                    return true;
                } else {
                    // Open external links in external browser
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // Handle form submissions and include CSRF token
                String csrfToken = extractCsrfTokenFromWebView(view);
                // You can then use the CSRF token in your further requests
            }

        });


        // Load the initial URL
        webView.loadUrl("https://webchat-z1to.onrender.com");
        return view;
    }
    private String extractCsrfTokenFromWebView(WebView view) {
        // Implement logic to extract the CSRF token from the WebView
        // For example, you can use JavaScript to get the token from the page's DOM
        // Note: This is just a placeholder; you need to adapt it based on your actual page structure

        String javascript = "javascript:(function() { " +
                "var csrfToken = document.getElementsByName('csrf_token')[0].value; " +
                "window.Android.onCsrfTokenExtracted(csrfToken); })()";

        view.evaluateJavascript(javascript, null);

        return null; // Placeholder, replace it with actual CSRF token value
    }


}