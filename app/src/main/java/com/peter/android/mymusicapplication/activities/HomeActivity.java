package com.peter.android.mymusicapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.peter.android.mymusicapplication.LoadSomePostsQuery;

import com.peter.android.mymusicapplication.PlayerService;
import com.peter.android.mymusicapplication.Playlist;
import com.peter.android.mymusicapplication.PlaylistHandler;
import com.peter.android.mymusicapplication.R;
import com.peter.android.mymusicapplication.Song;
import com.peter.android.mymusicapplication.adapters.AudioBlogsRvAdapter;
import com.peter.android.mymusicapplication.apollo.ApolloFactory;
import com.peter.android.mymusicapplication.models.AudioBlogModel;
import com.peter.android.mymusicapplication.models.AudioPlayerActivityModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements AudioBlogsRvAdapter.OnItemClicked {

    private ImageView ivCover;
    private SeekBar sbProgress;
    private TextView tvTime;
    private TextView tvDuration;



    private GuiReceiver receiver;
    private Handler handler = new Handler();
    private boolean blockGUIUpdate;
    private RecyclerView audioBlogRv;
    private volatile AudioPlayerActivityModel activityModel = new AudioPlayerActivityModel();
    private AudioBlogsRvAdapter audioBlogAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        audioBlogRv = findViewById(R.id.rv_audioBlog);
        setUpRv();
        ApolloFactory.getApolloClient().query(LoadSomePostsQuery.builder().build()).enqueue(new ApolloCall.Callback<LoadSomePostsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<LoadSomePostsQuery.Data> response) {
                List<LoadSomePostsQuery.Post> audioPosts = Objects.requireNonNull(response.getData()).posts();
                handler.post(() -> {
                    for(LoadSomePostsQuery.Post audioPost:audioPosts){
                        activityModel.addToListOfBlogsUI(new AudioBlogModel(audioPost));
                        activityModel.setCurrentSelected(0);
                    }
                    audioBlogAdapter.notifyDataSetChanged();

                    if (!isMyServiceRunning(PlayerService.class)) {
//            PlayerService.startActionSetPlaylist(this, playlist.getName(), 0);
                        PlayerService.startActionSetPlaylist(HomeActivity.this, activityModel);
                        PlayerService.startActionSelectAudio(HomeActivity.this, 0);
//            PlayerService.startActionPlay(this);
                    }

                    if (receiver == null) {
                        receiver = new GuiReceiver();
                        receiver.setPlayerActivity(HomeActivity.this);
                    }

                    IntentFilter filter = new IntentFilter();
                    filter.addAction(PlayerService.GUI_UPDATE_ACTION);
                    filter.addAction(PlayerService.NEXT_ACTION);
                    filter.addAction(PlayerService.SELECT_ACTION);
                    filter.addAction(PlayerService.PREVIOUS_ACTION);
                    filter.addAction(PlayerService.PLAY_ACTION);
                    filter.addAction(PlayerService.PAUSE_ACTION);
                    filter.addAction(PlayerService.LOADED_ACTION);
                    filter.addAction(PlayerService.LOADING_ACTION);
                    filter.addAction(PlayerService.DELETE_ACTION);
                    filter.addAction(PlayerService.COMPLETE_ACTION);
                    registerReceiver(receiver, filter);
                    PlayerService.startActionSendInfoBroadcast(HomeActivity.this);
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e("Apolo Error",e.toString());// should we retry ?
                Toast.makeText(HomeActivity.this,"Connection Error",Toast.LENGTH_SHORT).show();
            }
        });
        initilizeViews();
    }

    private void setUpRv() {
        audioBlogAdapter = new AudioBlogsRvAdapter(activityModel.getListOfBlogsUI(),activityModel.currentSelected, this,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        audioBlogRv.setLayoutManager(layoutManager);
        audioBlogRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        audioBlogRv.setItemAnimator(new DefaultItemAnimator());
        audioBlogRv.setAdapter(audioBlogAdapter);
    }


    private void initilizeViews() {
        ivCover = (ImageView) findViewById(R.id.ivCover);

        tvTime = (TextView) findViewById(R.id.tvTime);
        String stringActualTime = String.format("%02d:%02d", 0, 0);
        tvTime.setText(stringActualTime);


//        long s = song.getDuration() % 60;
//        long m = (song.getDuration() / 60) % 60;
//        long h = song.getDuration() / 3600;

//        String stringTotalTime;
//        if (h != 0)
//            stringTotalTime = String.format("%02d:%02d:%02d", h, m, s);
//        else
//            stringTotalTime = String.format("%02d:%02d", m, s);
        tvDuration = (TextView) findViewById(R.id.tvDuration);
//        tvDuration.setText(stringTotalTime);

        sbProgress = (SeekBar) findViewById(R.id.seekBar);
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int time;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                time = progress;

                sbProgress.setProgress(this.time);
                if (fromUser)
                    tvTime.setText(getTimeString(time));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                blockGUIUpdate = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                unblockGUIUpdate();
                setTime(time);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


        if(activityModel.currentSelected != -1) {
            if (receiver == null) {
                receiver = new GuiReceiver();
                receiver.setPlayerActivity(this);
            }

            IntentFilter filter = new IntentFilter();
            filter.addAction(PlayerService.GUI_UPDATE_ACTION);
            filter.addAction(PlayerService.NEXT_ACTION);
            filter.addAction(PlayerService.SELECT_ACTION);
            filter.addAction(PlayerService.PREVIOUS_ACTION);
            filter.addAction(PlayerService.PLAY_ACTION);
            filter.addAction(PlayerService.PAUSE_ACTION);
            filter.addAction(PlayerService.LOADED_ACTION);
            filter.addAction(PlayerService.LOADING_ACTION);
            filter.addAction(PlayerService.DELETE_ACTION);
            filter.addAction(PlayerService.COMPLETE_ACTION);
            registerReceiver(receiver, filter);
            PlayerService.startActionSendInfoBroadcast(this);
        }
    }

    private void setTime(int time) {
        PlayerService.startActionSeekTo(this, time * 1000);
    }

    private void unblockGUIUpdate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                blockGUIUpdate = false;
            }
        }, 150);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null)
            unregisterReceiver(receiver);
    }

    public void play(View view) {
        PlayerService.startActionPlay(this);
    }

    public void pause(View view) {
        PlayerService.startActionPause(this);
    }

    public void next(View view) {
        PlayerService.startActionNextSong(this);
        PlayerService.startActionSendInfoBroadcast(this);
    }

    public void previous(View view) {
        PlayerService.startActionPreviousSong(this);
        PlayerService.startActionSendInfoBroadcast(this);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private static String getTimeString(int totalTime) {
        long s = totalTime % 60;
        long m = (totalTime / 60) % 60;
        long h = totalTime / 3600;

        String stringTotalTime;
        if (h != 0)
            stringTotalTime = String.format(Locale.ENGLISH,"%02d:%02d:%02d", h, m, s);
        else
            stringTotalTime = String.format(Locale.ENGLISH,"%02d:%02d", m, s);
        return stringTotalTime;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.tv_date:
            case R.id.tv_size:
            case R.id.tv_name:
                PlayerService.startActionPause(this);
                activityModel.setCurrentSelected(position);
                audioBlogAdapter.setSelected(position);
                PlayerService.startActionSelectAudio(this,position);
                PlayerService.startActionSendInfoBroadcast(this);
                PlayerService.startActionPlay(this);
                break;

            case R.id.keepPlaying_cb:

                PlayerService.startActionKeepPlaying(this,position,((CheckBox)view).isChecked());


                break;
        }

    }

    private static class GuiReceiver extends BroadcastReceiver {

        private HomeActivity playerActivity;
        private int actualTime;

        public void setPlayerActivity(HomeActivity playerActivity) {
            this.playerActivity = playerActivity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PlayerService.GUI_UPDATE_ACTION)) {
                if (intent.hasExtra(PlayerService.TOTAL_TIME_VALUE_EXTRA)) {
                    int totalTime = intent.getIntExtra(PlayerService.TOTAL_TIME_VALUE_EXTRA, 0) / 1000;
                    if (playerActivity.sbProgress != null)
                        playerActivity.sbProgress.setMax(totalTime);
                    String stringTotalTime = getTimeString(totalTime);
                    if (playerActivity.tvDuration != null)
                        playerActivity.tvDuration.setText(stringTotalTime);
                }

                if (intent.hasExtra(PlayerService.ACTUAL_TIME_VALUE_EXTRA)) {
                    if (playerActivity.blockGUIUpdate)
                        return;

                    actualTime = intent.getIntExtra(PlayerService.ACTUAL_TIME_VALUE_EXTRA, 0) / 1000;

                    String time = getTimeString(actualTime);

                    if (playerActivity.sbProgress != null) {
                        playerActivity.sbProgress.setProgress(actualTime);
                    }
                    if (playerActivity.tvTime != null)
                        playerActivity.tvTime.setText(time);
                }

                if (intent.hasExtra(PlayerService.COVER_URL_EXTRA)) {
                    String cover = intent.getStringExtra(PlayerService.COVER_URL_EXTRA);
//                    Picasso.with(playerActivity).load(cover).fit().centerCrop().into(playerActivity.ivCover);
                }

                if (intent.hasExtra(PlayerService.SONG_NUM_EXTRA)) {
                    int num = intent.getIntExtra(PlayerService.SONG_NUM_EXTRA, 0);
                    playerActivity.activityModel.setCurrentSelected(num);
                    playerActivity.audioBlogAdapter.setSelected(num);
                    playerActivity.audioBlogAdapter.notifyDataSetChanged();

                }
            }
            if (intent.getAction().equals(PlayerService.DELETE_ACTION))
                if (playerActivity != null)
                    playerActivity.finish();
                else
                    PlayerService.startActionSendInfoBroadcast(playerActivity);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myService = new Intent(this, PlayerService.class);
        stopService(myService);
    }

}