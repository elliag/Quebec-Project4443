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

public class TypeTestActivity extends Activity implements TextWatcher {
    final static String MYDEBUG = "MYDEBUG";
    InputMethodManager imm;                     //to manage the keyboard
    private TextView testTitle;                 //title of the test (Test 1, Test 2, Test 3)
    private TextView testTextContent;           //content of the test (to be copies by the user)
    private char[] testTextContentCharArr;      //char array that holds the test content
    private Spannable spannable;                //this helps change the colour of the text
    private String inputTextBefore;             //the phrase right before the next key phrase
    private String inputTextString;             //String version of whatever tf the user is typing
    private EditText inputText;                 //EditText that holds what the user is typing
    private Button startButton;                 //start button to start the test

    //fields for collecting data
    private int testNumber;
    //accuracy
    private int numErrors = 0;
    private int numCorrects = 0;
    //time
    private long startTime;
    private long endTime;
    private long elapsedTime;

    //more
    int wordIndex;
    int letterIndex;
    String[] testContentStringArr;

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
        testContentStringArr = testTextContent.getText().toString().split(" ");     //separate words in the test content

        spannable = new SpannableString(testTextContent.getText());     //initialize the Spannable string that will allow us to change the colour of the test content when someone makes a boo boo mistake

        inputTextString = "";
        inputText = (EditText) findViewById(R.id.textInput);            //initialize the invisible EditText that will hold what the user types.
        inputText.getText().clear();
        inputText.addTextChangedListener(this);

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setEnabled(true);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {   //brings up the keyboard when start button pressed
                inputText.requestFocus();
                inputText.setFocusableInTouchMode(true);
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(inputText, InputMethodManager.SHOW_FORCED);

                startTime = System.currentTimeMillis(); //start the timer
                startButton.setEnabled(false);

            }
        });

        wordIndex = 0;
        letterIndex = 0;
    }

    private void nextTest() {   //called when the current test has finished
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        float elapsedTimeSeconds = elapsedTime / 1000.0f;   //get the time it took to type

        //Log.i(MYDEBUG, String.valueOf(elapsedTimeSeconds));

        imm.hideSoftInputFromWindow(inputText.getWindowToken(),0);  //hide the keyboard
        inputText.clearFocus();
        inputText.setFocusableInTouchMode(false);

        double accuracy;
        int total = numCorrects + numErrors;
        accuracy = ((double) numCorrects / total) * 100.0;  //compute accuracy (this is wrong idk what to do)

        //Log.i(MYDEBUG, "accuracy = " + String.valueOf(accuracy));

        switch(testNumber) {    //log the data depending on what test we're on
            case 1:
                ParticipantData.setTest1Time(elapsedTimeSeconds);
                ParticipantData.setTest1Accuracy(accuracy);
                //Log.i(MYDEBUG, String.valueOf(elapsedTimeSeconds));
                Log.i(MYDEBUG, "test 1 = " + ParticipantData.getTest1Time() + " test 2 = " + ParticipantData.getTest2Time() + " Test 3 = " + ParticipantData.getTest3Time());
                break;
            case 2:
                ParticipantData.setTest2Time(elapsedTimeSeconds);
                ParticipantData.setTest2Accuracy(accuracy);
                //Log.i(MYDEBUG, String.valueOf(elapsedTimeSeconds));
                Log.i(MYDEBUG, "test 1 = " + ParticipantData.getTest1Time() + " test 2 = " + ParticipantData.getTest2Time() + " Test 3 = " + ParticipantData.getTest3Time());
                break;
            case 3:
                ParticipantData.setTest3Time(elapsedTimeSeconds);
                ParticipantData.setTest3Accuracy(accuracy);
                //Log.i(MYDEBUG, String.valueOf(elapsedTimeSeconds));
                Log.i(MYDEBUG, "test 1 = " + ParticipantData.getTest1Time() + " test 2 = " + ParticipantData.getTest2Time() + " Test 3 = " + ParticipantData.getTest3Time());
                break;
        }

        testNumber++;   //increment test number for the next test

        Bundle b = new Bundle();    //this is the only thing thats still being bundled but we can probably change it

        b.putInt("next test number", testNumber);

        Intent i = new Intent(this, ProceedNextActivity.class);
        i.putExtras(b);
        //startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);      //bring up the continue screen
        startActivity(i);
        finish();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putCharSequence("TEST_TITLE_KEY", testTitle.getText());
        outState.putCharSequence("TEST_CONTENT_KEY", testTextContent.getText());
        outState.putCharSequence("INPUT_KEY", inputText.getText());
        outState.putString("INPUT_TEXT_STRING_KEY", inputTextString);
        outState.putInt("TEST_NUMBER_KEY", testNumber);
        outState.putString("INPUT_TEXT_BEFORE_KEY", inputTextBefore);
        outState.putInt("NUM_ERRORS_KEY", numErrors);
        outState.putInt("NUM_CORRECTS_KEY", numCorrects);
        outState.putLong("START_TIME_KEY", startTime);
        outState.putInt("WORD_INDEX_KEY", wordIndex);
        outState.putInt("LETTER_INDEX_KEY", letterIndex);
        outState.putStringArray("TEST_CONTENT_ARRAY_KEY", testContentStringArr);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        testTitle.setText(savedInstanceState.getCharSequence("TEST_TITLE_KEY"));
        testTextContent.setText(savedInstanceState.getCharSequence("TEST_CONTENT_KEY"));
        inputText.setText(savedInstanceState.getCharSequence("INPUT_KEY"));
        inputTextString = savedInstanceState.getString("INPUT_TEXT_STRING_KEY");
        testNumber = savedInstanceState.getInt("TEST_NUMBER_KEY");
        inputTextBefore = savedInstanceState.getString("INPUT_TEXT_BEFORE_KEY");
        numErrors = savedInstanceState.getInt("NUM_ERRORS_KEY");
        numCorrects = savedInstanceState.getInt("NUM_CORRECTS_KEY");
        startTime = savedInstanceState.getLong("START_TIME_KEY");
        wordIndex = savedInstanceState.getInt("WORD_INDEX_KEY");
        letterIndex = savedInstanceState.getInt("LETTER_INDEX_KEY");
        testContentStringArr = savedInstanceState.getStringArray("TEST_CONTENT_ARRAY_KEY");
        spannable = new SpannableString(testTextContent.getText());
    }

    //these methods are for listening for when the user input is edited
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        inputTextBefore = inputTextString; //the input string before any changes are made
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        char[] inputTextStringCharArray;
        char lastLetter;
        char currentPos;

        inputTextString = s.toString();
        inputTextStringCharArray = inputTextString.toCharArray();//convert the input string into a char array

        int difference = s.length() - inputTextBefore.length();

        if (difference == 1 && inputText.hasFocus()) {
            
            lastLetter = inputTextStringCharArray[inputTextStringCharArray.length-1];                               //get the last letter entered
            currentPos = testTextContentCharArr[inputTextStringCharArray.length-1];
            if (lastLetter != currentPos) {
                spannable.setSpan(new ForegroundColorSpan(Color.RED), inputTextStringCharArray.length-1, inputTextStringCharArray.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  //colour change red
                numErrors++; //increment number of errors
            }
            else {
                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), inputTextStringCharArray.length-1, inputTextStringCharArray.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  //colour change red
                numCorrects++; //increment number of errors
            }
            testTextContent.setText(spannable, TextView.BufferType.SPANNABLE);
            letterIndex++;
        }
        else if (difference > 1 && inputText.hasFocus()) {

            String[] wordArr = inputTextString.split(" ");
            String latestWord = wordArr[wordArr.length-1];

            int indexCurrentPosEnd = letterIndex + testContentStringArr[wordIndex].length();

            if (!latestWord.equals(testContentStringArr[wordIndex])) {
                spannable.setSpan(new ForegroundColorSpan(Color.RED), letterIndex, indexCurrentPosEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                numErrors = numErrors + latestWord.length();
            }
            else {
                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), letterIndex, indexCurrentPosEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                numCorrects = numCorrects + latestWord.length();
            }
            testTextContent.setText(spannable, TextView.BufferType.SPANNABLE);

            letterIndex = letterIndex + testContentStringArr[wordIndex].length();
            wordIndex++;

        }
        else if (difference < 0 && inputText.hasFocus()) {

            inputText.clearFocus();
            s.replace(0,s.length(),inputTextBefore);
            inputText.requestFocus();

        }

        if (wordIndex == testContentStringArr.length || letterIndex == testTextContentCharArr.length) {  //when the test is done
            nextTest();
        }

    }
}
