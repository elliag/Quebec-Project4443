package com.example.quebecproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        nextTestNumber = b != null ? b.getInt("next test number") : 0;  //get the next test number

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

                if (nextTestNumber == 4) {  //go to the results page
                    Intent i = new Intent(getApplicationContext(), Results.class);
                    startActivity(i);
                    finish();
                }
                else {                      //go to the next test
                    Bundle b = new Bundle();

                    b.putInt("test number", nextTestNumber);

                    Intent i = new Intent(getApplicationContext(), TypeTestActivity.class);
                    i.putExtras(b);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("NEXT_TEST_NUMBER_KEY", nextTestNumber);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nextTestNumber = savedInstanceState.getInt("NEXT_TEST_NUMBER_KEY");
    }
}
