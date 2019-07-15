# TTSHelper
This repository contains the Android TTS(TextToSpeech) funtions and besides that, you can also achieve the pause and resume ability by
the MediaPlayer and other ways.

# Getting started
## Dependency management tools
Below is a brief guide to using dependency management tools with gradle.

## Gradle
In the project build.gradle file, you should add the following dependency:
```
 maven { url "https://jitpack.io"}
```
Be careful of it's position, here is an example,
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io"}
    }
}
```
In the module build.gradle file,you should add the following dependency:
```
 implementation 'com.github.whyjr:TTSHelper:-SNAPSHOT'
```

## Usage
You can create a WhyTTS instance by MediaTTSManager or OriginalTTSManager by the following code:
```
 whyTTS= MediaTTSManager.getInstance(this);
 //whyTTS= OriginalTTSManager.getInstance(this);
```
And then, you can speak(String content),pause() and so on to controll TTS, here is an example:
```
whyTTS.speak(" speak anything you want ");
```

## About Chinese
If you want your device to speak Chinese ,you should firstly install an Chinese TTS engine, you can refer the following blog to
 learn more details about the setting.
 
 [**https://blog.csdn.net/hfut_why/article/details/80587630**]
 
