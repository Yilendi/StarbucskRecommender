/**
 * page for caffeine and protein selection
 */
package com.yilendad.starbucksrecommender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Caff extends FilterPage {

    Button btnCaffApply;
    Button btnCaffView;
    Button btnCaffNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caff);
        //page-specific setup
        columnName = DBHelper.colCaffiene;
        columnName2 = DBHelper.colProtein;
        originTable = DBHelper.tableArray[5];
        destTable = DBHelper.tableArray[6];
        //xml-related setup
        radioGroup = findViewById(R.id.rg_caff);
        radioGroup2 = findViewById(R.id.rg_pro);
        btnCaffApply = (Button) findViewById(R.id.btn_caff_apply);
        btnCaffView = findViewById(R.id.btn_caff_view);
        btnCaffNext = findViewById(R.id.btn_caff_next) ;
        //set min, max, thirds, and choice1-3
        setNum();
        //define rb
        rb1 = findViewById(R.id.rb_caff_1);
        rbId1 = R.id.rb_caff_1;
        rb2 = findViewById(R.id.rb_caff_2);
        rbId2 = R.id.rb_caff_2;
        rb3 = findViewById(R.id.rb_caff_3);
        rbId3 = R.id.rb_caff_3;
        rb4 = findViewById(R.id.rb_caff_4);
        rbId4 = R.id.rb_caff_4;
        rb5 = findViewById(R.id.rb_pro_1);
        rbId5 = R.id.rb_pro_1;
        rb6 = findViewById(R.id.rb_pro_2);
        rbId6 = R.id.rb_pro_2;
        rb7 = findViewById(R.id.rb_pro_3);
        rbId7 = R.id.rb_pro_3;
        rb8 = findViewById(R.id.rb_pro_4);
        rbId8 = R.id.rb_pro_4;
        //set rb
        rb1.setText(choice1);
        rb2.setText(choice2);
        rb3.setText(choice3);
        rb5.setText(choice4);
        rb6.setText(choice5);
        rb7.setText(choice6);
        //apply and view selection
        btnCaffApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create and populate new table with only one radio group implemented
                createAndCopyOrPopulate(true);
                //view what's selected
                viewProducts(destTable) ;
            }
        });
        //view all drinks
        btnCaffView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view all drinks
                viewProducts(DBHelper.tableName) ;
            }
        });
        //clear all data and go to home page
        btnCaffNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper helper = new DBHelper(Caff.this);
                helper.dropTable();
                helper.close();
                Intent home_intent = new Intent(Caff.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home_intent);
            }
        });
    }
}