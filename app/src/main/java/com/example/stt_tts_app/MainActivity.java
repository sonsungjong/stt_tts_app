package com.example.stt_tts_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton1, imageButton2, imageButton3, imageButton4,
            imageButton5, imageButton6, imageButton7,imageButton8,
            imageButton9,imageButton10,imageButton11,imageButton12,
            imageButton13,imageButton14,imageButton15,imageButton16;
    TextView textView1, textView2, textView3, textView4,
            textView5, textView6,textView7, textView8,
            textView9, textView10, textView11, textView12,
            textView13, textView14, textView15, textView16;
    TextToSpeech tts;
    Button go_stt_btn;
    ArrayList<String> m_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideBottomKey();

        // xml 버튼 연결
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);
        imageButton7 = findViewById(R.id.imageButton7);
        imageButton8 = findViewById(R.id.imageButton8);
        imageButton9 = findViewById(R.id.imageButton9);
        imageButton10 = findViewById(R.id.imageButton10);
        imageButton11 = findViewById(R.id.imageButton11);
        imageButton12 = findViewById(R.id.imageButton12);
        imageButton13 = findViewById(R.id.imageButton13);
        imageButton14 = findViewById(R.id.imageButton14);
        imageButton15 = findViewById(R.id.imageButton15);
        imageButton16 = findViewById(R.id.imageButton16);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);
        textView15 = findViewById(R.id.textView15);
        textView16 = findViewById(R.id.textView16);

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

        // [TTS] 클릭이벤트 추가
        setTTS(imageButton1, textView1);
        setTTS(imageButton2, textView2);
        setTTS(imageButton3, textView3);
        setTTS(imageButton4, textView4);
        setTTS(imageButton5, textView5);
        setTTS(imageButton6, textView6);
        setTTS(imageButton7, textView7);
        setTTS(imageButton8, textView8);
        setTTS(imageButton9, textView9);
        setTTS(imageButton10, textView10);
        setTTS(imageButton11, textView11);
        setTTS(imageButton12, textView12);
        setTTS(imageButton13, textView13);
        setTTS(imageButton14, textView14);
        setTTS(imageButton15, textView15);
        setTTS(imageButton16, textView16);

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

    // tts 해제
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }


    public void setTTS(ImageButton a_imageButton, TextView a_textView){
        a_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // text 읽기
                String text = a_textView.getText().toString();
                
                // tts 변환
                Bundle params = new Bundle();
                params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "UniqueID");
            }
        });
    }

    public void hideBottomKey(){
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);

        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        //newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;               // 상태바
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

}