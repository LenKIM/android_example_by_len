package yyy.xxx.handler_exam03;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Handler
 * 메인스레드의 핸드럴르 직접 참조 할 수 없을때,
 * 클래스가 분리되어 메인스레드의 핸드럴를 직접 사용하지 못하지만, 핸들러를 받은뒤,
 * 메세지를 생성하여 보내는 방법으로 스레드(Thread)를 구현할 수 있습니다.
 *
 * */

public class MainActivity extends AppCompatActivity {

    int mainValue = 0;
    TextView mainText;
    TextView backText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = (TextView)findViewById(R.id.mainvalue);
        backText = (TextView)findViewById(R.id.backvalue);

        BackThread thread = new BackThread(handler);  // 메인스레드의 핸들러 객체를 외부 클래스에 넘겨줌
        thread.setDaemon(true);
        thread.start();

    }
    public void mOnClick(View v){
        mainValue++;
        mainText.setText("MainValue:" + mainValue);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0) {
                //메세지를 통해 받은 값을 Backvalue로 출력
                backText.setText("BackValue" + msg.arg1);
            }
        }// end handlerMessage
    };
} // end class


// 메인스레드의 핸들러를 직접 사용할 수 없는 분리된 작업 스레드.
 class BackThread extends Thread {

     int backValue = 0;
     Handler handler;

     public BackThread(Handler handler) {
         this.handler = handler;
     } // end constructor

     @Override
     public void run() {
         while (true) {
             backValue++;

             Message msg = new Message();
             msg.what = 0;
             msg.arg1 = backValue;
             handler.sendMessage(msg);

             try {
                 Thread.sleep(1000);
             }catch (InterruptedException e) {
                 e.printStackTrace();
             }

         }
     }
 }
