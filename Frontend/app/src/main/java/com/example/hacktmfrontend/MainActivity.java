package com.example.hacktmfrontend;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hacktmfrontend.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(this.navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, this.navController, appBarConfiguration);

        Button add = (Button)findViewById(R.id.button_first);

        add.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
    private void navigateToSecondFragment()
    {
        this.navController.navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                null,
                new NavOptions.Builder()
                        .setEnterAnim(android.R.animator.fade_in)
                        .setExitAnim(android.R.animator.fade_out)
                        .build()
        );
    }

    public void onClick(View view)
    {
        EditText caloriesComponent = (EditText)findViewById(R.id.Calories);
        EditText proteinsComponent = (EditText)findViewById(R.id.proteins);
        EditText carbsComponent = (EditText)findViewById(R.id.carbs);
        EditText fatsComponent = (EditText)findViewById(R.id.fats);
        EditText daysComponent = (EditText)findViewById(R.id.days);
        EditText mealPerDayComponent = (EditText)findViewById(R.id.mealsPerDay);

        int calories = Integer.parseInt(caloriesComponent.getText().toString());
        int proteins = Integer.parseInt(proteinsComponent.getText().toString());
        int carbs = Integer.parseInt(carbsComponent.getText().toString());
        int fats = Integer.parseInt(fatsComponent.getText().toString());
        int days = Integer.parseInt(daysComponent.getText().toString());
        int mealPerDay = Integer.parseInt(mealPerDayComponent.getText().toString());

//        GoalApiService helper = new GoalApiService(this);
//        helper.sendPost(
//                calories,
//                proteins,
//                carbs,
//                fats);
//
        OptimizerApiService optimizerApiService= new OptimizerApiService(this);

        try {
            String dietPlan = optimizerApiService.getDiet(
                    proteins,
                    carbs,
                    fats,
                    days,
                    mealPerDay,
                    calories);

            this.showDietPlan(dietPlan);
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        this.navigateToSecondFragment();
    }

    public void showDietPlan(String dietPlan) throws JSONException {
        setContentView(R.layout.fragment_second);
        ListView listView = (ListView) findViewById(R.id.listView1);
        ArrayList<String> dietPlanArrayList = JsonToArrayListParser.parse(dietPlan);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                dietPlanArrayList );

        listView.setAdapter(arrayAdapter);
    }
}