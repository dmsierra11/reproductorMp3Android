package com.danieluchin.reproductormp3;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private static final String PLACEHOLDER="file:///android_asset/";

    @BindView(R.id.playerView)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.twiceSpeed)
    Button twiceSpeed;
//    @BindView(R.id.btnPlay)
//    Button btnPlay;

    private SimpleExoPlayer player;
//    private ExoPlayer player;

    private String audioMP4;
    private Uri mp4AudioUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mp4AudioUri = getAudioFile();

        createVideoPlayer();
        prepareVideoPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Create an ExoPlayer instance using ExoPlayerFactory. The factory provides a range of methods
     * for creating ExoPlayer instances with varying levels of customization.
     */
    public void createVideoPlayer() {
        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
//                new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3. Create the videoPlayer
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);



        simpleExoPlayerView.setPlayer(player);
    }

    /**
     * Shows how to prepare the videoPlayer with a MediaSource suitable for playback of an MP4 file.
     */
    public void prepareVideoPlayer() {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "yourApplicationName"), bandwidthMeter);
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(mp4AudioUri,
                dataSourceFactory, extractorsFactory, null, null);
        // Prepare the videoPlayer with the source.
        player.prepare(videoSource);
    }

    public void releaseVideoPlayer(){
        player.release();
    }

    @OnClick(R.id.twiceSpeed)
    public void doubleSpeed(){
        Toast.makeText(this, "Double speed", Toast.LENGTH_SHORT).show();
    }

    public Uri getAudioFile(){
        String[] assets = Utils.getInstance().getAssets();
        Log.d(TAG, assets.toString());
        audioMP4 = assets[0];
        return Uri.parse(PLACEHOLDER+audioMP4);
    }
}
