package com.example.quebecproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;

public class TypeTestActivity extends Activity {
    final static String MYDEBUG = "MYDEBUG";
    private TextView testTextContent;
    private char[] testTextContentCharArr;
    private Spannable spannable;
    private String inputTextBefore;
    private EditText inputText;
    private Button startButton;
    private String inputTextString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typetest3);
        Initialize();
    }

    private void Initialize() {
        testTextContent = (TextView) findViewById(R.id.testContent);    //initialize the text view that holds the phrase that needs to be typed.
        testTextContentCharArr = testTextContent.getText().toString().toCharArray();    //convert the content of that text view into a char array.
        spannable = new SpannableString(testTextContent.getText());     //initialize the Spannable string that will allow us to change the colour of the test content when someone makes a boo boo mistake
        inputTextString = "";
        inputText = (EditText) findViewById(R.id.textInput);            //initialize the invisible EditText that will hold what the user types.
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
                inputTextBefore = inputTextString; //the input string before any changes are made
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
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
        });
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputText.requestFocus();
                inputText.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(inputText, InputMethodManager.SHOW_FORCED);
            }
        });
    }
}
