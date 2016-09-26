package yyy.xxx.thread_timmer_exam01;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 핸들러의 사용시 다음과 같은 함수 사용가능함
 *
 * boolean sendMessageAtTime(Message msg, long delayMillis)
 * boolean sendMessageDelayed(Message msg, long delayMillis)
 * boolean postAtTime (Runnable r, long delayMillis)
 * boolean postDelayed (Runnable r, long delayMillis)
 *
 * 핸들러를 통해 메인스레드가 메인스레드에게 메세지를 보냄.
 *
 * */

public class MainActivity extends AppCompatActivity {

    int value = 0;
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView)findViewById(R.id.text);
        mHandler.sendEmptyMessage(0);
        /** sendEmptyMessage에서 첫번째 파라미터는 What 이다. 진짜 What?*/
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            value++;
            mText.setText("Value = " + value);

            /**
             * 메세지를 처리하고 또다시 핸들러에 메세지 전달(1000ms 지연)
             * 1초 뒤에 다시 보낸다.
             * */
            mHandler.sendEmptyMessageDelayed(0,1000);
        }
    };
}