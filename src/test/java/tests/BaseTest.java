package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.*;
import constants.Urls;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;
    protected HomePageScooter homePage;
    protected ScooterOrderPage scooterOrder;
    protected OrderDetailsPage orderDetails;

    // метод для старта драйвера хром
    @Before
    public void startDriverChrome() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        homePage = new HomePageScooter(driver);
        scooterOrder = new ScooterOrderPage(driver);
        orderDetails = new OrderDetailsPage(driver);
        //открыть домашнюю страницу
        driver.get(Urls.BASE_URL);
        // клик по кнопке принять кукис да все привыкли
        homePage.acceptCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
