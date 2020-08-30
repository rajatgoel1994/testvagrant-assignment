package tv.assignment.api;

import java.util.HashMap;
import java.util.Map;
import io.restassured.http.Method;
import io.restassured.response.Response;
import tv.assignment.base.Base;
import tv.assignment.model.WeatherConditions;
import tv.assignment.util.TestUtil;

public class WeatherConditionsAPI extends Base {

    private APIFunctions apiFunctions;
    private Response response;

    public void init(String cityName) {
        apiFunctions = new APIFunctions(prop.getProperty("baseUrl"), prop.getProperty("basePath"));
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("appid", prop.getProperty("APIKey"));
        queryParams.put("q", cityName);
        apiFunctions.set_query_params(queryParams);
    }

    public WeatherConditions getWeatherDetails() {
        init(prop.getProperty("city"));
        response = apiFunctions.get_response(Method.GET, prop.getProperty("getWeather"));
        response.then().statusCode(200);

        String tempDegrees = response.jsonPath().getString("main.temp");
        double tempFarhenites = Float.parseFloat(tempDegrees) * 1.8 + 32;
        Map<String, String> weatherResponseMap = new HashMap<String, String>();
        weatherResponseMap.put("condition", response.jsonPath().getString("weather[0].main"));
        weatherResponseMap.put("wind", response.jsonPath().getString("wind.speed"));
        weatherResponseMap.put("temp_in_degrees", tempDegrees);
        weatherResponseMap.put("temp_in_fahrenheit", String.format("%.2f", tempFarhenites));
        weatherResponseMap.put("humidity", response.jsonPath().getString("main.humidity"));
        return TestUtil.convertJSONToObject(weatherResponseMap, WeatherConditions.class);
    }
}
