package fidencio.jefferson.hbsisteste.models;

import java.io.Serializable;
import java.util.List;

public class CityWeather implements Serializable {

    private List<Weather> weather;
    private MainAttributesWeather main;
    private long id;
    private String name;
    private long cod;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public MainAttributesWeather getMain() {
        return main;
    }
}
