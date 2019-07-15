package aoto.com.mylibrary;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.HashMap;

/**
 * author:why
 * created on: 2019/7/13 8:49
 * description:
 */
public class OriginalTTSManager implements WhyTTS{

    private static final String TAG = "ChinaTTSManagerWhy";
    private static volatile WhyTTS whyTTS=null;
    private TextToSpeech mSpeech;
    private String residenceContent;
    private HashMap<Integer,Long> sentenceStep=new HashMap<>();
    private long startTime=0;
    private long duration=0;
    private long charStep=225;
    private long markStep=300;

    private OriginalTTSManager(Context context){
        mSpeech=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.e(TAG, "onInit: success" );
                //mSpeech.setSpeechRate(1.0f); 默认就是1.0f
            }
        });
    }

    /**
     * DCL
     * @param context
     * @return
     */
    public static WhyTTS getInstance(Context context){
        if(whyTTS==null){
            synchronized (OriginalTTSManager.class){
                if(whyTTS==null){
                    whyTTS=new OriginalTTSManager(context);
                }
            }
        }
        return whyTTS;
    }

    @Override
    public void speak(final String content) {
        residenceContent=content;
        sentenceStep.clear();
        //getSentenceStep();
        startTime=System.currentTimeMillis();
        mSpeech.speak(content,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    public void pause(){
        duration=System.currentTimeMillis()-startTime;
        residenceContent=getResidenceByDuration(duration);
        mSpeech.speak("",TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    public void resume(){
        speak(residenceContent);
    }

    @Override
    public void setSpeechRate(float newRate) {
        if(mSpeech!=null){
            mSpeech.setSpeechRate(newRate);
            //TODO update charStep
            charStep=charStep+(long)(newRate-1.0f)*charStep;
        }
    }

    @Override
    public void setSpeechPitch(float newPitch) {
        if(mSpeech!=null){
            mSpeech.setSpeechRate(newPitch);
        }
    }

    private String getResidenceByDuration(long duration){
        int tempIndex= (int) (duration/charStep);
        if(duration > (charStep * residenceContent.length())){
           return "";
        }
        residenceContent=residenceContent.substring(tempIndex-1);
        return residenceContent;
//        int index=findSentenceIndex(duration);
//        if(index==-1){
//            return "";
//        }else {
//            residenceContent=residenceContent.substring((int)((duration-sentenceStep.get(index-1))/charStep)+1);
//            return residenceContent;
//        }
    }

    /**
     * 根据朗读时间计算当前读到句子索引
     * @param duration
     */
    private int findSentenceIndex(long duration) {
        for(int i=0;i<sentenceStep.size()-1;i++){
            if(duration<=sentenceStep.get(i+1)&&duration>=sentenceStep.get(i)){
                return i+1;
            }
        }
        if(duration>sentenceStep.get(sentenceStep.size()-1)){
            return -1;
        }
        return -2;
    }

    /**
     * get the mark index list
     */
    private void getSentenceStep() {
        String[] array=residenceContent.split("，");
        if(array.length<=1){
            return;
        }else {
            for(int i=0;i<array.length;i++){
                long tempTime=0;
                for(int j=0;j<=i;j++){
                    tempTime+=array[j].length()*charStep;
                }
                tempTime+=(i+1)*markStep;
                sentenceStep.put(i,tempTime);
            }
        }
    }

    @Override
    public void release(){
        mSpeech.shutdown();
        mSpeech.stop();
    }

}
