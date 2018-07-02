package fidencio.jefferson.hbsisteste.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import fidencio.jefferson.hbsisteste.HbsisApp;
import fidencio.jefferson.hbsisteste.R;
import fidencio.jefferson.hbsisteste.adapters.CityWeatherRecyclerViewAdapter;
import fidencio.jefferson.hbsisteste.adapters.SortMenuArrayAdapter;
import fidencio.jefferson.hbsisteste.enums.ListSortOption;
import fidencio.jefferson.hbsisteste.models.CityWeather;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCityActivityOpenIntent = new Intent(MainActivity.this, AddCityActivity.class);
                startActivityForResult(addCityActivityOpenIntent, 0);
            }
        });

        //mapeando e carregando lista
        recyclerView = findViewById(R.id.recycler_view);
        spinner = findViewById(R.id.spinner);
        loadSortSpinner();
        loadList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            Snackbar.make(recyclerView, getResources().getString(R.string.success_city_add), Snackbar.LENGTH_SHORT).show();
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void loadList() {

        HbsisApp.readCacheFile(this); //força recuperar cache - melhorar
        CityWeatherRecyclerViewAdapter cityForecastListAdapter = new CityWeatherRecyclerViewAdapter(HbsisApp.getAddedCities(),
                new CityWeatherRecyclerViewAdapter.OnItemClickListener() {
            @Override public void onItemClick(CityWeather item) {

                Intent intentOpenDetailActivity = new Intent(MainActivity.this, DetailActivity.class);
                intentOpenDetailActivity.putExtra("CityWeather", item);
                startActivity(intentOpenDetailActivity);

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cityForecastListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void loadSortSpinner() {

            ArrayList sortOptionsList = new ArrayList(Arrays.asList(ListSortOption.nome, ListSortOption.temp_min, ListSortOption.temp_max));
            SortMenuArrayAdapter adapter = new SortMenuArrayAdapter(this,
                    R.layout.sort_menu_item, sortOptionsList);

            spinner.setAdapter(adapter); // set the adapter to provide layout of rows and content
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ListSortOption sortOrder = (ListSortOption) parent.getItemAtPosition(position);
                    ((CityWeatherRecyclerViewAdapter)recyclerView.getAdapter()).sortDataset(sortOrder);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
    }
}
