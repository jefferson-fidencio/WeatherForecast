package fidencio.jefferson.hbsisteste.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.SimpleDateFormat;

import fidencio.jefferson.hbsisteste.R;
import fidencio.jefferson.hbsisteste.models.CityFiveDaysForecast;
import fidencio.jefferson.hbsisteste.models.CityWeather;
import fidencio.jefferson.hbsisteste.models.TimeForecast;
import fidencio.jefferson.hbsisteste.services.ServerConnection;

public class CityForecastRecyclerViewAdapter extends RecyclerView.Adapter<CityForecastRecyclerViewAdapter.ViewHolder> {

    private static final String URI_GET_WEATHER_ICON = "http://openweathermap.org/img/w/";
    private CityFiveDaysForecast cityFiveDaysForecast;
    private static Context context;

    public CityForecastRecyclerViewAdapter(CityFiveDaysForecast cityFiveDaysForecast){
        this.cityFiveDaysForecast = cityFiveDaysForecast;
    }

    @Override
    public CityForecastRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(cityFiveDaysForecast.getList().get(position));
    }

    @Override
    public int getItemCount() {
        return cityFiveDaysForecast.getList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCityDate;
        public TextView textViewCityWeatherMainDescription;
        public TextView textViewCityMax;
        public TextView textViewCityMin;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCityDate = itemView.findViewById(R.id.textViewCityTempName);
            textViewCityWeatherMainDescription = itemView.findViewById(R.id.textViewCityWeatherMainDescription);
            textViewCityMax = itemView.findViewById(R.id.textViewCityMax);
            textViewCityMin = itemView.findViewById(R.id.textViewCityMin);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(final TimeForecast timeForecast) {
            textViewCityDate.setText(String.valueOf(timeForecast.getDt_txt()));
            textViewCityWeatherMainDescription.setText(timeForecast.getMain().getTemp() + " °C - " + timeForecast.getWeather().get(0).getMain());
            textViewCityMax.setText("Max: " + String.valueOf(timeForecast.getMain().getTemp_max()) + " °C");
            textViewCityMin.setText("Min: " + String.valueOf(timeForecast.getMain().getTemp_min()) + " °C");
            ServerConnection.getInstance(context).getImageLoader().get(URI_GET_WEATHER_ICON +  timeForecast.getWeather().get(0).getIcon() + ".png", new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    imageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }, 64, 64);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CityWeather item);
    }
}
