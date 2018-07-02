package fidencio.jefferson.hbsisteste.models;

import java.util.List;

public class TimeForecast {
    private MainForecast main;
    private List<Weather> weather;
    private String dt_txt;

    public String getDt_txt() {
        return dt_txt;
    }

    public MainForecast getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }
}
