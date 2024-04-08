package com.example.quebecproject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.Locale;

public class Results extends Activity implements OnClickListener {
    final static String MYDEBUG = "MYDEBUG";
    //bundle thingy
    //participant info
    private final static String NAME_KEY = "name";
    private final static String POSTURE_KEY = "posture";
    //total
    private final static String TOTAL_AVERAGE_TIME = "avg_time";
    private final static String TOTAL_AVERAGE_ACCURACY = "avg_acc";
    //test 1
    private final static String TEST1_TIME = "test1_time";
    private final static String TEST1_ACCURACY = "test1_acc";
    //test 2
    private final static String TEST2_TIME = "test2_time";
    private final static String TEST2_ACCURACY = "test2_acc";
    //test 3
    private final static String TEST3_TIME = "test3_time";
    private final static String TEST3_ACCURACY = "test1_acc";
    //button
    Button returnButton;
    //mutable texts
    TextView participant, posture, averageTime, averageAccuracy, test1Time, test1Accuracy, test2Time, test2Accuracy, test3Time, test3Accuracy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        Initialize();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putCharSequence(NAME_KEY, participant.getText());
        savedInstanceState.putCharSequence(POSTURE_KEY, posture.getText());

        savedInstanceState.putCharSequence(TOTAL_AVERAGE_TIME, averageTime.getText());
        savedInstanceState.putCharSequence(TOTAL_AVERAGE_ACCURACY, averageAccuracy.getText());

        savedInstanceState.putCharSequence(TEST1_TIME, test1Time.getText());
        savedInstanceState.putCharSequence(TEST1_ACCURACY, test1Accuracy.getText());
        savedInstanceState.putCharSequence(TEST2_TIME, test2Time.getText());
        savedInstanceState.putCharSequence(TEST2_ACCURACY, test2Accuracy.getText());
        savedInstanceState.putCharSequence(TEST3_TIME, test3Time.getText());
        savedInstanceState.putCharSequence(TEST3_ACCURACY, test3Accuracy.getText());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        participant.setText(savedInstanceState.getCharSequence(NAME_KEY));
        posture.setText(savedInstanceState.getCharSequence(POSTURE_KEY));

        averageTime.setText(savedInstanceState.getCharSequence(TOTAL_AVERAGE_TIME));
        averageAccuracy.setText((savedInstanceState.getCharSequence(TOTAL_AVERAGE_ACCURACY)));

        test1Time.setText(savedInstanceState.getCharSequence(TEST1_TIME));
        test1Accuracy.setText((savedInstanceState.getCharSequence(TEST1_ACCURACY)));
        test2Time.setText(savedInstanceState.getCharSequence(TEST2_TIME));
        test2Accuracy.setText((savedInstanceState.getCharSequence(TEST2_ACCURACY)));
        test3Time.setText(savedInstanceState.getCharSequence(TEST3_TIME));
        test3Accuracy.setText((savedInstanceState.getCharSequence(TEST3_ACCURACY)));
    }

    @Override
    public void onClick(View v) {
        if (v == returnButton) {
            Intent returnToStart = new Intent(Results.this, ParticipantInfo.class);
            //til intents are for moving between activities
            startActivity(returnToStart);
            //just return to the start of the application I guess??????
        }
    }

    private void Initialize() { //setup
        //calculations

        float t1s = ParticipantData.getTest1Time();
        float t2s = ParticipantData.getTest2Time();
        float t3s = ParticipantData.getTest3Time();

        String test1seconds = String.valueOf(ParticipantData.getTest1Time());//CalculateTime(ParticipantData.getTest1Time());
        String test2seconds = String.valueOf(ParticipantData.getTest2Time());//CalculateTime(ParticipantData.getTest2Time());
        String test3seconds = String.valueOf(ParticipantData.getTest3Time());//CalculateTime(ParticipantData.getTest3Time());
        String test1acc = String.valueOf(ParticipantData.getTest1Accuracy());
        String test2acc = String.valueOf(ParticipantData.getTest2Accuracy());
        String test3acc = String.valueOf(ParticipantData.getTest3Accuracy());
        String averageSeconds = String.valueOf((t1s + t2s + t3s) / 3f);//CalculateTime((ParticipantData.getTest1Time() + ParticipantData.getTest2Time() + ParticipantData.getTest3Time()) / 3);
        String averageAcc = String.valueOf((ParticipantData.getTest1Accuracy() + ParticipantData.getTest2Accuracy() + ParticipantData.getTest3Accuracy()) / 3);

        //assign IDs
        //user info
        participant = (TextView) findViewById(R.id.r_participant);
        posture = (TextView) findViewById(R.id.r_posture);
        //total
        averageTime = (TextView) findViewById(R.id.r_avgtime);
        averageAccuracy = (TextView) findViewById(R.id.r_avgacc);
        //test 1
        test1Time = (TextView) findViewById(R.id.r_test1time);
        test1Accuracy = (TextView) findViewById(R.id.r_test1acc);
        //test 2
        test2Time = (TextView) findViewById(R.id.r_test2time);
        test2Accuracy = (TextView) findViewById(R.id.r_test2acc);
        //test 3
        test3Time = (TextView) findViewById(R.id.r_test3time);
        test3Accuracy = (TextView) findViewById(R.id.r_test3acc);
        //button
        returnButton = (Button) findViewById(R.id.buttonReturn);

        //assign values
        participant.setText(ParticipantData.getName());
        switch (ParticipantData.getPosture()) {
            case "single":
                posture.setText("One-handed");
                break;
            case "double":
                posture.setText("Two-handed");
                break;
        }
        averageTime.setText(averageSeconds);
        averageAccuracy.setText(averageAcc);
        test1Time.setText(test1seconds);
        test2Time.setText(test2seconds);
        test3Time.setText(test3seconds);
        test1Accuracy.setText(test1acc);
        test2Accuracy.setText(test2acc);
        test3Accuracy.setText(test3acc);
    }

    //this method converts seconds to minutes and seconds
    private String CalculateTime(float time) {
        String answer;
        int minutes, seconds;
        minutes = (int) time / 60;
        seconds = (int) time % 60;
        answer = minutes + ":" + String.format(Locale.CANADA,"%02d", seconds); //need leading zeroes
        //also this weird canada locale thing cause it gave me warnings if I didn't put it in there ^
        return answer;
    }
}