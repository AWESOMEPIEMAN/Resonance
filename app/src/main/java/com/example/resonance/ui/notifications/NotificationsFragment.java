package com.example.resonance.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class NotificationsFragment extends Fragment{
    private String text = "";
    private NotificationsViewModel notificationsViewModel;
    private  String BASE_URL = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyAgl_tVAk1eyfsSfDOI-RPF8kiEerOVKhY&part=snippet,id&order=viewCount&maxResults=1&q=";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final int random = new Random().nextInt(9) +1;
        if (random == 1){
            text = "despacito";
        }
        else if (random == 2){text = "Hello - Adele";}
        else if (random == 3){text = "Fresh eyes - Andy Grammer";}
        else if (random == 4){text = "Rise Up - Andra Day";}
        else if (random == 5){text = "Hall of Fame";}
        else if (random == 6){text = "Heal The World";}
        else if (random == 7){text = "Photograph";}
        else if (random == 8){text = "Shape Of You";}
        else if (random == 9){text = "Never Gonna Give You Up - Rick Astley";}
        else {text = "Call Me Maybe";};

        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        TextView videoname = (TextView) v.findViewById(R.id.imageUpload);
        Button b = (Button) v.findViewById(R.id.uploadBtn);
        YouTubePlayerView youTubePlayerView = v.findViewById(R.id.youtube_player_view);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONWTF","YouTube button worked");

                getLifecycle().addObserver(youTubePlayerView);
                String text1 = text.replaceAll("\\s","%20");

                String videolink = requestData(BASE_URL + text1);
                Log.d("BUTTONWTF",videolink);

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
                    Log.d("FUCKYOU",name);

                } catch (JSONException e) {
                    Log.d("ONREADY",e.getMessage());
                    e.printStackTrace();
                }

                videoname.setText(name);
                String videoId = "lOqy8cC72wA";
                String finalVideo = video;
                if (video == null){Log.d("TEXTWTF","no video ID");}
                else {
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(finalVideo, 0);

                }
            });
            }}
            });

        return v;
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