package tv.assignment.Tests;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import tv.assignment.api.WeatherConditionsAPI;
import tv.assignment.base.Base;
import tv.assignment.comparator.WeatherComparator;
import tv.assignment.customexception.MatcherException;
import tv.assignment.model.WeatherConditions;
import tv.assignment.pages.HomePage;
import tv.assignment.pages.WeatherPage;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Slf4j
public class ValidateWeatherInfoTests extends Base {

    HomePage homePage;
    WeatherPage weatherPage;
    WeatherComparator weatherComparator;
    private WeatherConditions weatherConditionsWeb, weatherConditionsAPI;

    /*
    Tests are not dependent on one another and can be run independently.
    Common steps are segregated inside method to reduce duplicate code and increase reusability.
    Below method can be called inside tests and will navigate to a particular state on site
    and from there tests can perform different actions as per their need.
     */
    public void selectsearchedCity() {
        homePage = new HomePage();
        weatherPage = new WeatherPage();
        homePage.clickWeatherLink();
        weatherPage.searchAndSelectCity(prop.getProperty("searchcitytext"), prop.getProperty("city"));
    }

    @Test(priority = 1)
    public void validateCityAndTemperatureOnMap() {
        selectsearchedCity();
        log.info("Validating city and temperature on map");
        assertTrue(weatherPage.IsCityAndTemperatureInfoDisplayedOnMap(prop.getProperty("city")));
    }

    @Test(priority = 2)
    public void validateCityAndWeatherDetailsOnPopup() {
        selectsearchedCity();
        log.info("Clicking city text on map");
        weatherPage.clickCityTextOnMap(prop.getProperty("city"));
        assertTrue(weatherPage.IsWeatherPopupDisplayed());
        assertTrue(weatherPage.IsWeatherConditionDisplayedInsidePopup(prop.getProperty("city")));
    }

    @Test(priority = 3)
    public void compareWeatherObjectsWithVariance() {
        selectsearchedCity();
        weatherPage.clickCityTextOnMap(prop.getProperty("city"));
        weatherConditionsWeb = weatherPage.storeWeatherConditionsFromPopup();
        log.info("Weather Conditions on Web are: " + weatherConditionsWeb.toString());
        weatherConditionsAPI = new WeatherConditionsAPI().getWeatherDetails();
        log.info("Weather Conditions on API are: " + weatherConditionsAPI.toString());
        boolean isConditionMatched = weatherConditionsWeb.getCondition().equals(weatherConditionsAPI.getCondition()) ? true
                : false;
        weatherComparator = new WeatherComparator();
        boolean isWindSpeedMatched = weatherComparator.windSpeedComparator.compare(weatherConditionsWeb, weatherConditionsAPI) == 0;
        boolean isTempInDegreeMatched = weatherComparator.tempComparatorDegrees.compare(weatherConditionsWeb, weatherConditionsAPI) == 0;
        boolean isTempInFarhenheitMatched = weatherComparator.tempComparatorFahrenheit.compare(weatherConditionsWeb, weatherConditionsAPI) == 0;
        boolean isHumidityMatched = weatherComparator.humidityComparator.compare(weatherConditionsWeb, weatherConditionsAPI) == 0;
        if (isConditionMatched && isWindSpeedMatched && isTempInDegreeMatched && isTempInFarhenheitMatched
                && isHumidityMatched) {
            assertTrue(true);
            log.info("Weather objects are equal");
        } else {
            try {
                throw new MatcherException(isConditionMatched, isWindSpeedMatched, isTempInDegreeMatched,
                        isTempInFarhenheitMatched, isHumidityMatched);
            } catch (MatcherException m) {
                log.error("Weather objects are not equal", m.getMessage());
                fail(m.getMessage());
            }
        }
    }
}
