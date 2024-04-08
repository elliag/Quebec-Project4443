package com.example.quebecproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
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
    int LAUNCH_SECOND_ACTIVITY = 1;             //request code for the startActivityResult stuff
    InputMethodManager imm;                     //to manage the keyboard
    private TextView testTitle;                 //title of the test (Test 1, Test 2, Test 3)
    private TextView testTextContent;           //content of the test (to be copies by the user)
    private char[] testTextContentCharArr;      //char array that holds the test content
    private Spannable spannable;                //this helps change the colour of the text
    private String inputTextBefore;             //the phrase right before the next key phrase
    private String inputTextString;             //String version of whatever tf the user is typing
    private EditText inputText;                 //EditText that holds what the user is typing
    private Button startButton;                 //start button to start the test

    //Participant Info
    private String name;
    private String handPosture;

    //fields for collecting data
    private int testNumber;
    //accuracy
    private int numErrors = 0;
    private int numCorrects = 0;
    //time
    private long startTime;
    private long endTime;
    private long elapsedTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typetest);

        Bundle b = getIntent().getExtras();
        testNumber = b != null ? b.getInt("test number") : 0;

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
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
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(inputText, InputMethodManager.SHOW_FORCED);

                startTime = System.currentTimeMillis(); //start the timer

            }
        });
    }

    private void nextTest() {   //called when the current test has finished
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        float elapsedTimeSeconds = elapsedTime / 1000.0f;   //get the time it took to type

        testNumber++;   //increment test number for the next test

        imm.hideSoftInputFromWindow(inputText.getWindowToken(),0);  //hide the keyboard

        double accuracy;
        accuracy = (numCorrects - (double) numErrors) / testTextContent.length();   //compute accuracy (this is wrong idk what to do)

        Log.i(MYDEBUG, "accuracy = " + String.valueOf(accuracy));

        switch(testNumber) {    //log the data depending on what test we're on
            case 1:
                ParticipantData.setTest1Time(elapsedTimeSeconds);
                ParticipantData.setTest1Accuracy(accuracy);
                break;
            case 2:
                ParticipantData.setTest2Time(elapsedTimeSeconds);
                ParticipantData.setTest2Accuracy(accuracy);
                break;
            case 3:
                ParticipantData.setTest3Time(elapsedTimeSeconds);
                ParticipantData.setTest3Accuracy(accuracy);
        }

        Bundle b = new Bundle();    //this is the only thing thats still being bundled but we can probably change it

        b.putInt("next test number", testNumber);

        Intent i = new Intent(this, ProceedNextActivity.class);
        i.putExtras(b);
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);      //bring up the continue screen

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //i feel like this is whats making it slow, it can probably be changed
        super.onActivityResult(requestCode, resultCode, data);

        Bundle b;

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                b = data.getExtras();
                //name = b != null ? b.getString("name") : null;
                testNumber = b != null ? b.getInt("test number") : 0;
                //handPosture = b != null ? b.getString("hand posture") : null;

                inputText.removeTextChangedListener(this);


                Initialize();

            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putCharSequence("TEST_TITLE_KEY", testTitle.getText());
        outState.putCharSequence("TEST_CONTENT_KEY", testTextContent.getText());
        outState.putCharSequence("INPUT_KEY", inputText.getText());
        outState.putInt("TEST_NUMBER_KEY", testNumber);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        testTitle.setText(savedInstanceState.getCharSequence("TEST_TITLE_KEY"));
        testTextContent.setText(savedInstanceState.getCharSequence("TEST_CONTENT_KEY"));
        inputText.setText(savedInstanceState.getCharSequence("INPUT_KEY"));
        testNumber = savedInstanceState.getInt("TEST_NUMBER_KEY");
    }

    //these methods are for listening for when the user input is edited
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        inputTextBefore = inputTextString; //the input string before any changes are made
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == testTextContent.getText().toString().length()) {  //when the test is done
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
                numCorrects++;
            }
            testTextContent.setText(spannable, TextView.BufferType.SPANNABLE);
        }
        else {
            s.append(inputTextBefore.charAt(inputTextBefore.length()-1));   //block back space functionality by automatically re-adding the letter that was deleted
        }
    }
}
