package fidencio.jefferson.hbsisteste.models;

import java.io.Serializable;

public class MainAttributesWeather implements Serializable {

    private float temp;
    private float temp_min;
    private float temp_max;

    public float getTemp() {
        return temp;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }
}
