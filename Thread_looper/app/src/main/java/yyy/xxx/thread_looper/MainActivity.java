package yyy.xxx.thread_looper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    int mMainValue = 0;
    TextView mMainText;
    TextView mBackText;
    EditText mNumEdit;
    CalcThread mThread;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainText = (TextView)findViewById(R.id.mainvalue);
        mBackText = (TextView)findViewById(R.id.backvalue);
        mNumEdit = (EditText)findViewById(R.id.number);

        mThread = new CalcThread(mHandler);
        mThread.setDaemon(true);
        mThread.start();
    }

    public void mOnClick(View v) {
        Message msg;
        switch (v.getId()) {
            case R.id.increase:
                mMainValue++;
                mMainText.setText("MainValue : " + mMainValue);
                break;
            case R.id.square:
                msg = new Message();
                msg.what = 0;
                msg.arg1 = Integer.parseInt(mNumEdit.getText().toString());
                mThread.mBackHandler.sendMessage(msg);  // 작업스레드에 메세지를 던진다
                break;
            case R.id.root:
                msg = new Message();
                msg.what = 1;
                msg.arg1 = Integer.parseInt(mNumEdit.getText().toString());
                mThread.mBackHandler.sendMessage(msg);  // 작업스레드에 메세지를 던진다
                break;
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mBackText.setText("Square Result : " + msg.arg1);
                    break;
                case 1:
                    mBackText.setText("Root Result : " + ((Double)msg.obj).doubleValue());
                    break;
            }
        }
    };
}

    class CalcThread extends Thread {
        Handler mMainHandler;
        Handler mBackHandler;

        CalcThread(Handler handler) {
        mMainHandler = handler;
    }

        public void run() {
            Looper.prepare(); // 작업스레드를 위한 looper 준비
            mBackHandler = new Handler() {
            public void handleMessage(Message msg) {
                Message retmsg = new Message();
                switch (msg.what) {
                    case 0:
                        try { Thread.sleep(200); } catch (InterruptedException e) {;}
                        retmsg.what = 0;
                        retmsg.arg1 = msg.arg1 * msg.arg1;
                        break;
                    case 1:
                        try { Thread.sleep(200); } catch (InterruptedException e) {;}
                        retmsg.what = 1;
                        retmsg.obj = new Double(Math.sqrt((double)msg.arg1));
                        break;
                }
                mMainHandler.sendMessage(retmsg); // 결과값을 다시 메인으로 보내준다
            }
        };
        Looper.loop();  // 메세지 큐에서 메세지를 꺼내 핸들러로 전달한다.
    }
}