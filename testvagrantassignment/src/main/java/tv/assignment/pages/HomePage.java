package tv.assignment.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tv.assignment.base.Base;
import tv.assignment.driver.DriverManager;
import tv.assignment.util.TestUtil;

@Slf4j
public class HomePage extends Base {

    @FindBy(linkText = "No Thanks")
    private WebElement noThanksLink;
    @FindBy(id = "h_sub_menu")
    private WebElement subMenu;
    @FindBy(linkText = "WEATHER")
    private WebElement weather;

    public HomePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public void clickWeatherLink() {
        TestUtil.waitForVisibiltyOfElement(noThanksLink);
        TestUtil.waitAndClickElement(noThanksLink);
        TestUtil.waitAndClickElement(subMenu);
        TestUtil.waitAndClickElement(weather);
    }


}
