package tv.assignment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class WeatherConditions {

    private String condition;
    private float wind;
    private float humidity;
    private float temp_in_degrees;
    private float temp_in_fahrenheit;

}
