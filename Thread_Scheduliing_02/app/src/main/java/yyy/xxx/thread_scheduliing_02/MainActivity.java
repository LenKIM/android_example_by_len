package yyy.xxx.thread_scheduliing_02;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//* 핸들러로 러너블을 지연하여 보냄 (메인스레드가 메인스레드 자신에게 러너블을 보내는 경우임)
public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        new AlertDialog.Builder(this)
                .setTitle("질문")
                .setMessage("업로드 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 아래와 같이 View (Button)을 통해서도 Runnagle 을 생성해서 보낼수 있습니다.
                        Button btnUpload = (Button)findViewById(R.id.upload);
                        btnUpload.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doUpload();
                            }
                        },10);
                    }
                })
                .setNegativeButton("아니오", null)
                .show();
    }

    // 핸들러를 사용하지 않습니다!!
//    Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == 0) {
//                doUpload();
//            }
//        }
//    };

    void doUpload() {
        // 약 2초의 지연시간뒤에 (20 * 100ms) 업로드 완료 toast 출력
        for (int i = 0; i < 20; i++) {
            try { Thread.sleep(100); } catch (InterruptedException e) {;}
        }
        Toast.makeText(this, "업로드를 완료했습니다.", Toast.LENGTH_LONG).show();
    }
}
