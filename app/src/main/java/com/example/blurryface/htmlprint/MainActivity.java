package com.example.blurryface.htmlprint;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view);
                myWebView=null;
            }
        });
        String HtmlDocument = "<html><body><h1>Android Print Test</h1><p>"+
                "This is some sample content</p></body></html>";
        webView.loadDataWithBaseURL(null,HtmlDocument,"text/HTML","UTF-8",null);
        //ensuring that the java runtime does not interrupt printing
        myWebView=webView;



    }
    public void createWebPrintJob(WebView webView){
        PrintManager printManager = (PrintManager)this.getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printDocumentAdapter = webView.createPrintDocumentAdapter("MyDocument");
        String jobName = getString(R.string.app_name)+"PrintTest";
        printManager.print(jobName,printDocumentAdapter,new PrintAttributes.Builder().build());
    }

}
