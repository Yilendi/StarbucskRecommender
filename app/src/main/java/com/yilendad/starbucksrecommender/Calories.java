/**
 * page for calories selection
 */
package com.yilendad.starbucksrecommender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Calories extends FilterPage {

    Button btnCalApply;
    Button btnCalView;
    Button btnCalNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        //page-specific setup
        columnName = DBHelper.colCalories;
        originTable = DBHelper.tableArray[2];
        destTable = DBHelper.tableArray[3];
        //xml-related setup
        radioGroup = findViewById(R.id.rg_cal);
        btnCalApply = (Button) findViewById(R.id.btn_cal_apply);
        btnCalView = findViewById(R.id.btn_cal_view);
        btnCalNext = findViewById(R.id.btn_cal_next) ;
        //set min, max, thirds, and choice1-3
        setNum();
        //define rb
        rb1 = findViewById(R.id.rb_cal_1);
        rb2 = findViewById(R.id.rb_cal_2);
        rb3 = findViewById(R.id.rb_cal_3);
        rb4 = findViewById(R.id.rb_cal_4);
        rbId1 = R.id.rb_cal_1;
        rbId2 = R.id.rb_cal_2;
        rbId3 = R.id.rb_cal_3;
        rbId4 = R.id.rb_cal_4;
        //set rb
        rb1.setText(choice1);
        rb2.setText(choice2);
        rb3.setText(choice3);
        //apply and view selection
        btnCalApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create and populate new table with only one radio group implemented
                createAndCopyOrPopulate(false);
                //view what's selected
                viewProducts(destTable) ;
            }
        });
        //view all drinks
        btnCalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view all drinks
                viewProducts(DBHelper.tableName) ;
            }
        });
        //apply and go to next page
        btnCalNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create and populate new table with only one radio group implemented
                createAndCopyOrPopulate(false);
                //go to next page
                Intent i = new Intent(Calories.this, Fat_Cho.class) ;
                startActivity(i);
            }
        });
    }
}