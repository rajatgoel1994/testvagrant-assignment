package tv.assignment.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import tv.assignment.driver.DriverManager;
import tv.assignment.driver.LocalDriverManager;
import tv.assignment.util.TestUtil;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Base {

    public static Properties prop;
    public static int PAGE_LOAD_TIMEOUT;
    public static int EXPLICIT_WAIT;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) throws IOException {
        prop = TestUtil.readFromConfig(String
                .format("%s/src/main/resources/config.properties", System.getProperty("user.dir")));
        String url = prop.getProperty("url");
        PAGE_LOAD_TIMEOUT = Integer.parseInt(prop.getProperty("PAGE_LOAD_TIMEOUT"));
        EXPLICIT_WAIT = Integer.parseInt(prop.getProperty("EXPLICIT_WAIT"));
        DriverManager.setDriver(new LocalDriverManager().createInstance(browser));
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        DriverManager.getDriver().get(url);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quit();
    }
}
