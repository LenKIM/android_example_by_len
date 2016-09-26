package yyy.xxx.thread_timmer_exam02;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 *
 * 핸들러를 통해 메인스레드가 메인스레드에게 메세지를 보냄.
 * exam01과 다른점은 01은 핸들러를 외부에서 생성해서 사용했음을 반면에
 * exam02는 핸들러를 멤버변수로 선언하고 필요할 때마다 생성하여 사용했음.
 * */

public class MainActivity extends AppCompatActivity {

    int value = 0;
    TextView mText;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView)findViewById(R.id.textview);

        //메소드 내부에서 생성
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                value++;
                mText.setText("Value " + value);

                //메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        };
        mHandler.sendEmptyMessage(0); //핸들러에 메세지 전달.
    }
}
