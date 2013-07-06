package com.example.showweb;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class ShowWeb extends Activity
{

	EditText et;
	WebView wv;
	String http = "http://";
	String www =  "www.";
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.main);
		et = (EditText)findViewById(R.id.webAddress);
	    wv = (WebView)findViewById(R.id.webkit);
	    wv.setWebViewClient(new MyWebViewClient());
	    wv.getSettings().setJavaScriptEnabled(true);
	    

	    et.setOnKeyListener(new OnKeyListener()
		{
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent ke)
			{

			   if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode==KeyEvent.KEYCODE_ENTER))
               {
                    url = et.getText().toString();
                    url = validateUrl(url);
                    openUrl(url);
                    return(true);
               }
			   
			   if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
                       (keyCode==KeyEvent.KEYCODE_DPAD_LEFT))
			   {
				   Log.i("key down","back");
				   if(wv.canGoBack())
				   {
					   wv.goBack();
				   }
			   }
			   
			   if((ke.getAction() == KeyEvent.ACTION_DOWN) &&
                       (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT))
			   {
				   Log.i("key down","forward");
				   if(wv.canGoForward())
				   {
					   wv.goForward();
				   }
			   }
			   
			   	return(false);
			}
		});
	
	}
	
	public String validateUrl(String url)
	{
	    if(url.startsWith(www))   url = http + url;
		else if(!url.startsWith(http) && !url.startsWith(www))       
	       url = http+ www +url;
		return(url);
	}
	
	
	public void getUrl(View v)
	{	
		url = et.getText().toString();
		url = validateUrl(url);
		openUrl(url);
	}
	
	public void openUrl(String url)
	{
		wv.setInitialScale(80);
		wv.loadUrl(url);
	}
	
	//when we tap a link Android opensthe link in the 
	//default browser and not in our activity window.
	//In order to do that we are overriding the shouldOverrideUrlLoading
	//method 
	private class MyWebViewClient extends WebViewClient 
	{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) 
        {
            view.loadUrl(url);
            return true;
        }
    }	

}
