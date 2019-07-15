package aoto.com.ttstest;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import aoto.com.mylibrary.MediaTTSManager;
import aoto.com.mylibrary.OriginalTTSManager;
import aoto.com.mylibrary.WhyTTS;

/**
 * @author why
 * @date 2019-7-13 8:46:57
 */
public class Test1Activity extends AppCompatActivity {

    private WhyTTS whyTTS;
    private EditText ttsEditor;
    String testText="What can I do for you,please tell me";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ttsEditor=findViewById(R.id.tts_content);
        //Achieve TTS pause and resume by Android original TTS and MediaPlayer
        whyTTS= MediaTTSManager.getInstance(this);
        //Achieve TTS pause and resume by Android original TTS
        //whyTTS= OriginalTTSManager.getInstance(this);
    }

    public void startTest(View view){
        if(!ttsEditor.getText().toString().isEmpty()){
            whyTTS.speak(ttsEditor.getText().toString());
        }
        else {
            whyTTS.speak(testText);
        }
    }

    public void pause(View view){
        whyTTS.pause();
    }

    public void resume(View view){
        whyTTS.resume();
    }
}
