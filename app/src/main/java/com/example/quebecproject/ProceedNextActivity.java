package com.example.quebecproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ProceedNextActivity extends Activity {
    final static String MYDEBUG = "MYDEBUG";
    //final static int LAUNCH_SECOND_ACTIVITY = 1;
    private Bundle bundle;
    private TextView testComplete;
    private TextView proceedNext;
    private Button continueButton;
    private int nextTestNumber;
    private String name;
    private String handPosture;
    //fields for time and accuracy across all tests
    private int numErrorsTest1;
    private int numErrorsTest2;
    private int numErrorsTest3;
    private int elapsedTimeTest1;
    private int elapsedTimeTest2;
    private int elapsedTimeTest3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nexttest);

        Bundle b = getIntent().getExtras();
        nextTestNumber = b != null ? b.getInt("next test number") : 0;
        name = b != null ? b.getString("name") : null;
        handPosture = b != null ? b.getString("hand posture") : null;
        Initialize();
    }

    private void Initialize() {
        testComplete = (TextView) findViewById(R.id.testComplete);
        proceedNext = (TextView) findViewById(R.id.proceedNext);
        continueButton = (Button) findViewById(R.id.continueButton);

        switch(nextTestNumber) {
            case 2:
                testComplete.setText(R.string.testCompleted1);
                proceedNext.setText(R.string.proceedNext1);
                break;
            case 3:
                testComplete.setText(R.string.testCompleted2);
                proceedNext.setText(R.string.proceedNext2);
                break;
            case 4:
                testComplete.setText(R.string.testCompleted3);
                proceedNext.setText(R.string.proceedNext3);
                break;
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("name", name);
                b.putString("hand posture", handPosture);
                b.putInt("test number", nextTestNumber);

                Intent returnIntent = new Intent();
                returnIntent.putExtras(b);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

                //Log.i(MYDEBUG, String.valueOf(nextTestNumber));
            }
        });
    }
}
