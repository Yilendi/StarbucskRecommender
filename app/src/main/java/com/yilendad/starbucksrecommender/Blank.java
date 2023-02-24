/**
 * page for if no data is in the table for viewing
 */
package com.yilendad.starbucksrecommender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Blank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        Button btn_blank = findViewById(R.id.btn_blank);
        //go back to last page for re-selection
        btn_blank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Blank.this.finish();
            }
        });
    }
}