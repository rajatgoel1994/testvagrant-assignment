package tv.assignment.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;

public class LocalDriverManager implements IDriver {

    public WebDriver createInstance(String browser) {
        WebDriver driverInstance = null;
        try {
            DriverManagerType driverManagerType = DriverManagerType.valueOf(browser.toUpperCase());
            Class<?> driverClass = Class.forName(driverManagerType.browserClass());
            WebDriverManager.getInstance(driverManagerType).setup();
            driverInstance = (WebDriver) driverClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Problem during driver instantiation" + e);
        }
        return driverInstance;
    }

}
