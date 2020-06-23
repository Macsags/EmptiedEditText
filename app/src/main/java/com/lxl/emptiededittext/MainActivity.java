package com.lxl.emptiededittext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout constraintLayout = findViewById(R.id.cl);
        EmptiedEditText emptiedEditText = findViewById(R.id.ee);
        emptiedEditText.layout(constraintLayout,0xffb1b1b1);
    }
}
