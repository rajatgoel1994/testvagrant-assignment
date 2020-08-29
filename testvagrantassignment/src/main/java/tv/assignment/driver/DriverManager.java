package tv.assignment.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

/*
The class DriverManager create a ThreadLocal for the WebDriver instance,
to make sure there's no conflict when we run it in parallel.
 */
@Slf4j
public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driver) {
        DriverManager.driver.set(driver);
    }

    public static void quit() {
        DriverManager.driver.get().quit();
        driver.remove();
    }
}
