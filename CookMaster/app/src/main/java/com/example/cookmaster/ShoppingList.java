package com.example.cookmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.cookmaster.adapters.ChoppingListAdapter;
import com.example.cookmaster.domain.ShoppingEntry;
import com.example.cookmaster.services.DataService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        DataService dataService = new DataService(this);
        List<ShoppingEntry> shoppingEntries = dataService.GetShoppingList(0);

        ListView listView = findViewById(R.id.shopping_list);
        listView.setAdapter(new ChoppingListAdapter(shoppingEntries, false, this, dataService));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_shopping_list);
        bottomNavigationView.setSelectedItemId(R.id.action_shopping);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationList(this));
    }
}