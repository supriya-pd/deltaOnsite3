package com.example.delta_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    Button resetBTN;
    ToggleButton toggleButton;
    CustomClock customClock;

    //public MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetBTN=findViewById(R.id.reset);
        toggleButton=findViewById(R.id.toggleButton);
        customClock=findViewById(R.id.clock);
         resetBTN.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 customClock.resetButtonClicked();
                 toggleButton.setChecked(false);
             }
         });

         toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 if(b){
                     customClock.startButtonClicked();
                 }
                 else{
                     customClock.stopButtonClicked();
                 }
             }
         });

    }

    public void changeToggleName(){
        toggleButton.setChecked(false);
    }

}