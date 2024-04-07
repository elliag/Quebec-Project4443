package com.example.quebecproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TypeTestActivity extends Activity implements TextWatcher{
    final static String MYDEBUG = "MYDEBUG";
    int LAUNCH_SECOND_ACTIVITY = 1;
    private TextView testTitle;
    private TextView testTextContent;
    private char[] testTextContentCharArr;
    private Spannable spannable;
    private String inputTextBefore;
    private String inputTextString;
    private EditText inputText;
    private Button startButton;

    //Participant Info
    private String name;
    private String handPosture;

    //fields for collecting data
    private int testNumber;
    //accuracy
    private int numErrors = 0;
    //time
    private long startTime;
    private long endTime;
    private long elapsedTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typetest);

        Bundle b = getIntent().getExtras();
        name = b != null ? b.getString("name") : null;
        handPosture = b != null ? b.getString("hand posture") : null;
        testNumber = b != null ? b.getInt("test number") : 0;
        Initialize();
    }

    private void Initialize() {

        testTitle = (TextView) findViewById(R.id.testTitle);            //initialize the text view that holds the test title (level 1, level 2 or level 3)
        testTextContent = (TextView) findViewById(R.id.testContent);    //initialize the text view that holds the phrase that needs to be typed.
        switch(testNumber) {                                            //show different test title and test content per test number
            case 1:
                testTitle.setText(R.string.test1Title);
                testTextContent.setText(R.string.test1Content);
                break;
            case 2:
                testTitle.setText(R.string.test2Title);
                testTextContent.setText(R.string.test2Content);
                break;
            case 3:
                testTitle.setText(R.string.test3Title);
                testTextContent.setText(R.string.test3Content);
                break;
        }
        testTextContentCharArr = testTextContent.getText().toString().toCharArray();    //convert the content of that text view into a char array.
        spannable = new SpannableString(testTextContent.getText());     //initialize the Spannable string that will allow us to change the colour of the test content when someone makes a boo boo mistake
        inputTextString = "";
        inputText = (EditText) findViewById(R.id.textInput);            //initialize the invisible EditText that will hold what the user types.
        inputText.getText().clear();
        inputText.addTextChangedListener(this);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {   //brings up the keyboard when start button pressed
                inputText.requestFocus();
                inputText.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(inputText, InputMethodManager.SHOW_FORCED);


                Log.i(MYDEBUG, "name: " + ParticipantData.getName());
                Log.i(MYDEBUG, "hand posture: " + ParticipantData.getPosture());

                startTime = System.currentTimeMillis(); //start the timer

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void nextTest() {
        testNumber++;
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        double elapsedTimeSeconds = elapsedTime / 1000.0;

        Bundle b = new Bundle();
        b.putString("name", name);
        b.putString("hand posture", handPosture);
        b.putString("test time", String.valueOf(elapsedTimeSeconds));
        b.putInt("number of errors" , numErrors);
        b.putInt("next test number", testNumber);

        Intent i = new Intent(this, ProceedNextActivity.class);
        i.putExtras(b);
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);

        /*Intent i = new Intent(getApplicationContext(), ProceedNextActivity.class);
        i.putExtras(b);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        finish();*/

        /*Intent returnIntent = new Intent();
        returnIntent.putExtras(b);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle b;

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                b = data.getExtras();
                name = b != null ? b.getString("name") : null;
                testNumber = b != null ? b.getInt("test number") : 0;
                handPosture = b != null ? b.getString("hand posture") : null;

                inputText.removeTextChangedListener(this);
                Initialize();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        inputTextBefore = inputTextString; //the input string before any changes are made
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == testTextContent.getText().toString().length()) {
            nextTest();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        char[] inputTextStringCharArray;
        char lastLetter;
        char currentPos;

        if (s.length() >= inputTextBefore.length()) {
            inputTextString = s.toString();                                                                         //parse the string from the EditText every time a key is pressed
            inputTextStringCharArray = inputTextString.toCharArray();                                               //convert the input string into a char array
            lastLetter = inputTextStringCharArray[inputTextStringCharArray.length-1];                               //get the last letter entered
            currentPos = testTextContentCharArr[inputTextStringCharArray.length-1];                                 //get the letter to compare it ^ to
            if (lastLetter != currentPos) {
                spannable.setSpan(new ForegroundColorSpan(Color.RED), inputTextStringCharArray.length-1, inputTextStringCharArray.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  //colour change red
                numErrors++; //increment number of errors
            }
            else {
                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), inputTextStringCharArray.length-1, inputTextStringCharArray.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);    //colour change green = correct
            }
            testTextContent.setText(spannable, TextView.BufferType.SPANNABLE);
        }
        else {
            s.append(inputTextBefore.charAt(inputTextBefore.length()-1));   //block back space functionality by automatically re-adding the letter that was deleted
        }
    }
}
