package com.michen.olaxueyuan.common.manager;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.michen.olaxueyuan.app.MyConfig;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kevin on 15/9/29.
 */
public class MyAudioManager implements AudioManager.OnAudioFocusChangeListener {
    /**
     * @author kevin
     * @version 1.0
     * @Description: 音频播放监听回调
     * @date 2015-9-29
     */
    public interface PlayingListener {
        public void onLoadingUpdate(MediaPlayer mp, int percent, int position, String dataUrl);

        public void onLoadingError(MediaPlayer mp, int what, int extra, int position, String dataUrl);

        public void onLoadingComplete(MediaPlayer mp, int position, String dataUrl);

        /**
         * @param pos
         * @Description: 播放中的进度更新，更新的是百分比
         */
        public void updateProcess(long pos, int second, int position, String dataUrl);

        public void onPlayingComplete(MediaPlayer mp, int duration, int position, String dataUrl);

        public void onStopPlaying(int position, String dataUrl);

        public void onPlayingFailed(Object e, int position, String dataUrl);

    }

    /**
     * @version 1.0
     * @Description: 录音监听回调
     */
    public interface RecordingListener {
        public void updateRecordingTime(int current, long currentTimeMillis);

        public void onRecordingStart(long startTimeMillis);

        public void onAmplitudesUpdate(int level);

        public void onRecordingFailed(Object reason);

        public void onRecordingComplete(String filePath, long duration);
    }


    public static final int MESS_AMPLITUDE_ONE = 501;
    public static final int MESS_AMPLITUDE_TWO = 502;
    public static final int MESS_AMPLITUDE_THREE = 503;
    public static final int MESS_AMPLITUDE_FOUR = 504;
    protected static final String TAG = "MyAudioManager";

    private MediaRecorder mMediaRecorder;
    private MediaPlayer mMediaPlayer;
    private String mFilePath;
    private long mAudioStartTime;
    private long mAudioStopTime;

    private String mVoicePath;

    private PlayingListener mPlayingListener;

    private RecordingListener mRecordingListener;

    private Timer mTimer = new Timer();
    private PlayingTimerTask mPlayingTimerTask;
    private RecordingTimerTask mRecordingTimerTask;
    private AmplitudeUpdateTimerTask mAmplitudeUpdateTimerTask;

    private Activity mContext;

    private static final int MSG_UPDATE_PLAYING = 0;
    private static final int MSG_UPDATE_RECORDING = 1;
    private static final int MSG_UPDATE_AMPLITUDE = 2;

    private String voiceUrl;

