package ru.topacademy;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public static String getWeather(String message, Model model) throws IOException {
        //462d93bba72a6c93cf2c06894dbee11f

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message +"&appid=462d93bba72a6c93cf2c06894dbee11f&units=metric");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()){
            result += in.nextLine();
        }
        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray jsonArray = object.getJSONArray("weather");
        for (int i=0;i<jsonArray.length();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }
        return "Город: " + model.getName() + "\n" +
               "Темпиратура: " + model.getTemp() + "C\n" +
               "Влажность: " + model.getHumidity() + "%\n" +
               "Тип погоды: " + model.getMain() + "\n" +
               "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }


}
