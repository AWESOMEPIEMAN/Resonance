package com.example.resonance.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.resonance.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class DashboardFragment extends Fragment implements View.OnClickListener{

    private  String BASE_URL = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyAgl_tVAk1eyfsSfDOI-RPF8kiEerOVKhY&part=snippet,id&order=viewCount&maxResults=1&q=";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        EditText videonametyped = (EditText) v.findViewById(R.id.imageUpload);
        Button b2 = (Button) v.findViewById(R.id.uploadBtn2);
        Button b3 = (Button) v.findViewById(R.id.uploadBtn3);
        Button b = (Button) v.findViewById(R.id.button2);
        Log.d("TEST","Instantiating button listener...");
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONWTF","Clear button clicked");
                getActivity().onBackPressed();
            }

        });
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONWTF","Send button clicked");
                getActivity().onBackPressed();

            }

        });
        YouTubePlayerView youTubePlayerView = v.findViewById(R.id.youtube_player_view);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONWTF","YouTube button worked");
                String text1 = videonametyped.getText().toString();
                Log.d("TEXTWTF",text1);
                String text = text1.replaceAll("\\s","%20");
                String videolink = requestData(BASE_URL + text);
                Log.d("TEXTWTF",videolink);
                JSONObject videojson = null;
                String video = null;
                String name = null;
                JSONArray videoarray = null;
                JSONObject videoarray1 = null;
                JSONObject videoarray2 = null;
                try {
                    videojson = new JSONObject(videolink);
                    videoarray = videojson.getJSONArray("items");
                    videoarray1 = videoarray.getJSONObject(0).getJSONObject("id");
                    videoarray2 = videoarray.getJSONObject(0).getJSONObject("snippet");
                    video = videoarray1.getString("videoId");
                    name = videoarray2.getString("title");
                    Log.d("FUCKYOU",videoarray1.getString("videoId"));

                } catch (JSONException e) {
                    Log.d("ONREADY",e.getMessage());
                    e.printStackTrace();
                }
                getLifecycle().addObserver(youTubePlayerView);
                String finalVideo = video;
                if (video == null){Log.d("TEXTWTF","no video ID");}
                else {
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            youTubePlayer.cueVideo(finalVideo, 0);
                        }
                    });
                }
            }
        });
        return v;
    }

    public void onClick(View v) {
        Log.d("BUTTONWTF","Send button clicked");
        getFragmentManager().beginTransaction().remove(DashboardFragment.this).commit();
    }

    private String requestData(String urlstring) {

        try {
            final String[] response = new String[1];
            final CountDownLatch latch = new CountDownLatch(1);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Log.d("START", "Starting GET");
                        URL url = new URL(urlstring);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(5000);
                        connection.setReadTimeout(5000);
                        connection.connect();
                        Log.d("INFO", urlstring);
                        Log.d("INFO", Integer.toString(connection.getResponseCode()));
                        Log.d("INFO", connection.getResponseMessage());
                        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String content = "", line;
                        while ((line = rd.readLine()) != null) {
                            content += line + "\n";
                        }
                        response[0] = content;
                        Log.d("SUCCESS", response[0]);
                        latch.countDown();
                    } catch (Exception ex) {
                        Log.d("ERROR", "Error Processing Get Request...");
                        for (int i = 0; i < ex.getStackTrace().length; i++) {
                            Log.d("ERROR", ex.getStackTrace()[i].toString());
                        }
                        latch.countDown();
                    }
                }

            }).start();
            latch.await();
            return response[0];
        } catch (Exception ex) {
            return "";
        }


    }
}