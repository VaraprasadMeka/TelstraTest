package com.sample.telstratest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vara on 4/13/2015.
 * This class is an AsyncTask which downloads images from the network, stores them in the cache
 * using ImageCacheManager class and displays a scaled version of image in the ListView.
 */
public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap>{

    private final WeakReference<ImageView> imageViewReference;
    private String imageURL;
    private Context context;

    public ImageDownloadTask(Context context, ImageView imageView)
    {
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        imageURL = params[0];
        return downloadBitmap(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if ((imageViewReference != null) && ((imageViewReference.get()) != null))
        {
            String url = (String)imageViewReference.get().getTag();
            if((url != null) && (url.compareTo(imageURL) == 0))
            {
                if(bitmap != null)
                    imageViewReference.get().setImageBitmap(
                        Bitmap.createScaledBitmap(bitmap,100,75,false));
                else
                    imageViewReference.get().setImageBitmap(
                            BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image));
                imageViewReference.get().invalidate();
            }
        }
    }

    private Bitmap downloadBitmap(String url)
    {
        Bitmap bitmap = null;

        if(url.equalsIgnoreCase("null"))
        {
            bitmap = null;
        }
        else
        {
            HttpURLConnection httpConnection = null;
            try
            {
                URL imageUrl = new URL(url);
                httpConnection = (HttpURLConnection)imageUrl.openConnection();
                httpConnection.setConnectTimeout(2000);
                int statusCode = httpConnection.getResponseCode();

                if (statusCode == 200)
                {
                    InputStream inputStream = httpConnection.getInputStream();
                    if (inputStream != null)
                    {
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        ((NewsReaderActivity)context).cacheManager.addBitmapToMemoryCache(url,bitmap);
                    }
                }
            }
            catch(Exception e)
            {
                Log.e("NewsReader","Error in downloading image from " + url);
            }
            if(httpConnection != null)
                httpConnection.disconnect();
        }

        return bitmap;
    }
}
