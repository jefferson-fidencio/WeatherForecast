package fidencio.jefferson.hbsisteste;

import android.app.Application;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import fidencio.jefferson.hbsisteste.enums.ListAction;
import fidencio.jefferson.hbsisteste.models.CityWeather;

public class HbsisApp extends Application {

    public static final String OPEN_WEATHER_API_KEY = "4946df1ffdf27822ab21fcab61003ce2";

    private static List<CityWeather> addedCities = new ArrayList<>();

    public static void updateSavedCities(ListAction listAction, CityWeather cityWeather, Context context) {
        switch (listAction){
            case create:
                addedCities.add(cityWeather);
                break;
        }
        updateCacheFile(context);
    }

    private static void updateCacheFile(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("hbsiscachefile", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(addedCities);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readCacheFile(Context context){
        try {
        FileInputStream fis = context.openFileInput("hbsiscachefile");
        ObjectInputStream is = new ObjectInputStream(fis);
        addedCities = (List<CityWeather>) is.readObject();
        is.close();
        fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<CityWeather> getAddedCities() {
        return addedCities;
    }
}
