package tv.assignment.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tv.assignment.base.Base;
import tv.assignment.driver.DriverManager;
import tv.assignment.util.TestUtil;

public class WeatherPage extends Base {

    @FindBy(id = "searchBox")
    private WebElement citySearchBox;
    @FindBy(xpath = "//div[text()='Current weather conditions in your city.']")
    private WebElement currentWeatherConditionText;

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
            System.out.println("City is already Selected");
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
            System.out.println("City Title is not displayed");
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
