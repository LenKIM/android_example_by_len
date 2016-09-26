package yyy.xxx.thread_timer_exam03;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
  * 본격 타이머 구현 방법.
  * CountDownTimer를 사용함.
  * */

public class MainActivity extends AppCompatActivity {

    int Value = 0;
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText =(TextView)findViewById(R.id.text01);

        /**
         * 핸들러를 사용하지 않고도 일정시간(혹은 후에)코스를 수행할수 있도록
         * CountDownTimer 클래스가 제공된다.
         * '총시간'과 '인터벌(간격)'을 주면 매 간격마다 onTick메소드를 수행한다.
         * */
        new CountDownTimer(10 * 1000, 1000) {
            //Inner Class 에서 익명클래스 사용함과 동일함.
            @Override
            public void onTick(long millisUntilFinished) { //총 시간과 주기
                Value++;
                mText.setText("Value = " + Value);
            }

            @Override
            public void onFinish() {

            }
        }.start(); //타이머 시작.

    }
}