    public MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }

    public void setMode(int mode) {
        if (audioManager != null) {
            Logger.e("sensor changed11111111111 mode:" + mode);
//            switch (mode) {
//                case AudioManager.MODE_NORMAL:
//                    audioManager.setSpeakerphoneOn(true);
//                    audioManager.setMode(mode);
//                    break;
//                case AudioManager.MODE_IN_COMMUNICATION:
//                    if (audioManager.isWiredHeadsetOn()) {
//                        return;
//                    }
//                    audioManager.setSpeakerphoneOn(false);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//                    } else {
//                        audioManager.setMode(AudioManager.MODE_IN_CALL);
//                    }
//                    break;
//                default:
//                    audioManager.setSpeakerphoneOn(true);
//                    audioManager.setMode(mode);
//                    break;
//            }

        }
    }


    /**
     * @Description: 恢复屏幕休眠设置
     * @author Kevin Lin
     * @date 2014-4-3
     */
    private void resetScreenOn() {
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * @Description: 禁止屏幕休眠
     * @author Kevin Lin
     * @date 2014-4-3
     */
    private void keepScreenOn() {
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * @author Kevin Lin
     * @version 1.0
     * @Description: 更新播放进度
     * @date 2014-1-14
     */
    private class PlayingTimerTask extends TimerTask {
        public void run() {
            if (this == null || mMediaPlayer == null) {
                return;
            }
            try {
                if (mMediaPlayer.isPlaying()) {
                    mHandler.sendEmptyMessage(MSG_UPDATE_PLAYING);
                }
            } catch (IllegalStateException e) {
                Logger.e(TAG + e.toString());
            }

        }
    }

    private static MyAudioManager instance;

    public static MyAudioManager getIntance(PlayingListener l, Activity context) {
        if (instance == null) {
            instance = new MyAudioManager(l, context);
        }
//        if(instance.getmPlayingListener() != null){
//            instance.getMediaPlayer().stop();
//            instance.setPlayingListener(null);
//        }
        instance.setPlayingListener(l);
        return instance;
    }

    public static MyAudioManager getIntance(Activity context) {
        if (instance == null) {
            instance = new MyAudioManager(context);
        }
        return instance;
    }


    /**
     * @author Kevin Lin
     * @version 1.0
     * @Description: 更新录音时长计时
     * @date 2014-1-14
     */
    private class RecordingTimerTask extends TimerTask {
        public void run() {
            if (this == null || mMediaRecorder == null) {
                return;
            }
            mHandler.sendEmptyMessage(MSG_UPDATE_RECORDING);
        }
    }

    /**
     * @author Kevin Lin
     * @version 1.0
     * @Description: 录音中更新音频峰值
     * @date 2014-1-14
     */
    public class AmplitudeUpdateTimerTask extends TimerTask {
        public void run() {
            if (this == null || mMediaRecorder == null) {
                return;
            }
            mHandler.sendEmptyMessage(MSG_UPDATE_AMPLITUDE);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_PLAYING:
                    if (this == null || mMediaPlayer == null) {
                        return;
                    }

                    int pp = mMediaPlayer.getCurrentPosition();
                    int duration = mMediaPlayer.getDuration();
                    Logger.i(TAG + "position = " + pp + "|duration = " + duration);
                    if (duration > 0) {
                        long pos = 100 * pp / duration;
                        if (mPlayingListener != null) {
                            mPlayingListener.updateProcess(pos, pp / 1000, listPosition, voiceUrl);
                        }
                    }
                    break;
                case MSG_UPDATE_RECORDING:

                    long current = System.currentTimeMillis();
                    int time = (int) ((current - mAudioStartTime) / 1000);

                    if (mRecordingListener != null) {
                        mRecordingListener.updateRecordingTime(time, current);
                    }

                    break;
                case MSG_UPDATE_AMPLITUDE:
                    if (mMediaRecorder == null) {
                        break;
                    }
                    int x = mMediaRecorder.getMaxAmplitude();
                    if (x != 0) {
                        int f = (int) (10 * Math.log(x) / Math.log(10));
                        int amplitude = MESS_AMPLITUDE_ONE;
                        if (f < 26) {
                            amplitude = MESS_AMPLITUDE_ONE;
                        } else if (f < 32) {
                            amplitude = MESS_AMPLITUDE_TWO;
                        } else if (f < 38) {
                            amplitude = MESS_AMPLITUDE_THREE;
                        } else {
                            amplitude = MESS_AMPLITUDE_FOUR;
                        }
                        if (mRecordingListener != null) {
                            mRecordingListener.onAmplitudesUpdate(amplitude);
                        }
                    }
                    break;
                default:
                    break;
            }

        }

    };

    private AudioManager audioManager;

    public boolean requestFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    public boolean abadonFOucus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == audioManager.abandonAudioFocus(this);
    }

    @Override
    public void onAudioFocusChange(int i) {
        Logger.e("onAudioFocusChange======================" + i);
        switch (i) {
            case AudioManager.AUDIOFOCUS_GAIN://你已经得到了音频焦点。
                Logger.e("AudioManager.AUDIOFOCUS_GAIN========================");
                break;
            case AudioManager.AUDIOFOCUS_LOSS://你已经失去了音频焦点很长时间了。你必须停止所有的音频播放。因为你应该不希望长时间等待焦点返回，这将是你尽可能清除你的资源的一个好地方。例如，你应该释放MediaPlayer。
                Logger.e(" AudioManager.AUDIOFOCUS_LOSS========================");
                stopPlaying();
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT://你暂时失去了音频焦点，但很快会重新得到焦点。你必须停止所有的音频播放，但是你可以保持你的资源，因为你可能很快会重新获得焦点。
                Logger.e("AudioManager.AUDIOFOCUS_LOSS_TRANSIENT========================");
                if (isPlaying()) {
                    mMediaPlayer.pause();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK://你暂时失去了音频焦点，但你可以小声地继续播放音频（低音量）而不是完全扼杀音频。
                Logger.e("AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK========================");
                if (isPlaying()) {
                    mMediaPlayer.pause();
                }
                break;
        }
    }

    private MyAudioManager(Activity context) {
        mMediaPlayer = new MediaPlayer();
        mContext = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    private MyAudioManager(PlayingListener l, Activity context) {
        mPlayingListener = l;
        mMediaPlayer = new MediaPlayer();
        mContext = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }


    /**
     * @param l
     * @Description: 传入播放监听回调
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public void setPlayingListener(PlayingListener l) {
        stopPlaying();
        mPlayingListener = l;
    }

    public PlayingListener getmPlayingListener() {
        return mPlayingListener;
    }

    /**
     * @param l
     * @Description: 传入录音监听回调
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public void setRecordingListener(RecordingListener l) {
        mRecordingListener = l;
    }

    /**
     * @return
     * @Description: 获取音频播放MediaPlayer
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    /**
     * @param path
     * @Description: 设置音频播放地址
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public void setPlayingVoicePath(String path) {
        mVoicePath = path;
    }

    /**
     * @return
     * @Description: 获取音频播放地址
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public String getPlayingVoicePath() {
        return mVoicePath;
    }

    /**
     * @return
     * @Description: 获取录音文件本地路径
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public String getRecordingFilePath() {
        return mFilePath;
    }

    /**
     * @return
     * @Description: 获取录音开始时间(ms)
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public long getRecordingStartTime() {
        return mAudioStartTime;
    }

    /**
     * @return
     * @Description: 获取录音结束时间(ms)
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public long getRecordingStoptTime() {
        return mAudioStopTime;
    }

    /**
     * @return
     * @Description: 获取录音时长(s)
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public long getRecordingDuration() {
        long duration = (mAudioStopTime - mAudioStartTime) / 1000;
        if (duration >= MyConfig.SYS_AUDIO_MAX_DURATION) {
            duration = MyConfig.SYS_AUDIO_MAX_DURATION;
        }
        return duration;
    }

    /**
     * @return
     * @Description: 是否正在播放
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    /**
     * @return
     * @Description: 是否正在录音
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public boolean isRecording() {
        if (mMediaRecorder != null) {
            return true;
        }
        return false;
    }

    /**
     * @Description: 开始录音
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public void startRecording() {
        if (!AndroidUtil.hasAudioPermission()) {
            return;
        }
        if (mMediaRecorder != null) {
            stopRecording();
        }

        String outFileDir = CommonConstant.RECORD_PATH;
        File fileDir = new File(outFileDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        mFilePath = outFileDir + "/" + System.currentTimeMillis() + ".amr";
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mMediaRecorder.setOutputFile(mFilePath);

        try {
            mAudioStartTime = System.currentTimeMillis();
            if (mRecordingListener != null) {
                mRecordingListener.onRecordingStart(mAudioStartTime);
            }

            mMediaRecorder.prepare();
            mMediaRecorder.start();
            startRecordingTimer();
            keepScreenOn();
        } catch (Exception e) {
            mAudioStartTime = 0;
            stopRecording();
        }
    }

    /**
     * @Description: 结束录音
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public void stopRecording() {
        stopRecordingTimer();
        // 计算时间
        if (mAudioStartTime == 0) {
            mAudioStopTime = -1000;
        } else {
            mAudioStopTime = System.currentTimeMillis();
        }

        // 终止录音
        if (mMediaRecorder != null) {
            try {
                mMediaRecorder.stop();
                mMediaRecorder.release();
            } catch (Exception e) {
                mAudioStopTime = mAudioStartTime - 1000;
            }
            mMediaRecorder = null;
        } else {
            mAudioStopTime = mAudioStartTime - 1000;
        }

        if (getRecordingDuration() > 0) {
            if (mRecordingListener != null) {
                mRecordingListener.onRecordingComplete(mFilePath, getRecordingDuration());
            }
        } else if (getRecordingDuration() == 0) {
            if (mRecordingListener != null) {
                mRecordingListener.onRecordingFailed("录音时间太短");
            }
        } else {
            if (mRecordingListener != null) {
                mRecordingListener.onRecordingFailed("录音失败");
            }
        }
        resetScreenOn();
    }

    public boolean startPlayingVoice(final String voiceUrl) {
        mVoicePath = voiceUrl;
        /**
         * here need to add new method to download message
         */

        return true;
    }

    /**
     * @param voiceUrl 音频地址
     * @return
     * @Description: 播放音频
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public boolean startPlayingVoiceByCache(final String voiceUrl) {
        listPosition = -1;
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                stopPlaying();
            }
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    if (mPlayingListener != null) {
                        mPlayingListener.onPlayingComplete(mp, mMediaPlayer.getDuration() / 1000, listPosition, voiceUrl);
                    }
                    if (mMediaPlayer != null) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                    stopVoicePlayingTimer();
                }
            });
            mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    if (mPlayingListener != null) {
                        mPlayingListener.onLoadingUpdate(mp, percent, listPosition, voiceUrl);
                    }
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (mPlayingListener != null) {
                        mPlayingListener.onLoadingError(mp, what, extra, listPosition, voiceUrl);
                    }
                    stopVoicePlayingTimer();
                    return false;
                }
            });
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    if (mPlayingListener != null) {
                        mPlayingListener.onLoadingComplete(mp, listPosition, voiceUrl);
                        startVoicePlayingTimer();
                    }
                }
            });
            mMediaPlayer.setDataSource(voiceUrl);
            mMediaPlayer.prepareAsync();
            audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        } catch (Exception e) {
            if (mPlayingListener != null) {
                mPlayingListener.onPlayingFailed(e, listPosition, voiceUrl);
            }
            return false;
        }
        return true;
    }

    /**
     * 带回调参数的play
     *
     * @param voiceUrl
     * @param position
     * @return
     */
    private int listPosition = -1;

    public boolean startPlayingVoiceByCache(final String voiceUrl, int p) {
        this.voiceUrl = voiceUrl;
        this.listPosition = p;
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                stopPlaying();
            }
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    if (mPlayingListener != null) {
                        mPlayingListener.onPlayingComplete(mp, mMediaPlayer.getDuration() / 1000, listPosition, voiceUrl);
                    }
                    if (mMediaPlayer != null) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                    stopVoicePlayingTimer();
                }
            });
            mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    if (mPlayingListener != null) {
                        mPlayingListener.onLoadingUpdate(mp, percent, listPosition, voiceUrl);
                    }
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (mPlayingListener != null) {
                        mPlayingListener.onLoadingError(mp, what, extra, listPosition, voiceUrl);
                    }
                    stopVoicePlayingTimer();
                    return false;
                }
            });
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    if (mPlayingListener != null) {
                        mPlayingListener.onLoadingComplete(mp, listPosition, voiceUrl);
                        startVoicePlayingTimer();
                    }
                }
            });
            mMediaPlayer.setDataSource(voiceUrl);
            mMediaPlayer.prepareAsync();
            audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        } catch (Exception e) {
            if (mPlayingListener != null) {
                mPlayingListener.onPlayingFailed(e, listPosition, voiceUrl);
            }
            return false;
        }
        return true;
    }

    /**
     * @Description: 终止播放视频
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public void stopPlaying() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        stopVoicePlayingTimer();
        if (mPlayingListener != null) {
            mPlayingListener.onStopPlaying(listPosition, voiceUrl);
        }
    }

    public void pausePlaying() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    /**
     * @Description: 开启播放进度更新Timer
     * @author Kevin Lin
     * @date 2014-1-14
     */
    private void startVoicePlayingTimer() {
        if (mPlayingTimerTask != null) {
            mPlayingTimerTask.cancel();
        }
        mPlayingTimerTask = new PlayingTimerTask();
        mTimer.schedule(mPlayingTimerTask, 0, 500);
    }

    /**
     * @Description: 终止播放进度更新Timer
     * @author Kevin Lin
     * @date 2014-1-14
     */
    private void stopVoicePlayingTimer() {
        if (mPlayingTimerTask != null) {
            mPlayingTimerTask.cancel();
        }
    }

    /**
     * @Description: 开启录音时间进度更新Timer
     * @author Kevin Lin
     * @date 2014-1-14
     */
    private void startRecordingTimer() {
        if (mRecordingTimerTask != null) {
            mRecordingTimerTask.cancel();
        }
        mRecordingTimerTask = new RecordingTimerTask();
        mTimer.schedule(mRecordingTimerTask, 0, 1000);

        // 开启录音过程中,音频峰值更新Timer
        startAmplitudeUpdateTimer();
    }

    /**
     * @Description: 终止录音时间进度更新Timer
     * @author Kevin Lin
     * @date 2014-1-14
     */
    private void stopRecordingTimer() {
        if (mRecordingTimerTask != null) {
            mRecordingTimerTask.cancel();
        }
        // 终止录音过程中,音频峰值更新Timer
        stopAmplitudeUpdateTimer();
    }

    /**
     * @Description: 开启录音过程中, 音频峰值更新Timer
     * @author Kevin Lin
     * @date 2014-1-14
     */
    private void startAmplitudeUpdateTimer() {
        if (mAmplitudeUpdateTimerTask != null) {
            mAmplitudeUpdateTimerTask.cancel();
        }
        mAmplitudeUpdateTimerTask = new AmplitudeUpdateTimerTask();
        mTimer.schedule(mAmplitudeUpdateTimerTask, 0, 200);
    }

    /**
     * @Description: 终止录音过程中, 音频峰值更新Timer
     * @author Kevin Lin
     * @date 2014-1-14
     */
    private void stopAmplitudeUpdateTimer() {
        if (mAmplitudeUpdateTimerTask != null) {
            mAmplitudeUpdateTimerTask.cancel();
        }
    }


}
