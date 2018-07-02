package fidencio.jefferson.hbsisteste.models;

import java.util.List;

public class CityFiveDaysForecast {

    private long cod;
    private float message;
    private long cnt;
    private List<TimeForecast> list;
    private City city;

    public List<TimeForecast> getList() {
        return list;
    }
}
