/**
 * page for fat and cholesterol selection
 */
package com.yilendad.starbucksrecommender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Fat_Cho extends FilterPage {

    Button btnFatApply;
    Button btnFatView;
    Button btnFatNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_cho);
        //page-specific setup
        columnName = DBHelper.colTotalFat;
        columnName2 = DBHelper.colCholesterol;
        originTable = DBHelper.tableArray[3];
        destTable = DBHelper.tableArray[4];
        //xml-related setup
        radioGroup = findViewById(R.id.rg_fat);
        radioGroup2 = findViewById(R.id.rg_cho);
        btnFatApply = (Button) findViewById(R.id.btn_fat_apply);
        btnFatView = findViewById(R.id.btn_fat_view);
        btnFatNext = findViewById(R.id.btn_fat_next) ;
        //set min, max, thirds, and choice1-3
        setNum();
        //define rb
        rb1 = findViewById(R.id.rb_fat_1);
        rbId1 = R.id.rb_fat_1;
        rb2 = findViewById(R.id.rb_fat_2);
        rbId2 = R.id.rb_fat_2;
        rb3 = findViewById(R.id.rb_fat_3);
        rbId3 = R.id.rb_fat_3;
        rb4 = findViewById(R.id.rb_fat_4);
        rbId4 = R.id.rb_fat_4;
        rb5 = findViewById(R.id.rb_cho_1);
        rbId5 = R.id.rb_cho_1;
        rb6 = findViewById(R.id.rb_cho_2);
        rbId6 = R.id.rb_cho_2;
        rb7 = findViewById(R.id.rb_cho_3);
        rbId7 = R.id.rb_cho_3;
        rb8 = findViewById(R.id.rb_cho_4);
        rbId8 = R.id.rb_cho_4;
        //set rb
        rb1.setText(choice1);
        rb2.setText(choice2);
        rb3.setText(choice3);
        rb5.setText(choice4);
        rb6.setText(choice5);
        rb7.setText(choice6);
        //apply and view selection
        btnFatApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create and populate new table with only one radio group implemented
                createAndCopyOrPopulate(true);
                //view what's selected
                viewProducts(destTable) ;
            }
        });
        //view all drinks
        btnFatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view all drinks
                viewProducts(DBHelper.tableName) ;
            }
        });
        //apply and go to next page
        btnFatNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create and populate new table with only one radio group implemented
                createAndCopyOrPopulate(true);
                //go to next page if data is present, otherwise go to blank page
                if(zeroRow) {
                    startActivity(new Intent (Fat_Cho.this, Blank.class));
                }
                else{
                    Intent i = new Intent(Fat_Cho.this, Carbo.class) ;
                    startActivity(i);
                }
            }
        });
    }
}