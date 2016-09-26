package yyy.xxx.thread_scheduling_01;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Handler를 사용하여 Runnable을 지연하여 보내는 예제
 * 이번에는 Handler객체를 생성하고 Handler객체에서 postDelayed를 사용함.
 *
 *  핸들러로 Runnable을 지연하여 보냄 (메인스레드가 메인스레드 자신에게 Runnable을 보내는 경우임
 **/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void mOnClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("질문")
                .setMessage("업로드 하시겠습니까???")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doUpload();
                            }
                        }, 1000);
                        //아래가 먼저 실행되고 그 안에 doUpload()가 실행됨.
                        Toast.makeText(MainActivity.this, "gggggggg", Toast.LENGTH_SHORT).show();
                        //run 안에 10동안 동작후에 밖에꺼 동작함
                    }
                })
                .setNegativeButton("아니요", null)
                .show();
    }

    Handler mHandler = new Handler() {
        //BackThread가 동작시 이용 할수 있음.
        // 핸들러의 의미 상기시키기.
        // 핸들러란 선언되어져있는 클래스의 쓰레드에서 다른 쓰레드의 정보를 수정할때 사용하는 것이다.
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                doUpload();
            }
        }
    };

    void doUpload() {
        //약 2초의 지연시간뒤에 (20 * 100ms) 업로드 완료 Toast 출력
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(100);
            }catch (InterruptedException e) {;}
        }
        Toast.makeText(this, "업로드를 완료했습니다. ", Toast.LENGTH_SHORT).show();
    }
}
