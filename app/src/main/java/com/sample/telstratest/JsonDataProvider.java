package com.sample.telstratest;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vara on 4/13/2015.
 * This class downloads JSON message from the Cloud API and parses it using Google Gson library.
 */
public class JsonDataProvider {

    private static final String SERVER_URL = "https://dl.dropboxusercontent.com/u/746330/facts.json";
    private ArrayList<DataContainer> list;
    public static String MAIN_TITLE;

    public JsonDataProvider(ArrayList<DataContainer> list)
    {
        this.list = list;
    }
    public int getDataFromCloud()
    {
        int returnCode = -1;

        try
        {
            URL url = new URL(SERVER_URL);
            HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setConnectTimeout(5000);
            httpConnection.connect();

            int responseCode = httpConnection.getResponseCode();

            if(responseCode == 200)
                returnCode = parseResponseWithGson(
                        new InputStreamReader(httpConnection.getInputStream()));

            httpConnection.disconnect();
        }
        catch(Exception e)
        {
            Log.e("NewsReader","Error in downloading JSON message");
        }

        return returnCode;
    }

    private int parseResponseWithGson(InputStreamReader reader)
    {
        int returnCode = -1;

        Gson gsonObject = new Gson();
        JsonResponse response = gsonObject.fromJson(reader,JsonResponse.class);

        if(response != null)
        {
            MAIN_TITLE = response.title;

            List<JsonResponse.Row> rows = response.rows;
            String articleTitle;
            String articleDescription;
            String articleImageLocation;

            for(int i=0; i<rows.size(); i++)
            {
                if(rows.get(i).title != null)
                {
                    articleTitle = rows.get(i).title.trim();
                    if(rows.get(i).description == null)
                        articleDescription = "null";
                    else
                        articleDescription = rows.get(i).description.trim();
                    if(rows.get(i).imageHref == null)
                        articleImageLocation = "null";
                    else
                        articleImageLocation = rows.get(i).imageHref.trim();

                    list.add(new DataContainer(
                            articleTitle, articleDescription, articleImageLocation));
                }
            }

            returnCode = 0;
        }

        return returnCode;
    }
}
