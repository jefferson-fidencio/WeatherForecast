package fidencio.jefferson.hbsisteste.models;

import java.io.Serializable;

public class Weather implements Serializable {

    private long id;
    private String main;
    private String description;
    private String icon;

    public String getMain() {
        return main;
    }

    public String getIcon() {
        return icon;
    }
}
