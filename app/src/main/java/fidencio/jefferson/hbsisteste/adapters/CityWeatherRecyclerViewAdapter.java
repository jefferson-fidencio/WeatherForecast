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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fidencio.jefferson.hbsisteste.R;
import fidencio.jefferson.hbsisteste.enums.ListSortOption;
import fidencio.jefferson.hbsisteste.models.CityWeather;
import fidencio.jefferson.hbsisteste.services.ServerConnection;

public class CityWeatherRecyclerViewAdapter extends RecyclerView.Adapter<CityWeatherRecyclerViewAdapter.ViewHolder> {

    private static final String URI_GET_WEATHER_ICON = "http://openweathermap.org/img/w/";
    private List<CityWeather> cityWeatherList;
    private OnItemClickListener listener;
    private static Context context;

    public CityWeatherRecyclerViewAdapter(List<CityWeather> cityWeathers, OnItemClickListener listener){
        this.cityWeatherList = cityWeathers;
        this.listener = listener;
    }

    @Override
    public CityWeatherRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(cityWeatherList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return cityWeatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCityTempName;
        public TextView textViewCityWeatherMainDescription;
        public TextView textViewCityMax;
        public TextView textViewCityMin;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCityTempName = itemView.findViewById(R.id.textViewCityTempName);
            textViewCityWeatherMainDescription = itemView.findViewById(R.id.textViewCityWeatherMainDescription);
            textViewCityMax = itemView.findViewById(R.id.textViewCityMax);
            textViewCityMin = itemView.findViewById(R.id.textViewCityMin);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(final CityWeather cityWeather, final OnItemClickListener listener) {
            textViewCityTempName.setText(cityWeather.getMain().getTemp() + " °C em " + cityWeather.getName());
            textViewCityWeatherMainDescription.setText(cityWeather.getWeather().get(0).getMain());
            textViewCityMax.setText("Max: " + String.valueOf(cityWeather.getMain().getTemp_max()) + " °C");
            textViewCityMin.setText("Min: " + String.valueOf(cityWeather.getMain().getTemp_min()) + " °C");
            ServerConnection.getInstance(context).getImageLoader().get(URI_GET_WEATHER_ICON +  cityWeather.getWeather().get(0).getIcon() + ".png", new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    imageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }, 64, 64);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(cityWeather);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CityWeather item);
    }

    public void sortDataset(ListSortOption listSortOption){
        switch (listSortOption){
            case nome:
                Collections.sort(cityWeatherList, new Comparator<CityWeather>() {
                    @Override
                    public int compare(CityWeather o1, CityWeather o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                break;
            case temp_min:
                Collections.sort(cityWeatherList, new Comparator<CityWeather>() {
                    @Override
                    public int compare(CityWeather o1, CityWeather o2) {
                        if (o1.getMain().getTemp() < o2.getMain().getTemp())
                            return -1;
                        else
                            return 1;
                    }
                });
                break;
            case temp_max:
                Collections.sort(cityWeatherList, new Comparator<CityWeather>() {
                    @Override
                    public int compare(CityWeather o1, CityWeather o2) {
                        if (o1.getMain().getTemp() > o2.getMain().getTemp())
                            return -1;
                        else
                            return 1;
                    }
                });
                break;
        }
    }
}
