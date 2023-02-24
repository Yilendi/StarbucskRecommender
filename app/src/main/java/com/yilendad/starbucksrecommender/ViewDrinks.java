/**
 * recycler view page for drinks in a given table
 */
package com.yilendad.starbucksrecommender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ViewDrinks extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<DrinkModel> drinkModelList;
    Button btnHome;
    Button btnBack;

    Menu menu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks);
        //retrieve passed arraylist for display
        drinkModelList = this.getIntent().getExtras().getParcelableArrayList("Drinks");
        if (drinkModelList != null) {
            Log.d("passed list",""+drinkModelList.toString());
        }
        recyclerView = findViewById(R.id.rv_show) ;
        //use a linear layout manager
        layoutManager = new LinearLayoutManager(this) ;
        recyclerView.setLayoutManager(layoutManager);
        //specify an adapter
        mAdapter = new RecyclerAdapter(drinkModelList, this);
        recyclerView.setAdapter(mAdapter);

        btnHome = (Button) findViewById(R.id.btn_return) ;
        btnBack = (Button) findViewById(R.id.btn_last);
        //return home
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });
        //return to last page
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDrinks.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    /**
     * sort based on menu item selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuAtoZ:
                //sort a to z
                Collections.sort(drinkModelList, DrinkModel.nameAZSort);
                Toast.makeText(ViewDrinks.this, "Sort A to Z", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.menuCalDesc:
                //sort by calories, desc
                Collections.sort(drinkModelList, DrinkModel.calDesc);
                Toast.makeText(ViewDrinks.this, "Sort by Calories, DESC", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.menuCalAsc:
                //sort by calories, desc
                Collections.sort(drinkModelList, DrinkModel.calAsc);
                Toast.makeText(ViewDrinks.this, "Sort by Calories, ASC", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * return to home page
     */
    public void returnHome() {
        Intent homeIntent = new Intent(this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}