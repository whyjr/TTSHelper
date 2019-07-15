package aoto.com.mylibrary;

/**
 * author:why
 * created on: 2019/7/13 10:56
 * description: TTS interface
 */
public interface WhyTTS {
     void speak(final String content);
     void pause();
     void resume();
     void setSpeechRate(float newRate);
     void setSpeechPitch(float newPitch);
     void release();
     //WhyTTS getInstance();
}
