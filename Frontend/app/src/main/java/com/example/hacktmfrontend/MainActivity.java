package com.example.hacktmfrontend;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hacktmfrontend.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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
        EditText calories = (EditText)findViewById(R.id.Calories);
        EditText proteins = (EditText)findViewById(R.id.proteins);
        EditText carbs = (EditText)findViewById(R.id.carbs);
        EditText fats = (EditText)findViewById(R.id.fats);

        GoalApiService helper = new GoalApiService(this);
        helper.sendPost(
                calories.getText().toString(),
                proteins.getText().toString(),
                carbs.getText().toString(),
                fats.getText().toString());

        this.onSupportNavigateUp();

        this.navigateToSecondFragment();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, addGoalURL,
//                response -> Log.i("da", "Response is: " + response), error -> Log.i("err", "Response is: " + error));
//        queue.add(jsonOblect);
    }
}