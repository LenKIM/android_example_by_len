package yyy.xxx.internalstorager_create_read;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


/**
 * 안드로이드 기기에서 자료를 저장하는 방법은 크게 4가지방법이 있음.
 *
 * 1. 내부저장장치 InternalStorage
 * 2. 외부저장장치 ExternalStorage
 * 3. DB 저장 : SQLite
 * 4. SharedPreferences
 *
 *  아래 예제는 내부저장장치를 사용하는 예제.
 *  기본적으로 자바의 파일 입출력 스트림을 사용하며, openFileOutput()과 openFileInput()을
 *  사용하여 안드로이드 내부 저장장치에 파일을 생성하여 쓰고 읽기를 합니다.
 *
 *  etc,
 *    <requestFocus />
 *    요건 초기 포커스 줄때 사용하는 함수임. 이것은 파일당 (하나의 화면예)한번만 쓸 수 있다.
 * */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et = (EditText)findViewById(R.id.editText1);
        Button bSave = (Button) findViewById(R.id.button1);
        Button bLoad = (Button) findViewById(R.id.button2);
        final TextView tv = (TextView) findViewById(R.id.textView1);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = et.getText().toString();

                try {
                    FileOutputStream fos = openFileOutput
                            ("myFile.txt", //파일명 지정
                                    Context.MODE_APPEND ); // 저장모드
                    PrintWriter out = new PrintWriter(fos);
                    out.println(data);
                    out.close();

                    tv.setText("파일 저장 완료");
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        bLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파일의 내용을 읽어서 TextView에 보여주기
                try{
                    //파일에서 읽은 데이터를 저장하기 위해서 만든 변수
                    StringBuffer data = new StringBuffer();
                    FileInputStream fis = openFileInput("myFile.txt"); //파일명
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(fis));
                    String str = buffer.readLine();
                    while (str != null) {
                        data.append(str + "\n");
                        str = buffer.readLine();
                    }
                    tv.setText(data);
                    buffer.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
