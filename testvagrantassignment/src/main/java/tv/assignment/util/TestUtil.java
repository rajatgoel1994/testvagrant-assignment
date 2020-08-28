package tv.assignment.util;

import com.google.gson.Gson;
import groovy.json.JsonOutput;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tv.assignment.base.Base;
import tv.assignment.driver.DriverManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestUtil extends Base {

    public static Properties readFromConfig(String path) throws IOException {
        FileInputStream fp = new FileInputStream(path);
        Properties prop = new Properties();
        prop.load(fp);
        return prop;

    }

    public static void waitAndClickElement(WebElement element) {
        try {
            waitForElement(element, ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            javascriptClick(element);
        }

    }

    public static void waitForElement(WebElement element, ExpectedCondition<WebElement> expectedCondition) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Base.EXPLICIT_WAIT);
        wait.until(expectedCondition);
    }

    public static void waitForVisibiltyOfElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Base.EXPLICIT_WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForVisibiltyOfElement(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Base.EXPLICIT_WAIT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void javascriptClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public static void enterText(WebElement element, String message) {
        element.click();
        element.clear();
        element.sendKeys(message);
    }

}
