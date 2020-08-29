package tv.assignment.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tv.assignment.base.Base;
import tv.assignment.pages.HomePage;
import tv.assignment.pages.WeatherPage;

public class ValidateWeatherInfoTests extends Base {

    HomePage homePage;
    WeatherPage weatherPage;

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
        Assert.assertTrue(weatherPage.IsCityAndTemperatureInfoDisplayedOnMap(prop.getProperty("city")));
    }

    @Test(priority = 2)
    public void validateCityAndWeatherDetailsOnPopup() {
        selectsearchedCity();
        weatherPage.clickCityTextOnMap(prop.getProperty("city"));
        Assert.assertTrue(weatherPage.IsWeatherPopupDisplayed());
        Assert.assertTrue(weatherPage.IsWeatherConditionDisplayedInsidePopup(prop.getProperty("city")));
    }
}
