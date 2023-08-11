package com.example.stt_tts_app;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import android.Manifest;

public class SttActivity extends AppCompatActivity {

    private TextView stt_text;
    private Button stt_button;
    private Button go_tts_button;
    private SpeechRecognizer speech_recognizer;
    private Intent recognizer_intent;
    private boolean flag = false;

    private static final int REQUEST_CODE = 200; // 권한 요청 코드, 임의의 값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stt);

        stt_text = findViewById(R.id.stt_text);
        stt_button = findViewById(R.id.stt_button);
        go_tts_button = findViewById(R.id.go_tts_btn);
        speech_recognizer = SpeechRecognizer.createSpeechRecognizer(this);

        recognizer_intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizer_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);
        }


        speech_recognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                // 사람이 말한 것의 끝시점에 호출
            }

            @Override
            public void onError(int i) {
                if(i == SpeechRecognizer.ERROR_NO_MATCH){
                    // 일정 시간 동안 말이 없으면 발생하는 에러... 원인 분석이 필요 - 일단은 리스너 재실행
                    if (flag == true) {
                        speech_recognizer.startListening(recognizer_intent);
                    }
                }
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    stt_text.setText(stt_text.getText().toString() + "\n" + matches.get(0));
                } else {
                    stt_text.setText(stt_text.getText().toString() + "\n" + "Result의 matches오류");
                }

                // 다시 녹음 시작 (flag가 true일 때)
                if (flag == true) {
                    speech_recognizer.startListening(recognizer_intent);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                stt_text.setText(stt_text.getText().toString()+"\n"+"부분 Results");
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        stt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == false) {
                    speech_recognizer.startListening(recognizer_intent);
                    stt_button.setText("Stop");
                    flag = true;
                } else {
                    speech_recognizer.stopListening();
                    stt_button.setText("Start");
                    flag = false;
                }
            }
        });

        go_tts_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SttActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(speech_recognizer != null){
            speech_recognizer.destroy();
        }
    }
}