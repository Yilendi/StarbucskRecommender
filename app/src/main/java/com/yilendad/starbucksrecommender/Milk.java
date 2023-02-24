/**
 * page for milk selection
 */
package com.yilendad.starbucksrecommender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Milk extends FilterPage {

    Button btnMilkApply;
    Button btnMilkView;
    Button btnMilkNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk);

        //page-specific setup
        columnName = DBHelper.colMilk;
        columnName2 = DBHelper.colWhip;
        originTable = DBHelper.tableArray[1];
        destTable = DBHelper.tableArray[2];
        //xml-related setup
        radioGroup = findViewById(R.id.rg_milk);
        radioGroup2 = findViewById(R.id.rg_whip);
        btnMilkApply = (Button) findViewById(R.id.btn_milk_apply);
        btnMilkView = findViewById(R.id.btn_milk_view);
        btnMilkNext = findViewById(R.id.btn_milk_next) ;
        //apply and view selection
        btnMilkApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user choice of size
                String choice = getChoice();
                String choice2 = getChoice2();
                DBHelper helper = new DBHelper(Milk.this);
                helper.createNewTable(destTable, originTable);
                helper.populateTable(destTable, originTable, columnName, choice, columnName2, choice2);
                helper.close();
                //view what's selected
                viewProducts(destTable) ;
            }
        });
        //apply and go to next page
        btnMilkNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user choice of size
                String choice = getChoice();
                String choice2 = getChoice2();
                DBHelper helper = new DBHelper(Milk.this);
                helper.createNewTable(destTable, originTable);
                helper.populateTable(destTable, originTable, columnName, choice, columnName2, choice2);
                helper.close();
                //start new page
                Intent i = new Intent(Milk.this, Calories.class) ;
                startActivity(i);
            }
        });
        //view all drinks
        btnMilkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view everything
                viewProducts(DBHelper.tableName) ;
            }
        });


    }
}