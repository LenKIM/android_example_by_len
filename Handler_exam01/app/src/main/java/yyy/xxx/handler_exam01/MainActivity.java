package yyy.xxx.handler_exam01;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;



/**
 *  Handler란?
 *  메인쓰레드와 작업스레드 간에 통신을 할 수 있는 방법
 *
 *  -필요 주요 이유는?
 *   1. 작업스레드에서는 메인스레드의 변수를 참조하거나 변경할 수는 있지만, 메인 스레드에서 정의된 UI을 변경할 수 없다.
 *   2. 작업 스레드에서 메인스레드의 UI를 변경할 필요가 있을 경우, 작업스레드는 Handler를 통해 메인스레드에서
 *   UI를 변경하라고 알릴 수 있다.
 *
 *  -사용법
 *
 *  핸들러를 생성하는 스레드만이 다른 스레드가 전송하는 'Messager'나 'Runnable객체'를 수신하는 기능하는 기능
 *
 *  - 사용 예
 *  메인스레드에서 생성한 핸들러는 메인스레드나 작업스레드 어디에서나 참조 할수 있으며,
 *  메세지 전송 함수 sendMessage나 Runnable 전송 함수인 post를 호출할 수 있다.
 *  이 경우 전송되는 메시지나 Runnable은 핸들러 객체를 생성한 메인스레드에서만 수신 할 수 있다.
 * */
/**
 * 중요!
 * 핸들러를 생성하는 스레드만이 다른 스레드가 전송하는 Message나 Runnable객체를 수신하는 기능을 할 수 있다.
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

        mainText = (TextView)findViewById(R.id.mainvalue);
        backText = (TextView)findViewById(R.id.backvalue);

        //스레드 생성하고 시작
        BackThread thread = new BackThread();
        thread.setDaemon(true);
        //thread.setDaemon 으로 메인 스레드와 종료 동기화를 이룸,
        //종료 동기화는 메인스레드가 죽게 될 경우, 서브 스레드들도 같이 죽게 하는 것 입니다.
        //그러나 안드로이드의 Activtiy상의 쓰레드에서는 setDaemon()이 잘 안 먹힌다.

        thread.start();

    } // End onCreate();

    //버튼을 누르면 mainValue 증가
    public void mOnClick(View view) {
        mainValue++;
        mainText.setText("MainValue = " + mainValue);
    }

    class BackThread extends Thread {

        @Override
        public void run() {
            while (true) {
                backValue++;
                //메인에서 생성된 Handler 객체의 sendEmptyMessager를 통해 Messager 전달
                //백스레드에서 핸들러 인스턴스를 쓰면서 백스레드에 통로를 만든다 라고 생각하기/
                handler.sendEmptyMessage(0);

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            } // end while
        } // end run()
    }  // end class BackThread

    /**
     * '메인스레드'에서 Handler객체를 생성한다.
     *  Handler 객체를 생성한 스레드 만이 다른 스레드가 전송하는 Messager나 Runnable객체를
     *  수신 할 수 있다.
     *  아래 생성된 Handler객체는 handlerMessager()를 오버라이딩 하여
     *  Message를 수신합니다.
     *  */
    Handler handler = new Handler( ) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) { //Messager id가 0이면 아래 코드가 실행된다. 여기서 중요한건 핸들러를 통해.
                backText.setText("BackValue : " + backValue);
            }
        }
    }; //이너 클래스에 익명클래스 처럼 써서 오버라이딩함.

}
