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
    private TextView testComplete;  //title that says "Test ____ complete"
    private TextView proceedNext;    //title that says "proceed to test ____"
    private Button continueButton;   //button to proceed to the next test
    private int nextTestNumber;      //the next test number (either 1, 2, 3 or 4) (4 is for Results)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nexttest);

        Bundle b = getIntent().getExtras();
        nextTestNumber = b != null ? b.getInt("next test number") : 0;
        Log.i(MYDEBUG, String.valueOf(nextTestNumber));
        //name = b != null ? b.getString("name") : null;
        //handPosture = b != null ? b.getString("hand posture") : null;
        Initialize();
    }

    private void Initialize() {
        testComplete = (TextView) findViewById(R.id.testComplete);
        proceedNext = (TextView) findViewById(R.id.proceedNext);
        continueButton = (Button) findViewById(R.id.continueButton);

        switch(nextTestNumber) {    //shows different text depending on what test u will proceed to
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
                //Log.i(MYDEBUG, String.valueOf(nextTestNumber));
                if (nextTestNumber == 4) {
                    Intent i = new Intent(getApplicationContext(), Results.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Bundle b = new Bundle();

                    b.putInt("test number", nextTestNumber);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtras(b);
                    setResult(Activity.RESULT_OK,returnIntent); //kill me
                    finish();
                }
            }
        });
    }
}
