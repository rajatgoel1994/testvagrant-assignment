package tv.assignment.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tv.assignment.base.Base;
import tv.assignment.pages.HomePage;
import tv.assignment.pages.WeatherPage;

public class ValidateWeatherInfoTests extends Base {

    HomePage homePage;
    WeatherPage weatherPage;

    public void selectsearchedCity() {
        homePage = new HomePage();
        weatherPage = new WeatherPage();
        homePage.clickWeatherLink();
        weatherPage.searchAndSelectCity(prop.getProperty("searchcitytext"), prop.getProperty("city"));
    }

    @Test(priority = 1)
    public void validateCityAndTempeartureInfoIsDisplayed() {
        selectsearchedCity();
        Assert.assertTrue(weatherPage.IsCityAndTemperatureInfoDisplayedOnMap(prop.getProperty("city")));
    }
}
