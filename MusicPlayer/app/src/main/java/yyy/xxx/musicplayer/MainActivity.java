package yyy.xxx.musicplayer;

import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static yyy.xxx.musicplayer.R.raw.shelovesyou;

public class MainActivity extends AppCompatActivity {

    private Button StartButton01, PauseButton01, StopButton01;
    private Button StartButton02, PauseButton02, StopButton02;
    private Button StartButton03, PauseButton03, StopButton03;
    private TextView Title01, Title02, Title03;
    private TextView DurationTime01, DurationTime02, DurationTime03;
    private SeekBar SeekBar01, SeekBar02, SeekBar03;
    private MediaPlayer mp01, mp02, mp03;

    static TextView name1, name2, name3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *  3개의 음악파일이 정지, 일시정지, 재생 버튼넣고 구현.
         * */

        //AssetFileAccess();
       // MusicInit();


        name1 = (TextView) findViewById(R.id.Music01_title);
        name2 = (TextView) findViewById(R.id.Music02_title);
        name3 = (TextView) findViewById(R.id.Music03_title);

        SeekBar01 = (SeekBar) findViewById(R.id.Music01_SeekBar);
        SeekBar02 = (SeekBar) findViewById(R.id.Music02_SeekBar);
        SeekBar03 = (SeekBar) findViewById(R.id.Music03_SeekBar);

        DurationTime01 = (TextView) findViewById(R.id.DurationTime01);
        DurationTime02 = (TextView) findViewById(R.id.DurationTime02);
        DurationTime03 = (TextView) findViewById(R.id.DurationTime03);

        GetTitleNameByAsset();
        MusicEachBtn();

    }

    private void GetTitleNameByAsset() {
        AssetManager assetManager = getAssets();

        try {

            String[] files = assetManager.list("");

            String file01 = files[1];
            name1.setText(file01);
            String file02 = files[4];
            name2.setText(file02);
            String file03 = files[5];
            name3.setText(file03);

            String names[] = files.clone();
            for (int i = 0; i < names.length; i++) {
                String name = names[i]; //1 , 4 , 5;
                Log.e("이름", name);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void AssetFileAccess() {

        AssetManager assetManager = getAssets();
        String[] files = null;
        String mkdir = null;
        try {
            files = assetManager.list("");

            //이미지만 가져올때는 files = assetManager.list("image");

        }catch (IOException e){
            Log.e("tag", e.getMessage());
        }
        for (int i = 0; i <files.length; i++){
            InputStream in = null;
            OutputStream out = null;
            try{
                in = assetManager.open(files[i]);
                // 폴더 생성
                String str = Environment.getExternalStorageState();
                if(str.equals(Environment.MEDIA_MOUNTED)) {
                    mkdir = "/sdcard/elecgal/templet/";
                } else {
                    Environment.getExternalStorageDirectory();
                }
                File mpath = new File(mkdir);
                if(!mpath.isDirectory()){
                    mpath.mkdirs();
                }

                //
                out = new FileOutputStream("/sdcard/elecgal/templet/" + files[i]);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;

            }catch (Exception e){
                Log.e("tag", e.getMessage());
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void MusicInit() {

        Title01 = (TextView) findViewById(R.id.Music01_title);
        Title02 = (TextView) findViewById(R.id.Music02_title);
        Title03 = (TextView) findViewById(R.id.Music03_title);

        Title01.setText(getResources().getResourceName(R.raw.shelovesyou));
        Title02.setText(getResources().getResourceName(R.raw.yellowsubmarine));
        Title03.setText(getResources().getResourceName(R.raw.yesterday));

    }
    private void MusicEachBtn(){

        mp01 = MediaPlayer.create(this, shelovesyou);
        mp02 = MediaPlayer.create(this, R.raw.yellowsubmarine);
        mp03 = MediaPlayer.create(this, R.raw.yesterday);

        ButtonClick();

        initSeekBar(mp01,DurationTime01,SeekBar01);
        initSeekBar(mp02,DurationTime02,SeekBar02);
        initSeekBar(mp03,DurationTime03,SeekBar03);

    }
    private void ButtonClick(){

        //객체 찾기
        StartButton01 = (Button) findViewById(R.id.Music01_StartBtn);
        StartButton02 = (Button) findViewById(R.id.Music02_StartBtn);
        StartButton03 = (Button) findViewById(R.id.Music03_StartBtn);

        PauseButton01 = (Button) findViewById(R.id.Music01_PausetBtn);
        PauseButton02 = (Button) findViewById(R.id.Music02_PausetBtn);
        PauseButton03 = (Button) findViewById(R.id.Music03_PausetBtn);

     //   StopButton01 = (Button) findViewById(R.id.Music01_StopBtn);
     //   StopButton02 = (Button) findViewById(R.id.Music02_StopBtn);
     //   StopButton03 = (Button) findViewById(R.id.Music03_StopBtn);

        play(StartButton01, mp01, SeekBar01);
        play(StartButton02, mp02, SeekBar02);
        play(StartButton03, mp03, SeekBar03);
        pause(PauseButton01, mp01);
        pause(PauseButton02, mp02);
        pause(PauseButton03, mp03);
      //  stop(StopButton01, mp01);
      //  stop(StopButton02, mp02);
      //   stop(StopButton03, mp03);
    }


    private void play(Button playButton,final MediaPlayer mp, final SeekBar seekBar){
        Button.OnClickListener playClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mp.isPlaying()) {
                    mp.start();
                    Thread(mp, seekBar);
                } else {
                    mp.pause();
                }
            }
        };
        playButton.setOnClickListener(playClick);
    }


    private void Thread(final MediaPlayer mp, final SeekBar seekBar){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                //음악이 재생중일때
                while(mp.isPlaying()){
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                seekBar.setProgress(mp.getCurrentPosition());
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }


    private void pause(Button PauseButton ,final MediaPlayer mp){
        Button.OnClickListener PauseBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()) {
                    try {
                        mp.pause();
                        //어떤 예외가 일어날 것인가에 대해 궁금해 하기.
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        PauseButton.setOnClickListener(PauseBtn);
    }


    private void stop(Button stopButton, final MediaPlayer mp){
        Button.OnClickListener StopBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
            }
        };
        stopButton.setOnClickListener(StopBtn);
    }


    private void initSeekBar(final MediaPlayer mp, final TextView TimeID, SeekBar seekBar){

        seekBar.setVisibility(ProgressBar.VISIBLE);
        seekBar.setMax(mp.getDuration());

        SeekBar.OnSeekBarChangeListener MyOnseekBarChangeListener = new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mp.seekTo(progress);
                }
                int m = progress / 60000;
                int s = (progress % 60000) / 1000;
                String strTime = String.format("%02d:%02d", m, s);
                TimeID.setText(strTime);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        seekBar.setOnSeekBarChangeListener(MyOnseekBarChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mp01!= null) {
            mp01.release();
        } if (mp02 != null){
            mp02.release();
        } if(mp03 != null) {
            mp03.release();
        }
        mp01 = null;
        mp02 = null;
        mp03 = null;

    }
}
