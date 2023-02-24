/**
 * page for size selection
 */
package com.yilendad.starbucksrecommender;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Scanner;

public class SizeActivity extends FilterPage {

//    RadioGroup radioGroup;
//    RadioButton radioButton;

    Button btn_apply ;
    Button btn_view;
    Button btn_next;
    String size[];


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        //page-specific setup
        columnName = DBHelper.colSize;
        originTable = DBHelper.tableArray[0];
        destTable = DBHelper.tableArray[1];
        //xml-related setup
        radioGroup = findViewById(R.id.rg_size);
        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_view = findViewById(R.id.btn_view);
        btn_next = findViewById(R.id.btn_next_filter) ;
        //apply and view selection
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get user choice of size
                String choice = getChoice();
                size = parseString(choice);
                createPopulate(originTable, destTable, DBHelper.colSize, size[0], size[1]);
                //view what's selected
                viewProducts(DBHelper.sizeTable) ;
            }
        });
        //view all drinks
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view all drinks
                viewProducts(DBHelper.tableName) ;
            }
        });
        //apply and go to next page
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user choice of size
                String choice = getChoice();
                size = parseString(choice);
                createPopulate(originTable, destTable, DBHelper.colSize, size[0], size[1]);

                //createPopulate(choice);
                //go to next page
                Intent i = new Intent(SizeActivity.this, Milk.class) ;
                startActivity(i);
            }
        });
    }

}