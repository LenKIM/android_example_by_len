package yyy.xxx.handler_ex04;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 안드로이드에서 메인스레드의 핸들러를 사용할수 없을 경우 그 핸들러를 받아와서
 *Messager를 생성하여 sendMessage()할수 있는 예제가 Ex03
 * 이번에는 Messager생성을 생성자를 통해서가 아니라 obtain()메소드를 통해서도 생성하여 전송 가능함.
 *
 * */
public class MainActivity extends AppCompatActivity {

    int mainValue = 0;
    int backValue = 0;
    TextView mainText;
    TextView backText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = (TextView) findViewById(R.id.mainvalue);
        backText = (TextView) findViewById(R.id.backvalue);

        BackThread thread = new BackThread(handler);  // 메인스레드의 핸들러 객체를 외부 클래스에 넘겨줌
        thread.setDaemon(true);
        thread.start();
    } //end onCreate();

    public void mOnClick(View v) {
        mainValue++;
        mainText.setText("MainValue : " + mainValue);
    }

    Handler handler = new Handler() { //메인에서 생성한 핸들러


        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                //메세지를 통해 받은 값을 BackValue로 출력
                backText.setText("BackValue : " +msg.arg1);
            }
        }
    };
}
// 메인스레드의 핸들러를 직접 사용할 수 없는 분리된 작업 스레드
class BackThread extends Thread {

    int backValue = 0;
    Handler handler;

    BackThread(Handler handler){
        this.handler = handler;
    } //end constructor

    @Override
    public void run() {
        while (true) {
            backValue++;

            //obtain메소들 메세지 생성
            Message msg = Message.obtain(handler, 0, backValue, 0);
            // 두번째 파라미터가 Message의 What에 해당함.

            handler.sendMessage(msg);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
