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
    //variables for calculation (idk how tf we are going to use these tbh I am gonna kms (smile))
    //gee golly gosh I sure hope Ellia and Ada give me the time(s) in seconds!
    private int test1seconds, test2seconds, test3seconds;
    private float test1acc, test2acc, test3acc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent getData = getIntent();
        //do something here with the data
        // TODO: 4/6/2024
        
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
        
        //do calculations here (this is dependent on how data is given)
        // TODO: 4/6/2024  
        
        //apply calculated data to text fields here
        // TODO: 4/6/2024
    }
}