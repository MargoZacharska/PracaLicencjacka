package com.example.cookmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cookmaster.services.DataService;
import com.example.cookmaster.services.RecipeListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Main", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_recipes:
                        Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.my_week:
                        Toast.makeText(MainActivity.this, "My week", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_shopping:
                        Toast.makeText(MainActivity.this, "Shopping list", Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.shopping_list);
                        break;
                }
                return true;
            }
        });
    }

}