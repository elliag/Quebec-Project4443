package com.example.quebecproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TypeTestActivity extends Activity {
    TextView testText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typetest3);

        testText = (TextView) findViewById(R.id.testContent);
    }

    
}
