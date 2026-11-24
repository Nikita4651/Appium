package netology;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class SampleTest {

    enum Platform {Android, IOS}
    Platform platform = Platform.Android;
    //Platform platform = Platform.IOS;

    MobileObjects mobileObjects;
    private AppiumDriver driver;

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    String textToSet = "Netology";
    String textEmpty = "  ";

    @BeforeEach
    public void setUp() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        if (platform == Platform.Android) {
            desiredCapabilities.setCapability("platformName", "Android");
            desiredCapabilities.setCapability("appium:deviceName", "Some name");
            desiredCapabilities.setCapability("appium:appPackage", "ru.netology.testing.uiautomator");
            desiredCapabilities.setCapability("appium:appActivity", "ru.netology.testing.uiautomator.MainActivity");
            desiredCapabilities.setCapability("appium:automationName", "uiautomator2");

            driver = new AndroidDriver(getUrl(), desiredCapabilities);
        } else if (platform == Platform.IOS) {
            // настройки для iOS

            driver = new IOSDriver(getUrl(), desiredCapabilities);
        }
        mobileObjects = new MobileObjects(driver);
    }

    @Test
    public void emptyStringTest() {

        mobileObjects.changeText(textToSet);


        mobileObjects.changeText(textEmpty);

        mobileObjects.textView.isDisplayed();

        Assertions.assertEquals(textToSet, mobileObjects.textView.getText());
    }

    @Test
    public void newActivityTest() {

        mobileObjects.newActivity(textToSet);

        mobileObjects.text.isDisplayed();

        Assertions.assertEquals(textToSet, mobileObjects.text.getText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
