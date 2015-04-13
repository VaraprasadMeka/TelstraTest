package com.sample.telstratest;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class NewsReaderActivity extends ActionBarActivity {

    Context context;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_reader);
        context = this;
        fetchAndDisplayData(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_reader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh)
        {
            fetchAndDisplayData(false);
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchAndDisplayData(boolean showProgressDialog)
    {
        new NewsDownloadTask(context,showProgressDialog).execute();
    }
}
