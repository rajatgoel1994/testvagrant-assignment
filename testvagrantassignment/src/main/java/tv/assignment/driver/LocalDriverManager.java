package tv.assignment.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class LocalDriverManager implements IDriver {

    public WebDriver createInstance(String browser) {
        WebDriver driverInstance = null;
        try {
            log.info("Driver browser to initialize is: " + browser);
            DriverManagerType driverManagerType = DriverManagerType.valueOf(browser.toUpperCase());
            Class<?> driverClass = Class.forName(driverManagerType.browserClass());
            WebDriverManager.getInstance(driverManagerType).setup();
            driverInstance = (WebDriver) driverClass.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | ClassNotFoundException e) {
            log.error("The class could not be found", e);
        } catch (Exception e) {
            log.error("Problem during driver instantiation", e);
        }
        return driverInstance;
    }

}
