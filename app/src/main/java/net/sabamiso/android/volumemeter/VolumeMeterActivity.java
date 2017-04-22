//
// VolumeMeterView.java - How to use AudioRecord class...
//
// GitHub:
//     https://github.com/yoggy/VolumeMeter
//
// license:
//     Copyright (c) 2017 yoggy <yoggy0@gmail.com>
//     Released under the MIT license
//     http://opensource.org/licenses/mit-license.php;
//
package net.sabamiso.android.volumemeter;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class VolumeMeterActivity extends Activity implements Runnable {
    public final int PERMISSIONS_REQUEST_CODE_RECORD_AUDIO = 123;

    VolumeMeterView view;

    AudioRecord rec;
    Thread thread;
    boolean break_flag;
    short[] rec_buf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);

        view = new VolumeMeterView(this);
        setContentView(view);
        view.setup(); // don't forget to call setup()!

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0);
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE_RECORD_AUDIO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume(); // don't forget to call onResume()!
        hideSystemUI();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                startRecording();
            }
        }
    }

    @Override
    protected void onPause() {
        view.onPause(); // don't forget to call onPause()!
        super.onPause();
        stopRecording();
    }

    // immersive fullscreen mode
    private void hideSystemUI() {
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    void startRecording() {
        int buf_size = AudioRecord.getMinBufferSize(
                44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        rec_buf = new short[buf_size];

        rec = new AudioRecord(
                MediaRecorder.AudioSource.DEFAULT,
                44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                buf_size);
        rec.startRecording();

        thread = new Thread(this, "VolumeMeter::AudioReadThread");
        thread.start();
    }

    void stopRecording() {
        if (thread != null) {
            break_flag = true;
            try {
                thread.join();
            } catch (InterruptedException e) {
            }

            rec.stop();
            rec.release();
            rec = null;
        }
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(
                android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

        break_flag = false;
        while(!break_flag) {
            int size = rec.read(rec_buf, 0, rec_buf.length);
            if (size <= 0) {
                return;
            }

            int max_val = 0;
            for (int i = 0; i < size; i += 3) {
                int val = Math.abs(rec_buf[i]);
                if (val > max_val) max_val = val;
            }

            if (view != null) {
                view.setVolumeLevel(max_val / (float)Short.MAX_VALUE);
            }
        }
    }
}
