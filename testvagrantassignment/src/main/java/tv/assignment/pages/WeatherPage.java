package tv.assignment.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tv.assignment.base.Base;
import tv.assignment.driver.DriverManager;
import tv.assignment.model.WeatherConditions;
import tv.assignment.util.TestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class WeatherPage extends Base {

    @FindBy(id = "searchBox")
    private WebElement citySearchBox;
    @FindBy(xpath = "//div[text()='Current weather conditions in your city.']")
    private WebElement currentWeatherConditionText;
    @FindBy(css = ".leaflet-popup-content")
    private WebElement weatherInfoPopup;
    @FindBy(css = ".leaflet-popup-content span.heading")
    private List<WebElement> weatherConditionsInsidePopup;
    @FindBy(css = ".leaflet-popup-content span+span")
    private WebElement cityTextInsidePopup;

    public WeatherPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    /*
        Search city in search box and then checks if city is already selected and if not then
        select city from the city list.
     */
    public void searchAndSelectCity(String searchText, String cityName) {
        TestUtil.waitForVisibiltyOfElement(By.xpath("//div[text()='New Delhi']"));
        TestUtil.waitForVisibiltyOfElement(currentWeatherConditionText);
        TestUtil.enterText(citySearchBox, searchText);
        TestUtil.waitForVisibiltyOfElement(
                DriverManager.getDriver().findElement(By.xpath("//label[@for ='" + cityName + "']")));
        TestUtil.waitForVisibiltyOfElement(DriverManager.getDriver().findElement(By.id(cityName)));
        WebElement city = DriverManager.getDriver().findElement(By.id(cityName));
        if (!city.isSelected()) {
            TestUtil.waitAndClickElement(city);
        } else {
            log.info(cityName + " is already Selected");
        }
    }

    /*
        Validate city selected and temperature info is displayed on Map.
        Return true if info is displayed and false if info is not displayed on map.
     */
    public boolean IsCityAndTemperatureInfoDisplayedOnMap(String city) {
        String cityTitle = "//div[@title='" + city + "']";
        String temperatureTextInCelcius = cityTitle + "/div/span[@class='tempRedText']";
        String temperatureTextInFarhenite = cityTitle + "/div/span[@class='tempWhiteText']";
        String cityText = cityTitle + "/div[2]";
        try {
            TestUtil.waitForVisibiltyOfElement(DriverManager.getDriver().findElement(By.xpath(cityTitle)));
            WebElement cityTitleOnMap = DriverManager.getDriver().findElement(By.xpath(cityTitle));
            if (cityTitleOnMap.isDisplayed()) {
                DriverManager.getDriver().findElement(By.xpath(temperatureTextInCelcius)).isDisplayed();
                DriverManager.getDriver().findElement(By.xpath(temperatureTextInFarhenite)).isDisplayed();
                DriverManager.getDriver().findElement(By.xpath(cityText)).getText().equals(city);
            }
        } catch (Exception e) {
            log.error("City Title is not displayed", e);
            return false;
        }
        return true;
    }

    /*
        Click city on map.
     */
    public void clickCityTextOnMap(String cityName) {
        String cityText = "//div[@title='" + cityName + "']/div[2]";
        TestUtil.waitForVisibiltyOfElement(DriverManager.getDriver().findElement(By.xpath(cityText)));
        WebElement cityTextElement = DriverManager.getDriver().findElement(By.xpath(cityText));
        TestUtil.waitAndClickElement(cityTextElement);
    }

    /*
        Validate Popup is displayed after clicking city text on map.
     */
    public boolean IsWeatherPopupDisplayed() {
        try {
            TestUtil.waitForVisibiltyOfElement(weatherInfoPopup);
            if (!weatherInfoPopup.isDisplayed())
                return false;
        } catch (Exception e) {
            log.error("Weather Popup is not displayed", e);
        }
        return true;
    }

    /*
        Validate city and weather conditions are displayed inside popup.
        Return true if city contains the text(same which was selected on map) and
        weather conditions size is equal to 5.
     */
    public boolean IsWeatherConditionDisplayedInsidePopup(String cityName) {
        TestUtil.waitForVisibiltyOfElement(cityTextInsidePopup);
        TestUtil.waitForVisibiltyOfElement(weatherConditionsInsidePopup.get(0));
        if (weatherConditionsInsidePopup.size() == 5 && cityTextInsidePopup.getText().contains(cityName))
            return true;
        else
            return false;
    }

    /*
       Store different weather Conditions from popup in map and then
       Deserializes the specified Json into an object of the specified class.
       Return an object of specified class.
     */
    public WeatherConditions storeWeatherConditionsFromPopup() {
        Map<String, String> weather = new HashMap<String, String>();
        for (WebElement weatherCondition : weatherConditionsInsidePopup) {
            String[] arr = weatherCondition.getText().split(":");
            String key = arr[0].trim();
            String value = arr[1].trim();
            if (key.contains("Condition")) {
                weather.put(key.toLowerCase(), value);
            } else if (key.contains("Humidity")) {
                weather.put(key.toLowerCase(), value.replace("%", ""));
            } else {
                weather.put(key.toLowerCase().replaceAll(" ", "_"), value.split(" ")[0]);
            }
        }
        return TestUtil.convertJSONToObject(weather, WeatherConditions.class);
    }
}
