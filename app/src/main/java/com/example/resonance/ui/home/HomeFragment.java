package com.example.resonance.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    String videoId="uj3nFNIV2jY";
    String videoId2="Msl2fl3h59I";
    String videoId3="mk48xRzuNvA";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView videoname = (TextView) v.findViewById(R.id.imageUpload);
        Button b = (Button) v.findViewById(R.id.uploadBtn);
        YouTubePlayerView youTubePlayerView = v.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
         @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
            youTubePlayer.cueVideo(videoId, 0);
         }
        });
        YouTubePlayerView youTubePlayerView2 = v.findViewById(R.id.youtube_player_view2);
        getLifecycle().addObserver(youTubePlayerView2);
        youTubePlayerView2.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer2) {
                youTubePlayer2.cueVideo(videoId2, 0);
            }
        });

        YouTubePlayerView youTubePlayerView3 = v.findViewById(R.id.youtube_player_view3);
        getLifecycle().addObserver(youTubePlayerView3);
        youTubePlayerView3.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer3) {
                youTubePlayer3.cueVideo(videoId3, 0);
            }
        });



        return v;
    }


}