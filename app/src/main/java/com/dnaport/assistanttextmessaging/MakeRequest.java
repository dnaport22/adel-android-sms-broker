package com.dnaport.assistanttextmessaging;

import android.net.Uri;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;

public class MakeRequest {
    private JSONObject jsonObject = null;

    public JSONObject
    newRequest(final String query, final boolean withAudio) throws IOException, JSONException, InterruptedException {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    String line, jsonResponse = "";
                    Uri uri = new Uri.Builder()
                            .scheme("https")
                            .authority("google-assistant-api-dot-twist-ac01.appspot.com")
                            .path("ga-rest-api")
                            .appendQueryParameter("query", query)
                            .appendQueryParameter("withAudio", String.valueOf(withAudio))
                            .build();
                    URL urls = new URL(uri.toString());
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(urls.openStream(), "UTF-8"))) {
                        while ((line = reader.readLine()) != null) {
                            jsonResponse += line;
                        }
                        jsonObject = new JSONObject(jsonResponse);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(run);
        t.start();
        t.join();

        return jsonObject;
    }
}
