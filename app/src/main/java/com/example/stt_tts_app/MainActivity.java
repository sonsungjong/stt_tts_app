package com.example.stt_tts_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button hello_btn;
    TextView textView1;
    TextToSpeech tts;
    Button go_stt_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hello_btn = findViewById(R.id.hello_btn);
        textView1 = findViewById(R.id.textView1);
        go_stt_btn = findViewById(R.id.go_stt_btn);

        // tts 생성
        /*
        안드로이드
        설정 -> 일반 -> 글자 읽어주기 -> 기본 엔진 -> 'Google 음성' -> 재생 눌러서 테스트
        Play 스토어 -> 'Google TTS' 검색 -> 'Speech Recognition & Synthesis' 업데이트
         */
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        // hello_btn 클릭이벤트 추가
        hello_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView1.setText("안녕하세요 반갑습니다.");

                // tts 사용
                Bundle params = new Bundle();
                params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
                tts.speak("안녕하세요 반갑습니다.", TextToSpeech.QUEUE_FLUSH, params, "UniqueID");
            }
        });

        // go_stt_btn 클릭이벤트 추가
        go_stt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SttActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}