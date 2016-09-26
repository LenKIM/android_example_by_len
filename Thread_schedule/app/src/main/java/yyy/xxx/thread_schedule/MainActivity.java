package yyy.xxx.thread_schedule;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * 작업 스레드의 실행 시점을 조절하여,
 * 작업로드가 많은 작업을 나중으로 미룸으로써
 * 응용프로그램이 끊임없이 실행될 수 있도록함
 *
 *  핸들러의 사용시 다음과 같은 함수를 사용할 수 있음.
 *  boolean sendMessageAtTime(Message msg, long delayMillis)
 *  boolean sendMessageDelayed(Message msg, long delayMillis)
 *  boolean postAtTime (Runnable r, long delayMillis)
 *  boolean postDelayed(Runnable r, long delayMillis)
 *
 *  먼저 핸들러로 메시지를 지연하여 보내는 경우, 즉 메인스레드가 메인스레드 자신에게 메시지를 보내는 경우의 예제.
 * */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("질문")
                .setMessage("업로드 하시겠습니까? ")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        mHandler.sendEmptyMessageDelayed(0, 10);
                    }
                })
                .setNegativeButton("아니요", null)
                .show();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                doUpload();
            }
        }
    };

    void doUpload() {
        // 약 2초의 지연시간뒤에 (20 * 100ms) 업로드 완료 Toast 출력
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {;}
        }
        Toast.makeText(this, "업로드를 완료했습니다.", Toast.LENGTH_LONG).show();
    }
}


