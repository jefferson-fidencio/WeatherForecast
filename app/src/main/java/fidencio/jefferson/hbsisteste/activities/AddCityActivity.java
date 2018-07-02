package fidencio.jefferson.hbsisteste.activities;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import fidencio.jefferson.hbsisteste.HbsisApp;
import fidencio.jefferson.hbsisteste.R;
import fidencio.jefferson.hbsisteste.enums.ListAction;
import fidencio.jefferson.hbsisteste.models.CityWeather;
import fidencio.jefferson.hbsisteste.parsers.JsonParserRequest;
import fidencio.jefferson.hbsisteste.services.ServerConnection;

public class AddCityActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URI_GET_WEATHER_CITY = "http://api.openweathermap.org/data/2.5/weather?units=metric&q=";
    private static final String URI_GET_WEATHER_ICON = "http://openweathermap.org/img/w/";
    private EditText editText;
    private ConstraintLayout constraintLayoutProgressBar;
    private ConstraintLayout constraintLayoutPrevisao;
    private Button buttonProcurar;
    private Button buttonAdicionar;

    private CityWeather lastCityWeather = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        editText = findViewById(R.id.editText);
        buttonProcurar = findViewById(R.id.buttonProcurar);
        buttonAdicionar = findViewById(R.id.buttonAdicionar);
        constraintLayoutProgressBar = findViewById(R.id.constraintLayoutProgressBar);
        constraintLayoutPrevisao = findViewById(R.id.constraintLayoutPrevisao);
        buttonProcurar.setOnClickListener(this);
        buttonAdicionar.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.buttonProcurar:

                //verifica campos
                hideKeyboard(v);
                if (editText.getText().toString().equals(""))
                    return;

                //atualiza UI
                showProgressBar(true);

                //faz requisicao
                String URL_FINAL = URI_GET_WEATHER_CITY + editText.getText() + "&apikey="+ HbsisApp.OPEN_WEATHER_API_KEY;
                ServerConnection.getInstance(this).getRequestQueue().add(new JsonParserRequest<CityWeather>(URL_FINAL, CityWeather.class, null, new Response.Listener<CityWeather>() {
                    @Override
                    public void onResponse(CityWeather response)
                    {
                        showProgressBar(false);
                        showCityWeather(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getClass().equals(NoConnectionError.class))
                        {
                            showProgressBar(false);
                            Snackbar.make(v,R.string.error_no_connection,Snackbar.LENGTH_LONG).show();
                        }
                        else
                        {
                            showProgressBar(false);
                            Snackbar.make(v,R.string.error_unknown,Snackbar.LENGTH_LONG).show();
                        }
                    }
                }));
                break;
            case R.id.buttonAdicionar:
                if (lastCityWeather == null)
                {
                    Snackbar.make(v, getResources().getString(R.string.error_no_city_selected), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                HbsisApp.updateSavedCities(ListAction.create, lastCityWeather, AddCityActivity.this);
                finish();
                break;
        }
    }

    private void hideKeyboard(View v) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showCityWeather(CityWeather cityWeather) {

        // HACK para posterior adicao
        lastCityWeather = cityWeather;

        TextView textViewCityTempName = constraintLayoutPrevisao.findViewById(R.id.textViewCityTempName);
        textViewCityTempName.setText(cityWeather.getMain().getTemp() + " em " + cityWeather.getName());
        TextView textViewCityWeatherMainDescription = constraintLayoutPrevisao.findViewById(R.id.textViewCityWeatherMainDescription);
        textViewCityWeatherMainDescription.setText(cityWeather.getWeather().get(0).getMain());
        TextView textViewCityMax = constraintLayoutPrevisao.findViewById(R.id.textViewCityMax);
        textViewCityMax.setText("Max: " + String.valueOf(cityWeather.getMain().getTemp_max()) + " °C");
        TextView textViewCityMin = constraintLayoutPrevisao.findViewById(R.id.textViewCityMin);
        textViewCityMin.setText("Min: " + String.valueOf(cityWeather.getMain().getTemp_min()) + " °C");

        //icone
        final ImageView imageView = constraintLayoutPrevisao.findViewById(R.id.imageView);
        ServerConnection.getInstance(this).getImageLoader().get(URI_GET_WEATHER_ICON +  cityWeather.getWeather().get(0).getIcon() + ".png", new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }, 64, 64);

    }

    private void showProgressBar(boolean visible) {
        if (visible)
        {
            constraintLayoutProgressBar.setVisibility(ProgressBar.VISIBLE);
            constraintLayoutPrevisao.setVisibility(ProgressBar.INVISIBLE);
            buttonAdicionar.setVisibility(Button.INVISIBLE);
        }
        else
        {
            constraintLayoutProgressBar.setVisibility(ProgressBar.INVISIBLE);
            constraintLayoutPrevisao.setVisibility(ProgressBar.VISIBLE);
            buttonAdicionar.setVisibility(Button.VISIBLE);
        }
    }
}
