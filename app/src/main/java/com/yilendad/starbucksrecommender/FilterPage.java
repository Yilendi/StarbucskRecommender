package com.yilendad.starbucksrecommender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class FilterPage extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioGroup radioGroup2;
    RadioButton radioButton2;
    String columnName;
    String columnName2;

    String originTable;
    String destTable;


    RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8;
    int rbId1, rbId2, rbId3, rbId4, rbId5, rbId6, rbId7, rbId8;
    String choice1, choice2, choice3, choice4, choice5, choice6;

    double minVal, minVal2;
    double maxVal, maxVal2;
    double third, third2;
    double finalThird, finalThird2;

    boolean sameVal, sameVal2;
    boolean zeroRow;

    /**
     * set min, max, thirds and choice1-3 for radiobutton
     * if there is a second radio group, also set above parameters for that group
     */
    public void setNum() {
        sameVal2 = sameVal = false;
        DBHelper helper = new DBHelper(this);
        //get max and min value in first column
        maxVal = helper.getMaxColumnData(columnName, originTable);
        minVal = helper.getMinColumnData(columnName, originTable);
        //if min and max aren't the same
        if (maxVal != minVal) {
            third = (maxVal-minVal)/3;
            finalThird = Math.round( third * 100.00 ) / 100.00;
            choice1 = minVal + " - " + (minVal + finalThird);
            choice2 = (minVal + finalThird) + " - " + (minVal + finalThird*2);
            choice3 = (minVal + finalThird*2) + " - " + maxVal;
        } else {
            sameVal = true;
            choice1 = choice2 = choice3 = minVal + " - " + minVal;
        }
        //if there is a second column to operate on
        if (columnName2 != null) {
            //get max and min value in second column
            maxVal2 = helper.getMaxColumnData(columnName2, originTable);
            minVal2 = helper.getMinColumnData(columnName2, originTable);
            if (maxVal2 != minVal2) {
                third2 = (maxVal2-minVal2)/3;
                finalThird2 = Math.round( third2 * 100.00 ) / 100.00;
                choice4 = minVal2 + " - " + (minVal2 + finalThird2);
                choice5 = (minVal2 + finalThird2) + " - " + (minVal2 + finalThird2*2);
                choice6 = (minVal2 + finalThird2*2) + " - " + maxVal2;
            }
            else {
                sameVal2 = true;
                choice4 = choice5 = choice6 = minVal2 + " - " + minVal2;
            }
        }
        helper.close();
    }

    /**
     * go to page where user views products in given table
     * @param tableName
     */
    public void viewProducts(String tableName) {
        DBHelper dbhelper = new DBHelper(this) ;
        //populate drinkmodel list for all drinks in main table
        ArrayList<DrinkModel> all = dbhelper.getAllDrinks(tableName) ;
        dbhelper.close() ;
        //start a new page that views all drinks in main table
        Intent i = new Intent(this, ViewDrinks.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Drinks", all);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void createPopulate(String fromTable, String toTable, String col, String target1, String target2) {
        DBHelper dbhelper = new DBHelper(this) ;
        //create new table
        dbhelper.createNewTable(toTable, fromTable);
        //populate table based on selection
        dbhelper.populateTable(toTable, fromTable, col, target1, target2) ;
        dbhelper.close();
    }

    /**
     * toast text for the selected radio button
     * @param v
     */
    public void checkButton(View v) {
        RadioButton rb = (RadioButton)v;
        //get radio button selected
        String target = getChoice();
        //show the number of rows associated with this choice
        DBHelper dbhelper = new DBHelper(this) ;
        int row = dbhelper.getRowsCount(originTable, columnName, target);
        //show choice and number of rows
        Toast.makeText(this, row + " drinks qualified for " + rb.getText().toString() + ". (Based on this choice only.)",
                Toast.LENGTH_SHORT).show();
        dbhelper.close();
    }


    /**
     * toast text for the selected radio button (for radio group 1)
     * @param v
     */
    public void checkNumButton(View v) {
        int row;
        RadioButton rb = (RadioButton)v;
        DBHelper dbhelper = new DBHelper(this) ;
        if (sameVal) {
            row = dbhelper.getRowsCount(originTable);
            Toast.makeText(this, row + " drinks qualified.",
                    Toast.LENGTH_SHORT).show();
        } else {
            //show the number of rows associated with this choice
            String ans = rb.getText().toString();
            double[] range = parseDouble(ans);
            row = dbhelper.getRowsCount(originTable, columnName, range[0], range[1]);
            //show choice and number of rows
            Toast.makeText(this, row + " drinks qualified for " + rb.getText().toString() + ". (Based on this choice only.)",
                    Toast.LENGTH_SHORT).show();
        }
        dbhelper.close();
    }
    /**
     * toast text for the selected radio button (for radiogroup2, if any)
     * @param v
     */
    public void checkNumButtonSecond(View v) {
        int row;
        RadioButton rb = (RadioButton)v;
        DBHelper dbhelper = new DBHelper(this) ;
        if (sameVal2) {
            //get row count of the previous table
            row = dbhelper.getRowsCount(originTable);
            //show the number of drinks qualified based on this selection only
            Toast.makeText(this, row + " drinks qualified.",
                    Toast.LENGTH_SHORT).show();
        } else {
            //show the number of rows associated with this choice
            String ans = rb.getText().toString();
            double[] range = parseDouble(ans);
            //if this button is on the second radio group, apply range on the second column
            row = dbhelper.getRowsCount(originTable, columnName2, range[0], range[1]);
            //show choice and number of rows
            Toast.makeText(this, row + " drinks qualified for " + rb.getText().toString() + ". (Based on this choice only.)",
                    Toast.LENGTH_SHORT).show();
        }
        dbhelper.close();
    }

    /**
     * parse double array associated with user choice in radiobutton
     * used for numeric range selection
     * @param sample
     * @return
     */
    public double[] parseDouble(String sample) {
        //scan and set delimiter as " - "
        Scanner scan = new Scanner(sample).useDelimiter(" - ");
        double[] range = new double[2];
        //parse double from the string and put in double array in sequence
        for (int i = 0; i < 2; i++) {
            range[i] = Double.parseDouble(scan.next());
            Log.d("parse", "parsed double from radiobutton");
        }
        scan.close();
        return range;
    }

    /**
     * parse String array associated with user choice in radiobutton
     * used for size selection
     * @param sample
     * @return
     */
    public String[] parseString(String sample) {
        //scan and set delimiter as "/"
        Scanner scan = new Scanner(sample).useDelimiter("/");
        String[] size = new String[2];
        //parse string from the string and put in string array in sequence
        for (int i = 0; i <2; i ++) {
            size[i] = scan.next();
            Log.d("parse", "parsed String pair");
        }
        scan.close();
        return size;
    }
    /**
     * toast text for the selected radio button
     * @param v
     */
    public void checkNumButtonAll(View v) {
        RadioButton rb = (RadioButton)v;
        //show the number of rows associated with this choice
        DBHelper dbhelper = new DBHelper(this) ;
        int row = dbhelper.getRowsCount(originTable);
        //show choice and number of rows
        Toast.makeText(this, row + " drinks qualified for " + rb.getText().toString() + ". (Based on this choice only.)",
                Toast.LENGTH_SHORT).show();
        dbhelper.close();
    }

    /**
     * get user choice based on button
     * @return user choice
     */
    public String getChoice() {
        //get selected radio button
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        //get string on selected radio button
        String choice = radioButton.getText().toString();
        return choice;
    }
    /**
     * get user choice based on button (second choice)
     * @return user choice
     */
    public String getChoice2() {
        //get selected radio button
        int radioId = radioGroup2.getCheckedRadioButtonId();
        radioButton2 = findViewById(radioId);
        //get string on selected radio button
        String choice = radioButton2.getText().toString();
        return choice;
    }

    /**
     * create populate table based on user choice
     * @param twoGroup
     */
    public void getChoicePop(boolean twoGroup) {
        //get user choice of size
        String ans1 = getChoice();
        double[] range1 = parseDouble(ans1);
        DBHelper helper = new DBHelper(this);
        helper.createNewTable(destTable, originTable);
        //if only one radio group, filter based on one numeric range
        if (twoGroup == false) {
            helper.populateTable(destTable, originTable, columnName, range1[0], range1[1]);
            //when two radio groups are present, filter based on two numeric ranges
        } else {
            String ans2 = getChoice2();
            double[] range2 = parseDouble(ans2);
            helper.populateTable(destTable, originTable, columnName, range1[0], range1[1],
                    columnName2, range2[0], range2[1]);
        }
        helper.close();
    }

    /**
     * create new table and copy entire previous table into new one
     */
    public void createCopy() {
        DBHelper helper = new DBHelper(this);
        helper.createNewTable(destTable, originTable);
        helper.copyTable(destTable, originTable);
        helper.close();
    }

    /**
     * based on whether two radio groups are implemented, create and populate new table
     * @param twoGroup
     */
    public void createAndCopyOrPopulate(boolean twoGroup) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        //if there is only one radio group
        if (twoGroup == false) {
            //if selected for "no preference", create table and copy all from old table
            if (radioId == rbId4) {
                createCopy();
            } else {
                //otherwise, get user choice
                getChoicePop(false);
            }
            //if there are two radio groups
        } else {
           int radioId2 = radioGroup2.getCheckedRadioButtonId();
           //if no preference for both
           if (radioId == rbId4 &&radioId2 == rbId8) {
               createCopy();
               //if no preference for radio group 1, only filter on radio group 2
           } else if (radioId == rbId4){
               String ans2 = getChoice2();
               double[] range2 = parseDouble(ans2);
               DBHelper helper = new DBHelper(this);
               helper.createNewTable(destTable, originTable);
               helper.populateTable(destTable, originTable, columnName2, range2[0], range2[1]);
               helper.close();
               //if no preference for radio group 2, only filter on radio group 1
           } else if (radioId2 == rbId8) {
               String ans1 = getChoice();
               double[] range1 = parseDouble(ans1);
               DBHelper helper = new DBHelper(this);
               helper.createNewTable(destTable, originTable);
               helper.populateTable(destTable, originTable, columnName, range1[0], range1[1]);
               helper.close();
               //if has preference for both, then get choice and populate table
           } else {
               getChoicePop(true);
           }
        }
        DBHelper helper = new DBHelper(this);
        int row = helper.getRowsCount(destTable);
        //set whether a table has no data
        if (row == 0) {
            zeroRow = true;
        } else {
            zeroRow = false;
        }
    }
}