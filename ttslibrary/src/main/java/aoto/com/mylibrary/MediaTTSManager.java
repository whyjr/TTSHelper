package aoto.com.mylibrary;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

/**
 * author:why
 * created on: 2019/7/10 9:57
 * description:
 */
public class MediaTTSManager implements WhyTTS {

    private static final String TAG = "TTSManagerWhy";
    private static volatile WhyTTS manager = null;
    private static TextToSpeech mSpeech = null;
    private Context mContext;
    private String wavPath;
    private MediaPlayer player;
    private HashMap<String, String> myHashRender = new HashMap();

    @RequiresApi(api = Build.VERSION_CODES.M)
    private MediaTTSManager(Context context) {
        this.mContext = context;
        wavPath = Environment.getExternalStorageDirectory() + "/temp.wav";
        player = new MediaPlayer();
        initSpeech();
    }

    /**
     * Init TTS and set params
     */
    private void initSpeech() {
        mSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // mSpeech.setLanguage(Locale.ENGLISH);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static WhyTTS getInstance(Context context) {
        if (manager == null) {
            synchronized (MediaTTSManager.class) {
                if (manager == null) {
                    manager = new MediaTTSManager(context);
                }
            }
        }
        return manager;
    }


    @Override
    public void speak(String content) {
        Log.e(TAG, "speak content: " + content);
        myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, content);
        int r = mSpeech.synthesizeToFile(content, myHashRender, wavPath);
        if (r == TextToSpeech.SUCCESS) {
            Log.e(TAG, "save success" + wavPath);
        } else {
            Log.e(TAG, "save fail");
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            player.reset();
            player.setDataSource(wavPath);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }


    /**
     * pause the TTS
     */
    @Override
    public void pause() {
        if (player.isPlaying()) {
            player.pause();
        }
    }

    /**
     * reset the TTS
     */
    @Override
    public void resume() {
        player.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setSpeechRate(float newRate) {
        //6.0+可以设置
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            player.setPlaybackParams(player.getPlaybackParams().setSpeed(newRate));
        } else {
            Log.e(TAG, "setSpeechRate: 版本过低，接口不可用");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setSpeechPitch(float newPitch) {
        //6.0+可以设置
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            player.setPlaybackParams(player.getPlaybackParams().setPitch(newPitch));
        } else {
            Log.e(TAG, "setSpeechPitch: 版本过低，接口不可用");
        }

    }

    /**
     * stop the TTS
     */
    @Override
    public void release() {
        player.stop();
        player.release();
        mSpeech.shutdown();
        mSpeech.stop();
    }

}
