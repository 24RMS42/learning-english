package timer.fityfor.me.beginner.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import timer.fityfor.me.beginner.controllers.DataController;
import timer.fityfor.me.beginner.controllers.ViewController;
import timer.fityfor.me.beginner.interfacies.OnPlayCompliteListener;

import static timer.fityfor.me.beginner.fragments.LearnFragment.canClick;

/**
 * Created by Hovhannisyan.Karo on 23.08.2017.
 */

public class SoundHelper {
    private static SoundHelper INSTANCE = null;
    private final Context context;
    private final String packageName;
    MediaPlayer mp;

    public static SoundHelper getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new SoundHelper();
            } catch (NullPointerException ex) {
                Log.i("SoundHelper()", "didn't create");
            }
        }
        return INSTANCE;
    }

    private SoundHelper() {
        this.context = ViewController.getViewController().getContex();
        this.packageName = context.getApplicationInfo().packageName;
    }

    public void playTrack(int trackId, final OnPlayCompliteListener onPlayCompliteListener) {
        //DataController.getInstance().setCanTouch(false);
        stopPlayer();
        mp = MediaPlayer.create(context, trackId);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                onPlayCompliteListener.onComplite();
                DataController.getInstance().setCanTouch(true);
            }
        });

        mp.start();
        mp.setLooping(false);
    }

    public void playTrack(Uri trackId, final OnPlayCompliteListener onPlayCompliteListener) {
       // DataController.getInstance().setCanTouch(false);

        try {
            stopPlayer();
        } catch (Exception ex) {
            Log.i("stopPlayer()", "Can't stop player!");
        }

        if (mp == null) {
            try {
                mp = MediaPlayer.create(context, trackId);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        onPlayCompliteListener.onComplite();
                        DataController.getInstance().setCanTouch(true);
                        canClick = true;
                    }
                });

                mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                        return true;
                    }
                });

                LogUtils.d("BAG", "trackId " + trackId.toString());
                LogUtils.d("BAG", "mediaPlayer " + mp.toString());

                mp.start();
                mp.setLooping(false);
            } catch (Exception e) {
                Log.e("Media Player", "Can't create media player");
            }
        }
    }

    public void stopPlayer() {
        if (mp != null) {
            mp.reset();
            mp.release();
            mp = null;
        }
    }
}