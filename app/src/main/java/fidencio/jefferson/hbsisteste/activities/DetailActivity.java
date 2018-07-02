package fidencio.jefferson.hbsisteste.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import fidencio.jefferson.hbsisteste.HbsisApp;
import fidencio.jefferson.hbsisteste.R;
import fidencio.jefferson.hbsisteste.adapters.CityForecastRecyclerViewAdapter;
import fidencio.jefferson.hbsisteste.models.CityFiveDaysForecast;
import fidencio.jefferson.hbsisteste.models.CityWeather;
import fidencio.jefferson.hbsisteste.models.TimeForecast;
import fidencio.jefferson.hbsisteste.parsers.JsonParserRequest;
import fidencio.jefferson.hbsisteste.services.ServerConnection;

public class DetailActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView;
    private static final String URI_GET_FORECAST_5D_CITY = "http://api.openweathermap.org/data/2.5/forecast?units=metric&q=";
    private CityWeather cityWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        cityWeather = (CityWeather) getIntent().getExtras().get("CityWeather");
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        textView.setText(textView.getText() + " na cidade de " + cityWeather.getName() + ": ");
        loadList();
    }

    private void loadList() {
        //atualiza UI
        showProgressBar(true);

        //requisicao
        String url = URI_GET_FORECAST_5D_CITY + cityWeather.getName() + "&apikey="+ HbsisApp.OPEN_WEATHER_API_KEY;
        ServerConnection.getInstance(this).getRequestQueue().add(new JsonParserRequest<CityFiveDaysForecast>(url, CityFiveDaysForecast.class, null, new Response.Listener<CityFiveDaysForecast>() {
            @Override
            public void onResponse(CityFiveDaysForecast response)
            {
                showProgressBar(false);
                loadRecyclerView(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getClass().equals(NoConnectionError.class))
                {
                    showProgressBar(false);
                    Snackbar.make(recyclerView,R.string.error_no_connection,Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    showProgressBar(false);
                    Snackbar.make(recyclerView,R.string.error_unknown,Snackbar.LENGTH_LONG).show();
                }
            }
        }));

    }

    private void loadRecyclerView(CityFiveDaysForecast response) {
        CityForecastRecyclerViewAdapter cityForecastListAdapter = new CityForecastRecyclerViewAdapter(response);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cityForecastListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void showProgressBar(boolean visible) {
        if (visible)
        {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            recyclerView.setVisibility(ProgressBar.INVISIBLE);
        }
        else
        {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            recyclerView.setVisibility(ProgressBar.VISIBLE);
        }
    }
}
