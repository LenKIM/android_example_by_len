package yyy.xxx.handler_exam02;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 *  핸들러 주요 개념
 *  핸들러를 생성하는 스레드만이 다른 스레드가 전송하는
 *  Messager나 Runnable객체를 수신하는 기능을 할 수 있다.
 * */

public class MainActivity extends AppCompatActivity {

    int mainValue = 0;
    int backValue= 0;
    TextView mainText;
    TextView backText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = (TextView) findViewById(R.id.mainvalue);
        backText= (TextView) findViewById(R.id.backvalue);

        BackThread thread = new BackThread();
        thread.setDaemon(true);
        thread.start();
    } //end onCreate();

    public void mOnClick(View view){
        mainValue++;
        mainText.setText("MainValue : " + mainValue);
    }
    class BackThread extends Thread {

        @Override
        public void run() {
            while (true) {

                backValue++;

                //메인 스레드에 있던 handle객체를 사용하여 Runnable객체를 보내고(post)

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        backText.setText("backValue : " + backValue);
                    }
                });
                
                try {
                    Thread.sleep(1000);
                }catch ( InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    Handler handler = new Handler();
}
