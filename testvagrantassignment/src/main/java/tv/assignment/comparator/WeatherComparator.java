package tv.assignment.comparator;

import tv.assignment.base.Base;
import tv.assignment.model.WeatherConditions;

import java.util.Comparator;

public class WeatherComparator extends Base {

    public Comparator<WeatherConditions> windSpeedComparator = ((o1, o2) -> {
        float absValue = Math.abs(o1.getWind() - o2.getWind());
        float windSpeedVariance = Float.parseFloat(prop.getProperty("windVariance"));
        return (absValue >= 0 && absValue <= windSpeedVariance) ? 0 : 1;
    });

    public Comparator<WeatherConditions> humidityComparator = ((o1, o2) -> {
        float absValue = Math.abs(o1.getHumidity() - o2.getHumidity());
        float humidityVariance = Float.parseFloat(prop.getProperty("humidityVariancePercentage")) / 100;
        return (absValue >= 0 && absValue <= humidityVariance) ? 0 : 1;
    });

    public Comparator<WeatherConditions> tempComparatorDegrees = ((o1, o2) -> {
        float absValue = Math.abs(o1.getTemp_in_degrees() - o2.getTemp_in_degrees());
        float tempVariance = Float.parseFloat(prop.getProperty("tempVariance"));
        return (absValue >= 0 && absValue <= tempVariance) ? 0 : 1;
    });

    public Comparator<WeatherConditions> tempComparatorFahrenheit = ((o1, o2) -> {
        float absValue = Math.abs(o1.getTemp_in_fahrenheit() - o2.getTemp_in_fahrenheit());
        float tempVariance = Float.parseFloat(prop.getProperty("tempVariance"));
        return (absValue >= 0 && absValue <= tempVariance) ? 0 : 1;
    });
}
