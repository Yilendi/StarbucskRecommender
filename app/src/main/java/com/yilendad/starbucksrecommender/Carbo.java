/**
 * page for carbohydrates and added sugar selection
 */
package com.yilendad.starbucksrecommender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Carbo extends FilterPage {

    Button btnCarboApply;
    Button btnCarboView;
    Button btnCarboNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbo);
        //page-specific setup
        columnName = DBHelper.colTotalCarbohydrates;
        columnName2 = DBHelper.colSugar;
        originTable = DBHelper.tableArray[4];
        destTable = DBHelper.tableArray[5];
        //xml-related setup
        radioGroup = findViewById(R.id.rg_carbo);
        radioGroup2 = findViewById(R.id.rg_sug);
        btnCarboApply = (Button) findViewById(R.id.btn_carbo_apply);
        btnCarboView = findViewById(R.id.btn_carbo_view);
        btnCarboNext = findViewById(R.id.btn_carbo_next) ;
        //set min, max, thirds, and choice1-3
        setNum();
        //define rb
        rb1 = findViewById(R.id.rb_carbo_1);
        rbId1 = R.id.rb_carbo_1;
        rb2 = findViewById(R.id.rb_carbo_2);
        rbId2 = R.id.rb_carbo_2;
        rb3 = findViewById(R.id.rb_carbo_3);
        rbId3 = R.id.rb_carbo_3;
        rb4 = findViewById(R.id.rb_carbo_4);
        rbId4 = R.id.rb_carbo_4;
        rb5 = findViewById(R.id.rb_sug_1);
        rbId5 = R.id.rb_sug_1;
        rb6 = findViewById(R.id.rb_sug_2);
        rbId6 = R.id.rb_sug_2;
        rb7 = findViewById(R.id.rb_sug_3);
        rbId7 = R.id.rb_sug_3;
        rb8 = findViewById(R.id.rb_sug_4);
        rbId8 = R.id.rb_sug_4;
        //set rb
        rb1.setText(choice1);
        rb2.setText(choice2);
        rb3.setText(choice3);
        rb5.setText(choice4);
        rb6.setText(choice5);
        rb7.setText(choice6);
        //apply and view selection
        btnCarboApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create and populate new table with only one radio group implemented
                createAndCopyOrPopulate(true);
                //view what's selected
                viewProducts(destTable) ;
            }
        });
        //view all drinks
        btnCarboView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view all drinks
                viewProducts(DBHelper.tableName) ;
            }
        });
        //apply and go to next page
        btnCarboNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create and populate new table with only one radio group implemented
                createAndCopyOrPopulate(true);
                if(zeroRow) {
                    startActivity(new Intent (Carbo.this, Blank.class));
                }
                else{
                    //go to next page
                    Intent i = new Intent(Carbo.this, Caff.class) ;
                    startActivity(i);
                }
            }
        });
    }
}