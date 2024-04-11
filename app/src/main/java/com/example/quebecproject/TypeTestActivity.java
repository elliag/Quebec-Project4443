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
    private int testNumber;                     //the current test number (1, 2, 3)
    //accuracy
    private int numErrors = 0;                  //stores the number of errors the user has made
    private int numCorrects = 0;                //stores the number of correct inputs the user has made
    //time
    private long startTime;                     //capture the time the test started
    private long endTime;                       //capture the time the test ended
    private long elapsedTime;                   //calculate how much time the test took

    //more
    int wordIndex;                              //the word the user is currently at
    int letterIndex;                            //the letter the user is currently at
    String[] testContentStringArr;              //the content of the test in a string array

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

        inputTextString = "";                                           //the String that will hold the input text
        inputText = (EditText) findViewById(R.id.textInput);            //initialize the invisible EditText that will hold what the user types.
        inputText.getText().clear();
        inputText.addTextChangedListener(this);                 //add a text watcher to log errors and change colours of letters.

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

        wordIndex = 0;      //will hold the index of the current word the user is at
        letterIndex = 0;    //will hold the index of the current letter the user is at
    }

    /**
     * Prepares the activity to transition to the "proceed to next test" screen.
     */
    private void nextTest() {   //called when the current test has finished
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        float elapsedTimeSeconds = elapsedTime / 1000.0f;   //get the time it took to type

        imm.hideSoftInputFromWindow(inputText.getWindowToken(),0);  //hide the keyboard
        inputText.clearFocus();
        inputText.setFocusableInTouchMode(false);

        double accuracy;
        int total = numCorrects + numErrors;
        accuracy = ((double) numCorrects / total) * 100.0;  //compute accuracy (this is wrong idk what to do)

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
                break;
        }

        testNumber++;   //increment test number for the next test

        Bundle b = new Bundle();

        b.putInt("next test number", testNumber);   //bundle the next test number

        Intent i = new Intent(this, ProceedNextActivity.class);
        i.putExtras(b);
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

        int difference = s.length() - inputTextBefore.length(); //the difference in length between the current input and the input before the last change

        if (difference == 1 && inputText.hasFocus()) {  //for tap typing the difference will be 1
            
            lastLetter = inputTextStringCharArray[inputTextStringCharArray.length-1];    //get the last letter entered
            currentPos = testTextContentCharArr[inputTextStringCharArray.length-1];      //get the current letter we are at
            if (lastLetter != currentPos) { //if they are different
                spannable.setSpan(new ForegroundColorSpan(Color.RED), inputTextStringCharArray.length-1, inputTextStringCharArray.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  //colour change red
                numErrors++; //increment number of errors
            }
            else {  //if they are the same
                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), inputTextStringCharArray.length-1, inputTextStringCharArray.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  //colour change red
                numCorrects++; //increment number of errors
            }
            testTextContent.setText(spannable, TextView.BufferType.SPANNABLE);
            letterIndex++;  //increment the letter index
        }
        else if (difference > 1 && inputText.hasFocus()) {  //for swipe typing the difference will be more than 1

            String[] wordArr = inputTextString.split(" ");  //split the input text into a string array
            String latestWord = wordArr[wordArr.length-1];        //get the latest word entered

            int indexCurrentPosEnd = letterIndex + testContentStringArr[wordIndex].length();    //the end of the current word the user is at

            if (!latestWord.equals(testContentStringArr[wordIndex])) {  //if they are not equal
                spannable.setSpan(new ForegroundColorSpan(Color.RED), letterIndex, indexCurrentPosEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                numErrors = numErrors + latestWord.length();    //increment numErrors by the amount of letters in the latest word
            }
            else {
                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), letterIndex, indexCurrentPosEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                numCorrects = numCorrects + latestWord.length(); //increment numCorrects by the amount of letters in the latest word
            }
            testTextContent.setText(spannable, TextView.BufferType.SPANNABLE);

            letterIndex = letterIndex + testContentStringArr[wordIndex].length();   //increment letterIndex by the number of letters in the current word
            wordIndex++;    //increment word index by 1

        }
        else if (difference < 0 && inputText.hasFocus()) {  //to "disable" backspace

            inputText.clearFocus(); //so that the Text Watcher is not called again
            s.replace(0,s.length(),inputTextBefore);    //replace the text in input text with whatever the previous input was before the latest change
            inputText.requestFocus();

        }

        if (wordIndex == testContentStringArr.length || letterIndex == testTextContentCharArr.length) {  //when the test is done
            nextTest();
        }

    }
}
