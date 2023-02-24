/**
 * this is the home page
 */
package com.yilendad.starbucksrecommender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * more design
 *
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<DrinkModel> drinkModelList;
    //reference to buttons and other controls on the layout\
    Button btnNext;

    EditText etName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drinkModelList = new ArrayList<>();



        btnNext = (Button) findViewById(R.id.btn_next);
        etName = findViewById(R.id.user_name);

        btnNext.bringToFront();
        etName.bringToFront();

        //button listeners for adding all items from list and advance into the next page
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //refresh the database
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                dbHelper.refreshAll();
                //read all data
                readAll();
                //welcome message
                String userName = etName.getText().toString();
                String welcome = userName + ", let's begin our journey!" ;
                Toast.makeText(MainActivity.this, welcome, Toast.LENGTH_LONG).show();

                //launch a new activity
                Intent i = new Intent(MainActivity.this, SizeActivity.class);
                startActivity(i);
                dbHelper.close() ;
            }
        });
    }

    /**
     * read all drink stats from starbucks.csv
     */
    private void readAll() {
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //read from raw file
        InputStream is = getResources().openRawResource(R.raw.starbucks);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            String line ;
            int iteration = 0;
            db.beginTransaction();

            ContentValues cv = new ContentValues();

            while ((line = br.readLine()) != null) {

                Log.e("line", line);
                //skip header line
                if(iteration ==0) {
                    iteration++;
                    continue;
                }
                //after headerline
                String[] str = line.split(","); // defining 3 columns with null or blank field //values acceptance

                String name = str[0].toString();
                String size = str[1].toString();
                String milk = str[2].toString();
                String whip = str[3].toString();
                int cal = Integer.parseInt(str[4].toString());
                double fat = Double.parseDouble(str[5].toString());
                int cho = Integer.parseInt(str[6].toString());
                int carb = Integer.parseInt(str[7].toString());
                int sugar = Integer.parseInt(str[8].toString());
                double prot = Double.parseDouble(str[9].toString());
                int caf;
                try {//try parse caffeine content as int
                    caf = Integer.parseInt(str[10].toString());
                } catch (Exception e){
                    Log.d("parse", "Integer not parsed directly");
                    String caff = str[10].toString();
                    try {
                        //if caffeine is a range, parse content as an average for that range
                        Scanner scan = new Scanner(caff).useDelimiter("-");
                        double[] range = new double[2];
                        for (int i = 0; i < 2; i++) {
                            range[i] = Double.parseDouble(scan.next());
                            Log.d("parse", "parsed double from radiobutton");
                        }
                        scan.close();
                        double temp = (range[0] + range[1])/2;
                        caf = (int) Math.round(temp);
                    } catch (Exception e1){
                        //if caffeine is minimum number with "+", parse only the minimum number
                        Log.d("parse", "Integer parsed by first number");
                        caf = new Scanner(caff).useDelimiter("\\D+").nextInt();
                    }
                }
                //put in parsed values in content values
                cv.put(DBHelper.colName, name);
                cv.put(DBHelper.colSize, size);
                cv.put(DBHelper.colMilk, milk);
                cv.put(DBHelper.colWhip, whip);
                cv.put(DBHelper.colCalories, cal);
                cv.put(DBHelper.colTotalFat, fat);
                cv.put(DBHelper.colCholesterol, cho);
                cv.put(DBHelper.colTotalCarbohydrates, carb);
                cv.put(DBHelper.colSugar, sugar);
                cv.put(DBHelper.colProtein, prot);
                cv.put(DBHelper.colCaffiene, caf);

                db.insert(DBHelper.tableName, null, cv);
                Log.i("Import", "Successfully Updated Database.");
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (IOException e) {
            Log.e("IOException", e.getMessage().toString());
        }

    }
}

